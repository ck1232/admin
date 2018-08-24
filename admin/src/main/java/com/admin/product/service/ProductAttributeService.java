package com.admin.product.service;

import java.util.List;

import com.admin.product.vo.ProductAttributeVO;
import com.admin.to.ProductAttributeTO;
import com.admin.to.ProductTO;

public interface ProductAttributeService {
	List<ProductAttributeVO> convertToProductAttributeVOList(List<ProductAttributeTO> productAttributeTOList);
	void setProductAttribute(List<ProductAttributeVO> productAttributeVOList, ProductTO newProductTO);

}
