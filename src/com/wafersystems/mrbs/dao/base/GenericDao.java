package com.wafersystems.mrbs.dao.base;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import com.wafersystems.mrbs.vo.FinalUnifiedUser;
import com.wafersystems.mrbs.vo.meeting.MeetingType;
import com.wafersystems.mrbs.vo.user.Department;

/**
 * 通用Dao父类实现的接口，只提供常见的一些功能，其它特殊的功能，<br/>
 * 可以在子接口中定义。 事务也在需要的时候加到实现类中
 * @param <T> 实体类型
 */
public interface GenericDao<T> {

	public List<T> findTbyHql(String hql);
	
	public List<FinalUnifiedUser> findByHql(String hql);
	
	public List<FinalUnifiedUser> findBySql(String sql);
	
	public MeetingType findBySqlMeetingSql(String sql);
	
	public List<Department> findDBySql(String sql);

	public List<T> findTbyHql(final String hql, final Object[] queryParams);

	/**
	 * by this method you can get all information about T
	 * 
	 * @return
	 */
	public List<T> findAll();

	/**
	 * 和数据库同步
	 * 
	 * @param entityClass
	 *            实体类
	 * @return
	 */
	public void fulsh();

	/**
	 * 获取记录总数
	 * 
	 * @param entityClass
	 *            实体类
	 * @return
	 */
	public long getCount(final String wherejpql, final Object[] queryParams);

	/**
	 * 清除一级缓存的数据
	 */
	public void clear();

	/**
	 * 保存实体
	 * 
	 * @param entity 实体id
	 */
	public void save(Object entity);

	/**
	 * 保存或更新对象，有主键就更新否则保存<br/>
	 * （yangsf 2011-5-16添加）
	 * @param entity
	 */
	public void saveOrUpdate(Object entity);

	/**
	 * 更新实体
	 * @param entity  实体id
	 */
	public void update(Object entity);

	/**
	 * 删除实体
	 * 
	 * @param entityClass 实体类
	 * @param entityids 实体id数组
	 */
	public void delete(Serializable... entityids);
	
	public void deleteVO(Serializable... entityids) throws Throwable;

	/**
	 * 获取实体
	 * 
	 * @param <T>
	 * @param entityClass 实体类
	 * @param entityId 实体id
	 * @return
	 */
	public T get(Serializable entityId);

	/**
	 * 获取分页数据
	 * 
	 * @param <T>
	 * @param entityClass 实体类
	 * @param firstindex 开始索引
	 * @param maxresult 需要获取的记录数
	 * @return
	 */
	public PageData<T> getScrollData(int firstindex, int maxresult,
			String wherejpql, Object[] queryParams,
			LinkedHashMap<String, Object> orderby);

	public PageData<T> getScrollData(int firstindex, int maxresult,
			String wherejpql, Object[] queryParams);

	public PageData<T> getScrollData(int firstindex, int maxresult,
			LinkedHashMap<String, Object> orderby);

	public PageData<T> getScrollData(int firstindex, int maxresult);

	public PageData<T> getScrollData();

	public PageData<T> getScrollData(PageSortModel psm, String wherejpql,
			Object[] queryParams, LinkedHashMap<String, Object> orderby);

	public PageData<T> getScrollDataByHql(final int firstindex,
			final int maxresult, final String hql_search,
			final String hql_totalRecords);

	public int executeDML(final String hql);
	
	/**
	 * 更新meeting表中MCU相关参数信息
	 * <p>@Author baininghan
	 * <p>@Date 2015年2月27日
	 * @param conferenceGUID 网真会议主键ID
	 * @param CallMCU MCU定时循环标记
	 * @param tcsURL 录播播放链接地址
	 * @param id Meeting的主键ID
	 */
	public int executeDMLForMCU(String sethql, Object[] values);
	
	public int executeSql(String sql);

	public int executeDML(final String sethql, Object[] values);

	public List<T> limitFindByHql(final int firstindex, final int maxresult,
			final String wherejpql, final Object[] queryParams,
			final LinkedHashMap<String, Object> orderby);
}