package com.sooncode.sql;

import com.sooncode.ref.RObject;
import com.sooncode.utils.T2E;

import java.util.Map;
import java.util.Map.Entry;

//import org.apache.log4j.Logger;

/**
 * SQL工具类 注意: 对象的类名 与 数据库表名"一致"
 * 
 * @author pc
 *
 */
public class SQL {

	//private static Logger logger = Logger.getLogger("SQL.class");
	/**
	 * 可执行的SQL
	 */
	private String sqlString = "";

	public String toString() {

		 this.sqlString = this.sqlString.replace("WHERE AND", "WHERE");
		 this.sqlString = this.sqlString.replace("WHERE OR", "WHERE");
		//logger.debug("【可执行SQL】: " + this.sqlString);
		return this.sqlString;
	}

	/**
	 * 添加关键字到SQL中
	 * 
	 * @param key
	 *            关键字 集
	 * @return SelSql 对象
	 */
	public SQL PUT_KEY(String key) {

		this.sqlString = this.sqlString + " " + key;
		return this;
	}

	public SQL SELECT() {

		this.sqlString = this.sqlString + "SELECT ";
		return this;
	}

	public SQL FROM() {

		this.sqlString = this.sqlString + " FROM ";
		return this;
	}

	public SQL WHERE() {

		this.sqlString = this.sqlString + " WHERE ";
		return this;
	}

	public SQL AND() {

		this.sqlString = this.sqlString + " AND ";
		return this;
	}

	public SQL OR() {

		this.sqlString = this.sqlString + " OR ";
		return this;
	}

	public SQL TABLE(Class<?> clas) {

		String tableName = T2E.toColumn(clas.getSimpleName());
		this.sqlString = this.sqlString + " " + tableName;
		return this;
	}

	/**
	 * 获取实体类对应的字段
	 * 
	 * @param clas
	 *            实体类的代理
	 * @return 字段集 字段之间用 逗号分割
	 */
	public SQL COLUMNS(Class<?> clas) throws Exception{
		 
		RObject rObject = new RObject(clas);
		Map<String, Object> columns = rObject.getFiledAndValue();
		int m = 0;
		String c = "";
		for (Entry<String, Object> entry : columns.entrySet()) {

			if (m != 0) {
				c = c + ",";
			}
			c = c + T2E.toColumn(entry.getKey());
			m++;
		}
		this.sqlString = this.sqlString + c;
		return this;

	}

	/**
	 * 求和
	 * 
	 * @param fieldName
	 * @return
	 */
	public SQL SUM(String fieldName) {
		String column = T2E.toColumn(fieldName);
		String str = "SUM(" + column + ") ";
		this.sqlString = this.sqlString + str;
		return this;
	}

	/**
	 * 求平均
	 * 
	 * @param fieldName
	 * @return
	 */
	public SQL AVG(String fieldName) {
		String column = T2E.toColumn(fieldName);
		String str = "AVG(" + column + ") AS AVG" + column + " ";
		this.sqlString = this.sqlString + str;
		return null;
	}

	/**
	 * 求最大值
	 * 
	 * @param fieldName
	 * @return
	 */
	public SQL MAX(String fieldName) {
		String column = T2E.toColumn(fieldName);
		String str = "MAX(" + column + ") AS MAX" + column + " ";
		this.sqlString = this.sqlString + str;
		return null;
	}

	/**
	 * 求最小值
	 * 
	 * @param fieldName
	 * @return
	 */
	public SQL MIN(String fieldName) {
		String column = T2E.toColumn(fieldName);
		String str = "MIN(" + column + ") AS MIN" + column + " ";
		this.sqlString = this.sqlString + str;
		return null;
	}

