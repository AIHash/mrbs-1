package com.wafersystems.mrbs.web.group;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.service.user.UserGroupService;
import com.wafersystems.mrbs.service.user.UserService;
import com.wafersystems.mrbs.vo.user.UserGroup;
import com.wafersystems.mrbs.vo.user.UserInfo;

@Controller
@RequestMapping("/group")
public class UserGroupController {
	private static final Logger logger = LoggerFactory.getLogger(UserGroupController.class);

	private UserService userService;
	private UserGroupService groupService;

	@RequestMapping(value = "init", method = RequestMethod.GET)
	public String init() {
		return "group/main";
	}

	@RequestMapping(value = "list")
	public String list(HttpServletRequest request, UserGroup group) {
		PageSortModel psm = new PageSortModel(request);
		List<UserGroup> datas = groupService.getUserGroupByPro(psm, group);
		request.setAttribute(GlobalConstent.REPORT_DATA, datas);
		return "group/list";
	}

	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String addInit(Model model) {
		List<UserInfo> list = userService.getAllUnifiedUser();
		model.addAttribute("ALL_UNIFIEDUSER", list);
		return "group/add";
	}

	@RequestMapping(value = "saveOrUpdate", method = RequestMethod.POST)
	public String saveOrUpdate(UserGroup group,String[] userId) {
		StringBuilder builder = new StringBuilder("增加用户组，");
		builder.append("用户组名为：").append(group.getName()).
			append( ", 备注：").append(group.getRemark()).
			append(", 成员为：").append("[");
		for (String usr : userId) {
			builder.append(usr).append(", ");
		}
		builder.append("]");

		logger.debug(builder.toString());
		groupService.saveUserGroupAndMember(group, userId);
		return "redirect:init";
	}

	@RequestMapping(value = "modify", method = RequestMethod.GET)
	public String modify(short id, Model model){
		UserGroup group = groupService.getUserGroup(id);
		List<UserInfo> list = userService.getAllUnifiedUser();
		model.addAttribute("ALL_UNIFIEDUSER", list);
		model.addAttribute("GROUP", group);
		return "group/edit";
	}

	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String delete(UserGroup userGroup){
		groupService.delUserGroup(userGroup);
		return "redirect:init";
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Resource
	public void setGroupService(UserGroupService groupService) {
		this.groupService = groupService;
	}

}
