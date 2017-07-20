package com.yj.base.services;


import java.io.Serializable;
import java.util.List;
import java.util.Map;


/** 
* <p>类功能说明: 基本服务封装</p>
* @version V1.0
*/

public  interface BaseService<T> {
	List<T> selectList(Map<String,Object> paramMap);

	long update(T entity);

	void insertSelective(T entity);

	void delete(T entiy);

	/**
	 * <p>根据ID查找实体</p>
	 * @param id  实体编号
	 * @return  如果查到返回实体，否则返回null
	 */
	public abstract T get(Serializable id);

	/**
	 * <p>根据id删除实体</p>
	 * @param id
	 */
	public abstract void delete(Serializable id);

	public T queryById(String id) ;

	public T queryOne(Map<String,Object> map) ;

	public List<T> queryList(Map<String,Object> map,int pageNum, int pageSize) throws Exception ;

//	public PageInfo<List<T>> queryPage(Map<String,Object> map,int pageNum, int pageSize) throws Exception ;

	public void add(T entity) throws Exception ;

	public void addSelective(T entity) throws Exception;

	public int deleteById(String id) throws Exception ;

	public int deleteFalse(String id) throws Exception ;

	public int updateByIdSelective(T entity) throws Exception ;

	public int updateById(T entity) throws Exception ;

}
