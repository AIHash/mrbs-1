package com.wafersystems.mrbs.dao.base;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.wafersystems.mrbs.vo.FinalUnifiedUser;
import com.wafersystems.mrbs.vo.meeting.MeetingType;
import com.wafersystems.mrbs.vo.user.Department;
import com.wafersystems.util.GenericsUtil;
import com.wafersystems.util.StrUtil;

@SuppressWarnings("unchecked")   
public abstract class GenericDaoImpl<T> implements GenericDao<T>{

	protected HibernateTemplate template;

    protected Logger logger = LoggerFactory.getLogger(GenericDaoImpl.class);

    protected Class<T> entityClass = GenericsUtil.getSuperClassGenricType(this.getClass());

    public void clear() {
        getHibernateTemplate().clear();    
    }

	public void fulsh(){
		getHibernateTemplate().flush();
    }

    public void delete(Serializable... entityids) {
        for (int i = 0; i < entityids.length; i++) {
            T t = get(entityids[i]);
            if(t != null){
                getHibernateTemplate().delete(t);
            }
        }
    }
    
    public void deleteVO(Serializable... entityids) throws Throwable {
    	try{
	        for (int i = 0; i < entityids.length; i++) {
	            T t = get(entityids[i]);
	            if(t != null){
	                getHibernateTemplate().delete(t);
	            }
	        }
    	}catch(Throwable e){
			throw e;
		}
    }

    public T get(Serializable entityId){
    	if(entityId  == null){
    		throw new RuntimeException(this.entityClass.getName()+ ":id is empty or null");
        }
        return getHibernateTemplate().get(this.entityClass, entityId);
    }

    public long getCount(final String wherejpql, final Object[] queryParams) {
        final String hql = "select count(o) from " +
        	getEntityName(this.entityClass)+ " o " + (wherejpql==null || "".equals(wherejpql.trim())? "": "where 1=1 "+ wherejpql);

        return (Long)getHibernateTemplate().execute(new HibernateCallback<Long>(){
        	public Long doInHibernate(Session session)throws HibernateException, SQLException {
        		Query query = session.createQuery(hql);
        		setQueryParams(query, queryParams);
        		return (Long) query.uniqueResult();
        	}
        });
    }   

    public void save(Object entity) {
        getHibernateTemplate().save(entity);
    }

    /**
     * 保存或更新对象，有主键就更新否则保存<br/>
     * 	（yangsf 2011-5-16添加）
     * @param entity
     */
    public void saveOrUpdate(Object entity){
    	getHibernateTemplate().saveOrUpdate(entity);
    }

    public void update(Object entity) {
        getHibernateTemplate().update(entity);
    }

    public PageData<T> getScrollDataWithOrderDefault(final int firstindex, final int maxresult,
    	final String wherejpql, final Object[] queryParams,
    	final LinkedHashMap<String, Object> orderby,final LinkedHashMap<String, Object> defaultOrderby) {
    	final PageData<T> qr = new PageData<T>();
    	final String entityname = getEntityName(this.entityClass);
    	return getHibernateTemplate().execute(new HibernateCallback<PageData<T>>(){
    		public PageData<T> doInHibernate(Session session) throws HibernateException, SQLException {
                 Query query = session.createQuery("select count(o) from "+ entityname+ " o "+(wherejpql==null || "".equals(wherejpql.trim())? "": "where 1=1 "+ wherejpql));   
	             setQueryParams(query, queryParams);
	             qr.setTotalRecords((Long)query.uniqueResult());
	             query = session.createQuery("select o from "+ entityname+ " o "+(wherejpql==null || "".equals(wherejpql.trim())? "": "where 1=1 "+ wherejpql)+ buildOrderbyAndDefault(orderby,defaultOrderby));   
	             setQueryParams(query, queryParams);
	             if(firstindex!=-1 && maxresult!=-1){
	            	 int curFirstIndex = firstindex;
	            	 if(firstindex < 0)
	            		 curFirstIndex = 0;
	            	 if(firstindex > qr.getTotalRecords())
	            		 curFirstIndex = firstindex - maxresult;
	            	 query.setFirstResult(curFirstIndex).setMaxResults(maxresult);
	             }
	             qr.setResultlist(query.list());
	             return qr;
             }
        });
    }

