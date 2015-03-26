package com.wafersystems.mrbs.service.impl.media;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.base.PageData;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.dao.media.MediaDao;
import com.wafersystems.mrbs.dao.media.MediaRoleDao;
import com.wafersystems.mrbs.service.impl.meeting.ApplicationOperateServiceImpl;
import com.wafersystems.mrbs.service.media.MediaService;
import com.wafersystems.mrbs.vo.meeting.Media;
import com.wafersystems.mrbs.vo.meeting.MediaRole;
import com.wafersystems.mrbs.vo.user.UserInfo;

@Service
public class MediaServiceImpl implements MediaService{
	private static final Logger logger = LoggerFactory.getLogger(ApplicationOperateServiceImpl.class);
    private MediaDao mediaDao;
   private MediaRoleDao mediaRoleDao;
   
	@Resource(type = MediaDao.class)
	public void setMediaDao(MediaDao mediaDao) {
		this.mediaDao = mediaDao;
	}
	
	 
	@Resource(type = MediaRoleDao.class)
	public void setMediaRoleDao(MediaRoleDao mediaRoleDao) {
		this.mediaRoleDao = mediaRoleDao;
	}

	@Override
	public PageData<Media> getMediaList(PageSortModel psm, Media media,UserInfo user)
			throws Throwable {
		try{
			logger.debug("Enter MediaServiceImpl.getMediaList");
			return mediaDao.getMediaList(psm, media,user);
			
		}catch(Exception e){
			logger.error("Error MediaServiceImpl.getMediaList",e);
			throw e;
		}catch(Throwable e){
			logger.error("Error MediaServiceImpl.getMediaList",e);
			throw e;
		}
	}

	@Override
	public void delMedia(int mediaId) throws Throwable {
		// TODO Auto-generated method stub
		try{
			logger.debug("Enter MediaServiceImpl.delMedia");
			mediaDao.delete(mediaId); 
			mediaRoleDao.delMediaRole(mediaId);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Error MediaServiceImpl.delMedia",e);
			throw e;
		} 
	}


	/**
	 * add by wangzhenglin 添加共享
	 */
	@Override
	public void saveMedia(Media media) throws Throwable {
		
		
		try{
			logger.debug("Enter MediaServiceImpl.saveMedia");
			mediaDao.save(media);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Error MediaServiceImpl.saveMedia",e);
			throw e;
		} 
	}


	@Override
	public void saveMediaRole(MediaRole mediaRole) throws Throwable {
		try{
			logger.debug("Enter MediaServiceImpl.saveMediaRole");
			mediaRoleDao.save(mediaRole);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Error MediaServiceImpl.saveMediaRole",e);
			throw e;
		} 
	
		
	}

}
