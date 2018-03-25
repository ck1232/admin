package com.admin.dao;

import org.springframework.transaction.annotation.Transactional;

import com.admin.to.ImageLinkRsTO;
@Transactional(readOnly = true)
public interface ImageLinkRsDAO extends BaseDAO<ImageLinkRsTO> {
}
