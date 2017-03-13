package com.sooncode.sql;

import com.sooncode.ref.RObject;
import com.sooncode.utils.T2E;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

//import org.apache.log4j.Logger;

/**
 * 常见的SQL语句构造类
 * 
 * @author pc
 *
 */
public class ComSQL {

	//private static Logger logger = Logger.getLogger("Sql.class");

	/**
	 * 构造插入数据的可执行的SQL 说明 :1.根据object对象的类名映射成数据库表名.
	 * 2.根据object对象的属性,映射成字段,根据其属性值插入相应数据.
	 * 
	 * @param object
	 *            数据对象
	 * @return 可执行SQL
	 */
	public static String insert(Object object)  throws Exception{
		 
		String tableName = T2E.toColumn(object.getClass().getSimpleName());
		Map<String, Object> map =  new RObject(object).getFiledAndValue();
		String columnString = "(";
		String filedString = "(";
		int n = 0;
		for (Entry<String, Object> entry : map.entrySet()) {

			columnString = columnString + T2E.toColumn(entry.getKey());
			if (entry.getValue() == null) {
				filedString = filedString + "NULL";
			} else {

				if (entry.getValue().getClass().getName().equals("java.util.Date")) {
					filedString = filedString + "'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(entry.getValue()) + "'";
				} else {
					filedString = filedString + "'" + entry.getValue() + "'";
				}
			}
			if (n != map.size() - 1) {
				columnString += ",";
				filedString += ",";
			} else {
				columnString += ")";
				filedString += ")";
			}
			n++;

		}
		String sqlString = "INSERT INTO " + tableName + columnString + " VALUES " + filedString;
		//logger.debug("【可执行SQL】: " + sqlString);
		return sqlString;
	}

