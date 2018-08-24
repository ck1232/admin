package com.admin.product.service;

import java.util.List;

import com.admin.file.vo.ImageLinkVO;
import com.admin.product.vo.ProductVO;

public interface ProductService {
	void deleteProduct(List<Long> ids);
	ImageLinkVO getCoverImageByProductCode(String productCode);
	List<ProductVO> getProductList(List<Long> asList);
	void saveAddProduct(ProductVO newProduct);

}
