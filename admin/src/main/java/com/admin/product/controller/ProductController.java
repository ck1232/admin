package com.admin.product.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.admin.common.vo.JsonResponseVO;
import com.admin.file.service.ImageService;
import com.admin.file.service.ImageServiceImpl;
import com.admin.file.utils.ImageSequenceCompare;
import com.admin.file.vo.FileMetaVO;
import com.admin.file.vo.ImageLinkVO;
import com.admin.helper.GeneralUtils;
import com.admin.product.service.ProductService;
import com.admin.product.service.ProductTagsServiceImpl;
import com.admin.product.vo.ProductAttributeGroupVO;
import com.admin.product.vo.ProductAttributeVO;
import com.admin.product.vo.ProductOptionVO;
import com.admin.product.vo.ProductSubOptionVO;
import com.admin.product.vo.ProductVO;
import com.admin.product.vo.ResponseProductVO;
import com.admin.productcategory.service.ProductCategoryService;
import com.admin.productcategory.vo.ProductCategoryVO;
import com.admin.productoption.service.ProductOptionService;

@Controller  
@EnableWebMvc
@Scope("request")
@RequestMapping(value = "/product/product")
public class ProductController {
	private static final Logger logger = Logger.getLogger(ProductController.class);
	private ProductService productService;
	private ImageService imageService;
	private ProductCategoryService productCategoryService;
	private ProductOptionService productOptionService;
	private ProductVO newProduct;
	private ProductOptionVO selectedOption;
	private List<ProductOptionVO> allProductOptionVOList;
	@Autowired
	public ProductController(ProductService productService,
			ImageService imageService,
			ProductCategoryService productCategoryService,
			ProductOptionService productOptionService) {
		this.productService = productService;
		this.imageService = imageService;
		this.productCategoryService = productCategoryService;
		this.productOptionService = productOptionService;
	}
	
	@RequestMapping("/listProduct")  
    public String listProduct(HttpSession session, Model model) {
    	logger.debug("loading listProduct");
    	session.setAttribute("productOptionList", null);
    	session.setAttribute("product", null);
        return "listProduct";  
    }
	
	@RequestMapping(value = "/getProductList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getProductList() {
		logger.debug("getting Product list");
		List<ProductVO> productList = productService.getProductList(null);
		return GeneralUtils.convertListToJSONString(productList);
	}
	
	@RequestMapping(value="/getProductImage/{productCode}", method = RequestMethod.GET)
	public void getProductImage(@PathVariable String productCode, HttpServletRequest request, HttpServletResponse response){
		ImageLinkVO image = productService.getCoverImageByProductCode(productCode);
		if(image != null && image.getBytes() != null){
			 try {
				response.setContentType(image.getContentType());
				response.getOutputStream().write(image.getBytes(),0,image.getBytes().length);
				response.getOutputStream().flush();  
				return;
			} catch (Exception e) {
				logger.error("getProductImage-1: Exception", e);
			}
		}else{
			imageService.getNoFileFoundImage(response);
		}
	}
	
	@RequestMapping("/createProduct")
	public String createProduct(HttpSession session, Model model){
		logger.debug("loading create product");
		newProduct = new ProductVO();
		model.addAttribute("productForm", newProduct); //set request scope
		List<ProductCategoryVO> productCategoryVOList = productCategoryService.getAllProductCategoryList();
		model.addAttribute("categoryList", productCategoryVOList);
		session.setAttribute("product", newProduct); //set session scope
		return "createProduct";
	}
	
	@RequestMapping(value="/editProduct", method = RequestMethod.POST)
	public String editProduct(HttpSession session, Model model, @RequestParam("editBtn") Long id){
		logger.debug("loading edit product");
		if(id != null){
			List<ProductVO> productList = productService.getProductList(Arrays.asList(id));
			if(productList != null && productList.size() > 0){
				newProduct = productList.get(0);
				List<ProductCategoryVO> productCategoryVOList = productCategoryService.getAllProductCategoryList();
				model.addAttribute("productForm", newProduct);
				model.addAttribute("categoryList", productCategoryVOList);
				session.setAttribute("product", newProduct);
				return "editProduct";
			}
		}
		return "listProduct";
	}
	
