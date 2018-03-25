package com.admin.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.admin.to.ProductAttributeTO;
import com.admin.to.ProductTO;
@Transactional(readOnly = true)
public interface ProductAttributeDAO extends BaseDAO<ProductAttributeTO> {
	List<ProductAttributeTO> findByProductTOAndDeleteInd(ProductTO productTO, String deleteInd);
}
