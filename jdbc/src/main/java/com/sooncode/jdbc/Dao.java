package com.sooncode.jdbc;

import com.sooncode.cache.Cache;
import com.sooncode.ref.Genericity;
import com.sooncode.ref.RObject;
import com.sooncode.sql.ComSQL;
import com.sooncode.utils.Pager;
import com.sooncode.utils.T2E;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dao 基类 注意:子类必须在空构造器中对jdbc赋值
 * 
 * @author pc
 * 
 */
public abstract class Dao<T> {

	public final static Logger logger = Logger.getLogger("Dao.class");

	/** 是否需要缓存 */
	public Boolean hasCache = false;

	/** 数据处理对象JDBC */
	public Jdbc jdbc;

	public Dao() {
		jdbc = new Jdbc();
	}

	/** 临时SQL */
	private String sql;

	/** 临时对象 */
	private Object obj;

	/**
	 * 获取一个实体(逻辑上只有一个匹配的实体存在)
	 * 
	 * @param obj
	 *            封装的查询条件
	 * @return 实体
	 */
	@SuppressWarnings("unchecked")
	public T get(T obj) throws  Exception {

		if (hasCache == false) {
			// 去数据库查询
			String sql = ComSQL.select(obj);
			List<Map<String, Object>> list = jdbc.executeQueryL(sql);
			if (list.size() == 1) {
				List<T> tes = getEntitys(list);
				return tes.get(0);
			} else {
				return null;
			}

		} else {
			List<?> cache = Cache.get(obj);
			if (cache == null || cache.size() == 0) {
				String sql = ComSQL.select(obj);
				List<Map<String, Object>> list = jdbc.executeQueryL(sql);
				Cache.put(obj.getClass(), getEntitys(list));
				return getEntitys(list).get(0);
			} else {
				return (T) cache.get(0);
			}

		}
	}

	/**
	 * 获取一个实体对象集
	 * 
	 * @param obj
	 * @return
	 */

	@SuppressWarnings("unchecked")
	public List<T> gets(T obj)  throws Exception{
		if (hasCache == false) {
			// 去数据库查询
			String sql = ComSQL.select(obj);
			List<Map<String, Object>> list = jdbc.executeQueryL(sql);
			return getEntitys(list);
		} else {
			List<?> cache = Cache.get(obj);
			if (cache == null || cache.size() == 0) {
				String sql = ComSQL.select(obj);
				List<Map<String, Object>> list = jdbc.executeQueryL(sql);
				Cache.put(obj.getClass(), getEntitys(list));
				return getEntitys(list);
			} else {
				return (List<T>) cache;
			}
		}
	}

	/**
	 * 批量查询
	 * 
	 * @param objs
	 * @return
	 */

	/* public List<T> gets(List<?> objs) {
	 // 去数据库查询
	 String sql = ComSQL.select(obj);
	 List<Map<String, Object>> list = jdbc.executeQueryL(sql);
	 return getEntitys(list);
	 }*/

	/**
	 * 模糊查询
	 * 
	 * @param obj
	 * @return
	 */

	public Dao<T> startGets(T obj)  throws Exception{
		// 去数据库查询
		this.sql = ComSQL.select(obj);
		//logger.debug("[模糊查询]" + sql);
		this.obj = obj;
		return this;// getEntitys(list);
	}

	public Dao<T> like(String field) throws  Exception {

		String com = T2E.toColumn(field);
		RObject rObj = new RObject(this.obj);
		Object value = rObj.getFiledAndValue().get(field);
		String subSql = com + " = " + "'" + value + "'";
		String like = com + " LIKE " + "'%" + value + "%'";

		this.sql = sql.replace(subSql, like);
		return this;// getEntitys(list);
	}

	public Dao<T> like(String field, String newValue) throws Exception{

		String com = T2E.toColumn(field);
		RObject rObj = new RObject(this.obj);
		Object value = rObj.getFiledAndValue().get(field);
		String subSql = com + " = " + "'" + value + "'";
		String like = com + " LIKE " + "'%" + newValue + "%'";

		this.sql = sql.replace(subSql, like);
		return this;// getEntitys(list);
	}

