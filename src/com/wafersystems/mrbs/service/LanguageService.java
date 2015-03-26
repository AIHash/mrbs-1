package com.wafersystems.mrbs.service;

import com.wafersystems.mrbs.vo.Language;

public interface LanguageService {

	public void saveLanguage(Language vo);

	public void updateLanguage(Language vo);

	public void delLanguage(Language vo);
	
	public Language getLanguageById(int id);

}
