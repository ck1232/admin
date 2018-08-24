package com.admin.product.service;

import java.util.List;

import com.admin.product.vo.ProductOptionVO;
import com.admin.to.ProductTO;

public interface ProductSubOptionService {

	void setProductSubOption(List<ProductOptionVO> optionList, ProductTO newProductTO);

}
