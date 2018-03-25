package com.admin.dao;

import org.springframework.transaction.annotation.Transactional;

import com.admin.to.ProductTagsTO;
@Transactional(readOnly = true)
public interface ProductTagsDAO extends BaseDAO<ProductTagsTO> {
}
