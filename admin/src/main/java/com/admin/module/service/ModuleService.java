package com.admin.module.service;

import java.util.List;

import com.admin.module.vo.ModuleVO;

public interface ModuleService {
	ModuleVO findById(Long id);
	List<ModuleVO> findByIdList(List<Long> idList);
	void deleteModule(List<Long> idList);
	List<ModuleVO> getAllModules();
	void saveModule(ModuleVO moduleVO);
	void updateModule(ModuleVO moduleVO);
}
