package com.admin.submodule.vo;

import java.io.Serializable;
import java.util.List;

import com.admin.helper.vo.BaseVO;
import com.admin.permission.vo.SubModulePermissionTypeVO;
import com.admin.permission.vo.SubModulePermissionVO;

public class SubModuleVO extends BaseVO implements Serializable {
    private Long submoduleId;

    private Long parentId;

    private String name;

    private String icon;

    private String url;
    
    private String parentModuleName;
    
    private List<SubModulePermissionTypeVO> permissionTypeList;
    
    private List<SubModulePermissionVO> submodulePermissionList;

    private static final long serialVersionUID = 1L;

    public Long getSubmoduleId() {
        return submoduleId;
    }

    public void setSubmoduleId(Long submoduleId) {
        this.submoduleId = submoduleId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getParentModuleName() {
		return parentModuleName;
	}

	public void setParentModuleName(String parentModuleName) {
		this.parentModuleName = parentModuleName;
	}

	public List<SubModulePermissionTypeVO> getPermissionTypeList() {
		return permissionTypeList;
	}

	public void setPermissionTypeList(List<SubModulePermissionTypeVO> permissionTypeList) {
		this.permissionTypeList = permissionTypeList;
	}

	public List<SubModulePermissionVO> getSubmodulePermissionList() {
		return submodulePermissionList;
	}

	public void setSubmodulePermissionList(List<SubModulePermissionVO> submodulePermissionList) {
		this.submodulePermissionList = submodulePermissionList;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", submoduleId=").append(submoduleId);
        sb.append(", parentId=").append(parentId);
        sb.append(", name=").append(name);
        sb.append(", icon=").append(icon);
        sb.append(", url=").append(url);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}