	public List<T> endGets()  throws Exception{
		// logger.debug("【模糊查询】" + sql);
		List<Map<String, Object>> list = jdbc.executeQueryL(sql);
		sql = "";
		this.obj = null;
		return getEntitys(list);

	}

	/**
	 * 分页查询
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param left
	 * @param others
	 * @return 没有查询到数据时:Pager的 lists大小为0; entity属性为空;
	 */

	public Pager<T> getPager(Long pageNum, Long pageSize, T left, Object... others)  throws Exception{

		// 1.单表
		if (others.length == 0) {
			String sql = ComSQL.select(left, pageNum, pageSize);
			List<Map<String, Object>> list = jdbc.executeQueryL(sql);
			List<T> ts = getEntitys(list);

			Long size = getSize(left, others);
			Pager<T> pager = new Pager<>(pageNum, pageSize, size, ts);
			return pager;

		} else if (others.length == 1) {// 3.一对多
			RObject leftRO = new RObject(left);
			RObject rightRO = new RObject(others[0]);
			String leftPk = leftRO.getPk();
			String rightPk = rightRO.getPk();

			if (rightRO.hasField(leftPk) && !leftRO.hasField(rightPk)) {
				String sql = ComSQL.getO2M(left, others[0], pageNum, pageSize);
				List<Map<String, Object>> list = jdbc.executeQueryL(sql);
				T t = this.get2M(list);
				long size = getSize(left, others);
				Pager<T> pager = new Pager<>(pageNum, pageSize, size, t);

				return pager;
			} else {
				return o2o(pageNum, pageSize, left, others);
			}
		} else if (others.length == 2) {// 4.多对多
			String leftPk = new RObject(left).getPk();
			String rightPk = new RObject(others[1]).getPk();
			RObject middle = new RObject(others[0]);
			if (middle.hasField(leftPk) && middle.hasField(rightPk)) {
				String sql = ComSQL.getM2M(left, others[0], others[1], pageNum, pageSize);
				// logger.debug("SQL:" + sql);
				T t = this.get2M(jdbc.executeQueryL(sql));
				// RObject m = new RObject(middle);
				// m.setPk(new RObject(left).getPkValue());
				long size = getSize(left, others);
				Pager<T> pager = new Pager<T>(pageNum, pageSize, size, t);
				return pager;
			} else { // 一对一
				return o2o(pageNum, pageSize, left, others);
			}

		} else {// 一对一
			return o2o(pageNum, pageSize, left, others);
		}

	}

	/**
	 * 获取实体集
	 * 
	 * @param list
	 *            执行SQL语句得到的结果集
	 * @return 封装好的实体集
	 */
	@SuppressWarnings("unchecked")
	public T getEntity(Map<String, Object> map)  throws  Exception{

		String tClassName = Genericity.getGenericity(this.getClass(), 0);// 泛型T实际运行时的全类名

		RObject rObject = new RObject(tClassName);
		List<Field> fields = rObject.getFields();
		for (int i = 0; i < fields.size(); i++) {
			String fieldName = fields.get(i).getName();
			Object o = map.get(fieldName);
			rObject.invokeSetMethod(fields.get(i).getName(), o);
		}

		return (T) rObject.getObject();

	}

	/**
	 * 获取实体集
	 * 
	 * @param list
	 *            执行SQL语句得到的结果集
	 * @return 封装好的实体集
	 */
	@SuppressWarnings("unchecked")
	public List<T> getEntitys(List<Map<String, Object>> list) throws Exception {
		String tClassName = Genericity.getGenericity(this.getClass(), 0);// 泛型T实际运行时的全类名
		List<T> objects = new ArrayList<>();
		for (Map<String, Object> map : list) {
			RObject rObject = new RObject(tClassName);
			List<Field> fields = rObject.getFields();
			for (int i = 0; i < fields.size(); i++) {
				String fieldName = fields.get(i).getName();
				Object o = map.get(fieldName);
				rObject.invokeSetMethod(fields.get(i).getName(), o);
			}
			objects.add((T) rObject.getObject());
		}
		return objects;

	}

