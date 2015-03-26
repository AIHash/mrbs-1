package com.wafersystems.mrbs.service.impl.right;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.right.FunctionDao;
import com.wafersystems.mrbs.service.right.FunctionService;
import com.wafersystems.mrbs.vo.right.Function;

@Service
public class FunctionServiceImpl implements FunctionService {

	private FunctionDao dao;

	@Override
	public void saveFunction(Function vo) {
		dao.save(vo);
	}

	@Override
	public void updateFunction(Function vo) {
		dao.update(vo);
	}

	@Override
	public void delFunction(Function vo) {
		dao.delete(vo);

	}
	
	@Override
	public Function getFunctionById(int id){
		return dao.get(id);
	}

	@Resource(type = FunctionDao.class)
	public void setFunctionDao(FunctionDao dao) {
		this.dao = dao;
	}

}