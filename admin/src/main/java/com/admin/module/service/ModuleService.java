package com.admin.module.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.admin.common.vo.ModuleVO;
import com.admin.dao.ModuleDAO;
import com.admin.helper.GeneralUtils;
import com.admin.to.ModuleTO;

@Service
@Scope("prototype")
@Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class ModuleService {
	private ModuleDAO moduleDAO;
	
	@Autowired
	public ModuleService(ModuleDAO moduleDAO) {
		this.moduleDAO = moduleDAO;
	}

	public List<ModuleVO> getAllModules() {
		List<ModuleTO> moduleTOList = moduleDAO.findByDeleteInd(GeneralUtils.NOT_DELETED);
		return convertToModuleVOList(moduleTOList);
	}
	
	public List<ModuleVO> convertToModuleVOList(List<ModuleTO> toList) {
		List<ModuleVO> voList = new ArrayList<ModuleVO>();
		if(toList != null && !toList.isEmpty()){
			for(ModuleTO to : toList){
				ModuleVO vo = new ModuleVO();
				vo.setIcon(to.getIcon());
				vo.setModuleId(to.getModuleId());
				vo.setModuleName(to.getModuleName());
				voList.add(vo);
			}
		}
		return voList;
	}
}