	/**
	 * 1对多 ，多对多 查询
	 * 
	 * @param list
	 *            执行SQL语句得到的结果集
	 * @return 封装好的实体集 ,没有数据时返回空
	 */
	@SuppressWarnings("unchecked")
	private T get2M(List<Map<String, Object>> list) throws Exception {
		// 验证 list 参数
		if (list == null || list.size() == 0) {
			return null;
		}
		String tClassName = Genericity.getGenericity(this.getClass(), 0);// 泛型T实际运行时的全类名
		RObject leftObjet = new RObject(tClassName);
		List<Field> fields = leftObjet.getFields();

		Map<String, Object> map = list.get(0);
		String rightClassName = "";

		for (Map.Entry<String, Object> en : map.entrySet()) {
			String str = T2E.toColumn(en.getKey());
			String[] strs = str.split("_");
			String className = strs[0];
			if (!leftObjet.getClassName().toUpperCase().contains(className)) {
				rightClassName = className; // 获取 右实体对应的 类名 (大写)
				break;
			}
		}

		for (Field f : fields) {
			Object obj = map.get(T2E.toField(Genericity.getSimpleName(tClassName).toUpperCase() + "_" + T2E.toColumn(f.getName())));
			if (obj != null) {
				leftObjet.invokeSetMethod(f.getName(), obj); // 给左实体对应的类对象的属性赋值
			}
		}

		// logger.debug(leftObjet.getObject());

		Map<String, String> rightFields = new HashMap<String, String>();
		Map<String, String> listFields = new HashMap<String, String>();

		for (Field f : fields) {
			if (f.getType().toString().contains("interface java.util.List")) {
				ParameterizedType pt = (ParameterizedType) f.getGenericType();
				String str = pt.getActualTypeArguments()[0].toString(); // 获取List泛型参数类型名称
																		// (第一个参数)
				str = str.replace("class ", "").trim();// 全类名
				String[] strs = str.split("\\.");
				String simpleName = strs[strs.length - 1].toUpperCase();
				rightFields.put(simpleName, str);
				listFields.put(simpleName, f.getName()); // 属性名称
			}
		}

		String rightClass = rightFields.get(rightClassName);// 全类名
		// logger.debug(rightClass);
		List<Object> rightObjects = new ArrayList<>();
		// 获取 右端 实体集
		for (Map<String, Object> map2 : list) {

			RObject rightO = new RObject(rightClass);

			List<Field> fs = rightO.getFields();
			for (Field f : fs) {

				Object o = map2.get(T2E.toField(rightClassName.toUpperCase() + "_" + T2E.toColumn(f.getName())));

				if (o != null) {
					rightO.invokeSetMethod(f.getName(), o);
				}

			}
			rightObjects.add(rightO.getObject());
		}

		String listField = listFields.get(rightClassName);
		leftObjet.invokeSetMethod(listField, rightObjects); //
		return (T) leftObjet.getObject();

	}

	/**
	 * 超级查询
	 * 
	 * @param objs
	 * @return
	 */
	/* public List<Map<String, Object>> getSupe(Object... objs) {
		String sql = ComSQL.supSelect(objs);
		return jdbc.executeQueryL(sql);
	} */

	/**
	 * 一对一查询
	 * 
	 * @param list
	 *            执行SQL语句得到的结果集
	 * @return 封装好的实体集
	 */