	/**
	 * 等于
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public SQL EQ(String fieldName, Object value) {
		String sql = "";
		if (value != null) {
			sql = " " + T2E.toColumn(fieldName) + "='" + value + "'";
		} 
		this.sqlString = this.sqlString + sql;
		return this;
	}

	/**
	 * 大于
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public SQL GT(String fieldName, Object value) {
		String sql = "";
		if (value != null) {
			sql = " " + T2E.toColumn(fieldName) + ">'" + value + "'";
		}
		this.sqlString = this.sqlString + sql;
		return this;
	}

	/**
	 * 小于
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public SQL LT(String fieldName, Object value) {
		String sql = "";
		if (value != null) {
			sql = " " + T2E.toColumn(fieldName) + "<'" + value + "'";
		}
		this.sqlString = this.sqlString + sql;
		return this;
	}

	/**
	 * 大于等于
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public SQL GTEQ(String fieldName, Object value) {
		String sql = "";
		if (value != null) {
			sql = " " + T2E.toColumn(fieldName) + ">='" + value + "'";
		}
		this.sqlString = this.sqlString + sql;
		return this;
	}

	/**
	 * 小于等于
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public SQL LTEQ(String fieldName, Object value) {
		String sql = "";
		if (value != null) {
			sql = " " + T2E.toColumn(fieldName) + "<='" + value + "'";
		}
		this.sqlString = this.sqlString + sql;
		return this;
	}

	/**
	 * 排序语句片段
	 * 
	 * @param fields
	 *            需要排序的属性集
	 * @return SelSql 对象
	 */
	public SQL ORDER_BY(String... fields) {
		String str = "";
		int n = 0;
		for (String f : fields) {
			if (n != 0) {
				str = str + ",";
			}
			str = str + T2E.toColumn(f);
			n++;
		}
		this.sqlString = this.sqlString + " ORDER BY " + str;
		return this;
	}

	/**
	 * 降序
	 * 
	 * @return
	 */
	public SQL DESC() {
		String str = " DESC ";
		this.sqlString = this.sqlString + str;
		return this;
	}

	/**
	 * 匹配前部分
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public SQL LIKE_THIS(String fieldName, Object value) {
		String sql = "";
		if (value != null) {
			sql = " AND " + T2E.toColumn(fieldName) + " LIKE '%" + value + "'";
		}
		this.sqlString = this.sqlString + sql;
		return this;
	}

	/**
	 * 匹配后部分
	 * 
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public SQL THIS_LIKE(String fieldName, Object value) {
		String sql = "";
		if (value != null) {
			sql = " AND " + T2E.toColumn(fieldName) + " LIKE '" + value + "%'";
		}
		this.sqlString = this.sqlString + sql;
		return this;
	}

	public SQL LIKE(String fieldName, Object value) {
		String sql = "";
		if (value != null) {
			sql = " " + T2E.toColumn(fieldName) + " LIKE '%" + value + "%'";
		}
		this.sqlString = this.sqlString + sql;
		return this;
	}

	public SQL BETWEEN(String fieldName, Object value1, Object value2) {
		String sql = "";
		if (value1 != null && value2 != null) {
			sql = " AND " + T2E.toColumn(fieldName) + " BETWEEN '" + value1 + "' AND '" + value2 + "'";
		}

		this.sqlString = this.sqlString + sql;
		return this;
	}

	
	public SQL COUNT(String key){
		this.sqlString = this.sqlString + "COUNT("+key+") ";
		return this;
	}
	public SQL AS(String key){
		this.sqlString = this.sqlString + "AS "+T2E.toColumn(key)+" ";
		return this;
	}
	public SQL GROUP_BY(String key){
		this.sqlString = this.sqlString + " GROUP BY "+T2E.toColumn(key)+" ";
		return this;
	}
	public SQL DISTINCT(String key){
		this.sqlString = this.sqlString + " DISTINCT "+T2E.toColumn(key)+" ";
		return this;
	}
	/**
	 * mysql 时间函数 
	 * @param key 属性
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return
	 */
	public SQL DATE(String key,String startDate,String endDate){
		this.sqlString = this.sqlString + " DATE("+T2E.toColumn(key)+")BETWEEN '"+startDate+"' AND '"+endDate+"'";
		return this;
	}
	/**
	 * 分页功能
	 * 
	 * @param pageNumber
	 *            第几页
	 * @param pageSize
	 *            每页长度
	 * @return Sql 对象
	 */
	public SQL LIMIT(Long pageNumber, Long pageSize) {
		Long index = (pageNumber - 1) * pageSize;
		String sql = " LIMIT " + index + "," + pageSize;
		this.sqlString = this.sqlString + sql;
		return this;

	}
	
	public static void main(String[] args) {
//		SQL sql = new SQL();
//		sql.SELECT().COLUMNS(Persion.class)
//		.FROM().TABLE(Persion.class)
//		.WHERE().EQ("id", "1")
//		.AND().LIKE("name", "tome");
//
//		System.out.println(sql.toString());
	
	}

}