	@RequestMapping(value = "/getPreUploadImage", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<FileMetaVO> getPreUploadImage(HttpSession session) {
		newProduct = (ProductVO) session.getAttribute("product");
		newProduct.setImages(ImageServiceImpl.convertImageLinkVOToFileMetaVO(newProduct.getImagesLink()));
		if(newProduct == null || newProduct.getImages() == null ){
			return new ArrayList<FileMetaVO>();
		}else{
			Collections.sort(newProduct.getImages(),new ImageSequenceCompare());
			return newProduct.getImages();
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getProductOptionName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<String> getProductOptionList(HttpSession session) {
		logger.debug("getting product productOption list");
		allProductOptionVOList = (List<ProductOptionVO>) session.getAttribute("productOptionList");
		if(allProductOptionVOList == null){
			allProductOptionVOList = productOptionService.getAllProductOptions();
			session.setAttribute("productOptionList", allProductOptionVOList);
		}
		List<String> productOptionList = new ArrayList<String>();
		if(allProductOptionVOList != null && allProductOptionVOList.size() > 0){
			for(ProductOptionVO option : allProductOptionVOList){
				productOptionList.add(option.getName());
			}
		}
		return productOptionList;
	}
	
	@RequestMapping(value = "/getProductOptionsList",method = RequestMethod.GET)
	public @ResponseBody String getProductOptionsList(HttpSession session){
		logger.debug("getting Product Options list");
		newProduct = (ProductVO) session.getAttribute("product");
		if(newProduct != null && newProduct.getOptionList() != null && newProduct.getOptionList().size() > 0){
			return GeneralUtils.convertListToJSONString(newProduct.getOptionList());
		}else{
			return GeneralUtils.convertListToJSONString(new ArrayList<ProductOptionVO>());
		}
	}
	
	@RequestMapping(value = "/saveAddOption", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JsonResponseVO saveAddOption(HttpSession session, @RequestBody ProductOptionVO option) {
		newProduct = (ProductVO) session.getAttribute("product");
		if(newProduct != null){
			if(newProduct.getOptionList() == null){
				newProduct.setOptionList(new ArrayList<ProductOptionVO>());
			}
			option.setDeleteInd(GeneralUtils.NOT_DELETED);
			option.setDisplayInd(GeneralUtils.ALLOW_DISPLAY);
			
			for(ProductOptionVO optionVo : newProduct.getOptionList()){
				if(optionVo.getName().equalsIgnoreCase(option.getName())){
					return new JsonResponseVO("fail", "Option Name already exists.");
				}
			}
			List<ProductSubOptionVO> subOptionList = option.getSubOptionList();
			if(subOptionList != null && !subOptionList.isEmpty()){
				List<String> exsistingSKUList = new ArrayList<String>();
				for(ProductSubOptionVO subOptionVO : subOptionList){
					String sku = generateBaseSKUForSubOptionVO(subOptionVO.getName());
					int count = 1;
					while(exsistingSKUList.contains(sku)){
						sku = sku + count++;
					}
					subOptionVO.setCode(sku);
					exsistingSKUList.add(sku);
				}
			}
			newProduct.getOptionList().add(option);
		}
		newProduct.setProductAttributeVOList(generateProductAttributeVOList(newProduct.getProductCode(), newProduct.getOptionList()));
		return new JsonResponseVO("success");
	}
	private String generateBaseSKUForSubOptionVO (String subOptionName){
		if(subOptionName != null && !subOptionName.trim().isEmpty()){
			String trimName = subOptionName.trim();
			trimName.replaceAll(" ", "");
			StringBuilder sb = new StringBuilder();
			if(trimName.length() > 4){
				sb.append(trimName.substring(0, 2));
				sb.append(trimName.substring(trimName.length()-2, trimName.length()));
			}else{
				sb.append(trimName);
			}
			return sb.toString();
		}else{
			return null;
		}
	}
	
	@RequestMapping(value = "/editOption", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ProductOptionVO editOption(HttpSession session, @RequestBody ProductOptionVO optionName) {
		logger.debug(optionName.getName());
		newProduct = (ProductVO) session.getAttribute("product");
		if(newProduct.getOptionList() != null && newProduct.getOptionList().size() > 0){
			for(ProductOptionVO option: newProduct.getOptionList()){
				if(option.getName() != null && optionName.getName() != null && option.getName().compareToIgnoreCase(optionName.getName()) == 0){
					selectedOption = option;
					session.setAttribute("selectedOption", selectedOption);
					return option;
				}
			}
		}
		session.setAttribute("selectedOption", null);
		return new ProductOptionVO();
	}
	
	@RequestMapping(value = "/saveEditOption", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JsonResponseVO saveEditOption(HttpSession session, @RequestBody ProductOptionVO option ) {
		newProduct = (ProductVO) session.getAttribute("product");
		selectedOption = (ProductOptionVO) session.getAttribute("selectedOption");
		if(selectedOption != null){
			selectedOption.setName(option.getName());
			selectedOption.setProductOptionId(option.getProductOptionId());
			selectedOption.setSequence(option.getSequence());
			selectedOption.setSubOptionList(option.getSubOptionList());
			
			List<ProductSubOptionVO> subOptionList = selectedOption.getSubOptionList();
			if(subOptionList != null && !subOptionList.isEmpty()){
				List<String> exsistingSKUList = new ArrayList<String>();
				for(ProductSubOptionVO subOptionVO : subOptionList){
					String sku = generateBaseSKUForSubOptionVO(subOptionVO.getName());
					int count = 1;
					while(exsistingSKUList.contains(sku)){
						sku = sku + count++;
					}
					subOptionVO.setCode(sku);
					exsistingSKUList.add(sku);
				}
			}
		}
		newProduct.setProductAttributeVOList(generateProductAttributeVOList(newProduct.getProductCode(),newProduct.getOptionList()));
		if(newProduct.getProductAttributeVOList() != null){
			for(ProductAttributeVO vo : newProduct.getProductAttributeVOList()){
				if(option.getCurrentDisplayProductAttributeList() != null){
					if(option.getCurrentDisplayProductAttributeList().contains(vo.getSku())){
						vo.setDisplayInd(GeneralUtils.ALLOW_DISPLAY);
						continue;
					}
				}
				vo.setDisplayInd(GeneralUtils.NOT_ALLOW_DISPLAY);
			}
		}
		return new JsonResponseVO("success");
	}
	
	@RequestMapping(value = "/deleteOption", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JsonResponseVO deleteOption(HttpSession session, @RequestBody List<String> selectedOptions) {
		newProduct = (ProductVO) session.getAttribute("product");
		if(selectedOptions != null && selectedOptions.size() > 0 && newProduct.getOptionList() != null && newProduct.getOptionList().size() > 0){
			for(String option : selectedOptions){
				Iterator<ProductOptionVO> i = newProduct.getOptionList().iterator();
				while(i.hasNext()){
					ProductOptionVO optionVo = i.next();
					if(optionVo.getName() != null && optionVo.getName().compareToIgnoreCase(option) == 0){
						i.remove();
						break;
					}
				}
			}
		}
		newProduct.setProductAttributeVOList(generateProductAttributeVOList(newProduct.getProductCode(), newProduct.getOptionList()));
		return new JsonResponseVO("success");
	}
	
	@RequestMapping(value = "/getProductAttributeList",method = RequestMethod.GET)
	public @ResponseBody String getProductAttributeList(HttpSession session){
		logger.debug("getting Product Attribute list");
		newProduct = (ProductVO) session.getAttribute("product");
		/*if(newProduct != null && newProduct.getOptionList() != null && !newProduct.getOptionList().isEmpty() && newProduct.getProductAttributeVOList() != null && newProduct.getProductAttributeVOList().isEmpty()){
			newProduct.setProductAttributeVOList(generateProductAttributeVOList(newProduct.getProductCode(), newProduct.getOptionList()));
		}*/
		if(newProduct != null && newProduct.getProductAttributeVOList() != null && newProduct.getProductAttributeVOList().size() > 0){
			return GeneralUtils.convertListToJSONString(generateProductAttributeGroupVOList(newProduct.getProductAttributeVOList()));
		}else{
			return GeneralUtils.convertListToJSONString(new ArrayList<ProductAttributeGroupVO>());
		}
	}
	
	private List<ProductAttributeVO> generateProductAttributeVOList(String productCode, List<ProductOptionVO> list){
		List<ProductAttributeGroupVO> resultList = new ArrayList<ProductAttributeGroupVO>();
		Collections.sort(list, new ProductOptionSort());
		if(list != null && !list.isEmpty()){
			for(ProductOptionVO optionVO : list){
				resultList = multiplyOptionList(resultList, optionVO.getSubOptionList());
			}
		}
		//this is place here so that product with no attribute will have attributeVo
		resultList = generateSKU(productCode, resultList);
		List<ProductAttributeVO> productAttributeVOList = splitProductAttributeGroupVO(resultList);
		return productAttributeVOList;
	}
	
	private List<ProductAttributeVO> splitProductAttributeGroupVO(List<ProductAttributeGroupVO> resultList) {
		List<ProductAttributeVO> attributeVOList = new ArrayList<ProductAttributeVO>();
		if(resultList != null && !resultList.isEmpty()){
			for(ProductAttributeGroupVO groupVO : resultList){
				if(groupVO.getSubOptionList() != null && !groupVO.getSubOptionList().isEmpty()){
					for(ProductSubOptionVO subOptionVO : groupVO.getSubOptionList()){
						ProductAttributeVO vo = new ProductAttributeVO();
						vo.setDisplayInd(GeneralUtils.YES_IND);
						vo.setOptionId(subOptionVO.getProductOptionId());
						vo.setProductId(vo.getProductId());
						vo.setSku(groupVO.getSku());
						vo.setSubOptionVO(subOptionVO);
						attributeVOList.add(vo);
					}
				}else{
					ProductAttributeVO vo = new ProductAttributeVO();
					vo.setDisplayInd(GeneralUtils.YES_IND);
					vo.setOptionId(0L);
					vo.setSku(groupVO.getSku());
					vo.setSubOptionVO(null);
					attributeVOList.add(vo);
				}
			}
		}
		return attributeVOList;
	}

	private List<ProductAttributeGroupVO> generateSKU(String productCode, List<ProductAttributeGroupVO> groupVOList){
		if(groupVOList != null && !groupVOList.isEmpty()){
			for(ProductAttributeGroupVO vo : groupVOList){
				vo.setProductCode(productCode);
				if(vo.getSubOptionList() != null && !vo.getSubOptionList().isEmpty()){
					StringBuilder sb = new StringBuilder();
					if(productCode == null){
						sb.append("productCode");
					}else{
						sb.append(productCode);
					}
					for(ProductSubOptionVO subOptionVO : vo.getSubOptionList()){
						sb.append("-");
						sb.append(subOptionVO.getCode());
					}
					vo.setSku(sb.toString());
				}else{
					continue;
				}
				
			}
		}else{
			groupVOList = new ArrayList<ProductAttributeGroupVO>();
			ProductAttributeGroupVO groupVO = new ProductAttributeGroupVO();
			groupVO.setProductCode(productCode);
			groupVO.setSku(productCode);
			groupVOList.add(groupVO);
		}
		return groupVOList;
	}
	
	private List<ProductAttributeGroupVO> multiplyOptionList(List<ProductAttributeGroupVO> groupVOList, List<ProductSubOptionVO> subOptionList){
		List<ProductAttributeGroupVO> resultList = new ArrayList<ProductAttributeGroupVO>();
		if(groupVOList != null && !groupVOList.isEmpty()){
			for(ProductAttributeGroupVO paGroupVO : groupVOList){
				if(subOptionList != null && !subOptionList.isEmpty()){
					for(ProductSubOptionVO subOptionVO : subOptionList){
						ProductAttributeGroupVO groupVO = new ProductAttributeGroupVO();
						groupVO.setSubOptionList(new LinkedList<ProductSubOptionVO>());
						groupVO.getSubOptionList().addAll(paGroupVO.getSubOptionList());
						groupVO.getSubOptionList().add(subOptionVO);
						resultList.add(groupVO);
					}
				}
			}
		}else{
			if(subOptionList != null && !subOptionList.isEmpty()){
				for(ProductSubOptionVO subOptionVO : subOptionList){
					ProductAttributeGroupVO groupVO = new ProductAttributeGroupVO();
					groupVO.setSubOptionList(new LinkedList<ProductSubOptionVO>());
					groupVO.getSubOptionList().add(subOptionVO);
					resultList.add(groupVO);
				}
			}
		}
		return resultList;
	}
	
	private List<ProductAttributeGroupVO> generateProductAttributeGroupVOList(List<ProductAttributeVO> productAttributeVOList){
		List<ProductAttributeGroupVO> groupList = new ArrayList<ProductAttributeGroupVO>();
		Map<String, List<ProductAttributeVO>> skuMap = GeneralUtils.convertListToStringListMap(productAttributeVOList, "sku");
		if(skuMap != null && !skuMap.isEmpty()){
			for(String sku : skuMap.keySet()){
				ProductAttributeGroupVO groupVO = new ProductAttributeGroupVO();
				groupVO.setSku(sku);
				groupVO.setDisplayInd(true);
				groupVO.setHideByDefault(false);
				groupVO.setSubOptionList(new LinkedList<ProductSubOptionVO>());
				List<ProductAttributeVO> paVOList = skuMap.get(sku);
				if(paVOList != null && !paVOList.isEmpty()){
					for(ProductAttributeVO paVO : paVOList){
						
						if(GeneralUtils.NO_IND.equals(paVO.getDisplayInd())){
							groupVO.setDisplayInd(false);
						}
						
						ProductSubOptionVO subOptionVO = paVO.getSubOptionVO();
						if(subOptionVO != null){
							if(GeneralUtils.NO_IND.equals(subOptionVO.getDisplayInd())){
								groupVO.setHideByDefault(true);
							}
							groupVO.getSubOptionList().add(subOptionVO);
						}
					}
				}
				groupList.add(groupVO);
			}
		}
		return groupList;
	}
	
	@RequestMapping(value = "/saveProduct", method = RequestMethod.POST)
	public String saveProduct(HttpSession session, @ModelAttribute("productForm") ResponseProductVO responseProduct, final RedirectAttributes redirectAttributes){
		newProduct = (ProductVO) session.getAttribute("product");
		newProduct.setProductName(responseProduct.getProductName());
		newProduct.setSubCategoryId(responseProduct.getSubCategoryId());
		newProduct.setUnitAmt(responseProduct.getUnitAmt());
		newProduct.setWeight(responseProduct.getWeight() == null ? 0 : responseProduct.getWeight());
		newProduct.setTags(ProductTagsServiceImpl.convertToProductTagsVOList(responseProduct.getTags()));
		newProduct.setProductInfo(responseProduct.getProductInfo());
		
		//set product attribute vo display ind
		List<ProductAttributeVO> attributeVoList = newProduct.getProductAttributeVOList();
		List<String> productAttributeDisplayList = responseProduct.getProductAttributeList();
		if(attributeVoList != null && !attributeVoList.isEmpty()){
			for(ProductAttributeVO vo : attributeVoList){
				if(vo.getSubOptionVO() != null && GeneralUtils.NOT_ALLOW_DISPLAY.compareTo(vo.getSubOptionVO().getDisplayInd()) == 0){
					vo.setDisplayInd(GeneralUtils.ALLOW_DISPLAY);
					continue;
				}
				if(productAttributeDisplayList != null && productAttributeDisplayList.contains(vo.getSku())){
					vo.setDisplayInd(GeneralUtils.ALLOW_DISPLAY);
				}else{
					vo.setDisplayInd(GeneralUtils.NOT_ALLOW_DISPLAY);
				}
			}
		}
		//edit and add also call same method
		productService.saveAddProduct(newProduct);
		return "redirect:listProduct";
	}
	
	@RequestMapping(value = "/uploadImage",method = RequestMethod.POST)
	public @ResponseBody LinkedList<FileMetaVO> uploadImage(HttpSession session, MultipartHttpServletRequest request, HttpServletResponse response) {
		newProduct = (ProductVO) session.getAttribute("product");
		//1. build an iterator
        Iterator<String> itr =  request.getFileNames();
        MultipartFile mpf = null;
        //2. get each file
        while(itr.hasNext()){

            //2.1 get next MultipartFile
            mpf = request.getFile(itr.next()); 
//            System.out.println(mpf.getOriginalFilename() +" uploaded! "+files.size());

            //2.2 if files > 10 remove the first from the list
            if(newProduct!= null && newProduct.getImages() != null && newProduct.getImages().size() >= 10)
            	newProduct.getImages().pop();

            //2.3 create new fileMeta
            FileMetaVO fileMeta = new FileMetaVO();
            fileMeta.setFileName(mpf.getOriginalFilename());
            fileMeta.setFileSize(mpf.getSize()/1024+" Kb");
            fileMeta.setFileType(mpf.getContentType());

            try {
               fileMeta.setBytes(mpf.getBytes());
           } catch (IOException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           }
            //2.4 add to files
            if(newProduct != null){
            	if(newProduct.getImages() == null){
            		newProduct.setImages(new LinkedList<FileMetaVO>());
            	}
            	fileMeta.setSequence(newProduct.getImages().size() + 1);
            	newProduct.getImages().add(fileMeta);
            	return newProduct.getImages();
            }
            reshuffleImage(newProduct.getImages());
       }
       // result will be like this
       // [{"fileName":"app_engine-85x77.png","fileSize":"8 Kb","fileType":"image/png"},...]
       return null;
	}
	
	@RequestMapping(value = "/removeUploadImage",method = RequestMethod.POST)
	public @ResponseBody JsonResponseVO removeUploadImage(HttpSession session, HttpServletRequest request,@RequestParam(value="fileName", required=false) String fileName, HttpServletResponse response) {
		newProduct = (ProductVO) session.getAttribute("product");
		if(newProduct != null && newProduct.getImages() != null && newProduct.getImages().size() > 0 && fileName != null && !fileName.trim().isEmpty()){
			Iterator<FileMetaVO> iterator = newProduct.getImages().iterator();
			while(iterator.hasNext()){
				FileMetaVO file = iterator.next();
				if(file.getFileName().compareToIgnoreCase(fileName) == 0){
					if(file.getImageId() != null && file.getImageId() > 0){
						newProduct.addRemoveImagesLink(file);
					}
					iterator.remove();
					break;
				}
			}
			reshuffleImage(newProduct.getImages());
		}
		return new JsonResponseVO("success");
	}
	
	
	private void reshuffleImage(LinkedList<FileMetaVO> images) {
		if(images != null && images.size() > 0){
			Collections.sort(images,new ImageCompare());
			for(int i=0;i<images.size(); i++){
				images.get(i).setSequence(i+1);
			}
		}
	}
	
	@RequestMapping(value = "/deleteProduct", method = RequestMethod.POST)
	public String deleteProduct(@RequestParam(value = "checkboxId", required=false) List<Long> ids,
			final RedirectAttributes redirectAttributes) {
		if(ids == null || ids.size() < 1){
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "Please select at least one record!");
			return "redirect:listProduct";
		}
		productService.deleteProduct(ids);
		redirectAttributes.addFlashAttribute("css", "success");
		redirectAttributes.addFlashAttribute("msg", "Product(s) deleted successfully!");
		return "redirect:listProduct";
	}
	
	class ImageCompare implements Comparator<FileMetaVO>{

		@Override
		public int compare(FileMetaVO o1, FileMetaVO o2) {
			if(o1.getSequence() != null && o2.getSequence() != null){
				return o1.getSequence().compareTo(o2.getSequence());
			}else if(o1.getSequence() != null){
				return 1;
			}else if(o2.getSequence() != null){
				return -1;
			}
			return 0;
		}
	}
	
	class ProductOptionSort implements Comparator<ProductOptionVO>{
		@Override
		public int compare(ProductOptionVO o1, ProductOptionVO o2) {
			if(o1 != null && o2 != null && o1.getSequence() != null && o2.getSequence() != null){
				return o1.getSequence().compareTo(o2.getSequence());
			}else if(o1 != null && o1.getSequence() != null){
				return -1;
			}else if(o2 != null && o2.getSequence() != null){
				return 1;
			}
			return 0;
		}
	}
}