	@SuppressWarnings("unchecked")
	private List<T> getO2O(List<Map<String, Object>> list) throws Exception{
		// 基本的数据类型
		String str = "Integer Long Short Byte Float Double Character Boolean Date String List";

		String tClassName = Genericity.getGenericity(this.getClass(), 0);// 泛型T实际运行时的全类名

		List<Object> resultList = new ArrayList<>();

		for (Map<String, Object> m : list) { // 遍历数据库返回的数据集合
			RObject rObject = new RObject(tClassName);
			List<Field> fields = rObject.getFields();// T 对应的属性

			for (Field f : fields) {
				Object obj = m.get(T2E.toField(Genericity.getSimpleName(tClassName).toUpperCase() + "_" + T2E.toColumn(f.getName())));
				if (obj != null) {
					rObject.invokeSetMethod(f.getName(), obj);
				}

				String typeSimpleName = f.getType().getSimpleName();
				String typeName = f.getType().getName();
				if (!str.contains(typeSimpleName)) {

					RObject rO = new RObject(typeName);

					List<Field> fs = rO.getFields();

					for (Field ff : fs) {

						Object o = m.get(T2E.toField(typeSimpleName.toUpperCase() + "_" + T2E.toColumn(ff.getName())));
						if (o != null) {
							rO.invokeSetMethod(ff.getName(), o);
						}

					}

					rObject.invokeSetMethod(f.getName(), rO.getObject());
				}
			}

			resultList.add(rObject.getObject());

		}

		return (List<T>) resultList;

	}

	/**
	 * 获取查询长度
	 * 
	 * @param left
	 * @param others
	 * @return
	 */
	public Long getSize(T left, Object... others) throws Exception {
		String sql = "";
		if (others.length == 0) { // 单表
			sql = ComSQL.selectSize(left);
		} else

		if (others.length == 1) { // 一对多
			RObject leftRO = new RObject(left);
			RObject rightRO = new RObject(others[0]);
			String leftPk = leftRO.getPk();
			String rightPk = rightRO.getPk();

			if (rightRO.hasField(leftPk) && !leftRO.hasField(rightPk)) {
				sql = ComSQL.getO2Msize(left, others[0]);
			} else {
				sql = ComSQL.O2OSize(left, others);
			}

		} else if (others.length == 2) {// 多对多
			String leftPk = new RObject(left).getPk();
			String rightPk = new RObject(others[1]).getPk();
			RObject middle = new RObject(others[0]);
			if (middle.hasField(leftPk) && middle.hasField(rightPk)) {
				sql = ComSQL.selectSize(others[1]);
			} else {
				sql = ComSQL.O2OSize(left, others);
			}
		} else {// 一对一
			sql = ComSQL.O2OSize(left, others);
		}

		 
		Map<String, Object> map = jdbc.executeQueryM(sql);
		Object obj = map.get("size");
		return (Long) obj;

	}

	/**
	 * 保存一个实体对象 
	 * 
	 * @param object
	 * @return 保存数量
	 */
	public Long save(T object) throws Exception{
		// 验证obj
		if (isNull(object) == false) {
			return null;
		}
		String sql = ComSQL.insert(object).toString();
		Long n = jdbc.executeUpdate(sql);
		if (hasCache && n != null) {
			Cache.remove(object.getClass());
		}

		return n;

	}

	/**
	 * 保存多个实体对象
	 * 
	 * @param object
	 * @return 保存数量
	 */
	public Boolean saves(List<?> objs) throws Exception {
		// 验证obj
		for (Object object : objs) {
			if (isNull(object) == false) {
				return false;
			}

		}
		List<String> sqls = new ArrayList<>();
		for (Object obj : objs) {
			String sql = new String(ComSQL.insert(obj).toString());
			sqls.add(sql);
		}

		if (hasCache && objs.size() > 0) {
			Cache.remove(objs.get(0).getClass());
		}
		return jdbc.executeUpdates(sqls);

	}

	/**
	 * 保存和更新智能匹配 多个实体
	 * 
	 * @param objs
	 */
	public void saveOrUpdates(List<T> objs)  throws Exception{
		List<String> sqls = new ArrayList<>();
		for (T object : objs) {
			RObject rObj = new RObject(object);
			Object id = rObj.getPkValue();
			if (id != null) {// obj 有id update();
				sqls.add(ComSQL.update(object));

			} else {// obj 没有id
				Object oldObj = get(object);
				if (oldObj == null) {
					sqls.add(ComSQL.insert(object));
				}
			}
		}
		if (hasCache && objs.size() > 0) {
			Cache.remove(objs.get(0).getClass());
		}
		jdbc.executeUpdates(sqls);
	}

