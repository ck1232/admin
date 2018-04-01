package com.admin.common.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.admin.common.vo.FileVO;
import com.admin.common.vo.LoginUser;
import com.admin.common.vo.MenuVO;
import com.admin.common.vo.ModuleVO;
import com.admin.common.vo.SubModuleVO;
import com.admin.module.service.ModuleService;
import com.admin.submodule.service.SubModuleService;
import com.admin.to.RoleTO;
import com.admin.to.SubModulePermissionTO;
import com.admin.to.SubModuleTO;

@Controller
@Scope("session")
@PropertySources({
	@PropertySource(value = "classpath:admin-dev-config.properties", ignoreResourceNotFound = false),
	@PropertySource(value = "file:C:\\Inetpub\\vhosts\\ziumlight.com\\Configuration\\application-${spring.profiles.active}.properties", ignoreResourceNotFound=true)
})
@RequestMapping(value = "/")
public class CommonController {
	
	private static final Logger logger = Logger.getLogger(CommonController.class);
	
	@Value("${log.path}")
	private String logUrl;
	
	private ModuleService moduleService;
	private SubModuleService subModuleService;
	@Autowired
	public CommonController(ModuleService moduleService,
			SubModuleService subModuleService) {
		this.moduleService = moduleService;
		this.subModuleService = subModuleService;
	}

	@RequestMapping(value={"/login"},method = RequestMethod.GET)  
    public String login() {  
        return "login";  
    }
	
	@RequestMapping(value={"/","/dashboard"})  
    public String loadDashboard(HttpSession session) {  
    	logger.debug("dashboard is executed!");
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	if(principal instanceof LoginUser){
    		LoginUser userDetails = (LoginUser)principal;
    		if(session.getAttribute("userAccount") == null) {
    			session.setAttribute("userAccount", principal);
    		}
    		if(session.getAttribute("menu") == null) {
    			session.setAttribute("menu", this.populateMenu(userDetails.getRoleList()));
    		}
//	    	List<String> urlList = commonService.getAllowedUrlByRoleName(roleList);
//	    	session.setAttribute("allowedUrl", urlList);
    	}else if(principal instanceof String){
    		logger.error("principal object invalid:"+ principal);
    		logger.debug("principal:"+principal.toString());
    	}
        return "dashboard";  
    }
	
	public MenuVO populateMenu(List<RoleTO> roleList){
		MenuVO menu = new MenuVO();
		List<SubModuleTO> subModuleTOList= extractSubModuleFromRoleTO(roleList);
		List<SubModuleVO> subModuleList = subModuleService.convertToSubModuleVOList(subModuleTOList);
		List<ModuleVO> moduleList = moduleService.getAllModules();
		
		if(subModuleList != null && subModuleList.size() > 0){
			Map<Long, List<SubModuleVO>> subModuleMap = new HashMap<Long, List<SubModuleVO>>();
			for(SubModuleVO subModule : subModuleList){
				if(subModuleMap.get(subModule.getParentId()) == null){
					subModuleMap.put(subModule.getParentId(), new ArrayList<SubModuleVO>());
				}
				subModuleMap.get(subModule.getParentId()).add(subModule);
			}
			for(ModuleVO module : moduleList){
				module.setSubModuleList(subModuleMap.get(module.getModuleId()));
			}
			
		}
		menu.setModuleList(moduleList);
		menu.setDteUpdated(new Date());
		return menu;
	}
	
	private List<SubModuleTO> extractSubModuleFromRoleTO(List<RoleTO> roleList) {
		List<SubModuleTO> toList = new ArrayList<SubModuleTO>();
		Set<SubModuleTO> toSet = new HashSet<SubModuleTO>();
		if(roleList != null && !roleList.isEmpty()) {
			for(RoleTO role: roleList) {
				Set<SubModulePermissionTO> subModulePermissionSet = role.getSubModulePermissionSet();
				if(subModulePermissionSet != null && !subModulePermissionSet.isEmpty()) {
					for(SubModulePermissionTO subModulePermissionTO : subModulePermissionSet) {
						SubModuleTO subModuleTO = subModulePermissionTO.getSubModuleTO();
						if(subModuleTO != null) {
							toSet.add(subModuleTO);
						}
					}
				}
			}
		}
		toList.addAll(toSet);
		return toList;
	}

