package com.admin.productoption.service;

import java.util.List;

import com.admin.product.vo.ProductOptionVO;
import com.admin.product.vo.ProductSubOptionVO;
import com.admin.to.ProductOptionTO;
import com.admin.to.ProductSubOptionTO;

public interface ProductOptionService {

	List<ProductOptionVO> convertToProductOptionVOList(List<ProductOptionTO> asList);

	List<ProductSubOptionVO> convertToProductSubOptionVOList(List<ProductSubOptionTO> asList);

	List<ProductOptionVO> getAllProductOptions();

}
