package com.admin.common.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.admin.common.vo.MenuVO;
import com.admin.file.utils.ExcelFileHelper;
import com.admin.file.vo.FileVO;
import com.admin.helper.GeneralUtils;
import com.admin.module.service.ModuleService;
import com.admin.module.vo.ModuleVO;
import com.admin.role.service.RoleService;
import com.admin.submodule.service.SubmoduleService;
import com.admin.submodule.vo.SubModuleVO;
import com.admin.to.RoleTO;
import com.admin.to.SubModulePermissionTO;
import com.admin.to.SubModuleTO;
import com.admin.user.vo.LoginUserVO;

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
	
	@Value("${jdbc.driver}")
	private String driver;
	
	@Value("${jdbc.url}")
    private String url;
	
	@Value("${jdbc.query.user}")
    private String user;
	
	@Value("${jdbc.query.password}")
    private String password;
	
	@Value("${image.folder}")
    private String imageFolderSource;
	
	private ModuleService moduleService;
	private SubmoduleService submoduleService;
	private RoleService roleService;
	@Autowired
	public CommonController(ModuleService moduleService,
			SubmoduleService submoduleService,
			RoleService roleService) {
		this.moduleService = moduleService;
		this.submoduleService = submoduleService;
		this.roleService = roleService;
	}

	@RequestMapping(value={"/login"},method = RequestMethod.GET)  
    public String login() {  
        return "login";  
    }
	
	@RequestMapping(value={"/","/dashboard"})  
    public String loadDashboard(HttpSession session) {  
    	logger.debug("dashboard is executed!");
    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	if(principal instanceof LoginUserVO){
    		LoginUserVO userDetails = (LoginUserVO)principal;
    		if(session.getAttribute("userAccount") == null) {
    			session.setAttribute("userAccount", principal);
    		}
    		if(session.getAttribute("menu") == null) {
    			session.setAttribute("menu", this.populateMenu(userDetails.getRoleList()));
    		}
	    	List<String> urlList = roleService.getAllowedUrlByRoleName(userDetails.getRoleList());
	    	session.setAttribute("allowedUrl", urlList);
    	}else if(principal instanceof String){
    		logger.error("principal object invalid:"+ principal);
    		logger.debug("principal:"+principal.toString());
    	}
        return "dashboard";  
    }
	
	public MenuVO populateMenu(List<RoleTO> roleList){
		MenuVO menu = new MenuVO();
		List<SubModuleTO> subModuleTOList= extractSubModuleFromRoleTO(roleList); //allowed submodule
		List<SubModuleVO> subModuleList = submoduleService.convertToSubModuleVOList(subModuleTOList);
		List<ModuleVO> moduleList = moduleService.getAllModules();
		
		if(subModuleList != null && !subModuleList.isEmpty()){
			Map<Long, List<SubModuleVO>> subModuleMap = new HashMap<Long, List<SubModuleVO>>();
			for(SubModuleVO subModule : subModuleList){ //add to submodulemap
				if(subModuleMap.get(subModule.getParentId()) == null){
					subModuleMap.put(subModule.getParentId(), new ArrayList<SubModuleVO>()); 
				}
				subModuleMap.get(subModule.getParentId()).add(subModule);
			}
			
			Iterator<ModuleVO> it = moduleList.iterator();
			while(it.hasNext()) {
				ModuleVO module = it.next();
				List<SubModuleVO> subModuleVOList = subModuleMap.get(module.getModuleId()); // allowed submodule retrieved from db
				if(subModuleVOList != null && !subModuleVOList.isEmpty()) {
					subModuleVOList = GeneralUtils.sortByVariable(subModuleVOList, "name");
					module.setSubModuleList(subModuleVOList);
				}
				else {
					it.remove();
				}
			}
			
			
		}
		moduleList = GeneralUtils.sortByVariable(moduleList, "moduleName");
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
	
	@RequestMapping(value="/query", method = RequestMethod.POST)
	public String runSqlpage (@RequestParam(value="sqlStatement", required=true) String sqlStatement, final RedirectAttributes redirectAttributes, Model model) {
		Statement statement = null;
		String message = null;
		if(sqlStatement != null && !sqlStatement.trim().isEmpty()){
			try{
				sqlStatement = sqlStatement.trim();
				if(sqlStatement.charAt(sqlStatement.length()-1) != ';'){
					sqlStatement += ';';
				}
				Class.forName(driver);
				Connection connection = DriverManager.getConnection(url, user, password);
				statement = connection.createStatement();
				String sqlType = sqlStatement.substring(0, 6);
				String sqlType2 = sqlStatement.substring(0, 4);
				logger.debug("loading sql :"+ sqlStatement);
				if (sqlType.equalsIgnoreCase("select") || sqlType2.equalsIgnoreCase("desc")){
			         ResultSet resultSet = statement.executeQuery(sqlStatement);
			         message =  GeneralUtils.getHtmlRows(resultSet);
			         statement.close();
			     }else{
			    	 int i = statement.executeUpdate(sqlStatement);
			    	 statement.close();
			    	 message =  "<tr><td>" +
		               "The statement executed successfully.<br>" +
		               i + " row(s) affected." +
		             "</td></tr>";
			     }
			}catch(Exception ex){
				ex.printStackTrace();
				logger.debug(ex.getMessage());
				if(statement != null){
					try {
						statement.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if(ex.getMessage().contains("denied ")){
					message =  "<tr><td>Error executing the SQL statement: <br>"+
				               "Access denied!"+
				             "</td></tr>";
				}else{
					message =  "<tr><td>Error executing the SQL statement: <br>"+
				               ex.getMessage()+
				             "</td></tr>";
				}
			}
		}
		model.addAttribute("sqlStatement", sqlStatement);
		redirectAttributes.addFlashAttribute("sqlStatement", sqlStatement);
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:query";
	}
	
	@RequestMapping(value="/query/export", method = RequestMethod.POST)
	public String runSqlExportPage (@RequestParam(value="sqlStatement", required=true) String sqlStatement,
			final RedirectAttributes redirectAttributes, Model model, HttpServletResponse response) {
		Statement statement = null;
		String message = null;
		if(sqlStatement != null && !sqlStatement.trim().isEmpty()){
			try{
				sqlStatement = sqlStatement.trim();
				if(sqlStatement.charAt(sqlStatement.length()-1) != ';'){
					sqlStatement += ';';
				}
				Class.forName(driver);
				Connection connection = DriverManager.getConnection(url, user, password);
				statement = connection.createStatement();
				String sqlType = sqlStatement.substring(0, 6);
				String sqlType2 = sqlStatement.substring(0, 4);
				logger.debug("loading sql :"+ sqlStatement);
				if (sqlType.equalsIgnoreCase("select") || sqlType2.equalsIgnoreCase("desc")){
			         ResultSet resultSet = statement.executeQuery(sqlStatement);
			         Workbook wb =  ExcelFileHelper.writeToFile(resultSet);
			         statement.close();
			         if(wb != null){
			         	response.setContentType("application/vnd.ms-excel");
			         	response.addHeader("Content-Disposition", "attachment; filename=data.xls");
			             try{
			             	wb.write(response.getOutputStream());
			                response.getOutputStream().flush();
			             } 
			             catch (IOException ex) {
			                 ex.printStackTrace();
			             }
			         }
			         return null;
			     }else{
			    	 int i = statement.executeUpdate(sqlStatement);
			    	 statement.close();
			    	 message =  "<tr><td>" +
		               "The statement executed successfully.<br>" +
		               i + " row(s) affected." +
		             "</td></tr>";
			     }
			}catch(Exception ex){
				ex.printStackTrace();
				logger.debug(ex.getMessage());
				if(statement != null){
					try {
						statement.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if(ex.getMessage().contains("denied ")){
					message =  "<tr><td>Error executing the SQL statement: <br>"+
				               "Access denied!"+
				             "</td></tr>";
				}else{
					message =  "<tr><td>Error executing the SQL statement: <br>"+
				               ex.getMessage()+
				             "</td></tr>";
				}
				
			}
		}
		model.addAttribute("sqlStatement", sqlStatement);
		redirectAttributes.addFlashAttribute("sqlStatement", sqlStatement);
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:query";
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
