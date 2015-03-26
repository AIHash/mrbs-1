package com.wafersystems.mrbs.dao.media;

import java.util.List;

import com.wafersystems.mrbs.dao.base.GenericDao;
import com.wafersystems.mrbs.vo.meeting.MediaRole;

public interface MediaRoleDao extends GenericDao<MediaRole>{
	
	public List<MediaRole> getMediaRoleOfMediaId(int mediaId) throws Exception;
	
	public Integer delMediaRole(int mediaId) throws Exception;
}
