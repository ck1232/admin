package com.admin.productmgmt.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.admin.dao.ProductDAO;
import com.admin.filemgmt.service.ImageService;
import com.admin.filemgmt.vo.ImageLinkVO;
import com.admin.helper.GeneralUtils;
import com.admin.productmanagement.vo.ProductOptionVO;
import com.admin.productmanagement.vo.ProductSubCategoryVO;
import com.admin.productmanagement.vo.ProductSubOptionVO;
import com.admin.productmanagement.vo.ProductVO;
import com.admin.productoptionmgmt.service.ProductOptionMgmtService;
import com.admin.to.ProductSubOptionTO;
import com.admin.to.ProductTO;

@Service
@Scope("prototype")
@Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.READ_UNCOMMITTED)
public class ProductMgmtService {
	private static final Logger logger = Logger.getLogger(ProductMgmtService.class);
	private ProductDAO productDAO;
	private ImageService imageService;
	private ProductTagsService productMgmtService;
	private ProductSpecificationMgmtService productSpecificationMgmtService;
	private ProductSubOptionMgmtService productSubOptionMgmtService;
	private ProductAttributeService productAttributeMgmtService;
	private static final int code_limit = 6;
	@Autowired
	public ProductMgmtService(ProductDAO productDAO, 
			ImageService imageService,
			ProductTagsService productTagsMgmtService,
			ProductSpecificationMgmtService productSpecificationMgmtService,
			ProductSubOptionMgmtService productSubOptionMgmtService,
			ProductAttributeService productAttributeMgmtService) {
		this.productDAO = productDAO;
		this.imageService = imageService;
		this.productMgmtService = productTagsMgmtService;
		this.productSpecificationMgmtService = productSpecificationMgmtService;
		this.productSubOptionMgmtService = productSubOptionMgmtService;
		this.productAttributeMgmtService = productAttributeMgmtService;
	}

	public List<ProductVO> getProductList(List<Long> productIdList) {
		List<ProductTO> productTOList = null;
		if(productIdList == null){
			productTOList = productDAO.findByDeleteInd(GeneralUtils.NOT_DELETED);
		}else if (!productIdList.isEmpty()){
			productTOList = productDAO.findByProductIdInAndDeleteInd(productIdList, GeneralUtils.NOT_DELETED);
		}
		List<ProductVO> productVOList = convertToProductVOList(productTOList);
		return productVOList;
	}
	
	private List<ProductVO> convertToProductVOList(List<ProductTO> productList/*, Map<Long, LinkedList<ImageLinkVO>> imageMap*/) {
		List<ProductVO> voList = new ArrayList<ProductVO>();
		if(productList != null && productList.size() > 0){
			for(ProductTO to : productList){
				ProductVO vo = new ProductVO();
				vo.setDescription(to.getDescription());
				vo.setPaypalId(to.getPaypalId());
				vo.setProductCode(to.getProductCode());
				vo.setProductId(to.getProductId());
				vo.setProductName(to.getProductName());
				vo.setSubCategoryId(to.getSubCategoryId());
				vo.setUnitAmt(to.getUnitAmt());
				vo.setWeight(to.getWeight());
				
				//set attribute
				vo.setProductAttributeVOList(ProductAttributeService.convertToProductAttributeVOList(to.getProductAttributeTOList()));
				//set tags
				vo.setTags(ProductTagsService.convertToProductTagsVOList(to.getProductTagsTOList(), vo.getProductId()));
				
				//set image
				LinkedList<ImageLinkVO> imageList = ImageService.convertToNewImageLinkVOMapOrdered(to.getImageLinkRsTOList());
				vo.setImagesLink(imageList);
				
				//set specification
				vo.setProductInfo(to.getProductSpecificationTO()==null?null:to.getProductSpecificationTO().getContent());
				
				//set option
				Map<Long, ProductOptionVO> optionMap = new HashMap<Long, ProductOptionVO>();
				List<ProductSubOptionTO> subOptionTOList = to.getProductionSubOptionTOList();
				if(subOptionTOList != null && !subOptionTOList.isEmpty()){
					for(ProductSubOptionTO subOptionTO : subOptionTOList){
						List<ProductSubOptionVO> subOptionList = ProductOptionMgmtService.convertToProductSubOptionVOList(Arrays.asList(subOptionTO));
						if(subOptionTO.getProductOptionTO() != null){
							List<ProductOptionVO> optionList = ProductOptionMgmtService.convertToProductOptionVOList(Arrays.asList(subOptionTO.getProductOptionTO()));
							if(optionList != null && !optionList.isEmpty()){
								ProductOptionVO optionVO = optionList.get(0);
								ProductSubOptionVO subOptionVO = subOptionList.get(0);
								if(!optionMap.containsKey(optionVO.getProductOptionId())){
									optionMap.put(optionVO.getProductOptionId(), optionVO);
								}
								optionMap.get(subOptionVO.getProductOptionId()).getSubOptionList().add(subOptionVO);
							}
							
						}
					}
					vo.setOptionList(new ArrayList<ProductOptionVO>(optionMap.values()));
				}
				
				GeneralUtils.copyFromTO(vo, to);
				voList.add(vo);
			}
		}
		return voList;
	}

