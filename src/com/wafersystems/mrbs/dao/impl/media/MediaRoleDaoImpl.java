package com.wafersystems.mrbs.dao.impl.media;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.wafersystems.mrbs.dao.base.GenericDaoImpl;
import com.wafersystems.mrbs.dao.media.MediaRoleDao;
import com.wafersystems.mrbs.vo.meeting.Media;
import com.wafersystems.mrbs.vo.meeting.MediaRole;

@Repository
public class MediaRoleDaoImpl extends GenericDaoImpl<MediaRole> implements MediaRoleDao{

	@Override
	public List<MediaRole> getMediaRoleOfMediaId(int mediaId) throws Exception {
		String hql = "from MediaRole m where mediaId="+mediaId;
		List<MediaRole> mediaroles = this.findTbyHql(hql);
		return mediaroles;
	}

	 

	public Integer delMediaRole(int mediaId) throws Exception {
		final String hql = "delete from media_role where media_id = "+mediaId;
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				Integer size = session.createSQLQuery(hql).executeUpdate();
				return size;
			}
		});
	}

 

}
