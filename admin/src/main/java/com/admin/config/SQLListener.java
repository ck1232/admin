package com.admin.config;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.admin.helper.GeneralUtils;
import com.admin.to.BaseTO;

public class SQLListener {
	
	private String getUserName(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof UserDetails){
			UserDetails user = (UserDetails)principal;
			return user.getUsername();
		}else{
			return "SYSTEM";
		}
	}
	
	@PrePersist
    public void onPrePersist(BaseTO baseTO) {
		if(baseTO == null){return;}
		baseTO.setCreatedOn(new Date());
		baseTO.setUpdatedOn(new Date());
		String userName = getUserName();
		baseTO.setCreatedBy(userName);
		baseTO.setUpdatedBy(userName);
		if(baseTO.getDeleteInd() == null){
			baseTO.setDeleteInd(GeneralUtils.NOT_DELETED);
		}
		baseTO.setVersion(1);
    }
	@PreUpdate
	private void onPreUpdate(BaseTO baseTO){
		if(baseTO == null){return;}
		String userName = getUserName();
		baseTO.setUpdatedOn(new Date());
		baseTO.setVersion(baseTO.getVersion()+1);
		if(baseTO.getDeleteInd() == null){
			baseTO.setDeleteInd(GeneralUtils.NOT_DELETED);
		}
		baseTO.setUpdatedBy(userName);
	}

}
