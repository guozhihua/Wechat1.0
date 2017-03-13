package com.sooncode.jdbc;

import com.sooncode.utils.Pager;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * JDBC 服务
 * 
 * @author pc
 * 
 */
public final class JdbcService {

	public final static Logger logger = Logger.getLogger("JdbcService.class");

	/**
	 * 数据处理对象JDBC
	 */
	private JdbcDao jdbcDao;

	public JdbcService() {
		jdbcDao = new JdbcDao();
	}

	public JdbcService(String dbKey) {
		jdbcDao = new JdbcDao(dbKey);
	}

	/**
	 * 获取一个实体(逻辑上只有一个匹配的实体存在)
	 * 
	 * @param obj
	 *            封装的查询条件
	 * @return
	 * @return 实体
	 */
	public Object get(Object obj) throws Exception {
		return jdbcDao.get(obj);
	}

	/**
	 * 获取多个实体
	 * 
	 * @param obj
	 *            封装的查询条件
	 * @return 实体集
	 */
	public List<?> gets(Object obj) throws Exception {

		return jdbcDao.gets(obj);
	}

	/**
	 * 分页查询
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param left
	 * @param others
	 * @return
	 */
	public Pager<?> getPager(Long pageNum, Long pageSize, Object left, Object... others)  throws Exception{
		return jdbcDao.getPager(pageNum, pageSize, left, others);
	}

	/**
	 * 保存一个实体对象
	 * 
	 * @param object
	 * @return 保存数量
	 */
	public Long save(Object object) throws Exception{
		return jdbcDao.save(object);

	}

	/**
	 * 保存多个实体对象
	 * 
	 * @param object
	 * @return 保存数量
	 */
	public void saves(List<Object> objs) throws Exception{
		// 验证obj
		jdbcDao.saves(objs);

	}

	/**
	 * 保存和更新智能匹配 多个实体
	 * 
	 * @param objs
	 */
	public void saveOrUpdates(List<Object> objs) throws Exception {
		jdbcDao.saveOrUpdates(objs);
	}

	/**
	 * 保存和更新智能匹配
	 * 
	 * @param obj
	 *            要保存或者更新的对象
	 * @return -1 ：没有更新 也没有保存
	 */
	public Long saveOrUpdate(Object obj) throws Exception {

		return jdbcDao.saveOrUpdate(obj);

	}

	/**
	 * 修改一个实体对象
	 * 
	 * @param object
	 * @return 更新数量
	 */
	public Long update(Object object)  throws Exception{
		return jdbcDao.update(object);

	}

	/**
	 * 移除一个实体对象
	 * 
	 * @param object
	 * @return 删除数量
	 */
	public int delete(Object object) throws Exception {
		return jdbcDao.delete(object);

	}

	 
}