package com.admin.submodule.service;

import java.util.List;

import com.admin.submodule.vo.SubModuleVO;
import com.admin.to.ModuleTO;
import com.admin.to.SubModuleTO;

public interface SubmoduleService {

	List<SubModuleTO> convertToSubModuleTOList(List<SubModuleVO> subModuleList, ModuleTO to);

	List<SubModuleVO> convertToSubModuleVOList(List<SubModuleTO> subModuleTOList);

	SubModuleVO findById(Long longValue);

	List<SubModuleVO> getSubmodulesById(List<Long> idList);

	void deleteSubmodule(List<Long> idList);

}