	/**
	 * 删除
	 *
	 * @param object
	 * @return
	 */
	public static String delete(Object object) throws Exception{
		String tableName = T2E.toColumn(object.getClass().getSimpleName());
	    Map<String, Object> map = new RObject(object).getFiledAndValue();
		String sql = "DELETE FROM " + tableName + " WHERE ";
		String s = "";
		int n = 0;
		for (Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() != null) {
				if (n != 0) {
					s = s + " AND ";
				}
				s = s + T2E.toColumn(entry.getKey()) + "='" + entry.getValue() + "'";
				n++;
			}
		}
		sql = sql + s;
		//logger.debug("【可执行SQL】: " + sql);
		return sql;

	}

	/**
	 * 获取修改数据的SQL
	 *
	 * @param obj
	 * @return
	 */
	public static String update(Object object)  throws Exception{

		String tableName = T2E.toColumn(object.getClass().getSimpleName());
		Map<String, Object> map = new RObject(object).getFiledAndValue();
		RObject rObject = new RObject(object);
		int n = 0;
		String s = "";
		String pk = T2E.toColumn(rObject.getPk());

		String pkString = pk + "='" + rObject.getPkValue() + "'";
		for (Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() != null && !entry.getKey().trim().equals(rObject.getPk().trim())) {
				if (n != 0) {
					s = s + " , ";
				}
				if (entry.getValue().getClass().getName().equals("java.util.Date")) {
					s = s + T2E.toColumn(entry.getKey()) + "='" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(entry.getValue()) + "'";
				} else {
					s = s + T2E.toColumn(entry.getKey()) + "='" + entry.getValue() + "'";
				}
				n++;
			}
		}

		String sql = "UPDATE " + tableName + "  SET  " + s + " WHERE " + pkString;
		//logger.debug("【可执行SQL】: " + sql);
		return sql;
	}

	/**
	 * 获取查询语句的可执行SQL
	 *
	 * @param object
	 * @return 可执行SQL
	 */
	public static String select(Object object) throws Exception {
		String tableName = T2E.toColumn(object.getClass().getSimpleName());
		Map<String, Object> map = new RObject(object).getFiledAndValue();
		int m = 0;
		String s = "1=1";
		String c = "";
		for (Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() != null) {
				s = s + " AND ";
				s = s + T2E.toColumn(entry.getKey()) + " = '" + entry.getValue() + "'";
			}
			if (m != 0) {
				c = c + ",";
			}
			c = c + T2E.toColumn(entry.getKey());
			m++;
		}
		String sql = "SELECT " + c + " FROM " + tableName + " WHERE " + s;
		//logger.debug("【可执行SQL】:" + sql);

		return sql;
	}

	public static String from (Object...objs){
		String from = "";
		int n = 0;
		for (Object o : objs) {

			if(n != 0){
				from = from + " , ";
			}

			from = from + T2E.toColumn(o.getClass().getSimpleName());
			n++;
		}
		return from;
	}

	/**
	 *
	 * 超级查询
	 * @param object
	 * @return 可执行SQL
	 */
	public static String supSelect(Object ... objs) throws Exception{

		String sql = "SELECT " + columns(objs) + " FROM "+ from(objs) + " WHERE " + joint(objs) + where(objs);

		return sql;
	}

	/**
	 *
	 * 超级查询 连接分析
	 * @param object
	 * @return 可执行SQL
	 */
	public static String joint(Object ... objs) throws Exception {

		Map<String,String> map = new HashMap<>();
		for (Object o : objs) {
			RObject rO = new RObject(o);
			map.put(rO.getClassName(),rO.getPk());
		}

		String joint = "";

		for(Entry<String, String> e : map.entrySet()){

			String fk = T2E.toColumn(e.getValue());
			String value = e.getValue();
			for (Object o : objs) {
				RObject rO = new RObject(o);
				if(rO.hasField(value)  && !e.getKey().equals(rO.getClassName())){
					joint = joint + " AND "	;
					joint = joint +T2E.toColumn(e.getKey())+"."+fk + " = " + T2E.toColumn(rO.getClassName())+"."+ fk;

				}
			}


		}

		if(joint.equals("")){
			joint = "1 = 1";
		}

		return joint;
	}




	/**
	 * 获取字段
	 * @param obj
	 * @return
	 */
	public static String columns (Object object) throws  Exception{
		String tableName = T2E.toColumn(object.getClass().getSimpleName());
		Map<String, Object> columns = new RObject(object).getFiledAndValue();
		int m = 0;
		String c = "";
		for (Entry<String, Object> entry : columns.entrySet()) {
			if (m != 0) {
				c = c + ",";
			}
			c = c +tableName + "."+ T2E.toColumn(entry.getKey()) + " AS "+tableName + "_"+ T2E.toColumn(entry.getKey());
			m++;
		}
		return c;
	}


	/**
	 * 获取字段
	 * @param obj
	 * @return
	 */
	public static String columns (Object...objs) throws Exception{
		 String sql = "";
		 int i = 0;
		 for (Object o : objs) {
			 if(i != 0){
				 sql = sql + " , ";
			 }
			sql = sql + columns(o);
			i++;
		}
		 return sql;
	}
	/**
	 * 查询条件sql片段
	 *
	 * @param object
	 * @return 可执行SQL
	 */
	public static String where(Object object) throws Exception{
		String tableName = T2E.toColumn(object.getClass().getSimpleName());
	    Map<String, Object> map = new RObject(object).getFiledAndValue();
		String s = "";
		for (Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() != null) {
				s = s + " AND ";
				s = s + tableName + "."+ T2E.toColumn(entry.getKey()) + " = '" + entry.getValue() + "'";
			}
		}
		return s;
	}
	public static String where(Object...objs) throws Exception{
		 String where = "";
		 for (Object o : objs) {
			where = where + " " + where(o);
		}
		 return where;
	}

	/**
	 * 获取查询语句的可执行SQL(带分页)
	 *
	 * @param object
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public static String select(Object object, Long pageNumber, Long pageSize) throws Exception {
		Long index = (pageNumber - 1) * pageSize;
		String sql = select(object) + " LIMIT " + index + "," + pageSize;
		return sql;
	}

	/**
	 * 获取记录的条数的可执行SQL
	 *
	 * @param object
	 * @return 可执行SQL
	 */
	public static String selectSize(Object object) throws Exception{
		String tableName = T2E.toColumn(object.getClass().getSimpleName());
	    Map<String, Object> map = new RObject(object).getFiledAndValue();

		int m = 0;
		String s = "1=1";
		String c = "";
		for (Entry<String, Object> entry : map.entrySet()) {
			if (entry.getValue() != null) {
				s = s + " AND ";
				s = s + T2E.toColumn(entry.getKey()) + "='" + entry.getValue() + "'";
			}
			if (m != 0) {
				c = c + ",";
			}
			c = c + T2E.toColumn(entry.getKey());
			m++;
		}
		String sql = "SELECT COUNT(1) AS SIZE" + " FROM " + tableName + " WHERE " + s;
		//logger.debug("可执行SQL语句:" + sql);
		return sql;

	}

	/**
	 * 获取记录的条数的可执行SQL
	 *
	 * @param object
	 * @return 可执行SQL
	 */
	public static String O2OSize(Object left, Object... others) throws Exception{
		String leftTable = T2E.toColumn(left.getClass().getSimpleName());

		Map<String, String> map = new HashMap<>();
		for (int i = 0; i < others.length; i++) {
			String simpleName = others[i].getClass().getSimpleName();
			String table = T2E.toColumn(simpleName);
			RObject rObject = new RObject(others[i]);
			String pk = T2E.toColumn(rObject.getPk());
			map.put(table, pk);
		}

		String where = "";
		String from = " " + leftTable;
		int m = 0;
		for (Entry<String, String> en : map.entrySet()) {
			if (m != 0) {
				where = where + " AND ";
			}
			where = where + leftTable + "." + en.getValue() + " = " + en.getKey() + "." + en.getValue();
			from = from + "," + en.getKey();
			m++;
		}

        String leftWhere = where(left);

		for (Object obj : others) {
			leftWhere = leftWhere + where(obj);
		}
		String sql = "SELECT COUNT(1) AS SIZE  FROM " + from + " WHERE " + where + leftWhere;

		return sql;

	}

	/**
	 * 获取多对多映射的可执行SQL
	 *
	 * @param left
	 *            主表对应的实体类
	 * @param middle
	 *            中间表对应的实体类
	 * @param right
	 *            N端对应的实体类
	 * @return 可执行SQL
	 */
	public static String getM2M(Object left, Object middle, Object right, long pageNumber, long pageSize) throws Exception {

		String leftTable = T2E.toColumn(left.getClass().getSimpleName());
		String middleTable = T2E.toColumn(middle.getClass().getSimpleName());
		String rightTable = T2E.toColumn(right.getClass().getSimpleName());

		RObject leftRObject = new RObject(left);
		RObject rightRObject = new RObject(right);

		String leftPk = T2E.toColumn(leftRObject.getPk());
		String rightPk = T2E.toColumn(rightRObject.getPk());
		Map<String, Object> leftFileds = new RObject(left).getFiledAndValue();//EntityCache.getKeyAndValue(left);
		Map<String, Object> rightFileds =new RObject(right).getFiledAndValue();// EntityCache.getKeyAndValue(right);

		String col = "";
		int n = 0;
		for (Entry<String, Object> en : leftFileds.entrySet()) {
			if (n != 0) {
				col = col + " , ";
			}
			col = col + leftTable + "." + T2E.toColumn(en.getKey()) + " AS " + leftTable + "_" + T2E.toColumn(en.getKey());
			n++;
		}
		for (Entry<String, Object> en : rightFileds.entrySet()) {

			col = col + " , " + rightTable + "." + T2E.toColumn(en.getKey()) + " AS " + rightTable + "_" + T2E.toColumn(en.getKey());

		}
		String sql = "SELECT " + col + " FROM " + leftTable + " ," + middleTable + " , " + rightTable;

		sql = sql + " WHERE " + leftTable + "." + leftPk + " = " + middleTable + "." + leftPk + " AND " + rightTable + "." + rightPk + " = " + middleTable + "." + rightPk + " AND " + leftTable + "." + leftPk + "='" + leftFileds.get(T2E.toField(leftPk)) + "'";
		Long index = (pageNumber - 1) * pageSize;
		sql = sql + " LIMIT " + index + "," + pageSize;

		return sql;
	}

	/**
	 * 获取 一对一模型的可执行SQL
	 *
	 * @param left
	 *            被参照表对应的实体类
	 * @param other
	 *            其他参照表对应的实体类 ,至少有一个实体类
	 * @return 可执行SQL
	 */
	public static String getO2M(Object left, Object right,long pageNumber,long pageSize) throws Exception {


		String leftTable = T2E.toColumn(left.getClass().getSimpleName());
		String rightTable = T2E.toColumn(right.getClass().getSimpleName());
		RObject leftRObject = new RObject(left);
		String leftPk = T2E.toColumn(leftRObject.getPk());
		Object leftValue = leftRObject.getPkValue();
		Map<String, Object> leftFileds =new RObject(left).getFiledAndValue();// EntityCache.getKeyAndValue(left);
		String col = "";
		int n = 0;
		for (Entry<String, Object> en : leftFileds.entrySet()) {
			if (n != 0) {
				col = col + ",";
			}
			col = col + " " + leftTable + "." + T2E.toColumn(en.getKey()) + " AS " + leftTable + "_" + T2E.toColumn(en.getKey());
			n++;
		}

		Map<String, Object> field = new RObject(right).getFiledAndValue();//EntityCache.getKeyAndValue(right);

		for (Entry<String, Object> en : field.entrySet()) {
			col = col + "," + rightTable + "." + T2E.toColumn(en.getKey()) + " AS " + rightTable + "_" + T2E.toColumn(en.getKey());
		}

		String where = "";
		String from = " " + leftTable;

		where = where + leftTable + "." + leftPk + " = " + rightTable + "." + leftPk;
		where = where +" AND "+leftTable + "." + leftPk + " = '" + leftValue + "'" ;
		from = from + "," + rightTable;

		String sql = "SELECT " + col + " FROM " + from + " WHERE " + where;
		Long index = (pageNumber - 1) * pageSize;
		sql = sql + " LIMIT " + index + "," + pageSize;
		return sql;
	}
	/**
	 * 获取 一对
	 * @param left
	 *            被参照表对应的实体类
	 * @param other
	 *            其他参照表对应的实体类 ,至少有一个实体类
	 * @return 可执行SQL
	 */
	public static String getO2Msize(Object left, Object right) throws Exception {

		String leftTable = T2E.toColumn(left.getClass().getSimpleName());
		String rightTable = T2E.toColumn(right.getClass().getSimpleName());
		RObject leftRObject = new RObject(left);
		String leftPk = T2E.toColumn(leftRObject.getPk());
		Object leftValue = leftRObject.getPkValue();

		String where = "";
		String from = " " + leftTable;

		where = where + leftTable + "." + leftPk + " = " + rightTable + "." + leftPk;
		where = where +" AND "+leftTable + "." + leftPk + " = '" + leftValue + "'" ;
		from = from + "," + rightTable;

		String sql = "SELECT COUNT(1) AS SIZE FROM " + from + " WHERE " + where;

		return sql;
	}

	/**
	 * 获取 一对一模型的可执行SQL
	 *
	 * @param left
	 *            被参照表对应的实体类
	 * @param other
	 *            其他参照表对应的实体类 ,至少有一个实体类
	 * @return 可执行SQL
	 */
	public static String getO2O(Object left, Object... others) throws Exception {

		String leftTable = T2E.toColumn(left.getClass().getSimpleName());

		Map<String, Object> leftFileds = new RObject(left).getFiledAndValue();//EntityCache.getKeyAndValue(left);
		String col = "";
		int n = 0;
		for (Entry<String, Object> en : leftFileds.entrySet()) {
			if (n != 0) {
				col = col + ",";
			}
			col = col + " " + leftTable + "." + T2E.toColumn(en.getKey()) + " AS " + leftTable + "_" + T2E.toColumn(en.getKey());
			n++;
		}

		Map<String, String> map = new HashMap<>();

		for (Object obj : others) {

			String table = T2E.toColumn(obj.getClass().getSimpleName());
			RObject rObject = new RObject(obj);
			String pk = T2E.toColumn(rObject.getPk());
			map.put(table, pk);
			Map<String, Object> field = new RObject(obj).getFiledAndValue();//EntityCache.getKeyAndValue(obj) ;

			for (Entry<String, Object> en : field.entrySet()) {
				col = col + "," + table + "." + T2E.toColumn(en.getKey()) + " AS " + table + "_" + T2E.toColumn(en.getKey());
			}
		}

		String where = "";
		String from = " " + leftTable;
		int m = 0;
		for (Entry<String, String> en : map.entrySet()) {
			if (m != 0) {
				where = where + " AND ";
			}
			where = where + leftTable + "." + en.getValue() + " = " + en.getKey() + "." + en.getValue();
			from = from + "," + en.getKey();
			m++;
		}

		String leftWhere = where(left);
		
		for (Object obj : others) {
			leftWhere = leftWhere + where(obj);
		}
		
		String sql = "SELECT " + col + " FROM " + from + " WHERE " + where + leftWhere;

		return sql;
	}

	 
}
