package com.wafersystems.mrbs.service.impl.meeting;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.base.PageData;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.dao.meeting.AccessoriesDao;
import com.wafersystems.mrbs.logmng.ann.MrbsLog;
import com.wafersystems.mrbs.service.meeting.AccessoriesService;
import com.wafersystems.mrbs.vo.meeting.Accessories;
import com.wafersystems.mrbs.vo.user.UserInfo;
import com.wafersystems.mrbs.web.criteriavo.ManagementOfAccessoriesVO;

@Service
public class AccessoriesServiceImpl implements AccessoriesService {

	private AccessoriesDao dao;

	@Override
	@MrbsLog(desc="group.accessories_create")
	public void saveAccessories(Accessories vo) {
		dao.save(vo);
	}

	@Override
	@MrbsLog(desc="group.accessories_update")
	public void updateAccessories(Accessories vo) {
		dao.update(vo);
	}

	@Override
	@MrbsLog(desc="group.accessories_delete")
	public void delAccessories(Accessories vo) {
		dao.delete(vo);
	}
	
	@Override
	public Accessories getAccessoriesById(int id){
		return dao.get(id);
	}

	public Integer deleteAccessoriesByAppId(Integer appId){
		return dao.deleteAccessoriesByAppId(appId);
	}
	
	/**
	 * 删除视频申请的附件
	 */
	public Integer deleteAccessoriesByVoidId(Integer voidId){
		return dao.deleteAccessoriesByVoidId(voidId);
	}

	/**
	 * 获得所有的附件
	 * */
	@Override
	public PageData<Accessories> getAllAccessories(PageSortModel psm,ManagementOfAccessoriesVO managementOfAccessoriesVo,UserInfo userinfo) throws Throwable {
		return dao.getAllAccessories(psm,managementOfAccessoriesVo,userinfo);
	}

	@Override
	public void saveBatch(Accessories[] vos){
		dao.saveBatchData(vos);
	}

	@Override
	public void fulsh() {
		dao.fulsh();
	}

	@Override
	public void clear() {
		dao.clear();
	}

	@Resource(type = AccessoriesDao.class)
	public void setAccessoriesDao(AccessoriesDao dao) {
		this.dao = dao;
	}
}