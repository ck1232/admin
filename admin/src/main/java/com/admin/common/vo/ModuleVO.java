package com.admin.common.vo;

import java.io.Serializable;
import java.util.List;

import com.admin.helper.vo.BaseVO;

public class ModuleVO extends BaseVO implements Serializable {
    private Long moduleId;

    private String moduleName;

    private String icon;
    
    private List<SubModuleVO> subModuleList;
    
    private static final long serialVersionUID = 1L;

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName == null ? null : moduleName.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public List<SubModuleVO> getSubModuleList() {
		return subModuleList;
	}

	public void setSubModuleList(List<SubModuleVO> subModuleList) {
		this.subModuleList = subModuleList;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", moduleId=").append(moduleId);
        sb.append(", moduleName=").append(moduleName);
        sb.append(", icon=").append(icon);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}