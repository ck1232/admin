package com.admin.dao;

import org.springframework.transaction.annotation.Transactional;

import com.admin.to.ProductSubOptionTO;
@Transactional(readOnly = true)
public interface ProductSubOptionDAO extends BaseDAO<ProductSubOptionTO> {
	ProductSubOptionTO findByproductSuboptionId(Long id);
}
