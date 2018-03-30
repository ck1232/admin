package com.admin.to;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Where;

@Entity
@DynamicUpdate
@Table(name = "module")
public class ModuleTO extends BaseTO  {
	@Transient
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "module_id", nullable=false)
	private Long moduleId;

	@Column(name = "module_name", nullable=false)
	private String moduleName;
    
	@Column(name = "icon", nullable=true)
	private String icon;
	
	@OneToMany(mappedBy="moduleTO", cascade=CascadeType.ALL)
	@Where(clause="delete_ind='N'")
	@ForeignKey( name = "none" )
	private Set<SubModuleTO> subModuleTOSet;
	
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
    
    public Set<SubModuleTO> getSubModuleTOSet() {
		return subModuleTOSet;
	}

	public void setSubModuleTOSet(Set<SubModuleTO> subModuleTOSet) {
		this.subModuleTOSet = subModuleTOSet;
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
