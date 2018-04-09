package com.admin.permission.vo;

import java.io.Serializable;

import com.admin.helper.vo.BaseVO;

public class SubModulePermissionVO extends BaseVO implements Serializable {
    private Long permissionId;
    
    private Long roleId;
    
	private String roleName;
	
	private Long submoduleId;
	
	private String submoduleName;
	
	private Long permissionTypeId;
	
	private String permissionName;
	
	private String permissionTypeIdList;
	

    private static final long serialVersionUID = 1L;

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Long getSubmoduleId() {
		return submoduleId;
	}

	public void setSubmoduleId(Long submoduleId) {
		this.submoduleId = submoduleId;
	}

	public String getSubmoduleName() {
		return submoduleName;
	}

	public void setSubmoduleName(String submoduleName) {
		this.submoduleName = submoduleName;
	}

	public Long getPermissionTypeId() {
		return permissionTypeId;
	}

	public void setPermissionTypeId(Long permissionTypeId) {
		this.permissionTypeId = permissionTypeId;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

    public String getPermissionTypeIdList() {
		return permissionTypeIdList;
	}

	public void setPermissionTypeIdList(String permissionTypeIdList) {
		this.permissionTypeIdList = permissionTypeIdList;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", permissionId=").append(permissionId);
        sb.append(", roleId=").append(roleId);
        sb.append(", roleName=").append(roleName);
        sb.append(", submoduleId=").append(submoduleId);
        sb.append(", submoduleName=").append(submoduleName);
        sb.append(", permissionTypeId=").append(permissionTypeId);
        sb.append(", permissionName=").append(permissionName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}