	/**
	 * 保存和更新智能匹配
	 * 
	 * @param obj
	 *            要保存或者更新的对象
	 * @return -1 ：没有更新 也没有保存
	 */
	public Long saveOrUpdate(T obj)  throws  Exception{

		RObject rObj = new RObject(obj);
		Object id = rObj.getPkValue();
		if (id == null) {// obj 没有id 保存();
			return save(obj);
		} else {// obj 有id

			String pk = rObj.getPk();
			RObject newRobj = new RObject(obj.getClass());
			newRobj.invokeSetMethod(pk, id);
			@SuppressWarnings("unchecked")
			T newObj = (T) newRobj.getObject();
			Object oldObj = get(newObj); // 去数数据库查询

			if (hasCache) {
				Cache.remove(obj.getClass());
			}
			if (oldObj == null) { // 数据库没有记录
				return save(obj);// 保存
			} else {// 数据库有记录
				return update(obj);// 更新
			}
		}

	}

	/**
	 * 修改一个实体对象
	 * 
	 * @param object
	 * @return 更新数量
	 */
	public Long update(T object) throws Exception{
		if (isNull(object) == false) {
			return 0L;
		}
		String sql = ComSQL.update(object).toString();
		Long n = jdbc.executeUpdate(sql);
		if (hasCache && n != null) {
			Cache.remove(obj.getClass());
		}
		return n;

	}

	/**
	 * 移除一个实体对象
	 * 
	 * @param object
	 * @return 删除数量
	 */
	public int delete(T object) throws Exception{
		if (isNull(object) == false) {
			return 0;
		}
		String sql = ComSQL.delete(object).toString();
		int n = new Long(jdbc.executeUpdate(sql)).intValue();
		if (hasCache && n != 0) {
			Cache.remove(obj.getClass());
		}
		return n;

	}

	/**
	 * 验证 object是否为空 或 其属性是否全为空
	 * 
	 * @param object
	 *            被验证的实体
	 * @return
	 */
	private boolean isNull(Object object) throws Exception{
		if (object == null) {
			return false;
		}
		// obj的属性值不全为null
		RObject rObj = new RObject(object);
		Map<String, Object> files = rObj.getFiledAndValue();
		boolean b = false;
		for (Map.Entry<String, Object> en : files.entrySet()) {
			if (en.getValue() == null) {
				b = b || false;
			} else {
				b = b || true;
			}
		}

		if (b == false) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param left
	 * @param others
	 * @return
	 */
	private Pager<T> o2o(Long pageNum, Long pageSize, T left, Object... others) throws Exception{
		String sql = ComSQL.getO2O(left, others);
		// logger.debug(sql);
		List<Map<String, Object>> list = jdbc.executeQueryL(sql);
		List<T> ts = this.getO2O(list);
		long size = getSize(left, others);
		Pager<T> pager = new Pager<>(pageNum, pageSize, size, ts);
		return pager;
	}

	/**
	 * 保存一个实体对象
	 * 
	 * @param object
	 * @return 保存数量
	 */
	public String insert4Sql(T object) throws Exception {
		String sql = ComSQL.insert(object).toString();
		return sql;

	}

	/**
	 * 删除一个实体对象
	 * 
	 * @param object
	 * @return 删除语句
	 */
	public String delete4Sql(T object) throws Exception {
		String sql = ComSQL.delete(object).toString();
		return sql;

	}

	/**
	 * 修改一个实体对象
	 * 
	 * @param object
	 * @return 更新数量
	 */
	public String update4Sql(T object) throws Exception{
		String sql = ComSQL.update(object).toString();
		return sql;

	}

	/**
	 * 事务处理
	 * 
	 * @param ms
	 *            含更新语句返回模型集
	 * @return 成功返回true,反之返回false.
	 */
	public boolean transaction(String... sqls) {
		return jdbc.transaction(sqls);
	}

}
