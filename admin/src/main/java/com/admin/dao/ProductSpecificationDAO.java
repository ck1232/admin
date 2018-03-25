package com.admin.dao;

import org.springframework.transaction.annotation.Transactional;

import com.admin.to.ProductSpecificationTO;
import com.admin.to.ProductTO;
@Transactional(readOnly = true)
public interface ProductSpecificationDAO extends BaseDAO<ProductSpecificationTO> {
	ProductSpecificationTO findByProductTOAndDeleteInd(ProductTO productTO, String deleteInd );
}
