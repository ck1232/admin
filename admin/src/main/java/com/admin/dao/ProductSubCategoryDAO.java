package com.admin.dao;

import org.springframework.transaction.annotation.Transactional;

import com.admin.to.ProductSubCategoryTO;

@Transactional(readOnly = true)
public interface ProductSubCategoryDAO extends BaseDAO<ProductSubCategoryTO>{
	ProductSubCategoryTO findBySubCategoryIdAndDeleteInd(Long id , String deleteInd);
}
