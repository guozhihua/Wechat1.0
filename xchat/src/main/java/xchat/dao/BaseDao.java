package xchat.dao;



import xchat.sys.SuperVO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public interface BaseDao<T extends SuperVO> {

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

	public <V extends T> V selectOne(Map<String, Object> map);

	public <V extends T> V selectByPrimaryKey(Serializable id);

	public <V extends T> List<V> selectList(Map<String, Object> map);


	public Long selectCount(Map<String, Object> map);

	public int insert(T entity);

	public int deleteByPrimaryKey(Serializable id);

	public int deleteByIdFalse(Serializable id);

	public int updateByPrimaryKey(T entity);

	public int updateByPrimaryKeySelective(T entity);

	public int deleteByPrimaryKeyInBatch(List<Serializable> idList);

	public int insertInBatch(List<T> entityList);

	public int updateInBatch(List<T> entityList);
	
	
}
