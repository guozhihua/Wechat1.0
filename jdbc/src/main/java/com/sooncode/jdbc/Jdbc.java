package com.sooncode.jdbc;

import com.sooncode.db.DBs;
import com.sooncode.utils.T2E;
import org.apache.log4j.Logger;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JDBC 执行SQL语句
 * 
 * @author pc
 *
 */
public class Jdbc {

	public final static Logger logger = Logger.getLogger("Jdbc.class");
	private static int counter = 0;
	private String dbKey = "default";
	 
	/**
	 * 
	 * @param dbKey
	 *            数据源关键码
	 */
	public Jdbc(String dbKey) {
		this.dbKey = dbKey;
		 
	}

	public Jdbc() {
		
	}
	 
	/**
	 * 执行非查询语句
	 * 
	 * @param connection
	 *            数据库连接
	 * @param sql
	 *            可执行的非查询语句
	 * @return 一般情况是返回受影响的行数,当有主键为自增字段,在添加数据时返回 自增值
	 */
	public Long executeUpdate(String sql)  throws Exception{
		logger.debug("【可执行SQL】: " + sql);
		Connection	connection = DBs.getConnection(this.dbKey);
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Long n = 0L;
		try {
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			n = (long) preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys(); // 获取ID
			if (resultSet.next()) {
				Long id = resultSet.getLong(1);
				return id;
			} else {
				return n;
			}
		} catch (SQLException e) {
			logger.error("[SQL语句执行异常]: " + sql);
			throw  e;
		} finally {
			//if (DBs.c3p0properties == null) {
				DBs.close(resultSet, preparedStatement, connection);
			//}

		}
	}

	/**
	 * 批量执行更新语句
	 * 
	 * @param sqls
	 *            可执行更新SQL语句集
	 * @return
	 */
	public Boolean executeUpdates(List<String> sqls) {
		
		Connection	connection = DBs.getConnection(this.dbKey);
		Statement statement =null;
		try {
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			for (String sql : sqls) {
				logger.debug("【可执行SQL】: " + sql);
				statement.addBatch(sql);
			}
			statement.executeBatch(); // 执行批处理
			connection.commit();
			//if (DBs.c3p0properties == null) {

			//}
			return true;
		} catch (Exception e) {
			logger.error("[批量执行失败",e);
			return false;
		}finally {
			DBs.close(statement, connection);
		}

	}

	/**
	 * 执行查询语句(可能有多条记录)
	 * 
	 * @param sql可执行SQL
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> executeQueryL(String sql) throws SQLException{

		Connection	connection = DBs.getConnection(this.dbKey);
		List<Map<String, Object>> resultList = new ArrayList<>();

		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				Map<String, Object> map = new HashMap<>();

				ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
				int columnCount = resultSetMetaData.getColumnCount();
				for (int i = 1; i <= columnCount; i++) {
					String columnName = resultSetMetaData.getColumnLabel(i).toUpperCase();// 获取别名
					columnName = T2E.toField(columnName);
					Object columnValue = resultSet.getObject(i);
					map.put(columnName, columnValue);
				}
				resultList.add(map);
			}
			return resultList;
		} catch (SQLException e) {
			logger.error("jdbc error,sql is ["+sql+"]",e);
			throw  e;
		} finally {
			//if (DBs.c3p0properties == null) {
				DBs.close(resultSet, preparedStatement, connection);
			//}
		}
	}

	/**
	 * 执行查询语句 (只有一条返回记录)
	 * 
	 * @param sql可执行SQL
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Object> executeQueryM(String sql)  throws SQLException{
		logger.debug("【可执行SQL】: " + sql);
		List<Map<String, Object>> list = executeQueryL(sql);
		if (list.size() == 1) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 执行查询语句 (只有一条返回记录)
	 * 
	 * @param sql
	 *            可执行文件
	 * @param entityClass
	 *            实体模型类型
	 * @return 实体对象
	 */
	public Object executeQuery(String sql, Class<?> entityClass) throws Exception {
		List<Map<String, Object>> list = executeQueryL(sql);
		if (list.size() == 1) {
			Map<String, Object> map = list.get(0);
			try {
				Object object = entityClass.newInstance();
				Field[] fields = entityClass.getDeclaredFields();
				for (Field field : fields) {
					Object value = map.get(field.getName());
					if (value == null) {
						continue;
					}
					PropertyDescriptor pd = new PropertyDescriptor(field.getName(), entityClass);
					// 获得set方法
					Method method = pd.getWriteMethod();
					method.invoke(object, value);
				}
				return object;
			} catch (Exception e) {
				logger.error("jdbc error sql ["+sql+"]",e);
				throw  e;
			}

		} else {
			return null;
		}
	}

