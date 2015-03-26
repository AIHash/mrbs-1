package com.wafersystems.mrbs.dao.impl.media;

import java.util.LinkedHashMap;

import org.springframework.stereotype.Repository;

import com.wafersystems.mrbs.dao.base.GenericDaoImpl;
import com.wafersystems.mrbs.dao.base.PageData;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.dao.media.MediaDao;
import com.wafersystems.mrbs.vo.meeting.Media;
import com.wafersystems.mrbs.vo.user.UserInfo;
import com.wafersystems.util.StrUtil;

@Repository
public class MediaDaoImpl extends GenericDaoImpl<Media> implements MediaDao{

	@Override
	public PageData<Media> getMediaList(PageSortModel psm, Media media,UserInfo user)throws Throwable {
		try{
			logger.debug("Enter MediaDaoImpl.getMediaList");
			String hql=" ";
			 
			if(!StrUtil.isEmptyStr(media.getMedianame())){
				hql+=" and o.medianame like '%"+media.getMedianame()+"%'";
			}
			//代表申请的会诊类型（远程会诊为1；视频讲座为2）
			if(media.getMediaType() != 0){
				hql+=" and o.mediaType ="+media.getMediaType();
			}
		    int roleId = user.getRole().getId();
		    hql +=" and  exists ( select s.id  from MediaRole as s where s.mediaId = o.id and s.roleId="+roleId+")";
			LinkedHashMap<String,Object> linkmap=new LinkedHashMap<String,Object>();
			linkmap.put("id", "desc");
			linkmap.put("mediaType", "asc");
			PageData<Media> data = this.getScrollData(psm, hql, null, linkmap);
			return data;
		}catch(Exception e){
			logger.error("Error MeetingDaoImpl.getMeetingList",e);
			throw e;
		}catch(Throwable e){
			logger.error("Error MeetingDaoImpl.getMeetingList",e);
			throw e;
		}
		
	}

}
