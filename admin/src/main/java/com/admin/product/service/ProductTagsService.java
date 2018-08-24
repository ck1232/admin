package com.admin.product.service;

import java.util.List;

import com.admin.product.vo.ProductTagsVO;
import com.admin.to.ProductTO;

public interface ProductTagsService {

	void setProductTagsTO(List<ProductTagsVO> tags, ProductTO newProductTO);

}
