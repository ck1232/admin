package com.admin.user.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.admin.helper.GeneralUtils;
import com.admin.user.service.UserService;
import com.admin.user.vo.UserVO;
import com.admin.validator.UserFormValidator;


@Controller  
@EnableWebMvc
@Scope("request")
@RequestMapping(value = "/admin")
public class UserController {
	private static final Logger logger = Logger.getLogger(UserController.class);
	
	private UserService userService;
	private UserFormValidator userFormValidator;
	
	@Autowired
	public UserController(UserService userService,
			UserFormValidator userFormValidator) {
		this.userService = userService;
		this.userFormValidator = userFormValidator;
	}
	
	
	@RequestMapping(value = "/listUser", method = RequestMethod.GET)  
    public String listUser() {  
    	logger.debug("loading listUser");
        return "listUser";  
    }  
	
	@RequestMapping(value = "/getUserList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getUserList() {
		logger.debug("getting user list");
		List<UserVO> userList = userService.getAllUsers();
		return GeneralUtils.convertListToJSONString(userList);
	}
	
	@RequestMapping(value = "/createUser", method = RequestMethod.GET)
    public String showAddUserForm(Model model) {  
    	logger.debug("loading showAddUserForm");
    	UserVO userVO = new UserVO();
    	
    	userVO.setEnabled("1");
    	model.addAttribute("userForm", userVO);
        return "createUser";  
    }  
	
	@InitBinder("userForm")
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(userFormValidator);
	}
	
	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("userForm") @Validated UserVO userVO, 
    		BindingResult result, Model model, final RedirectAttributes redirectAttributes) {  
    	
		logger.debug("saveUser() : " + userVO.toString());
		if (result.hasErrors()) {
			return "createUser";
		} else {
			
			userService.saveUser(userVO);
			redirectAttributes.addFlashAttribute("css", "success");
			redirectAttributes.addFlashAttribute("msg", "User added successfully!");
		}
		
		
        return "redirect:listUser";  
    }  
	
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	public String deleteUser(@RequestParam(value = "checkboxId", required=false) List<String> ids,
			final RedirectAttributes redirectAttributes) {
		if(ids == null || ids.size() < 1){
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "Please select at least one record!");
			return "redirect:listUser";
		}
		for (String id : ids) {
			userService.deleteUser(Long.parseLong(id));
			logger.debug("deleted "+ id);
		}
		redirectAttributes.addFlashAttribute("css", "success");
		redirectAttributes.addFlashAttribute("msg", "User(s) deleted successfully!");
		return "redirect:listUser";
	}
	
	@RequestMapping(value = "/updateUser", method = RequestMethod.POST)
	public String getUserToUpdate(@RequestParam("editBtn") String id, Model model) {
		
		UserVO userVO = userService.findById(Long.parseLong(id));
		logger.debug("Loading update user page for " + userVO.toString());
		
		model.addAttribute("userForm", userVO);
		return "updateUser";
	}
	
	@RequestMapping(value = "/updateUserById/{userid}", method = RequestMethod.GET)
	public String getUserToUpdateByUserId(@PathVariable String userid, Model model) {
		UserVO userVO = userService.findByUserName(userid);
		logger.debug("Loading update user page for " + userVO.toString());
		model.addAttribute("userForm", userVO);
		return "updateUser";
	}
	
	@RequestMapping(value = "/resetpassword", method = RequestMethod.POST)
	public String resetPassword(@RequestParam(value="userid", required=false) String userid, @RequestParam(value="password", required=false) String password, RedirectAttributes redirectAttributes) {
		userService.resetPassword(userid, password);
		redirectAttributes.addFlashAttribute("css", "success");
		redirectAttributes.addFlashAttribute("msg", "Password reset!");
		return "redirect:updateUserById/"+userid;
	}
	
	@RequestMapping(value = "/updateUserToDb", method = RequestMethod.POST)
	public String updateUser(@ModelAttribute("userForm") @Validated UserVO userVO,
			BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
		
		logger.debug("updateUser() : " + userVO.toString());
		
		if (result.hasErrors()) {
			return "updateUser";
		} else {
			if(userVO.getEnabledBoolean()){
				userVO.setEnabled("Y");
			}else{
				userVO.setEnabled("N");
			}
			userService.updateUser(userVO);
			redirectAttributes.addFlashAttribute("css", "success");
			redirectAttributes.addFlashAttribute("msg", "User updated successfully!");
		}
		
		return "redirect:listUser";
	}
	
	@RequestMapping(value = "/viewUser", method = RequestMethod.POST)
	public String viewUser(@RequestParam("viewBtn") String id, Model model) {
		logger.debug("id = " + id);
		UserVO userVO = userService.findById(Long.parseLong(id));
		if (userVO == null) {
			model.addAttribute("css", "danger");
			model.addAttribute("msg", "User not found");
		}
		model.addAttribute("user", userVO);

		return "viewUser";

	}
	
}