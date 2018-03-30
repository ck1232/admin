package com.admin.to;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Where;
@Entity
@DynamicUpdate
@Table(name = "submodule")
public class SubModuleTO extends BaseTO {

	@Transient
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "submodule_id", nullable=false)
	private Long subModuleId;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_id", nullable=false)
	@Where(clause="delete_ind='N'")
	@ForeignKey( name = "none" )
	private ModuleTO moduleTO;
	
	@Column(name = "name", nullable=false)
	private String name;
	
	@Column(name = "icon", nullable=false)
	private String icon;
	
	@Column(name = "url", nullable=false)
	private String url;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "subModuleTO", cascade=CascadeType.ALL)
	@Where(clause="delete_ind='N'")
	private Set<SubModulePermissionTO> subModulePermissionSet;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "subModuleTO", cascade=CascadeType.ALL)
	@Where(clause="delete_ind='N'")
	private Set<PermissionTypeTO> permissionTypeTOSet;

	public ModuleTO getModuleTO() {
		return moduleTO;
	}

	public void setModuleTO(ModuleTO moduleTO) {
		this.moduleTO = moduleTO;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Set<SubModulePermissionTO> getSubModulePermissionSet() {
		return subModulePermissionSet;
	}

	public void setSubModulePermissionSet(Set<SubModulePermissionTO> subModulePermissionSet) {
		this.subModulePermissionSet = subModulePermissionSet;
	}

	public Long getSubModuleId() {
		return subModuleId;
	}

	public void setSubModuleId(Long subModuleId) {
		this.subModuleId = subModuleId;
	}

	public Set<PermissionTypeTO> getPermissionTypeTOSet() {
		return permissionTypeTOSet;
	}

	public void setPermissionTypeTOSet(Set<PermissionTypeTO> permissionTypeTOSet) {
		this.permissionTypeTOSet = permissionTypeTOSet;
	}
}
