package com.wafersystems.mrbs.quartz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.GlobalParam;
import com.wafersystems.mrbs.service.SystemServiceLogService;
import com.wafersystems.mrbs.service.user.DepartmentService;
import com.wafersystems.mrbs.service.user.UserService;
import com.wafersystems.mrbs.vo.SystemServiceLog;
import com.wafersystems.mrbs.vo.user.Department;
import com.wafersystems.mrbs.vo.user.UserInfo;

@Component("syncImportJob")
public class SyncImportJob {

	private static Logger logger = LoggerFactory.getLogger(SyncImportJob.class);
	private String driver = "net.sourceforge.jtds.jdbc.Driver";
	private DepartmentService departmentService;
	private SystemServiceLogService serviceLogService;
	private UserService userService;

	public void insertData() {
//		List<Department> depts = syncDepartment();
//		departmentService.saveSyncHisDept(depts);//同步部门

		List<UserInfo> experts = syncExpert();
		userService.saveSyncExpert(experts);//同步用户
	}

	private List<Department> syncDepartment() {
		List<Department> depts = new LinkedList<Department>();
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			if(conn == null){
				return Collections.emptyList();
			}

			String sql = new StringBuilder("select zd_unit_code.unit_sn,")
					.append("zd_unit_code.name,")
					.append("zd_unit_code.abbname,")
					.append("zd_unit_code.py_code ")
					.append(" from zd_unit_code ")
					.append(" where zd_unit_code.unit_sn IN ")
					.append("(select DISTINCT a_employee_mi.dept_sn from a_employee_mi WHERE a_employee_mi.dept_sn IS NOT NULL AND a_employee_mi.dept_sn !='0000000')")
					.append(" ORDER BY zd_unit_code.name ").toString();

			rs = conn.prepareStatement(sql).executeQuery();
			Department department = null;
			String deptCode = "001001";
			while(rs.next()) {
				department = new Department();
				department.setName(rs.getString("name"));
				department.setHisCode(rs.getString("unit_sn"));
				department.setPyCode(rs.getString("py_code"));
				department.setParentDeptCode("001001");
				/**
				 * depts的list无数据时，设置001001，否则根据方法计算得到下一个可用的deptCode
				 */
				if(null != depts.get(0)){//当前不为第一个同步项
					deptCode = getNextDeptCode(deptCode);
				}
				department.setDeptcode(deptCode);
				depts.add(department);
			}
		} catch (SQLException e) {
			logger.error("同步科室时，获取数据出错", e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
			}
		}
		return depts;
	}

	private List<UserInfo> syncExpert() {
		List<UserInfo> users = new LinkedList<UserInfo>();
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			if(conn == null)
				return Collections.emptyList();								

			String sql = new StringBuilder("select a_employee_mi.emp_sn, a_employee_mi.name,a_employee_mi.py_code,a_employee_mi.dept_sn ")
				.append(" from zd_unit_code, a_employee_mi ")
				.append(" where zd_unit_code.unit_sn = a_employee_mi.dept_sn ")
				.append(" and a_employee_mi.dept_sn is not null and a_employee_mi.dept_sn !='00000' ").toString();
			rs = conn.prepareStatement(sql).executeQuery();

			UserInfo user = null;
			Department dept = null;
			while (rs.next()) {
				if (StringUtils.isBlank(rs.getString("dept_sn")) || StringUtils.isBlank(rs.getString("name"))) {
					logger.debug("员工【" + rs.getString("name") + "】的部门或姓名为空，忽略");
					rs.next();
				}

				user = new UserInfo();

				user.setUserId(rs.getString("emp_sn").toLowerCase());
				user.setName(rs.getString("name"));
				user.setState(GlobalConstent.USER_STATE_ON);
				user.setPyCode(rs.getString("py_code"));
				user.setSource(GlobalConstent.USER_SOURCE_HIS);

				dept = departmentService.getDeptByHisCodeAndName(rs.getString("dept_sn"), null);

				if (dept != null) {
					user.setDeptId(dept);
					users.add(user);
				} else {
					logger.debug("员工" + user.getName() + "的部门为空，忽略同步");
				}
			}
		} catch (SQLException e) {
			logger.error("同步专家时，获取数据出错", e);
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
			}
		}
		return users;
	}

	private Connection getConnection(){	
		SystemServiceLog log = new SystemServiceLog();
		log.setObjectId("信息同步操作");
		log.setCreateTime(new Date());
		log.setName("信息同步操作");

		String url = "jdbc:jtds:sqlserver://" + GlobalParam.sync_data_inner_ip + ":1433/" + GlobalParam.sync_data_db_name;
		Connection conn = null;
		try {
			Class.forName(driver);
			if (conn == null || conn.isClosed())
				conn = DriverManager.getConnection(url, GlobalParam.sync_data_db_user, GlobalParam.sync_data_db_pwd);
		} catch (Exception e) {
			log.setContent("同步数据时，数据库连接出错");
			log.setResult(GlobalConstent.SYSTEM_LOG_FAILED);
			serviceLogService.saveSystemServiceLog(log);
			logger.error("同步数据时，数据库连接出错", e);
		}
		return conn;
	}

	@Value("${sqlserverClass}")
	public void setDriver(String driver) {
		this.driver = driver;
	}

	@Resource
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Resource
	public void setServiceLogService(SystemServiceLogService serviceLogService) {
		this.serviceLogService = serviceLogService;
	}

	/**
	 * 得到下一个可用的deptCode
	 * @param deptCode
	 * @return
	 */
	private String getNextDeptCode(String deptCode){
		if(deptCode == null)
			return "001001";
		String lastThreeCode = deptCode.substring(3, deptCode.length());
		int previous = Integer.parseInt(lastThreeCode);
		return "001" + String.format("%03d", previous + 1);  
	}

}