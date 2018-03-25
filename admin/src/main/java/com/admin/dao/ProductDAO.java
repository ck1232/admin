package com.admin.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.admin.to.ProductTO;
@Transactional(readOnly = true)
public interface ProductDAO extends BaseDAO<ProductTO> {
	List<ProductTO> findByDeleteInd(String deleteInd);
	List<ProductTO> findByProductIdNotAndDeleteInd(Long productId, String deleteInd);
	List<ProductTO> findByProductIdInAndDeleteInd(List<Long> productIdList, String deleteInd);
	ProductTO findByProductCodeAndDeleteInd(String productCode, String deleteInd);
	List<ProductTO> findBySubCategoryIdInAndDeleteInd(List<Long> subCategoryIdList, String deleteInd);
}
