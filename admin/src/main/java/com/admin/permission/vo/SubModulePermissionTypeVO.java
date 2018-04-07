package com.admin.permission.vo;

import java.io.Serializable;

import com.admin.helper.vo.BaseVO;

public class SubModulePermissionTypeVO extends BaseVO implements Serializable {
    private Long typeId;

    private Long submoduleId;

    private String permissionType;

    private Long seqNum;

    private String url;

    private static final long serialVersionUID = 1L;

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getSubmoduleId() {
        return submoduleId;
    }

    public void setSubmoduleId(Long submoduleId) {
        this.submoduleId = submoduleId;
    }

    public String getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(String permissionType) {
        this.permissionType = permissionType == null ? null : permissionType.trim();
    }

    public Long getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(Long seqNum) {
        this.seqNum = seqNum;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", typeId=").append(typeId);
        sb.append(", submoduleId=").append(submoduleId);
        sb.append(", permissionType=").append(permissionType);
        sb.append(", seqNum=").append(seqNum);
        sb.append(", url=").append(url);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}