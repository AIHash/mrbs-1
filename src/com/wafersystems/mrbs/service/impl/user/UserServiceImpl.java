package com.wafersystems.mrbs.service.impl.user;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.SystemContext;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.dao.user.UserDao;
import com.wafersystems.mrbs.exception.BaseException;
import com.wafersystems.mrbs.exception.ConfigFailedException;
import com.wafersystems.mrbs.exception.CreateFailedException;
import com.wafersystems.mrbs.exception.DeleteFailedException;
import com.wafersystems.mrbs.exception.InfoExistException;
import com.wafersystems.mrbs.exception.LoginFailedException;
import com.wafersystems.mrbs.exception.UpdateFailedException;
import com.wafersystems.mrbs.integrate.haina.facade.HainaCall;
import com.wafersystems.mrbs.logmng.ann.MrbsLog;
import com.wafersystems.mrbs.service.user.UserService;
import com.wafersystems.mrbs.vo.right.Role;
import com.wafersystems.mrbs.vo.user.Department;
import com.wafersystems.mrbs.vo.user.UnifiedUserType;
import com.wafersystems.mrbs.vo.user.UserInfo;
import com.wafersystems.mrbs.web.criteriavo.AutoCompleteItem;
import com.wafersystems.mrbs.web.criteriavo.SelectItem;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

	Logger logger = LoggerFactory.getLogger(UserService.class);
	
	private UserDao userDao;
	private HainaCall hainaCall;

	@MrbsLog(desc="admin.user_create")
	@Override
	public void saveUser(UserInfo user)throws BaseException {
		try{
			UserInfo exists = this.getUserByUserId(user.getUserId());
			if(exists != null)
				throw new InfoExistException();
			userDao.save(user);
			//处理海纳的同步问题
			try {
				if(user.getUserType().getId() == GlobalConstent.USER_TYPE_UNION){
					hainaCall.addHospital(user.getName(), user.getUserId());
				}
			} catch (Exception e) {
				logger.error("连接海纳服务器同步数据出错", e);
			}
		}catch(InfoExistException e){
			throw e;
		}catch(Exception e){
			logger.error("创建用户异常", e);
			throw new CreateFailedException();
		}
	}

	@MrbsLog(desc="admin.user_update")
	@Override
	public void updateUser(UserInfo user)throws BaseException {
		try{
			userDao.update(user);
		}catch(Exception e){
			logger.error("修改用户异常", e);
			throw new UpdateFailedException();
		}
	}

	@MrbsLog(desc="admin.user_delete")
	@Override
	public void delUser(UserInfo user)throws BaseException {
		try{
			if(GlobalConstent.USER_ADMIN_ID.equalsIgnoreCase(user.getUserId()))
				return;
			user.setState(GlobalConstent.USER_STATE_DELETED);
			userDao.update(user);
			//处理海纳的同步问题
			try {
				if(user.getUserType().getId() == GlobalConstent.USER_TYPE_UNION){
					hainaCall.deleteHospitalInfo(user.getUserId());
				}
			} catch (Exception e) {
				logger.error("连接海纳服务器同步数据出错", e);
			}
		}catch(Exception e){
			logger.error("删除用户异常", e);
			throw new DeleteFailedException();
		}
	}

	@Override
	public UserInfo getUserByUserId(String userId) {
		return userDao.get(userId);
	}
	
	@Override
	public List<UserInfo> getUserListByDeptId(PageSortModel psm, Department deptId){
		String wherejpql = " and deptId = ? and state != ? and userId != ?";
		Object[] queryParams = new Object[]{deptId, GlobalConstent.USER_STATE_DELETED, GlobalConstent.USER_ADMIN_ID};
		LinkedHashMap<String, Object> orderby = new LinkedHashMap<String, Object>();
		orderby.put("userId", "asc");
		return  userDao.getScrollData(psm, wherejpql, queryParams, orderby).getResultlist();
	}
	
	@Override
	public List<UserInfo> getUserList(PageSortModel psm, String userId, String userName) throws Throwable{
		logger.debug("enter UserServiceImpl getUserList");
		return userDao.getUserList(psm, userId, userName);
	}
	
	@Override
	@MrbsLog(desc="admin.logon")  
	public UserInfo checkUserLogin(String userId, String password)throws LoginFailedException{
		UserInfo user = userDao.get(userId);
		if(user != null){
			if(user.getState() == GlobalConstent.USER_STATE_ON && user.getPassword().equals(password)){
				return user;
			}
		}
		throw new LoginFailedException("UserId=" + userId);
	}

	@Override
	@MrbsLog(desc="admin.changepassword")  
	public void changePassword(UserInfo user, String oldPassword, String newPassword)throws ConfigFailedException{
		if(user.getPassword().equals(oldPassword)){
			user.setPassword(newPassword);
			userDao.update(user);
		}else{
			throw new ConfigFailedException("UserId=" + user.getUserId());
		}
	}
	
	@Override
	@MrbsLog(desc="admin.Exit")  
	public void checkUserExit(String userId) throws LoginFailedException{
		// TODO 用户退出处理待完善
		try{
			SystemContext.get_session().invalidate();
			SystemContext.set_session(null);
		}catch(Exception e){
			e.printStackTrace();
			throw new LoginFailedException();
		}
	}

	@Override
	public List<UserInfo> getUserList(String hql) {
		List<UserInfo> results = userDao.findTbyHql(hql);
		return results;
	}

	@Override
	public List<UserInfo> getAllUnifiedUser() {
		return userDao.findTbyHql("from UserInfo u where u.userType.id = " + 5);
	}

	@Override
	public List<UserInfo> getUsersByUserName(String userName) {
		String hql = "from UserInfo u Where u.name = '" + userName +"'";
		return userDao.findTbyHql(hql);
	}

	@Override
	public void saveSyncExpert(List<UserInfo> experts) {
		if(experts!=null&&!experts.isEmpty()){
			logger.debug("UserServiceImpl.saveSyncExpert start 开始同步专家数据");
			int deleteCount = userDao.updateAllHisExpertStateDelete();
			logger.debug("共设置了" + deleteCount + "个His专家状态为删除");
			UserInfo userInfo = null;
			UserInfo temp = null;
			UserInfo admin = new UserInfo("admin");
			for (Iterator<UserInfo> iterator = experts.iterator(); iterator.hasNext();) {
				userInfo = iterator.next();
				temp = userDao.getUserByHisCode(userInfo.getHisCode(), userInfo.getName());
				if(temp == null){//添加专家
					userInfo.setCreateTime(new Date());

					userInfo.setCreator(admin);

					UnifiedUserType userType = new UnifiedUserType(GlobalConstent.USER_TYPE_PROFESSIONAL);
					userInfo.setUserType(userType);

					Role role = new Role(GlobalConstent.USER_ROLE_PROFESSIONAL);
					userInfo.setRole(role);
					userInfo.setState(GlobalConstent.USER_STATE_ON);
					userDao.save(userInfo);
					logger.debug("同步添加专家，" + userInfo.getName());
				}else{
					userDao.updateHisExpertInfo(userInfo);
					logger.debug("同步更新专家，" + userInfo.getName());
				}
			}
			logger.debug("UserServiceImpl.saveSyncExpert over 同步专家结束");
		}
	}

	/**
	 * 根据用户名、部门名模糊查询用户选单
	 * @param departmentKind 部门类型
	 * @param userNameOrDeptName 模糊查询的条件
	 * @param notInUserId 不包含的userId
	 * @return
	 * @throws Throwable
	 */
	public List<SelectItem> getUserListByUserNameOrDeptName(String departmentKind,String userNameOrDeptName,String notInUserId) throws Throwable{
		try{
			return userDao.getUserListByUserNameOrDeptName(departmentKind, userNameOrDeptName, notInUserId);
		}catch(Exception e){
			logger.error("Error UserServiceImpl.getUserListByUserNameOrDeptName",e);
			throw e;
		}catch(Throwable e){
			logger.error("Error UserServiceImpl.getUserListByUserNameOrDeptName",e);
			throw e;
		}
	}

	public List<AutoCompleteItem> getUserListByUserName(String name)throws Throwable{
		try{
			return userDao.getUserListByUserName(name);
		}catch(Exception e){
			logger.error("Error UserServiceImpl.getUserListByUserName",e);
			throw e;
		}catch(Throwable e){
			logger.error("Error UserServiceImpl.getUserListByUserName",e);
			throw e;
		}
	}
	
	@Resource(type = UserDao.class)
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Resource
	public void setHainaCall(HainaCall hainaCall) {
		this.hainaCall = hainaCall;
	}
	
}
