package com.wafersystems.mrbs.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.LanguageDao;
import com.wafersystems.mrbs.service.LanguageService;
import com.wafersystems.mrbs.vo.Language;

@Service
public class LanguageServiceImpl implements LanguageService {

	private LanguageDao dao;

	@Override
	public void saveLanguage(Language vo) {
		dao.save(vo);
	}

	@Override
	public void updateLanguage(Language vo) {
		dao.update(vo);
	}

	@Override
	public void delLanguage(Language vo) {
		dao.delete(vo);

	}
	
	@Override
	public Language getLanguageById(int id){
		return dao.get(id);
	}

	@Resource(type = LanguageDao.class)
	public void setLanguageDao(LanguageDao dao) {
		this.dao = dao;
	}

}