    public PageData<T> getScrollData(final int firstindex, final int maxresult,
        	final String wherejpql, final Object[] queryParams,
        	final LinkedHashMap<String, Object> orderby) {
        	final PageData<T> qr = new PageData<T>();
        	final String entityname = getEntityName(this.entityClass);
        	return getHibernateTemplate().execute(new HibernateCallback<PageData<T>>(){
        		public PageData<T> doInHibernate(Session session) throws HibernateException, SQLException {
                     Query query = session.createQuery("select count(o) from "+ entityname+ " o "+(wherejpql==null || "".equals(wherejpql.trim())? "": "where 1=1 "+ wherejpql));   
    	             setQueryParams(query, queryParams);
    	             qr.setTotalRecords((Long)query.uniqueResult());
    	             query = session.createQuery("select o from "+ entityname+ " o "+(wherejpql==null || "".equals(wherejpql.trim())? "": "where 1=1 "+ wherejpql)+ buildOrderby(orderby));   
    	             setQueryParams(query, queryParams);
    	             if(firstindex!=-1 && maxresult!=-1){
    	            	 int curFirstIndex = firstindex;
    	            	 if(firstindex < 0)
    	            		 curFirstIndex = 0;
    	            	 if(firstindex > qr.getTotalRecords())
    	            		 curFirstIndex = firstindex - maxresult;
    	            	 query.setFirstResult(curFirstIndex).setMaxResults(maxresult);
    	             }
    	             qr.setResultlist(query.list());
    	             return qr;
                 }
            });
        }
    
