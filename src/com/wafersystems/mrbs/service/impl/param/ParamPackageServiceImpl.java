package com.wafersystems.mrbs.service.impl.param;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.param.ParamPackageDao;
import com.wafersystems.mrbs.exception.BaseException;
import com.wafersystems.mrbs.exception.UpdateFailedException;
import com.wafersystems.mrbs.logmng.ann.MrbsLog;
import com.wafersystems.mrbs.service.param.ParamPackageService;
import com.wafersystems.mrbs.vo.param.ParamPackage;

@Service
public class ParamPackageServiceImpl implements ParamPackageService {

	private static final Logger logger = LoggerFactory.getLogger(ParamPackageService.class);
	
	private ParamPackageDao dao;

	@MrbsLog(desc="admin.system_param_update")
	@Override
	public void updateParamPackage(ParamPackage vo)throws BaseException {
		try{
			dao.update(vo);
		}catch(Exception e){
			logger.error("更新系统参数错误", e);
			throw new UpdateFailedException();
		}
	}
	
	@MrbsLog(desc="admin.system_param_update")
	@Override
	public void updateParamPackageBatch(Collection<ParamPackage> paramPackages) throws BaseException{
		try{
			Iterator<ParamPackage> it = paramPackages.iterator();
			while(it.hasNext()){
				dao.update(it.next());
			}
		}catch(Exception e){
			logger.error("批量更新系统参数错误", e);
			throw new UpdateFailedException();
		}
	}
	
	@Override
	public ParamPackage getParamPackageById(String key){
		return dao.get(key);
	}
	
	@Override
	public List<ParamPackage> getAll(){
		return dao.findAll();
	}

	@Resource(type = ParamPackageDao.class)
	public void setParamPackageDao(ParamPackageDao dao) {
		this.dao = dao;
	}

}