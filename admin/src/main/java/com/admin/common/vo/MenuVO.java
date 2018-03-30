package com.admin.common.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MenuVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private List<ModuleVO> moduleList;
	private Date dteUpdated;
	
	public List<ModuleVO> getModuleList() {
		return moduleList;
	}
	public void setModuleList(List<ModuleVO> moduleList) {
		this.moduleList = moduleList;
	}
	public Date getDteUpdated() {
		return dteUpdated;
	}
	public void setDteUpdated(Date dteUpdated) {
		this.dteUpdated = dteUpdated;
	}
	
	
}