	public PageData<T> getScrollDataByHql(final int firstindex, final int maxresult,final String hql_search, final String hql_totalRecords) {   
		final PageData<T> qr = new PageData<T>();   
		return (PageData<T>)getHibernateTemplate().execute(new HibernateCallback<PageData<T>>(){   
			public PageData<T> doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = null;
				query = session.createQuery(hql_totalRecords);
				qr.setTotalRecords((Long)query.uniqueResult());
				query = session.createQuery(hql_search);
				if(firstindex!=-1 && maxresult!=-1){
					int curFirstIndex = firstindex;
					if(firstindex < 0)
						curFirstIndex = 0;
					if(firstindex > qr.getTotalRecords())
						curFirstIndex = firstindex - maxresult;
					query.setFirstResult(curFirstIndex).setMaxResults(maxresult); 
                 }
                 qr.setResultlist(query.list());
                 return qr;
			}
        });
    }

    /**  
     * 设置查询条件的参数  
     * @param query  
     * @param queryParams  
     */  
	protected static void setQueryParams(Query query, Object[] queryParams){
    	if(queryParams!=null && queryParams.length>0){
    		for(int i = 0; i < queryParams.length; i++){
    			query.setParameter(i, queryParams[i]);
    		}
    	}
	}

    /**  
     * 组装order by语句 
     * @param orderby
     * @return
     */
    protected static String buildOrderby(LinkedHashMap<String, Object> orderby){
        StringBuffer orderbyql = new StringBuffer("");
        if(orderby!=null && orderby.size()>0){
        	orderbyql.append(" order by ");
            for(String key : orderby.keySet()){
            	if(orderby.get(key) != null && !StrUtil.isEmptyStr(orderby.get(key).toString())){
            		if(!orderby.get(key).toString().trim().equalsIgnoreCase("default")){
            			orderbyql.append("o.").append(key).append(" ").append(orderby.get(key)).append(",");
            		}else{
            			orderbyql = new StringBuffer("");
            		}
            		
            	}
            }
            orderbyql.deleteCharAt(orderbyql.length()-1);
        }
        return orderbyql.toString();
    }
    
    /**  
     * 组装带有默认排序的order by语句 
     * @param orderby
     * @param defaultOrderby 默认排序
     * @return
     */
    protected static String buildOrderbyAndDefault(LinkedHashMap<String, Object> orderby,LinkedHashMap<String, Object> defaultOrderby){
        StringBuffer orderbyql = new StringBuffer("");
        boolean falg = false;//标记是否有排序条件
        if(orderby!=null && orderby.size()>0){
        	orderbyql.append(" order by ");
        	String ord = "";
            for(String key : orderby.keySet()){
            	if(orderby.get(key) != null && !StrUtil.isEmptyStr(orderby.get(key).toString())){
            		if(!orderby.get(key).toString().trim().equalsIgnoreCase("default")){
            			ord = "o."+key;
            			orderbyql.append("convert_gbk("+ord+")").append(" ").append(orderby.get(key)).append(",");
            			falg = true;
            		}else{//当排序为default时则使用默认排序
            			orderbyql = new StringBuffer("");
            			falg = false;
            			break;
            		}
            	}
            }
            if(falg){
            	orderbyql.deleteCharAt(orderbyql.length()-1);
            }else{
    			orderbyql = new StringBuffer("");
    			falg = false;
    		}
            
        }
        //处理默认排序
        if(!falg&&defaultOrderby!=null && defaultOrderby.size()>0){
        	orderbyql.append(" order by ");
        	String ord = "";
            for(String key : defaultOrderby.keySet()){
            	if(defaultOrderby.get(key) != null && !StrUtil.isEmptyStr(defaultOrderby.get(key).toString())){
            		ord = "o."+key;
        			orderbyql.append("convert_gbk("+ord+")").append(" ").append(defaultOrderby.get(key)).append(",");
            	}
            }
            orderbyql.deleteCharAt(orderbyql.length()-1);
        }
        return orderbyql.toString();
    }

    public PageData<T> getScrollData(int firstindex, int maxresult,
            String wherejpql, Object[] queryParams) {
        return getScrollData(firstindex, maxresult, wherejpql, queryParams, null);
    }
  
    public PageData<T> getScrollData(int firstindex, int maxresult,
    		LinkedHashMap<String, Object> orderby) {
    	return getScrollData(firstindex, maxresult, null, null, orderby);
    }

    public PageData<T> getScrollData(int firstindex, int maxresult) {
        return getScrollData(firstindex, maxresult, null, null, null);
    }

    public PageData<T> getScrollData() {
        return getScrollData(-1, -1);
    }
    
	public PageData<T> getScrollData(PageSortModel psm, String wherejpql,
			Object[] queryParams, LinkedHashMap<String, Object> orderby) {
		int firstindex = -1;
		int maxresult = -1;
		if(-100 != psm.getPageSize()) {
			firstindex = psm.getRowStart();
			maxresult = psm.getPageSize();
        }
		LinkedHashMap<String, Object> orderby2 = new LinkedHashMap<String, Object>();
		if(psm.getOrderMap() != null && psm.getOrderMap().size() > 0){
			orderby2.putAll(psm.getOrderMap());
		}
//		if(orderby != null)
//			orderby2.putAll(orderby);
		//orderby默认排序单独处理
		PageData<T> data = getScrollDataWithOrderDefault(firstindex, maxresult, wherejpql, queryParams, orderby2,orderby);
		psm.setTotalRows(new Long(data.getTotalRecords()).intValue());
		return data;
	}
       
    /**
     * 获取实体的名称 
     * @param <E>
     * @param clazz 实体类 
     * @return
     */
    protected static <E> String getEntityName(Class<E> clazz){
        String entityname = clazz.getSimpleName();
        return entityname;
    }

    public List<T> findAll(){
        List<T> list = getHibernateTemplate().loadAll(entityClass);
        return list;
    }
    
    public List<T> findTbyHql(String hql){
        List<T> list = getHibernateTemplate().find(hql);   
        return list;   
    }
    public List<FinalUnifiedUser> findByHql(String hql){
        List<FinalUnifiedUser> list = getHibernateTemplate().find(hql);   
        return list;   
    }
    
    public List<Department> findDBySql(String sql){
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        Query query = session.createSQLQuery(sql);
//        List<Department> depts = query.list();
    	List<Department> depts = new ArrayList();
        List<Object[]> list = query.list();
        for(Object[] obj:list){
        	Department dept = new Department();
        	Object[] value = (Object[]) obj;
        }
    	return depts;
    }

    public MeetingType findBySqlMeetingSql(String sql){
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        Query query = session.createSQLQuery(sql);
        List<Object[]> list = query.list();
        List<MeetingType> meetingTypes = new ArrayList<MeetingType>();
        for(Object[] obj:list){
        	MeetingType mt = new MeetingType();
        	Object[] value = (Object[]) obj;
        	mt.setId(Short.valueOf(value[0].toString()));
        	mt.setName(String.valueOf(value[1]));
        	mt.setValue(Short.valueOf(value[2].toString()));
        	meetingTypes.add(mt);
        }
//        MeetingType meetingType = (MeetingType)query.list().get(0);
    	return meetingTypes.get(0);
    }
    
    public List<FinalUnifiedUser> findBySql(String sql){
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        Query query = session.createSQLQuery(sql);
        
        List<FinalUnifiedUser> finalUnifiedUser = new ArrayList<FinalUnifiedUser>();
        List<Object[]> list = query.list();
        for(Object[] obj:list){
        	FinalUnifiedUser user = new FinalUnifiedUser();
        	Object[] value = (Object[]) obj;
        	user.setDeptName(String.valueOf(value[0]));
        	user.setDeptNode(String.valueOf(value[1]));
        	user.setUserId(String.valueOf(value[2]));
        	user.setUserName(String.valueOf(value[3]));
        	user.setUserType(value[4] !=null ?Integer.parseInt(String.valueOf(value[4])):0);
        	user.setLianxiren(String.valueOf(value[5]));
        	user.setTelephone(String.valueOf(value[6]));
        	user.setMoblie(String.valueOf(value[7]));
        	user.setMail(String.valueOf(value[8]));
        	finalUnifiedUser.add(user);
        }
        
        return finalUnifiedUser;   
    }
    
    public List<T> findTbyHql(final String hql, final Object[] queryParams){
    	return getHibernateTemplate().execute(new HibernateCallback<List<T>>(){
            public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
            	Query query = session.createQuery(hql);
            	setQueryParams(query, queryParams);
            	return query.list();
            }
       });
    }

    
    public int executeDML(final String hql){
        Integer result = 0;
        result = getHibernateTemplate().bulkUpdate(hql);
        return result;
    }

    public int executeDML(final String sethql, Object[] values){
        Integer result = 0;
        final String entityname = getEntityName(this.entityClass);
        String hql = "update " + entityname + " o " + sethql ;
        result = getHibernateTemplate().bulkUpdate(hql, values);
        return result;
    }

    /**
	 * 更新meeting表中MCU相关参数信息
	 * <p>@Author baininghan
	 * <p>@Date 2015年2月27日
	 * @param conferenceGUID 网真会议主键ID
	 * @param CallMCU MCU定时循环标记
	 * @param tcsURL 录播播放链接地址
	 * @param id Meeting的主键ID
	 */
    public int executeDMLForMCU(String sethql, Object[] values){
        Integer result = 0;
        final String entityname = getEntityName(this.entityClass);
        String hql = "update " + entityname + " o " + sethql ;
        result = getHibernateTemplate().bulkUpdate(hql, values);
        return result;
    }
    
    public int executeSql(String sql){
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        Query query = session.createSQLQuery(sql);    
        // return ((java.math.BigInteger)query.uniqueResult()).intValue();
        if(query==null||query.uniqueResult()==null){
        	return 0;
        }
        return Integer.parseInt(query.uniqueResult().toString());
    }
    
    public List<T> limitFindByHql(final int firstindex, final int maxresult,  
        final String wherejpql, final Object[] queryParams,
        final LinkedHashMap<String, Object> orderby) {
        final String entityname = getEntityName(this.entityClass);
        return getHibernateTemplate().execute(new HibernateCallback<List<T>>(){
        	public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
        		Query query = session.createQuery("select o from "+ entityname+ " o "+(wherejpql==null || "".equals(wherejpql.trim())? "": "where 1=1 "+ wherejpql)+ buildOrderby(orderby));   
        		setQueryParams(query, queryParams);
        		if(firstindex!=-1 && maxresult!=-1)
        			query.setFirstResult(firstindex).setMaxResults(maxresult); 
        		return query.list();
             }
        });
    }

    public String createWhereSqlBySet(Map <String,Object> map){
    	if(map == null || map.isEmpty())
			return null;
		Set<String>set = map.keySet();
    	StringBuffer sb = new StringBuffer(" ");
    	if(set == null || set.isEmpty())
    		return sb.toString();
        for(String temp : set){
        	if(map.get(temp) instanceof String){
        		sb.append(" and o.").append(temp).append(" like ?");
        		map.put(temp, "%"+map.get(temp)+"%");
        	}else if(map.get(temp) instanceof Date){
        		if(temp.startsWith("s"))
        			sb.append(" and o.").append(temp).append(" >=?");
        		else if(temp.startsWith("e"))
        			sb.append(" and o.").append(temp).append(" <=?");
        		else
        			sb.append(" and o.").append(temp).append(" =?");
        	}else{
        		sb.append(" and o.").append(temp).append(" =?");
        	}
        }
    	return sb.toString();
    }

    public HibernateTemplate getHibernateTemplate(){
		return this.template;
	}

    @Resource(name = "hibernateTemplate")
	public void setTemplate(HibernateTemplate template) {
		this.template = template;
	}

}