package com.sooncode.jdbc;


import com.sooncode.utils.Pager;

import java.util.List;
import java.util.Map;

/**
 * Dao 接口 注意:子类必须在空构造器中对jdbc赋值
 * 
 * @author pc
 * 
 */
public interface DaoI<T> {

	/**
	 * 获取一个实体(逻辑上只有一个匹配的实体存在)
	 * 
	 * @param obj
	 *            封装的查询条件
	 * @return 实体
	 */
	public T get(T obj) throws Exception;

	/**
	 * 获取一个实体对象集
	 * 
	 * @param obj
	 * @return
	 */

	public List<T> gets(T obj) throws Exception;

	/**
	 * 分页查询
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param left
	 * @param others
	 * @return
	 */
	public Pager<T> getPager(Long pageNum, Long pageSize, T left, Object... others) throws Exception;

	/**
	 * 获取实体集
	 * 
	 * @param list
	 *            执行SQL语句得到的结果集
	 * @return 封装好的实体集
	 */

	public T getEntity(Map<String, Object> map) throws Exception;

	/**
	 * 获取实体集
	 * 
	 * @param list
	 *            执行SQL语句得到的结果集
	 * @return 封装好的实体集
	 */

	public List<T> getEntitys(List<Map<String, Object>> list) throws Exception;

	/**
	 * 超级查询
	 * 
	 * @param objs
	 * @return
	 */
	//public List<Map<String, Object>> getSupe(Object... objs);

	/**
	 * 获取查询长度
	 * 
	 * @param left
	 * @param others
	 * @return
	 */
	public Long getSize(T left, Object... others) throws Exception;

	/**
	 * 保存一个实体对象
	 * 
	 * @param object
	 * @return 保存数量
	 */
	public Long save(T object) throws Exception;

	/**
	 * 保存和更新智能匹配
	 * 
	 * @param obj
	 *            要保存或者更新的对象
	 * @return -1 ：没有更新 也没有保存
	 */
	public Long saveOrUpdate(T obj) throws Exception;

	/**
	 * 保存和更新智能匹配
	 * 
	 */
	public void saveOrUpdates(List<T> objs) throws Exception;

	/**
	 * 保存多个实体对象
	 * 
	 * @return 保存数量
	 */
	public Boolean saves(List<?> objs) throws Exception;

	/**
	 * 修改一个实体对象
	 * 
	 * @param object
	 * @return 更新数量
	 */
	public Long update(T object) throws Exception;

	/**
	 * 移除一个实体对象
	 * 
	 * @param object
	 * @return 删除数量
	 */
	public int delete(T object) throws Exception;
	/**
	 * 验证 object是否为空 或 其属性是否全为空
	 * 
	 * @param object
	 *            被验证的实体
	 * @return
	 */

	/**
	 * 模糊查询
	 * 
	 * @param obj
	 * @return
	 */

	public Dao<T> startGets(T obj) throws Exception;

	public Dao<T> like(String filed) throws Exception;

	public Dao<T> like(String field, String newValue) throws Exception;

	public List<T> endGets() throws Exception;

	/**
	 * 保存一个实体对象
	 * 
	 * @param object
	 * @return SQL语句
	 */
	public String insert4Sql(T object) throws Exception;

	/**
	 * 删除一个实体对象
	 * 
	 * @param object
	 * @return SQL语句
	 */
	public String delete4Sql(T object) throws Exception;

	/**
	 * 修改一个实体对象
	 * 
	 * @param object
	 * @return SQL语句
	 */
	public String update4Sql(T object)  throws Exception;

	/**
	 * 事务处理
	 * 
	 * @param 可执行的更新语句集
	 * @return 成功返回true,反之返回false.
	 */
	public boolean transaction(String... sqls) throws Exception;

}
