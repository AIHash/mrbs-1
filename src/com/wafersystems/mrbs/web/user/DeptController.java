package com.wafersystems.mrbs.web.user;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.wafersystems.mrbs.exception.InfoExistException;
import com.wafersystems.mrbs.exception.UpdateFailedException;
import com.wafersystems.mrbs.listener.BaseDataServiceListener;
import com.wafersystems.mrbs.service.user.DepartmentService;
import com.wafersystems.mrbs.tag.MessageTag;
import com.wafersystems.mrbs.vo.user.Department;
import com.wafersystems.mrbs.vo.user.UserInfo;
import com.wafersystems.util.Pinyin4jUtil;
import com.wafersystems.util.StrUtil;

@Controller
@RequestMapping(value="/dept")
public class DeptController {

	private static final Logger logger = LoggerFactory.getLogger(DeptController.class);
	private DepartmentService departmentService;
	private BaseDataServiceListener serviceListener;

	@RequestMapping(value="/list")
	public String list(HttpSession session, HttpServletRequest request) {
		String parentNode = request.getParameter("nodeNo");
		String parentNodeNo;
		List<Department> datas;
		if(StrUtil.isEmptyStr(parentNode)){
			UserInfo user = (UserInfo)session.getAttribute(GlobalConstent.USER_LOGIN_SESSION);
			parentNodeNo = user.getDeptId().getDeptcode();
		}else{
			parentNodeNo = parentNode;
		}
		logger.debug("ParentNodeCode : " + parentNodeNo);
		PageSortModel psm = new PageSortModel(request); //eXtremeTable
		datas = departmentService.getSubDepartmentOfNodeCode(psm, parentNodeNo);
		session.setAttribute("parentNodeNo", parentNodeNo);
		session.setAttribute(GlobalConstent.REPORT_DATA, datas);
		return "dept/list";
	}

	@RequestMapping(value="/main", method=RequestMethod.GET)
	public String main() {
		return "dept/main";
	}

	@RequestMapping(value="/mainForDelete", method=RequestMethod.GET)
	public String mainForDelete(HttpSession session, HttpServletRequest request) {
		String parentNodeNo = (String)session.getAttribute("parentNodeNo");
		request.setAttribute("parentNodeNo", parentNodeNo);
		return "dept/mainForDelete";
	}

	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String add() {
		return "dept/add";
	}

	@RequestMapping(value="/create")
	public String create(String deptName,String deptRemark, HttpSession session, HttpServletRequest request,HttpServletResponse response) throws IOException {
		try{
			String parentNodeNo = (String)session.getAttribute("parentNodeNo");
			Department dept = new Department();
			dept.setName(deptName);
			dept.setPyCode(Pinyin4jUtil.getFirstUpperStr(deptName));
			dept.setParentDeptCode(parentNodeNo);
			dept.setRemark(deptRemark);
			boolean flag = departmentService.saveDepartment(dept);
			List<Department> datas;
			request.setAttribute("freshTree", "true");
			PageSortModel psm = new PageSortModel(request);
			datas = departmentService.getSubDepartmentOfNodeCode(psm, parentNodeNo);
			session.setAttribute(GlobalConstent.REPORT_DATA, datas);
			if(flag){
				response.getWriter().write("adddept_successful");
			}else{
				response.getWriter().write("adddept_error");
			}
			response.getWriter().flush();
		}catch(BaseException e){
			e.printStackTrace();
			if(e instanceof InfoExistException){
				session.setAttribute("message", MessageTag.getMessage("comm.infoexistexception","admin.department_name"));
			}else if(e instanceof CreateFailedException){
				session.setAttribute("message", MessageTag.getMessage("comm.failedexception","admin.department_create"));
			}else{
				session.setAttribute("message", MessageTag.getMessage("comm.operation_result_failure"));
			}

			response.getWriter().write("adddept_failed");
			response.getWriter().flush();
		}
		return null;
	}

	@RequestMapping(value="/edit/{deptId}", method=RequestMethod.GET)
	public String edit(@PathVariable Integer deptId, Model model) {
		Department dept = departmentService.getDepartment(deptId);
		model.addAttribute("dept", dept);
		return "dept/edit";
	}
	
	@RequestMapping(value="/update/{deptId}")
	public String update(Integer deptId,
			String deptName, String deptRemark,
			HttpSession session, HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			Department dept = departmentService.getDepartment(deptId);
			if(dept != null){
				dept.setName(deptName);
				dept.setPyCode(Pinyin4jUtil.getFirstUpperStr(deptName));
				dept.setRemark(deptRemark);
				departmentService.updateDepartment(dept);
				request.setAttribute("freshTree", "true");
				PageSortModel psm = new PageSortModel(request);
				String parentNodeNo = (String)session.getAttribute("parentNodeNo");
				List<Department> datas = departmentService.getSubDepartmentOfNodeCode(psm, parentNodeNo);
				session.setAttribute(GlobalConstent.REPORT_DATA, datas);
			}
			serviceListener.loadPara();
			response.getWriter().write("editdept_successful");
			response.getWriter().flush();
		}catch(BaseException e){
			if(e instanceof UpdateFailedException){
				session.setAttribute("message", MessageTag.getMessage("comm.failedexception","admin.department_update"));
			}else{
				session.setAttribute("message", MessageTag.getMessage("comm.operation_result_failure"));
			}
			response.getWriter().write("editdept_failed");
			response.getWriter().flush();
		}
		return null;
	}
	
	@RequestMapping(value="/deleteDept", method=RequestMethod.POST)
	public String delete(@RequestParam Integer deptId, HttpSession session, HttpServletRequest request,HttpServletResponse response) throws IOException {
		try{
			Department dept = departmentService.getDepartment(deptId);
			if(dept != null){
				if((dept.getUsers()!=null&&!dept.getUsers().isEmpty())||(dept.getSubDepts()!=null&&!dept.getSubDepts().isEmpty())){
					response.getWriter().write("failed");
					response.getWriter().flush();
					return null;
				}
				boolean deleteFlag = departmentService.delDepartment(dept);
				if(deleteFlag){
					String parentNodeNo = (String)session.getAttribute("parentNodeNo");
					session.setAttribute("parentNodeNo", parentNodeNo);
					response.getWriter().write("success");
					response.getWriter().flush();
				}else{
					response.getWriter().write("failed");
					response.getWriter().flush();
				}
				
			}else{
				response.getWriter().write("notExist");
				response.getWriter().flush();
			}
			//serviceListener.loadPara();
			return null;
		}catch(Throwable e){
			response.getWriter().write("failed");
			response.getWriter().flush();
		}
		return null;
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
		return "dept/tree";
	}

	@Resource(type = DepartmentService.class)
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@Resource(name = "initListener")
	public void setServiceListener(BaseDataServiceListener serviceListener) {
		this.serviceListener = serviceListener;
	}

}