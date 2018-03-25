package com.admin.dao;

import java.util.List;

import com.admin.to.ProductOptionTO;

public interface ProductOptionDAO extends BaseDAO<ProductOptionTO>{
	List<ProductOptionTO> findByDeleteIndAndDisplayInd(String deleteInd, String displayInd);
	ProductOptionTO findByProductOptionId(Long productOptionId);
	ProductOptionTO findByNameAndDeleteInd(String name, String deleteInd);
}
