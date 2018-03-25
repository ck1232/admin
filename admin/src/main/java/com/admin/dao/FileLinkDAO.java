package com.admin.dao;

import org.springframework.transaction.annotation.Transactional;

import com.admin.to.FileLinkTO;
@Transactional(readOnly = true)
public interface FileLinkDAO extends BaseDAO<FileLinkTO>{
	
}