	@RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
		logger.debug("access denied!");
        return "accessDenied";
    }
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       if (auth != null){    
          new SecurityContextLogoutHandler().logout(request, response, auth);
       }
       request.getSession().setAttribute("user", null);
       request.getSession().setAttribute("menu", null);
       return "redirect:/login?logout";
    }
	
	@RequestMapping(value={"/query","/q"}, method = RequestMethod.GET)
	public String queryPage (Model model) {
		return "query";
	}
	
	@RequestMapping(value={"/404"}, method = RequestMethod.GET)
	public String errorPage (Model model) {
		return "404";
	}
	/*
	@RequestMapping(value={"/500",}, method = RequestMethod.GET)
	public String notFoundPage (Model model) {
		return "500";
	}*/
	
	@RequestMapping(value={"/viewLogs"}, method = RequestMethod.POST)
	public String viewLogsPage (HttpServletResponse response, @RequestParam(value="view", required=true) int hashCode, Model model) throws Exception{
//		String url = System.getProperty("wtp.deploy");
//		String url = "C:\\Inetpub\\vhosts\\ziumlight.com";
//		File folder = new File(url + "\\logs");
		File folder = new File(logUrl);
		List<File> fileList = Arrays.asList(folder.listFiles());
		List<FileVO> filesList = convertToFileVO(fileList);
		if(filesList != null && filesList.size() > 0){
			for(FileVO vo :filesList){
				if(vo.hashCode()==hashCode){
					File file = vo.getFile();
					String content = "";
					BufferedReader br = new BufferedReader(new FileReader(file));
					String line = null;
					StringBuilder sb = new StringBuilder();
					while ((line = br.readLine()) != null) {
						sb.append(line);
						sb.append("<br/>");
					}
					br.close();
					content = sb.toString();
					model.addAttribute("content", content);
					return "viewLogs";
				}
			}
		}
		return "redirect:logs";
	}
	
	@RequestMapping(value={"/downloadLogs"}, method = RequestMethod.POST)
	public String downloadLogsPage (HttpServletResponse response, @RequestParam(value="download", required=true) int hashCode) throws Exception{
		File folder = new File(logUrl);
		List<File> fileList = Arrays.asList(folder.listFiles());
		List<FileVO> filesList = convertToFileVO(fileList);
		if(filesList != null && filesList.size() > 0){
			for(FileVO vo :filesList){
				if(vo.hashCode()==hashCode){
					response.setContentType("application/octet-stream");
		         	response.addHeader("Content-Disposition", "attachment; filename="+vo.getFileName()+".logs");
		         	File file = vo.getFile();
		         	FileInputStream fileIn = new FileInputStream(file);
		            ServletOutputStream out = response.getOutputStream();
		            byte[] outputByte = new byte[(int)file.length()];
		            //copy binary contect to output stream
		            while(fileIn.read(outputByte, 0, (int)file.length()) != -1)
		            {
		            	out.write(outputByte, 0, (int)file.length());
		            }
		            out.flush();
		            fileIn.close();
		            return null;
				}
			}
		}
		return "logs";
	}
	
	@RequestMapping(value={"/logs","/l"}, method = RequestMethod.GET)
	public String logsPage (Model model) throws Exception{
		File folder = new File(logUrl);
		List<File> fileList = Arrays.asList(folder.listFiles());
		List<FileVO> filesList = convertToFileVO(fileList);
		model.addAttribute("files", filesList);
		return "logs";
	}
	
	private List<FileVO> convertToFileVO(List<File> fileList) {
		List<FileVO> fileVOList = new ArrayList<FileVO>();
		if(fileList != null && fileList.size() > 0){
			for(File file : fileList){
				if(file.isDirectory()) continue;
				FileVO vo = new FileVO();
				vo.setFileName(file.getName());
				vo.setLastModified(file.lastModified());
				vo.setHashCode(vo.hashCode());
				vo.setFile(file);
				fileVOList.add(vo);
			}
		}
		return fileVOList;
	}
}
