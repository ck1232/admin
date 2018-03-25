package com.admin.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.admin.to.ModuleTO;
@Transactional(readOnly = true)
public interface ModuleDAO extends BaseDAO<ModuleTO> {
	ModuleTO findByModuleId(Long moduleId);
	List<ModuleTO> findByModuleIdIn(List<Long> moduleIdList);
}
