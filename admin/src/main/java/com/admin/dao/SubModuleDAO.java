package com.admin.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.admin.to.SubModuleTO;
@Transactional(readOnly = true)
public interface SubModuleDAO extends BaseDAO<SubModuleTO> {
	SubModuleTO findBySubModuleId(Long submoduleId);
	List<SubModuleTO> findBySubModuleIdIn(List<Long> submoduleIdList);
}
