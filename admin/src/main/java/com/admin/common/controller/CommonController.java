package com.admin.common.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("session")
@RequestMapping(value = "/")
public class CommonController {
	
	private static final Logger logger = Logger.getLogger(CommonController.class);
	
	@RequestMapping(value={"/login"},method = RequestMethod.GET)  
    public String login() {  
        return "login";  
    }
	
	@RequestMapping(value={"/","/dashboard"})  
    public String loadDashboard(HttpSession session) {  
    	logger.debug("dashboard is executed!");
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	if(principal instanceof UserDetails){
    		UserDetails userDetails = (UserDetails)principal ;
	    	session.setAttribute("userAccount", principal);
//	    	session.setAttribute("menu", this.populateMenu(userDetails));
	    	List<String> roleList = new ArrayList<String>();
	    	for(GrantedAuthority authority : SecurityContextHolder.getContext().getAuthentication().getAuthorities()){
	    		roleList.add(authority.getAuthority());
	    	}
//	    	List<String> urlList = commonService.getAllowedUrlByRoleName(roleList);
//	    	session.setAttribute("allowedUrl", urlList);
    	}else if(principal instanceof String){
    		logger.debug("principal:"+principal.toString());
    	}
        return "dashboard";  
    }
	
	@RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
		logger.debug("access denied!");
        model.addAttribute("user", getPrincipal());
        return "accessDenied";
    }
	
	private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
}
