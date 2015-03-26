package com.wafersystems.mrbs.web.user;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.exception.BaseException;
import com.wafersystems.mrbs.exception.CreateFailedException;
import com.wafersystems.mrbs.exception.DeleteFailedException;
import com.wafersystems.mrbs.exception.InfoExistException;
import com.wafersystems.mrbs.exception.UpdateFailedException;
import com.wafersystems.mrbs.service.user.DepartmentService;
import com.wafersystems.mrbs.service.user.UserService;
import com.wafersystems.mrbs.service.user.UserTypeService;
import com.wafersystems.mrbs.tag.MessageTag;
import com.wafersystems.mrbs.vo.FinalUnifiedUser;
import com.wafersystems.mrbs.vo.right.Role;
import com.wafersystems.mrbs.vo.user.Department;
import com.wafersystems.mrbs.vo.user.UserInfo;
import com.wafersystems.util.Excel;
import com.wafersystems.util.MD5Util;
import com.wafersystems.util.Pinyin4jUtil;
import com.wafersystems.util.StrUtil;

@Controller
@RequestMapping(value="/user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	private UserService userService;
	private DepartmentService departmentService;
	private UserTypeService userTypeService;

	@RequestMapping(value="/self", method=RequestMethod.GET)
	public String self() {
		return "user/self";
	}
	
	@RequestMapping(value="/changepassword", method=RequestMethod.POST)
	public String changepassword(@RequestParam String OldPassword, @RequestParam String NewPassword, HttpSession session){
		try{
			UserInfo user = (UserInfo)session.getAttribute(GlobalConstent.USER_LOGIN_SESSION);
			userService.changePassword(user, OldPassword, NewPassword);
			//重置session
			UserInfo newUser = userService.getUserByUserId(user.getUserId());
			session.removeAttribute(GlobalConstent.USER_LOGIN_SESSION);
			session.setAttribute(GlobalConstent.USER_LOGIN_SESSION, newUser);
			session.setAttribute("returnmessage", MessageTag.getMessage("admin.changepasswordok"));
		}catch(BaseException e){
			logger.debug("用户修改密码错误");
			session.setAttribute("returnmessage", MessageTag.getMessage("admin.changePasswordFalse"));
		}
		return "redirect:/user/self";
	}
	
	@RequestMapping(value="/main", method=RequestMethod.GET)
	public String main() {
		return "user/main";
	}
	
	@RequestMapping(value="/queryUserFromMain", method=RequestMethod.GET)
	public String queryUserFromMain(HttpServletRequest request, Model model) {
		request.getSession().removeAttribute("parentNodeNo");
		return "user/queryUser";
	}
	
	@RequestMapping(value="/queryUser", method=RequestMethod.GET)
	public String queryUser(HttpServletRequest request, Model model) {
		//request.getSession().removeAttribute("parentNodeNo");
		return "user/queryUser";
	}
	
	@RequestMapping(value="/queryUserList", method=RequestMethod.GET)
	public String queryUserList(HttpServletRequest request, Model model) {
		try{
			String userId=request.getParameter("logon_name");
			String userName=new String(request.getParameter("user_name").toString().getBytes("iso8859_1"), "utf-8");
			String parentDeptCode = (String)request.getSession().getAttribute("parentNodeNo");			
			Department dept = departmentService.getDepartmentByNodeCode(parentDeptCode);
			if(StrUtil.isEmptyStr(parentDeptCode)){
				UserInfo user = (UserInfo)request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
				dept = user.getDeptId();
			}else{
				dept = departmentService.getDepartmentByNodeCode(parentDeptCode);
			}
			
			PageSortModel psm = new PageSortModel(request);
			//不输入查询条件时，数据默认为该部门下所有直属用户数据
			List<UserInfo> datas=null;				
			if((userName!=null&&userName!="")||(userId!=null&&userId!="")){
				datas=userService.getUserList(psm, userId, userName);
			}else{
				datas=userService.getUserListByDeptId(psm, dept);
			}				
			request.setAttribute(GlobalConstent.REPORT_DATA, datas);
		}catch(Exception e){
			logger.error("UserController queryUserList error",e);
			e.printStackTrace();			
		}catch(Throwable e){
			logger.error("UserController queryUserList error",e);
			e.printStackTrace();
		}
		return "user/list";
	}

	@RequestMapping(value="/tree", method=RequestMethod.GET)
	public String tree(Model model, HttpSession session) {
		UserInfo user = (UserInfo)session.getAttribute(GlobalConstent.USER_LOGIN_SESSION);
		if(user.getUserType().getValue().equals(GlobalConstent.USER_TYPE_MEETING_ADMIN)){
			Department department = departmentService.getDepartmentByNodeCode(GlobalConstent.PARENT_DEPARTMENT_CODE);
			model.addAttribute("deptId", department.getId());
			model.addAttribute("deptName", department.getName());
			model.addAttribute("deptNodeCode", department.getDeptcode());
			model.addAttribute("deptTree", departmentService.getSubDepartmentLikeNodeCode(GlobalConstent.PARENT_DEPARTMENT_CODE));
		}else{
			model.addAttribute("deptId", user.getDeptId().getId());
			model.addAttribute("deptName", user.getDeptId().getName());
			model.addAttribute("deptNodeCode", user.getDeptId().getDeptcode());
			model.addAttribute("deptTree", departmentService.getSubDepartmentLikeNodeCode(user.getDeptId().getDeptcode()));
		}
		return "user/tree";
	}
	
	@RequestMapping(value="/editUser")
	public String editUser(HttpServletRequest request, HttpServletResponse response, UserInfo user) throws IOException
	{
		try {
			UserInfo temp = userService.getUserByUserId(user.getUserId());
			if(user.getPassword() != null && user.getPassword().trim() != "")
				temp.setPassword(MD5Util.encodeNoEqualSign(user.getPassword()));
			if(user.getName() !=null){
				temp.setName(user.getName());
				temp.setPyCode(Pinyin4jUtil.getFirstUpperStr(user.getName()));
			}
			if(user.getMail() !=null)
				temp.setMail(user.getMail());
			if(user.getMobile() !=null)
				temp.setMobile(user.getMobile());
			
			userService.updateUser(temp);
			//更新完成后，重置
			temp = userService.getUserByUserId(user.getUserId());
			request.getSession().setAttribute(GlobalConstent.USER_LOGIN_SESSION, temp);
			response.getWriter().write("succ");
		} catch (Exception e) {
			response.getWriter().write("fail");
			e.printStackTrace();
		} 
		return null;
	}
	
	/**资料修改页面*/
	@RequestMapping(value="/getUserInfo")
	public String getUserInfo(HttpSession session,HttpServletRequest request)
	{	
		UserInfo user = (UserInfo)session.getAttribute(GlobalConstent.USER_LOGIN_SESSION);		
		request.setAttribute("userinfo", user);
		return "user/editUser";
	}

	@RequestMapping(value="/list")
	public String list(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();

		String parentNode = request.getParameter("nodeNo");
		if(StrUtil.isEmptyStr(parentNode))
			parentNode = (String)session.getAttribute("parentNodeNo");
		List<UserInfo> datas;
		Department dept = null;
		if(StrUtil.isEmptyStr(parentNode)){
			UserInfo user = (UserInfo)session.getAttribute(GlobalConstent.USER_LOGIN_SESSION);
			dept = user.getDeptId();
		}else{
			dept = departmentService.getDepartmentByNodeCode(parentNode);
		}

		logger.debug("DepartMent NodeCode : " + dept.getDeptcode());
		PageSortModel psm = new PageSortModel(request); //eXtremeTable
		datas = userService.getUserListByDeptId(psm, dept);
		session.setAttribute("parentNodeNo", dept.getDeptcode());
		session.setAttribute(GlobalConstent.REPORT_DATA, datas);
		
		//是否为主级目录人们医院下的第一层目录（只有第一层目录才有导出button）
		boolean flag = false;
		if(dept!=null&&dept.getParentDeptCode().equals(GlobalConstent.PARENT_DEPARTMENT_CODE)){
			flag = true;
		}
		model.addAttribute("showButton",flag );
		return "user/list";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute("USER_TYPE", userTypeService.getAll());
		return "user/add";
	}

	@RequestMapping(value="/create")
	public String create(String userId,Short allowedOrRefusedFlag,
			String userName,String userPassword,
			Short userType,Short state,
			String username,String telephone,
			String mobile,String mail,
			String terminal,Short gender,
			HttpSession session,HttpServletRequest request,Model model,HttpServletResponse response) {
		try {
			String parentNodeNo = (String)session.getAttribute("parentNodeNo");
			Department dept = departmentService.getDepartmentByNodeCode(parentNodeNo);
			UserInfo user = new UserInfo();
			user.setDeptId(dept);
			user.setCreateTime(new Date());
			user.setCreator((UserInfo)session.getAttribute(GlobalConstent.USER_LOGIN_SESSION));
			user.setGender(gender);
			user.setMail(mail);
			user.setMobile(mobile);
			user.setUsername(username);
			user.setTelephone(telephone);
			user.setName(userName);
			user.setPyCode(Pinyin4jUtil.getFirstUpperStr(userName));
			user.setPassword(userPassword);
			user.setState(state);
			user.setUserId(userId.toLowerCase());
			user.setAllowedOrRefusedFlag(allowedOrRefusedFlag);
			user.setUserType(userTypeService.getUserTypeByValue(userType));
			Role role = new Role();
			if(userType == GlobalConstent.USER_TYPE_PROFESSIONAL){
				role.setId(GlobalConstent.USER_ROLE_PROFESSIONAL);
			}else if(userType == GlobalConstent.USER_TYPE_UNION){
				role.setId(GlobalConstent.USER_ROLE_UNION);
			}else if(userType == GlobalConstent.USER_TYPE_SUPER_ADMIN){
				role.setId(GlobalConstent.USER_ROLE_SUPER_ADMIN);
			}else if(userType == GlobalConstent.USER_TYPE_MEETING_ADMIN){
				role.setId(GlobalConstent.USER_ROLE_MEETING_ADMIN);
			}else if(userType == GlobalConstent.USER_TYPE_DEPT_ADMIN){
				role.setId(GlobalConstent.USER_ROLE_DEPT_ADMIN);
			}
			user.setRole(role);
			user.setVideoPoint(terminal);
			userService.saveUser(user);
			
			logger.debug("DepartMent NodeCode : " + dept.getDeptcode());
			PageSortModel psm = new PageSortModel(request); //eXtremeTable
			List<UserInfo> datas = userService.getUserListByDeptId(psm, dept);
			session.setAttribute("parentNodeNo", dept.getDeptcode());
			session.setAttribute(GlobalConstent.REPORT_DATA, datas);
			
			//是否为主级目录人们医院下的第一层目录（只有第一层目录才有导出button）
			boolean flag = false;
			if(dept!=null&&dept.getParentDeptCode().equals(GlobalConstent.PARENT_DEPARTMENT_CODE)){
				flag = true;
			}
			model.addAttribute("showButton",flag );
			try {
				response.getWriter().write("adduser_successful");
				response.getWriter().flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			//return "user/list";
		}catch(BaseException e){
			if(e instanceof InfoExistException){
				session.setAttribute("message", MessageTag.getMessage("comm.infoexistexception","admin.user"));
			}else if(e instanceof CreateFailedException){
				session.setAttribute("message", MessageTag.getMessage("comm.failedexception","admin.user_create"));
			}else{
				session.setAttribute("message", MessageTag.getMessage("comm.operation_result_failure"));
			}
			try {
				response.getWriter().write("adduser_failed");
				response.getWriter().flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}

	@RequestMapping(value="/edit/{userId}", method=RequestMethod.GET)
	public String edit(@PathVariable String userId, Model model) {
		UserInfo user = userService.getUserByUserId(userId);
		model.addAttribute("user", user);
		model.addAttribute("USER_TYPE", userTypeService.getAll());
		return "user/edit";
	}
	
	@RequestMapping(value="/update/{userId}")
	public String update(String userId,Short allowedOrRefusedFlag,
			String userName, String password,
			Short userType, Short state,
			String username, String telephone,
			String mobile, String mail,
			String terminal, Short gender,
			HttpSession session,HttpServletRequest request,Model model,HttpServletResponse response) {
		try {
			UserInfo user = userService.getUserByUserId(userId);
			if(user != null){
				user.setName(userName);
				user.setPyCode(Pinyin4jUtil.getFirstUpperStr(userName));
				user.setCreateTime(new Date());
				user.setCreator((UserInfo)session.getAttribute(GlobalConstent.USER_LOGIN_SESSION));
				user.setGender(gender);
				user.setUsername(username);
				user.setTelephone(telephone);
				user.setMail(mail);
				user.setMobile(mobile);
				user.setAllowedOrRefusedFlag(allowedOrRefusedFlag);
				if(StringUtils.isNotBlank(password))
					user.setPassword(password);

				user.setState(state);
				user.setUserType(userTypeService.getUserTypeByValue(userType));
				user.setVideoPoint(terminal);
				//根据用户类型修改用户角色
				Role role = new Role();
				if(userType == GlobalConstent.USER_TYPE_PROFESSIONAL){
					role.setId(GlobalConstent.USER_ROLE_PROFESSIONAL);
				}else if(userType == GlobalConstent.USER_TYPE_UNION){
					role.setId(GlobalConstent.USER_ROLE_UNION);
				}else if(userType == GlobalConstent.USER_TYPE_SUPER_ADMIN){
					role.setId(GlobalConstent.USER_ROLE_SUPER_ADMIN);
				}else if(userType == GlobalConstent.USER_TYPE_MEETING_ADMIN){
					role.setId(GlobalConstent.USER_ROLE_MEETING_ADMIN);
				}else if(userType == GlobalConstent.USER_TYPE_DEPT_ADMIN){
					role.setId(GlobalConstent.USER_ROLE_DEPT_ADMIN);
				}
				user.setRole(role);
				userService.updateUser(user);
			}
			
			Department dept = user.getDeptId();
			logger.debug("DepartMent NodeCode : " + dept.getDeptcode());
			PageSortModel psm = new PageSortModel(request); //eXtremeTable
			List<UserInfo> datas = userService.getUserListByDeptId(psm, dept);
			session.setAttribute("parentNodeNo", dept.getDeptcode());
			session.setAttribute(GlobalConstent.REPORT_DATA, datas);
			
			//是否为主级目录人们医院下的第一层目录（只有第一层目录才有导出button）
			boolean flag = false;
			if(dept!=null&&dept.getParentDeptCode().equals(GlobalConstent.PARENT_DEPARTMENT_CODE)){
				flag = true;
			}
			model.addAttribute("showButton",flag );

			try {
				response.getWriter().write("editdept_successful");
				response.getWriter().flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}catch(BaseException e){
			if(e instanceof InfoExistException){
				session.setAttribute("message", MessageTag.getMessage("comm.infoexistexception","admin.user"));
			}else if(e instanceof UpdateFailedException){
				session.setAttribute("message", MessageTag.getMessage("comm.failedexception","admin.user_update"));
			}else{
				session.setAttribute("message", MessageTag.getMessage("comm.operation_result_failure"));
			}
			try {
				response.getWriter().write("editdept_failed");
				response.getWriter().flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		return null;
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(HttpSession session, HttpServletRequest request, Model model,HttpServletResponse response)throws IOException {
		try{		
            String str=request.getParameter("userIDs");
			String[] ids = str.split(","); 
			if(ids.length > 0){					
				for(String userId : ids){
					UserInfo user = userService.getUserByUserId(userId);
					userService.delUser(user);
				}
			    response.getWriter().write("success");
			    response.getWriter().flush();
			}								
			return null;
			//return list(request, model);
		}catch(BaseException e){
			if(e instanceof DeleteFailedException){
				session.setAttribute("message", MessageTag.getMessage("comm.failedexception","admin.user_delete"));
			}else{
				session.setAttribute("message", MessageTag.getMessage("comm.operation_result_failure"));
			}
			return "redirect:/message.jsp";
		}
	}
	
	/**
	 * 是否允许用户参加会议
	 * @param flag 标记 Y：是，N：否
	 * @param session
	 * @param request
	 * @param model
	 * @author wafer-rengeng
	 * @create Date 2011/09/27
	 * @return
	 */
	@RequestMapping(value="/allowedOrRefused", method=RequestMethod.POST)
	public String allowedOrRefused(HttpSession session, HttpServletRequest request, Model model,HttpServletResponse response) {
		String flag=request.getParameter("flag");
		try{
			String str=request.getParameter("userIDs");
			String[] ids = str.split(","); 
			logger.debug("Enter UserController.allowedOrRefused  flag = "+flag);
			if(ids.length > 0){
				for(String userId : ids){
					UserInfo user = userService.getUserByUserId(userId);
					if("Y".equals(flag)){
						user.setAllowedOrRefusedFlag(GlobalConstent.USER_STATE_ON);
						userService.updateUser(user);
					}else if("N".equals(flag)){
						user.setAllowedOrRefusedFlag(GlobalConstent.USER_STATE_OFF);
						userService.updateUser(user);
					}
				}
			}
			response.getWriter().write("success");
		    response.getWriter().flush();
		    return null;
		}catch(Exception e){
			logger.debug("error UserController.allowedOrRefused  flag = "+flag,e);
			if(e instanceof DeleteFailedException){
				if("Y".equals(flag)){
					session.setAttribute("message", MessageTag.getMessage("comm.failedexception","comm.allowed"));
				}else if("N".equals(flag)){
					session.setAttribute("message", MessageTag.getMessage("comm.failedexception","comm.refused"));
				}
			}else{
				
			}
			return "redirect:/message.jsp";
		}
	}
	
	//得到用户的Excel形式
	@RequestMapping(value="/getUserExcelForm")
	public String getUserExcelForm(HttpServletRequest request,HttpServletResponse response)
	{
		// 从缓冲中获取列表类型的数据信息，然后转换为Excel格式报表的数据类型。
		String parentDeptCode = (String)request.getSession().getAttribute("parentNodeNo");
		
		Department department = departmentService.getDepartmentByNodeCode(parentDeptCode);
		
		String[] data = null;
		
//		String tableName = MessageTag.getMessage("admin.department_unified_admin");
		
		Excel excel = null;
		
		try{
			List<FinalUnifiedUser> deptTempData = departmentService.getFinalUnifiedUserLikeParentNodeCode(parentDeptCode);
			
			data = new String[deptTempData.size()];
			  			
			StringBuffer temp = new StringBuffer();
            
			//获得部门的级别
			int maxDeptCode = departmentService.getMaxDeptCode(parentDeptCode);
			int deptLevel;
			if(maxDeptCode==0){
				deptLevel=0;
			}else{
				deptLevel =   maxDeptCode / 3 - parentDeptCode.length()/3;
			}
				
			//临时MAP
			Map<String,String> deptNameMap  = new HashMap<String,String>();
			int rowCount = 0;
			//封装报表中所填数据
			for (int i = 0; i < deptTempData.size(); i++)
			{	
				
				temp.delete(0, temp.length());
				
				String deptName = deptTempData.get(i).getDeptName();//获得部门名称
				
				deptNameMap.put(deptTempData.get(i).getDeptNode(), deptName);
				
				int deptNodeLength = deptTempData.get(i).getDeptNode().length() /3 ;
				
				int _deptLength = deptNodeLength -  parentDeptCode.length() / 3 -1;
				
				 for(int j=0; j<deptLevel; j++)
				 {					 
					 if(_deptLength == j){						 
						 temp.append(Excel.SPLIT_STR).append(deptName);
					 }else if(_deptLength > j){						 
						 String parentNode = deptTempData.get(i).getDeptNode().substring(0, 3 * (deptNodeLength - _deptLength + j) );						 
						 temp.append(Excel.SPLIT_STR).append(deptNameMap.get(parentNode));
					 }else{
						 temp.append(Excel.SPLIT_STR).append(" ");
					 }
					 
				 }
				 
				 temp.append(Excel.SPLIT_STR);

				int userType = deptTempData.get(i).getUserType();				
				
			    String userTypeValue = "";
			    
			    switch(userType){
			    case 1:
			    	userTypeValue="超级管理员";
			    	break;
			    case 2:
			    	userTypeValue="会议管理员";
			    	break;
			    case 3:
			    	userTypeValue="科室管理员";
			    	break;
			    case 4:
			    	userTypeValue="专家用户";
			    	break;
			    case 5:
			    	userTypeValue="共同体用户";
			    	break;
			    	
			    }
			    //获取用户的状态，若为已删除状态，则不导出
			    short userState=0;
			    if(!StrUtil.isEmptyStr(userTypeValue)){
			    	userState=userService.getUserByUserId(deptTempData.get(i).getUserId()).getState();
			    }			    			    
			    int hasSubDept=0;
			    //增加两个判断条件，以提高代码执行效率。判断条件分别表示：该部门不是最小级别的部门，且该部门下没有用户
			    if( maxDeptCode!=0&&StrUtil.isEmptyStr(userTypeValue)){
			    	//判断部门是否有子部门，hasSubDept=0时，则没有子部门
			    	try{
			    		hasSubDept = departmentService.isParentDept(deptTempData.get(i).getDeptNode());
			    	}catch(Exception e){
			    		logger.error("判断部门是否有子部门出错，导致导出excel出错", e);
			    		e.printStackTrace();
			    	}catch(Throwable e){
						logger.error("判断部门是否有子部门出错，导致导出excel出错",e);
						e.printStackTrace();
					}
			    }			    
			    //userTypeValue有可能为空,删除状态的用户也不导出
			    if(!StrUtil.isEmptyStr(userTypeValue)&&userState>=0){
			    	temp.append(StrUtil.formatStr(deptTempData.get(i).getUserId())+" ").append(Excel.SPLIT_STR)
					.append(StrUtil.formatStr(deptTempData.get(i).getUserName())+" ").append(Excel.SPLIT_STR)
					.append(StrUtil.formatStr(userTypeValue)+" ").append(Excel.SPLIT_STR)
					.append(StrUtil.formatStr(deptTempData.get(i).getLianxiren())+" ").append(Excel.SPLIT_STR)
					.append(StrUtil.formatStr(deptTempData.get(i).getTelephone())+" ").append(Excel.SPLIT_STR)
					.append(StrUtil.formatStr(deptTempData.get(i).getMoblie())+" ").append(Excel.SPLIT_STR)
					.append(StrUtil.formatStr(deptTempData.get(i).getMail())+" ").append(Excel.SPLIT_STR);
			    	data[rowCount] = temp.toString().substring(Excel.SPLIT_STR.length());
			    	rowCount++;
			    	//增加判断，存在子部门，则不添加空白行；删除状态的用户，不添加空白行
			    }else if(userState>=0&&hasSubDept==0){
			    	temp.append(" ").append(Excel.SPLIT_STR)
					.append(" ").append(Excel.SPLIT_STR)
					.append(" ").append(Excel.SPLIT_STR)
					.append(" ").append(Excel.SPLIT_STR)
					.append(" ").append(Excel.SPLIT_STR)
					.append(" ").append(Excel.SPLIT_STR)
					.append(" ").append(Excel.SPLIT_STR);
			    	data[rowCount] = temp.toString().substring(Excel.SPLIT_STR.length());
			    	rowCount++;
			    }
			    
			}

			// 加载demo.xls模板内容。
			excel = new Excel("TreeEnterpriseAddressBook.xls");
			
			// 设置当前工作薄为第一个sheet页。
			//excel.setCurrentSheet("用户基本信息报表");
			excel.setCurrentSheet(1);
			
			// 合并第一行

//			excel.mergeCell(1, 1, 1, deptLevel + 6);
//			excel.insert(1, 1, tableName);
			
			//动态生成列标题（根据部门最大级别数）
			for (int i = 1; i <= deptLevel; i++)
			{
					excel.createColumn((short) (i), 1);			
				    excel.insert(2, i, i + MessageTag.getMessage("admin.department_unified_level"));				
			}
		    
			if(deptLevel >= 0){
				excel.createColumn((short) (deptLevel + 1), 2);
				excel.insert(2, deptLevel + 1, MessageTag.getMessage("admin.logon_name"));
				excel.createColumn((short) (deptLevel + 2), 2);
				excel.insert(2, deptLevel + 2, MessageTag.getMessage("admin.user_name"));
				excel.createColumn((short) (deptLevel + 3), 2);
				excel.insert(2, deptLevel + 3, MessageTag.getMessage("admin.user_type"));
				excel.createColumn((short) (deptLevel + 4), 2);
				excel.insert(2, deptLevel + 4, MessageTag.getMessage("admin.user_username"));
				excel.createColumn((short) (deptLevel + 5), 2);
				excel.insert(2, deptLevel + 5, MessageTag.getMessage("admin.user_tel"));
				excel.createColumn((short) (deptLevel + 6), 2);
				excel.insert(2, deptLevel + 6, MessageTag.getMessage("admin.user_mobile"));
				excel.createColumn((short) (deptLevel + 7), 2);
				excel.insert(2, deptLevel + 7, MessageTag.getMessage("admin.user_email"));
			}
			    
			// 插入统计数据
			excel.insert(3, data);
			excel.mergeCell(1, 1, 1, deptLevel + 7);
			excel.insert(1, 1, department.getName());
			//合并单位列
			for (int i = 1; i <= deptLevel; i++){
				excel.autoMergeColumnNotNull(3, (short)i);
			}

			response.setContentType("Application/Octet-Stream");
			
			response.setHeader("Content-Disposition", "attachment; filename=" + new String(department.getName().getBytes("GBK"), "ISO8859-1") + ".xls");

			OutputStream os = response.getOutputStream();
			
			excel.getContent().write(os);
			os.flush();
			os.close();
		}
		
		catch (Exception e) {
			logger.error("终端用户报表下载错误：", e);
		}
			
		return null;
		
	}
	
	//ajax方法，检测用户输入Id是否可用
	@RequestMapping(value="/testId", method=RequestMethod.POST)
	public String testId(HttpServletRequest request,HttpServletResponse response)  throws IOException{
		String userId=request.getParameter("userIDs");
		UserInfo exsit = userService.getUserByUserId(userId);
		if(exsit!=null){
			response.getWriter().write("failed");
			response.getWriter().flush();
			return null;
		}
		response.getWriter().write("success");
		response.getWriter().flush();
		return null;
	}
		
	@Resource(type = UserService.class)
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Resource(type = DepartmentService.class)
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@Resource(type = UserTypeService.class)
	public void setUserTypeService(UserTypeService userTypeService) {
		this.userTypeService = userTypeService;
	}

}