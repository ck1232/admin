package com.admin.module.service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.admin.dao.ModuleDAO;
import com.admin.helper.GeneralUtils;
import com.admin.module.vo.ModuleVO;
import com.admin.submodule.service.SubmoduleService;
import com.admin.to.ModuleTO;
import com.admin.to.SubModuleTO;

@Service
@Scope("prototype")
@Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class ModuleService {
	
	private ModuleDAO moduleDAO;
	private SubmoduleService submoduleService;
	
	@Autowired
	public ModuleService(ModuleDAO moduleDAO,
			SubmoduleService submoduleService) {
		this.moduleDAO = moduleDAO;
		this.submoduleService = submoduleService;
	}
	
	public ModuleVO findById(Long id) {
		ModuleTO moduleTO = moduleDAO.findByModuleId(id);
		List<ModuleVO> moduleVOList = convertToModuleVOList(Arrays.asList(moduleTO));
		if(moduleVOList != null && !moduleVOList.isEmpty()){
			return moduleVOList.get(0);
		}
		return new ModuleVO();
	}

	public List<ModuleVO> getAllModules() {
		List<ModuleTO> moduleTOList = moduleDAO.findByDeleteInd(GeneralUtils.NOT_DELETED);
		return convertToModuleVOList(moduleTOList);
	}
	
	public void saveModule(ModuleVO moduleVO) {
		List<ModuleTO> moduleTOList = convertToModuleTOList(Arrays.asList(moduleVO));
		moduleDAO.save(moduleTOList);
	}
	
	public void deleteModule(Long id) {
		deleteModule(Arrays.asList(id));
	}
	
	public void deleteModule(List<Long> idList) {
		List<ModuleTO> moduleTOList = moduleDAO.findByModuleIdIn(idList);
		if(moduleTOList != null && !moduleTOList.isEmpty()){
			for(ModuleTO moduleTO : moduleTOList){
				moduleTO.setDeleteInd(GeneralUtils.DELETED); // module set to delete
				List<SubModuleTO> submoduleTOList = moduleTO.getSubmoduleTOList();
				if(submoduleTOList != null && !submoduleTOList.isEmpty()){
					for(SubModuleTO to: submoduleTOList){
						to.setDeleteInd(GeneralUtils.DELETED); // submodule list set to delete
					}
				}
			}
			moduleDAO.save(moduleTOList);
		}
	}
	
	public void updateModule(ModuleVO vo) {
		if(vo != null && vo.getModuleId() != null){
			ModuleTO to = moduleDAO.findByModuleId(vo.getModuleId());
			to.setModuleName(vo.getModuleName());
			to.setIcon(vo.getIcon());
			moduleDAO.save(to);
		}
	}
	
	public List<ModuleVO> convertToModuleVOList(List<ModuleTO> toList) {
		List<ModuleVO> voList = new ArrayList<ModuleVO>();
		if(toList != null && !toList.isEmpty()){
			for(ModuleTO to : toList){
				ModuleVO vo = new ModuleVO();
				vo.setIcon(to.getIcon());
				vo.setModuleId(to.getModuleId());
				vo.setModuleName(to.getModuleName());
				vo.setSubModuleList(submoduleService.convertToSubModuleVOList(to.getSubmoduleTOList()));
				voList.add(vo);
			}
		}
		return voList;
	}
	
	public List<ModuleTO> convertToModuleTOList(List<ModuleVO> voList) {
		List<ModuleTO> toList = new ArrayList<ModuleTO>();
		if(voList != null && !voList.isEmpty()){
			List<Long> idList = GeneralUtils.convertListToLongList(voList, "moduleId", false);
			Map<Long, ModuleTO> moduleTOMap = new HashMap<Long, ModuleTO>();
			if(!idList.isEmpty()){
				List<ModuleTO> moduleTOList = moduleDAO.findByModuleIdIn(idList);
				moduleTOMap = GeneralUtils.convertListToLongMap(moduleTOList, "moduleId");
			}
			
			for(ModuleVO vo : voList){
				ModuleTO to = new ModuleTO();
				ModuleTO dbTO = moduleTOMap.get(vo.getModuleId());
				if(dbTO != null){ //update
					to = dbTO;
				}
				to.setIcon(vo.getIcon());
				to.setModuleId(vo.getModuleId());
				to.setModuleName(vo.getModuleName());
				to.setSubmoduleTOList(submoduleService.convertToSubModuleTOList(vo.getSubModuleList(), to));
				toList.add(to);
			}
		}
		return toList;
	}
}
