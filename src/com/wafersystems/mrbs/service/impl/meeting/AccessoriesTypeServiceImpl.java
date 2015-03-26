package com.wafersystems.mrbs.service.impl.meeting;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.meeting.AccessoriesTypeDao;
import com.wafersystems.mrbs.logmng.ann.MrbsLog;
import com.wafersystems.mrbs.service.meeting.AccessoriesTypeService;
import com.wafersystems.mrbs.vo.meeting.AccessoriesType;

@Service
public class AccessoriesTypeServiceImpl implements AccessoriesTypeService {

	private AccessoriesTypeDao dao;

	@Override
	@MrbsLog(desc = "group.accessoriesType_create")
	public void saveAccessoriesType(AccessoriesType vo) {
		dao.save(vo);
	}

	@Override
	@MrbsLog(desc = "group.accessoriesType_updateAccessoriesType")
	public void updateAccessoriesType(AccessoriesType vo) {
		dao.update(vo);
	}

	@Override
	@MrbsLog(desc = "group.accessoriesType_delAccessoriesType")
	public void delAccessoriesType(AccessoriesType vo) {
		dao.delete(vo);
	}

	@Override
	public short saveOrUpdateAccessoriesType(AccessoriesType vo) {
		AccessoriesType temp = dao.getAccessoriesTypeByName(vo.getName());
		if(temp == null){
			short val = dao.getMaxAccessoriesType();
			vo.setValue(val++);
			dao.save(vo);
			return vo.getId();
		}
		return temp.getId();
	}

	@Override
	public AccessoriesType getAccessoriesTypeById(Short id) {
		return dao.get(id);
	}

	@Resource(type = AccessoriesTypeDao.class)
	public void setAccessoriesTypeDao(AccessoriesTypeDao dao) {
		this.dao = dao;
	}

	/**
	 * 获得所有附件的类型
	 * */
	@Override
	public List<AccessoriesType> getAllAccessoriesTypes() {
		return dao.getAllAccessoriesType();
	}

}