package com.admin.product.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.admin.helper.GeneralUtils;
import com.admin.to.ProductSpecificationTO;
import com.admin.to.ProductTO;

@Service
@Scope("prototype")
@Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class ProductSpecificationServiceImpl implements ProductSpecificationService {

	public void setProductSpecification(String content, ProductTO newProductTO ){
		ProductSpecificationTO to = convertToProductSpecificationTO(content);
		to = setExistingProductSpecificationTO(to, newProductTO);
		newProductTO.setProductSpecificationTO(to);
	}

	private ProductSpecificationTO convertToProductSpecificationTO(String content) {
		ProductSpecificationTO to = new ProductSpecificationTO();
		to.setContent(content);
		return to;
	}
	
	private ProductSpecificationTO setExistingProductSpecificationTO(ProductSpecificationTO specTO, ProductTO newProductTO) {
		specTO.setProductTO(newProductTO);
		if(newProductTO != null && newProductTO.getProductId() != null && newProductTO.getProductSpecificationTO() != null){
			ProductSpecificationTO dbTO = newProductTO.getProductSpecificationTO();
			if(dbTO != null){
				specTO.setProductSpecificationId(dbTO.getProductSpecificationId());
				GeneralUtils.copyFromTO(specTO, dbTO);
			}
		}
		return specTO;
	}
}
