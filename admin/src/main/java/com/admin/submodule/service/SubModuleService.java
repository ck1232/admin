package com.admin.submodule.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.admin.common.vo.SubModuleVO;
import com.admin.to.SubModuleTO;

@Service
@Scope("prototype")
@Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class SubModuleService {
	
	public List<SubModuleVO> convertToSubModuleVOList(List<SubModuleTO> dbObjList) {
		List<SubModuleVO> voList = new ArrayList<SubModuleVO>();
		if(dbObjList != null && !dbObjList.isEmpty()){
			for(SubModuleTO dbObj : dbObjList){
				SubModuleVO vo = new SubModuleVO();
				vo.setIcon(dbObj.getIcon());
				vo.setName(dbObj.getName());
				vo.setParentId(dbObj.getModuleTO() == null ? null : dbObj.getModuleTO().getModuleId());
				vo.setSubmoduleId(dbObj.getSubModuleId());
				vo.setUrl(dbObj.getUrl());
				voList.add(vo);
			}
		}
		return voList;
	}
}
