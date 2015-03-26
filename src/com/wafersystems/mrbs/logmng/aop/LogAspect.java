package com.wafersystems.mrbs.logmng.aop;

import java.util.Date;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.SystemContext;
import com.wafersystems.mrbs.exception.BaseException;
import com.wafersystems.mrbs.exception.LogFailedException;
import com.wafersystems.mrbs.logmng.ann.MrbsLog;
import com.wafersystems.mrbs.service.SystemOperationLogService;
import com.wafersystems.mrbs.service.user.UserService;
import com.wafersystems.mrbs.tag.MessageTag;
import com.wafersystems.mrbs.vo.SystemOperationLog;
import com.wafersystems.mrbs.vo.user.UserInfo;

@Component  
@Aspect
public class LogAspect {

	private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

	private SystemOperationLogService logService;
	private UserService userService;

	// 标注该方法体为后置通知，当目标方法执行成功后执行该方法体
	@AfterReturning("execution(* com.wafersystems.mrbs.service.*..*.*(..)) && @annotation(rl)")
	public void addLogSuccess(JoinPoint jp, MrbsLog rl) {
		String className = jp.getTarget().getClass().toString();// 获取目标类名
		className = className.substring(className.indexOf("com"));
		String signature = jp.getSignature().toString();// 获取目标方法签名
		String methodName = signature.substring(signature.lastIndexOf(".") + 1,	signature.indexOf("("));
		String modelName = getModelName(className); // 根据类名获取所属的模块
		try {
			UserInfo actor = null;
			if (SystemContext.get_session() == null || SystemContext.get_session().isNew()) {
				if (methodName.startsWith("check")) {
					Object[] oos = jp.getArgs();
					actor = userService.getUserByUserId(oos[0].toString());
				}
			} else {
				actor = (UserInfo) SystemContext.get_session().getAttribute(
						GlobalConstent.USER_LOGIN_SESSION);
			}
			if (actor == null)
				return;
			SystemOperationLog logvo = new SystemOperationLog();
			logvo.setUser(actor);
			logvo.setLogDate(new Date());
			logvo.setModuleNo(modelName);
			logvo.setResult(true);
			logvo.setType(methodName);
			logvo.setContent(MessageTag.getMessage(rl.desc()));
			logService.saveSystemOperationLog(logvo);
		} catch (LogFailedException e) {
			// TODO
		} catch (IllegalStateException e) {
			logger.error("Session 已经失效，记录日志失败");
		} catch (Exception e) {
			logger.error("记录日志失败", e);
		}
	}

	//标注该方法体为异常通知，当目标方法出现异常时，执行该方法体  
	@AfterThrowing(pointcut="execution(* com.wafersystems.mrbs.service.*..*.*(..)) && @annotation(rl)", throwing="ex")  
	public void addLog(JoinPoint jp, MrbsLog rl, BaseException ex) {
		String className = jp.getTarget().getClass().toString();
		className = className.substring(className.indexOf("com"));
		String signature = jp.getSignature().toString();
		String methodName = signature.substring(signature.lastIndexOf(".") + 1,	signature.indexOf("("));
		String modelName = getModelName(className);

		try {
			UserInfo actor = null;
			if (SystemContext.get_session() == null) {
				if (methodName.startsWith("check")) {
					Object[] oos = jp.getArgs();
					actor = userService.getUserByUserId(oos[0].toString());
				}
			} else {
	    		actor = (UserInfo)SystemContext.get_session().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
	    	}
			if (actor == null)
				return;
			SystemOperationLog logvo = new SystemOperationLog();
			logvo.setUser(actor);
			logvo.setLogDate(new Date());
			logvo.setModuleNo(modelName);
			logvo.setResult(false);
			logvo.setType(methodName);
			logvo.setContent(MessageTag.getMessage(rl.desc()) + "["
					+ ex.getClass().getSimpleName() + "]");
			logService.saveSystemOperationLog(logvo);
		} catch (LogFailedException e) {
			// TODO
		} catch (Exception e) {
			logger.error("记录日志失败", e);
		}
	}

	/** 
	 * 根据包名查询检索其所属的模块 
	 * @param packageName 包名 
	 * @return 模块名称 
	 */
	private String getModelName(String packageName){  
		// TODO 多种模块检索方式可用，待实现
		String modelName = packageName.substring(packageName.lastIndexOf(".")+1).replace("Impl", "");
		return modelName;  
	}

	@Resource(type=SystemOperationLogService.class)
	public void setLogService(SystemOperationLogService logService) {
		this.logService = logService;
	}

	@Resource(type=UserService.class)
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