	/**
	 * 执行查询语句
	 * 
	 * @param sql
	 *            可执行文件
	 * @param entityClass
	 *            实体模型类型
	 * @return 实体对象
	 */
	public List<?> executeQuerys(String sql, Class<?> entityClass) throws Exception{
		logger.debug("【可执行SQL】: " + sql);
		List<Map<String, Object>> list = executeQueryL(sql);

		List<Object> objects = new ArrayList<>();
		for (Map<String, Object> map : list) {

			try {
				Object object = entityClass.newInstance();
				Field[] fields = entityClass.getDeclaredFields();
				for (Field field : fields) {
					Object value = map.get(field.getName());
					if (value == null) {
						continue;
					}
					PropertyDescriptor pd = new PropertyDescriptor(field.getName(), entityClass);
					// 获得set方法
					Method method = pd.getWriteMethod();
					method.invoke(object, value);
				}
				objects.add(object);
			} catch (Exception e) {
				logger.error("jdbc error sql is ["+sql+"]",e);
				throw e;
			}

		}

		return objects;
	}

	/**
	 * 执行存储过程
	 * 
	 * @param connection
	 *            数据源
	 * 
	 * @param sql
	 *            存储过程 调用SQL语句 ,其中约定最后一个参数为输出参数， 但参数个数与 in 的参数相同时，表示没有输出参数。 如
	 *            {call proc_name2(?,?)}
	 * @param in
	 *            存储过程需要的输入参数集
	 * 
	 * @return 存储过程的 返回参数值,当没有返回参数时 返回null
	 */
	public Object executeProcedure(String sql, Object... in)  throws Exception{
		logger.debug("【存储过程 SQL】: " + sql);
		Connection	connection = DBs.getConnection(this.dbKey);
		// sql 中参数的个数
		int n = countParameter(sql, "?");
		// 创建调用存储过程的预定义SQL语句

		CallableStatement callableStatement = null;
		try {
			// 创建过程执行器
			callableStatement = connection.prepareCall(sql);
			// 设置入参和出参
			for (int i = 1; i <= in.length; i++) {
				callableStatement.setObject(i, in[i - 1]);
			}

			if (n - in.length == 1) {
				callableStatement.registerOutParameter(n, Types.JAVA_OBJECT); // 注册出参
				callableStatement.executeUpdate();
				Object result = callableStatement.getObject(n);
				return result;
			} else if (n == in.length) { // 没有输出参数
				callableStatement.executeUpdate();
				return null;
			} else { // 参数不匹配
				return null;
			}

		} catch (SQLException e) {
			logger.error("jdbc error sql ["+sql+"]",e);
			 throw  e;
		} finally {
			DBs.close(callableStatement, connection);
		}
	}

	/**
	 * 事务处理
	 * 
	 * @param connection
	 *            数据源
	 * @param sqls 可执行的更新（非查询语句）语句集 (按秩序执行)
	 * @return 成功返回true,反之返回false.
	 */
	public boolean transaction(String... sqls) {
		Connection	connection = DBs.getConnection(this.dbKey);
		StringBuffer sb =new StringBuffer();
		// ----------------验证参数-------------------

		if (connection == null) {
			return false;
		}
		// ----------------------------------------
		PreparedStatement preparedStatement = null;
		try {
			// 设置事务的提交方式为非自动提交：
			connection.setAutoCommit(false);
			// 创建执行语句
			for (String sql : sqls) {
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.executeUpdate();
				sb.append(sql);
				logger.debug("【可执行SQL】: " + sql);
			}
			// 在try块内添加事务的提交操作，表示操作无异常，提交事务。
			connection.commit();
			return true;
		} catch (SQLException e) {
			try {
				// .在catch块内添加回滚事务，表示操作出现异常，撤销事务：
				connection.rollback();
			} catch (SQLException e1) {
				//e1.printStackTrace();
				logger.error("jdbc transaction error,sql is ["+sb.toString()+"]",e1);
			}
			//e.printStackTrace();
			logger.error("jdbc transaction error,sql is ["+sb.toString()+"]",e);
			return false;

		} finally {
			try {
				// 设置事务提交方式为自动提交：
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				logger.error("jdbc transaction error,sql is ["+sb.toString()+"]",e);
			}
			//if (DBs.c3p0properties == null) {
				DBs.close(preparedStatement, connection);
			//}
		}
	}

	/**
	 * 计算sql中的参数个数
	 * 
	 * @param sql
	 * @param parameter
	 *            "?"
	 * @return 参数个数
	 */
	private static int countParameter(String sql, String parameter) {

		if (sql.indexOf(parameter) == -1) {
			return 0;
		} else if (sql.indexOf(parameter) != -1) {
			counter++;
			countParameter(sql.substring(sql.indexOf(parameter) + parameter.length()), parameter);
			return counter;
		}
		return 0;
	}

}
