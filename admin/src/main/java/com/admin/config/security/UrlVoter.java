package com.admin.config.security;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Service;

import com.admin.user.vo.LoginUserVO;


@Service
public class UrlVoter implements AccessDecisionVoter<FilterInvocation>{
	
	private static final Logger logger = Logger.getLogger(UrlVoter.class);
    @Override
    public int vote(Authentication authentication, FilterInvocation fi, Collection<ConfigAttribute> collection) {
    	String url = fi.getRequestUrl();
    	String method = fi.getHttpRequest().getMethod();
    	String username = "GUEST";
    	Object userObj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(userObj instanceof LoginUserVO){
			LoginUserVO user = (LoginUserVO)userObj ;
			username = user.getUsername();
		}
    	HttpSession session = fi.getRequest().getSession();
    	@SuppressWarnings("unchecked")
		List<String> allowedUrlList = (List<String>) session.getAttribute("allowedUrl");
    	
    	int index = url.indexOf('?');
    	if(index >= 0){
    		url = url.substring(0,index);
    	}
    	
    	if(authentication == null ){
    		logger.debug("user:"+username + ", url:"+ url +" ~"+ method +", permission:DENIED");
    		return ACCESS_DENIED;
    	}
    	if(url.equalsIgnoreCase("/") || url.equalsIgnoreCase("/dashboard")){
    		logger.debug("user:"+username + ", url:"+ url +" ~"+ method +", permission:GRANTED");
    		return ACCESS_GRANTED;
    	}
    	if(allowedUrlList != null && allowedUrlList.size() > 0){
    		for(String allowedUrl : allowedUrlList){
        		if(allowedUrl.contains(url) || url.contains(allowedUrl)){
        			logger.info("user:"+username + ", url:"+ url +" ~"+ method +", permission:GRANTED");
        			return ACCESS_GRANTED;
        		}
        	}
    	}
    	logger.info("user:"+username + ", url:"+ url +" ~"+ method +", permission:ABSTAIN");
    	return ACCESS_ABSTAIN;
        
//            return ACCESS_DENIED; // Abstain Based on your requirement
    }

	@Override
	public boolean supports(ConfigAttribute attribute) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(FilterInvocation.class);
	}

}