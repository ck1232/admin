package com.admin.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.admin.to.ProductCategoryTO;

@Transactional(readOnly = true)
public interface ProductCategoryDAO extends BaseDAO<ProductCategoryTO>{
	List<ProductCategoryTO> findByDeleteInd(String deleteInd);
	List<ProductCategoryTO> findByDeleteIndAndDisplayInd(String deleteInd, String displayInd);
	ProductCategoryTO findByCategoryIdAndDeleteInd(Long categoryId, String deleteInd);
}
