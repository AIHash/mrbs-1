package com.wafersystems.mrbs.service.impl.param;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.param.UnifiedParameterDao;
import com.wafersystems.mrbs.service.param.UnifiedParameterService;
import com.wafersystems.mrbs.vo.param.UnifiedParameter;

@Service
public class UnifiedParameterServiceImpl implements UnifiedParameterService {

	private UnifiedParameterDao dao;

	@Override
	public void saveUnifiedParameter(UnifiedParameter vo) {
		dao.save(vo);
	}

	@Override
	public void updateUnifiedParameter(UnifiedParameter vo) {
		dao.update(vo);
	}

	@Override
	public void delUnifiedParameter(UnifiedParameter vo) {
		dao.delete(vo);

	}
	
	@Override
	public UnifiedParameter getUnifiedParameterById(int id){
		return dao.get(id);
	}

	@Resource(type = UnifiedParameterDao.class)
	public void setUnifiedParameterDao(UnifiedParameterDao dao) {
		this.dao = dao;
	}

}