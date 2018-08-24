package com.admin.product.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.admin.helper.GeneralUtils;
import com.admin.product.vo.ProductAttributeVO;
import com.admin.product.vo.ProductOptionVO;
import com.admin.product.vo.ProductSubOptionVO;
import com.admin.productoption.service.ProductOptionService;
import com.admin.to.ProductAttributeTO;
import com.admin.to.ProductSubOptionTO;
import com.admin.to.ProductTO;
@Service
@Scope("prototype")
@Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class ProductAttributeServiceImpl implements Serializable, ProductAttributeService {
//	private static final Logger logger = Logger.getLogger(ProductAttributeService.class);
	private static final long serialVersionUID = 1L;
	
	private ProductOptionService productOptionService;
	
	
	@Autowired
	public ProductAttributeServiceImpl(ProductOptionService productOptionService) {
		this.productOptionService = productOptionService;
	}

	public List<ProductAttributeVO> convertToProductAttributeVOList(List<ProductAttributeTO> toList){
		List<ProductAttributeVO> voList = new ArrayList<ProductAttributeVO>();
		if(toList != null && !toList.isEmpty()){
			for(ProductAttributeTO to : toList){
				ProductAttributeVO vo = new ProductAttributeVO();
//				vo.setProductAttributeId(to.getProductAttributeId());
				vo.setProductId(to.getProductTO().getProductId());
				vo.setOptionId(to.getProductOptionTO().getProductOptionId());
				vo.setSku(to.getSku());
				vo.setSubOptionId(to.getProductSubOptionTO().getProductSuboptionId());
				vo.setDisplayInd(to.getDisplayInd());
				List<ProductOptionVO> optionList = productOptionService.convertToProductOptionVOList(Arrays.asList(to.getProductOptionTO()));
				List<ProductSubOptionVO> subOptionList = productOptionService.convertToProductSubOptionVOList(Arrays.asList(to.getProductSubOptionTO()));
				vo.setSubOptionId(subOptionList.get(0).getProductSuboptionId());
				vo.setOptionId(optionList.get(0).getProductOptionId());
				vo.setSubOptionVO(subOptionList.get(0));
				voList.add(vo);
			}
		}
		return voList;
	}

	public void setProductAttribute(List<ProductAttributeVO> productAttributeVOList, ProductTO newProductTO) {
		List<ProductAttributeTO> productAttributeTOList = convertToProductAttributeTOList(productAttributeVOList, newProductTO);
		newProductTO.setProductAttributeTOList(productAttributeTOList);
		if(newProductTO.getProductAttributeTOList() != null){
//			for(ProductAttributeTO to : newProductTO.getProductAttributeTOList()){
//				logger.debug("productAttributeTO sku: "+to.getSku() + " , subOptionId:"+ to.getProductSubOptionTO().getProductSuboptionId());
//			}
		}
	}

	public List<ProductAttributeTO> convertToProductAttributeTOList(List<ProductAttributeVO> productAttributeVOList,ProductTO newProductTO) {
		List<ProductAttributeTO> productAttributeTOList = new ArrayList<ProductAttributeTO>();
		Map<String, ProductSubOptionTO> subOptionNameMap = GeneralUtils.convertListToStringMap(newProductTO.getProductionSubOptionTOList(), "name");
		Map<String, List<ProductAttributeTO>> attributeMap = GeneralUtils.convertListToStringListMap(newProductTO.getProductAttributeTOList(), "sku");
		Map<String, Map<String, ProductAttributeTO>> attributeNameMap = new HashMap<String, Map<String,ProductAttributeTO>>();
		if(attributeMap != null){
			for(String sku : attributeMap.keySet()){
				List<ProductAttributeTO> list = attributeMap.get(sku);
				if(list != null){
					attributeNameMap.put(sku,GeneralUtils.convertListToStringMap(list, "productSubOptionTO.code"));
				}
			}
		}
		if(productAttributeVOList != null && !productAttributeVOList.isEmpty() 
				&& newProductTO.getProductionSubOptionTOList() != null 
				&& !newProductTO.getProductionSubOptionTOList().isEmpty() ){
			for(ProductAttributeVO vo : productAttributeVOList){
				//create attribute
				ProductAttributeTO to = new ProductAttributeTO();
				if(vo.getSku().startsWith("productCode-")){
					vo.setSku(vo.getSku().replaceFirst("productCode-", newProductTO.getProductCode()+"-"));
				}
				to.setSku(vo.getSku());
				to.setDisplayInd(vo.getDisplayInd());
				to.setProductTO(newProductTO);
				
				//find suboption
				ProductSubOptionTO subOptionTO = subOptionNameMap.get(vo.getSubOptionVO().getName());
				if(subOptionTO != null && subOptionTO.getProductOptionTO() != null){
					to.setProductOptionTO(subOptionTO.getProductOptionTO());
					to.setProductSubOptionTO(subOptionTO);
				}else{
					continue;
				}
				
				//find prodcut attributte, if found, update display ind
				Map<String, ProductAttributeTO> attributeOneMap = attributeNameMap.get(vo.getSku());
				if(attributeOneMap != null){
					ProductAttributeTO oneTO = attributeOneMap.get(vo.getSubOptionVO().getCode());
					if(oneTO != null){
						to.setProductAttributeId(oneTO.getProductAttributeId());
						GeneralUtils.copyFromTO(to, oneTO);
					}
				}
				productAttributeTOList.add(to);
			}
		}else{ //no suboption
			ProductAttributeTO to = new ProductAttributeTO();
			to.setSku(newProductTO.getProductCode());
			to.setDisplayInd(GeneralUtils.ALLOW_DISPLAY);
			to.setProductTO(newProductTO);
			to.setProductOptionTO(null);
			to.setProductSubOptionTO(null);
			Map<String, ProductAttributeTO> attributeOneMap = attributeNameMap.get(newProductTO.getProductCode());
			if(attributeOneMap != null){
				ProductAttributeTO oneTO = attributeOneMap.get(null);
				if(oneTO != null){
					to.setProductAttributeId(oneTO.getProductAttributeId());
					GeneralUtils.copyFromTO(to, oneTO);
				}
			}
			productAttributeTOList.add(to);
		}
		return productAttributeTOList;
	}
}