	public ImageLinkVO getCoverImageByProductCode(String productCode) {
		ProductTO productTO = productDAO.findByProductCodeAndDeleteInd(productCode, GeneralUtils.NOT_DELETED);
		if(productTO != null){
			LinkedList<ImageLinkVO> list = ImageService.convertToNewImageLinkVOMapOrdered(productTO.getImageLinkRsTOList());
			if(list != null && !list.isEmpty()){
				ImageLinkVO imageLinkVO = list.get(0);
				imageLinkVO = imageService.readImageFromURL(imageLinkVO);
				return imageLinkVO;
			}
		}
		return null;
	}
	
	public static ProductTO convertToProductTO(ProductVO vo){
		ProductTO productTO = new ProductTO();
		productTO.setProductName(vo.getProductName());
		productTO.setProductCode(vo.getProductCode());
		productTO.setProductId(vo.getProductId());
		productTO.setUnitAmt(vo.getUnitAmt());
		productTO.setWeight(vo.getWeight());
		productTO.setSubCategoryId(vo.getSubCategoryId());
		GeneralUtils.copyFromVO(vo, productTO);
		return productTO;
	}
	
	private ProductVO generateNewProductCode(ProductVO product){
		if(product.getProductCode() != null && !product.getProductCode().trim().isEmpty())return product;
		List<String> productCodeList = GeneralUtils.convertListToStringList(productDAO.findAll(), "productCode", true);
		String productCode = "";
		if(product.getProductName() == null || product.getProductName().trim().isEmpty()) return product;
		String noSpaceName = product.getProductName().replaceAll(" ", "");
		if(noSpaceName.length() > code_limit){
			productCode = noSpaceName.substring(0, code_limit);
		}else{
			productCode = noSpaceName;
		}
		int counter = 1;
		while(productCodeList.contains(productCode)){
			productCode = productCode+counter++;
		}
		product.setProductCode(productCode);
		return product;
	}
	
	public void saveAddProduct(ProductVO newProductVO){
		
		ProductTO newProductTO = convertToProductTO(generateNewProductCode(newProductVO));
		
		//save into product table and retrieve productId
		if(newProductVO.getProductId() != null){
			List<ProductTO> toList = productDAO.findByProductIdInAndDeleteInd(Arrays.asList(newProductVO.getProductId()), GeneralUtils.NOT_DELETED);
			if(toList != null && !toList.isEmpty()){
				ProductTO to = toList.get(0);
				newProductTO.setImageLinkRsTOList(to.getImageLinkRsTOList());
				newProductTO.setProductSpecificationTO(to.getProductSpecificationTO());
				newProductTO.setProductionSubOptionTOList(to.getProductionSubOptionTOList());
				newProductTO.setProductAttributeTOList(to.getProductAttributeTOList());
			}
		}
		//product tags
		productMgmtService.setProductTagsTO(newProductVO.getTags(), newProductTO);
		
		//product info
		productSpecificationMgmtService.setProductSpecification(newProductVO.getProductInfo(), newProductTO);
		
		//image
		newProductVO.setImagesLink(ImageService.convertFileMetaVOListToImageLinkVOList(newProductVO.getImages(), GeneralUtils.TYPE_PRODUCT));
		imageService.setProductImage(newProductVO.getImagesLink(), newProductTO);
		
		//proudct suboption
		productSubOptionMgmtService.setProductSubOption(newProductVO.getOptionList(), newProductTO);
		
		//product attribute
		productAttributeMgmtService.setProductAttribute(newProductVO.getProductAttributeVOList(), newProductTO);
		productDAO.save(newProductTO);
	}

	public void deleteProduct(List<Long> ids) {
		List<ProductTO> list = productDAO.findByProductIdInAndDeleteInd(ids, GeneralUtils.NOT_DELETED);
		if(list != null && !list.isEmpty()){
			for(ProductTO product : list){
				product.setDeleteInd(GeneralUtils.DELETED);
			}
			productDAO.save(list);
		}
	}
	
	public List<ProductVO> getProductVOBySubCategory(List<ProductSubCategoryVO> subCategoryList){
		List<Long> subCategoryIdList = GeneralUtils.convertListToLongList(subCategoryList, "subCategoryId");
		List<ProductTO> toList = productDAO.findBySubCategoryIdInAndDeleteInd(subCategoryIdList,GeneralUtils.NOT_DELETED);
		return convertToProductVOList(toList);
	}
}
