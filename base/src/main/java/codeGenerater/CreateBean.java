package codeGenerater;

import codeGenerater.def.CommUtil;
import codeGenerater.def.FtlDef;
import codeGenerater.def.TableConvert;
import org.apache.commons.lang.StringUtils;

import codeGenerater.def.CodeResourceUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class CreateBean {
	private Connection connection = null;
	static String url;
	static String username;
	static String password;
	static String rt = "\r\t";
	String SQLTables = "show tables";
	private String method;
	private String argv;
	static String selectStr = "select ";
	static String from = " from ";

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setMysqlInfo(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}

	public List<String> getTables() throws SQLException {
		Connection con = getConnection();
		PreparedStatement ps = con.prepareStatement(SQLTables);
		ResultSet rs = ps.executeQuery();
		List list = new ArrayList();
		while (rs.next()) {
			String tableName = rs.getString(1);
			list.add(tableName);
		}
		rs.close();
		ps.close();
		con.close();
		return list;
	}

	public List<ColumnData> getColumnDatas(String tableName) throws SQLException {
		String SQLColumns = "select column_name ,data_type,column_comment,0,0,character_maximum_length,is_nullable nullable from information_schema.columns where table_name =  '"
				+ tableName + "' " + "and table_schema =  '" + CodeResourceUtil.DATABASE_NAME + "'";

		Connection con = getConnection();
		PreparedStatement ps = con.prepareStatement(SQLColumns);
		List columnList = new ArrayList();
		ResultSet rs = ps.executeQuery();
		StringBuffer str = new StringBuffer();
		StringBuffer getset = new StringBuffer();
		while (rs.next()) {
			String name = rs.getString(1);
			if(name.equalsIgnoreCase("ts")||name.equalsIgnoreCase("d")){
				continue;
			}
			String type = rs.getString(2);
			String comment = rs.getString(3);
			String precision = rs.getString(4);
			String scale = rs.getString(5);
			String charmaxLength = rs.getString(6) == null ? "" : rs.getString(6);
			String nullable = TableConvert.getNullAble(rs.getString(7));
			ColumnData cd = new ColumnData();
			type = getType(type, precision, scale);
			cd.setColumnName(name);
			cd.setDataType(type);
			if(rs.getString(2).toUpperCase().equals("INT")){
				cd.setColumnType("INTEGER");
			}else if(rs.getString(2).toUpperCase().equals("DATETIME")){
				cd.setColumnType("DATE");
			}else{
				cd.setColumnType(rs.getString(2).toUpperCase());
			}
			cd.setColumnComment(comment);
			cd.setPrecision(precision);
			cd.setScale(scale);
			cd.setCharmaxLength(charmaxLength);
			cd.setNullable(nullable);
			formatFieldClassType(cd);
			columnList.add(cd);
		}
		argv = str.toString();
		method = getset.toString();
		rs.close();
		ps.close();
		con.close();
		return columnList;
	}

	public String getBeanFeilds(String tableName) throws SQLException {
		List<ColumnData> dataList = getColumnDatas(tableName);
		StringBuffer str = new StringBuffer();
		StringBuffer getset = new StringBuffer();
		for (ColumnData d : dataList) {
			String name = CommUtil.formatName(d.getColumnName());
			String type = d.getDataType();
			String comment = d.getColumnComment();

			String maxChar = name.substring(0, 1).toUpperCase();
			str.append("\r\t").append("private ").append(type + " ").append(name).append(";//   ").append(comment);
			String method = maxChar + name.substring(1, name.length());
			getset.append("\r\t").append("public ").append(type + " ").append("get" + method + "() {\r\t");
			getset.append("    return this.").append(name).append(";\r\t}");
			getset.append("\r\t").append("public void ").append("set" + method + "(" + type + " " + name + ") {\r\t");
			getset.append("    this." + name + "=").append(name).append(";\r\t}");
		}
		argv = str.toString();
		this.method = getset.toString();
		return argv;
//		return argv + this.method;
	}

	private String formatTableName(String name) {
		name=name.toLowerCase();
		String[] split = name.split("_");
		if (split.length > 1) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < split.length; i++) {
				String tempName = split[i].substring(0, 1).toUpperCase() + split[i].substring(1, split[i].length());
				sb.append(tempName);
			}

			return sb.toString();
		}
		String tempName = split[0].substring(0, 1).toUpperCase() + split[0].substring(1, split[0].length());
		return tempName;
	}

	private void formatFieldClassType(ColumnData columnt) {
		String fieldType = columnt.getColumnType();
		String scale = columnt.getScale();

		if ("N".equals(columnt.getNullable())) {
			columnt.setOptionType("required:true");
		}
		if (("datetime".equals(fieldType)) || ("time".equals(fieldType))) {
			columnt.setClassType("easyui-datetimebox");
		} else if ("date".equals(fieldType)) {
			columnt.setClassType("easyui-datebox");
		} else if ("int".equals(fieldType)) {
			columnt.setClassType("easyui-numberbox");
		} else if ("number".equals(fieldType)) {
			if ((StringUtils.isNotBlank(scale)) && (Integer.parseInt(scale) > 0)) {
				columnt.setClassType("easyui-numberbox");
				if (StringUtils.isNotBlank(columnt.getOptionType()))
					columnt.setOptionType(columnt.getOptionType() + "," + "precision:2,groupSeparator:','");
				else
					columnt.setOptionType("precision:2,groupSeparator:','");
			} else {
				columnt.setClassType("easyui-numberbox");
			}
		} else if (("float".equals(fieldType)) || ("double".equals(fieldType)) || ("decimal".equals(fieldType))) {
			columnt.setClassType("easyui-numberbox");
			if (StringUtils.isNotBlank(columnt.getOptionType()))
				columnt.setOptionType(columnt.getOptionType() + "," + "precision:2,groupSeparator:','");
			else
				columnt.setOptionType("precision:2,groupSeparator:','");
		} else {
			columnt.setClassType("easyui-validatebox");
		}
	}

	public String getType(String dataType, String precision, String scale) {
		dataType = dataType.toLowerCase();
		if (dataType.contains("char")||dataType.contains("text"))
			dataType = "java.lang.String";
		else if (dataType.contains("bit"))
			dataType = "java.lang.Boolean";
		else if (dataType.contains("bigint"))
			dataType = "java.lang.Long";
		else if (dataType.contains("int"))
			dataType = "java.lang.Integer";
		else if (dataType.contains("float"))
			dataType = "java.lang.Float";
		else if (dataType.contains("double"))
			dataType = "java.lang.Double";
		else if (dataType.contains("number")) {
			if ((StringUtils.isNotBlank(scale)) && (Integer.parseInt(scale) > 0))
				dataType = "java.math.BigDecimal";
			else if ((StringUtils.isNotBlank(precision)) && (Integer.parseInt(precision) > 6))
				dataType = "java.lang.Long";
			else
				dataType = "java.lang.Integer";
		} else if (dataType.contains("decimal"))
			dataType = "BigDecimal";
		else if (dataType.contains("date"))
			dataType = "java.util.Date";
		else if (dataType.contains("time"))
			dataType = "java.sql.Timestamp";
		else if (dataType.contains("clob"))
			dataType = "java.sql.Clob";
		else {
			dataType = "java.lang.Object";
		}
		return dataType;
	}

	public void getPackage(int type, String createPath, String content, String packageName, String className, String extendsClassName, String[] importName) throws Exception {
		if (packageName == null) {
			packageName = "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("package ").append(packageName).append(";\r");
		sb.append("\r");
		for (int i = 0; i < importName.length; i++) {
			sb.append("import ").append(importName[i]).append(";\r");
		}
		sb.append("\r");
		sb.append("/**\r *  entity. @author wolf Date:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\r */");
		sb.append("\r");
		sb.append("\rpublic class ").append(className);
		if (extendsClassName != null) {
			sb.append(" extends ").append(extendsClassName);
		}
		if (type == 1)
			sb.append(" ").append("implements java.io.Serializable {\r");
		else {
			sb.append(" {\r");
		}
		sb.append("\r\t");
		sb.append("private static final long serialVersionUID = 1L;\r\t");
		String temp = className.substring(0, 1).toLowerCase();
		temp = temp + className.substring(1, className.length());
		if (type == 1) {
			sb.append("private " + className + " " + temp + "; // entity ");
		}
		sb.append(content);
		sb.append("\r}");
		System.out.println(sb.toString());
		createFile(createPath, "", sb.toString());
	}

	public String getTablesNameToClassName(String tableName) {
		String tempTables = formatTableName(tableName);
		return tempTables;
	}

	public void createFile(String path, String fileName, String str) throws IOException {
		FileWriter writer = new FileWriter(new File(path + fileName));
		writer.write(new String(str.getBytes("utf-8")));
		writer.flush();
		writer.close();
	}

	public Map<String, Object> getAutoCreateSql(String tableName,String keyType) throws Exception {
		Map sqlMap = new HashMap();
		List columnDatas = getColumnDatas(tableName);
		String columns = getColumnSplit(columnDatas);
		System.out.println(columnDatas.size());
		String formatColumns = getFormatColumnSplit(columnDatas);
		String[] columnList = getColumnList(columns);
		String columnFields = getColumnFields(columns);
		String insert = "insert into " + tableName + "(" + columns.replaceAll("\\|", ",") + ")\n\t\t values(\n\t\t\t#{" + formatColumns.replaceAll("\\|", "},\n\t\t\t#{") + "})";
		String insertSelective=getInsertSelectiveSql(tableName, columnDatas);
		String update = getUpdateSql(tableName, columnDatas);
		String updateSelective = getUpdateSelectiveSql(tableName, columnDatas);
		String selectById = getSelectByIdSql(tableName, columnList);
		String delete = getDeleteSql(tableName, columnList);
		sqlMap.put("columnList", columnList);
		sqlMap.put("columnFields", columnFields);
		sqlMap.put("insert", insert.replace("#{createTime}", "now()").replace("#{updateTime}", "now()"));
		sqlMap.put("insertSelective", insertSelective.replace("#{createTime}", "now()").replace("#{updateTime}", "now()"));
		sqlMap.put("update", update.replace("#{createTime}", "now()").replace("#{updateTime}", "now()"));
		sqlMap.put("delete", delete);
		sqlMap.put("updateSelective", updateSelective);
		sqlMap.put("selectById", selectById);
		sqlMap.put("insertBatch",getInsertBatch(tableName,columnDatas,keyType));
		sqlMap.put("updateInBatch",getUpdateBatch(tableName,columnDatas));
		sqlMap.put("keyName", CommUtil.formatName(columnList[0]));
		return sqlMap;
	}

	public String getDeleteSql(String tableName, String[] columnsList) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("delete\n\t\t ");
		sb.append(" from ").append(tableName).append(" where ");
		sb.append(columnsList[0]).append(" = #{").append(CommUtil.formatName(columnsList[0])).append("}");
		return sb.toString();
	}

	public String getSelectByIdSql(String tableName, String[] columnsList) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("select\n\t <include refid=\"Base_Column_List\" /> \n\t\t");
		sb.append("from ").append(tableName).append("\n\t where ");
		sb.append(columnsList[0]).append(" = #{").append(CommUtil.formatName(columnsList[0])).append("}");
		return sb.toString();
	}

	public String getColumnFields(String columns) throws SQLException {
		String fields = columns;
		if ((fields != null) && (!"".equals(fields))) {
			fields = fields.replaceAll("[|]", ",");
		}
		return fields;
	}

	public String[] getColumnList(String columns) throws SQLException {
		String[] columnList = columns.split("[|]");
		return columnList;
	}

	public String getInsertBatch(String tableName, List<ColumnData> columnList,String keyType){
		StringBuffer sb = new StringBuffer();
        sb.append("insert into ").append(tableName).append("(\n");
		int num =1 ;
		for(ColumnData data:columnList){
			//自增主键不需生成
			if(num==1&&keyType== FtlDef.KEY_TYPE_01){
				num ++ ;
				continue;
			}
			sb.append("\t").append(data.getColumnName());
			if(num==columnList.size()){
				sb.append(") values \n\t\t<foreach collection=\"list\" item=\"item\" index=\"index\" separator=\",\">\n    (");
			}else{
				sb.append(",");
			}
			num ++ ;
		}
		int num1 =1 ;
		for(ColumnData data:columnList){
			//自增主键不需生成
			if(num1==1&&keyType== FtlDef.KEY_TYPE_01){
				num1 ++ ;
				continue;
			}
			//uuid主键不需生成
			if(num1==1&&keyType== FtlDef.KEY_TYPE_02){
				sb.append("UUID(),\n");
				num1 ++ ;
				continue;
			}
			if(num1!=columnList.size()){
				sb.append("\t#{item."+data.getFormatColumnName()+",jdbcType="+data.getColumnType()+"},\n");
			}else{
				sb.append("\t#{item."+data.getFormatColumnName()+",jdbcType="+data.getColumnType()+"})\n" +
						"\t\t</foreach>");
			}
			num1++;
		}
		return  sb.toString();
	}

	
	public String getInsertSelectiveSql(String tableName, List<ColumnData> columnList) throws SQLException {
		StringBuffer sb = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		//ColumnData cd = (ColumnData) columnList.get(0);
		sb.append("\t<trim  prefix=\"(\" suffix=\")\"  suffixOverrides=\",\" >\n");
		sb2.append("\t<trim  prefix=\"values (\" suffix=\")\"  suffixOverrides=\",\" >\n");
		for (int i = 0; i < columnList.size(); i++) {
			ColumnData data = (ColumnData) columnList.get(i);
			String columnName = data.getColumnName();
			sb.append("\t<if test=\"").append(CommUtil.formatName(columnName)).append(" != null ");
			sb2.append("\t<if test=\"").append(CommUtil.formatName(columnName)).append(" != null ");

			if ("String" == data.getDataType()) {
				sb.append(" and ").append(CommUtil.formatName(columnName)).append(" != ''");
				sb2.append(" and ").append(CommUtil.formatName(columnName)).append(" != ''");
			}
			sb.append(" \">\n\t\t");
			sb2.append(" \">\n\t\t");
			sb.append(columnName + ",\n");
			sb2.append("#{" + CommUtil.formatName(columnName) + ",jdbcType="+data.getColumnType()+"},\n");
			//sb.append(columnName + "=#{" + CommUtil.formatName(columnName) + "},\n");
			sb.append("\t</if>\n");
			sb2.append("\t</if>\n");
		}
		sb.append("\t</trim>");
		sb2.append("\t</trim>");
		String insert = "insert into " + tableName + "\n"+sb.toString() +"\n"+sb2.toString();
		//String insert = "insert into " + tableName + "(" + columns.replaceAll("\\|", ",") + ")\n values(#{" + formatColumns.replaceAll("\\|", "},#{") + "})";
		//String update = "update " + tableName + " set \n" + sb.toString() + " where " + cd.getColumnName() + "=#{" + CommUtil.formatName(cd.getColumnName()) + "}";
		return insert;
	}

	public String getUpdateSql(String tableName, List columnDatas) throws SQLException {
		StringBuffer sb = new StringBuffer();

		for (int i = 1; i < columnDatas.size(); i++) {
			ColumnData column = (ColumnData) columnDatas.get(i);
			if (!"CREATETIME".equals(column.getColumnName().toUpperCase())) {
				if ("UPDATETIME".equals(column.getColumnName().toUpperCase()))
					sb.append(column.getColumnName() + "=now()");
				else {
					sb.append("\t"+column.getColumnName() + "=#{" +column.getFormatColumnName() + ",jdbcType="+column.getColumnType()+"}");
				}
				if (i + 1 < columnDatas.size())
					sb.append(",\n");
			}
		}
		ColumnData cd = (ColumnData) columnDatas.get(0);
		String update = "update " + tableName + " set " + sb.toString() + " where " + cd.getColumnName() + "=#{" + CommUtil.formatName(cd.getColumnName()) + ",jdbcType="+cd.getColumnType()+"}";
		return update;
	}

	public String getUpdateSelectiveSql(String tableName, List<ColumnData> columnList) throws SQLException {
		StringBuffer sb = new StringBuffer();
		ColumnData cd = (ColumnData) columnList.get(0);
		sb.append("\t<trim  suffixOverrides=\",\" >\n");
		for (int i = 1; i < columnList.size(); i++) {
			ColumnData data = (ColumnData) columnList.get(i);
			String columnName = data.getColumnName();
			sb.append("\t<if test=\"").append(CommUtil.formatName(columnName)).append(" != null");

			if ("String" == data.getDataType()) {
				sb.append(" and ").append(CommUtil.formatName(columnName)).append(" != ''");
			}
			sb.append(" \">\n\t\t");
			sb.append(columnName + "=#{" + CommUtil.formatName(columnName) + ",jdbcType="+data.getColumnType()+"},\n");
			sb.append("\t</if>\n");
		}
		sb.append("\t</trim>");
		String update = "update " + tableName + " set \n" + sb.toString() + " where " + cd.getColumnName() + "=#{" + CommUtil.formatName(cd.getColumnName()) + ",jdbcType="+cd.getColumnType()+"}";
		return update;
	}

	public String getColumnSplit(List<ColumnData> columnList) throws SQLException {
		StringBuffer commonColumns = new StringBuffer();
		for (ColumnData data : columnList) {
			commonColumns.append(data.getColumnName() + "|");
			System.out.println(data.getColumnName());
		}
		System.out.println(columnList.size()+"-------");
		return commonColumns.delete(commonColumns.length() - 1, commonColumns.length()).toString();
	}

	public String getFormatColumnSplit(List<ColumnData> columnList) throws SQLException {
		StringBuffer commonColumns = new StringBuffer();
		for (ColumnData data : columnList) {
			commonColumns.append(data.getFormatColumnName() + ",jdbcType="+data.getColumnType()+"|");
		}
		return commonColumns.delete(commonColumns.length() - 1, commonColumns.length()).toString();
	}


	public String getColumnType(List<ColumnData> columnList,String columnName) throws SQLException {
		for (ColumnData data : columnList) {
			 if(columnName.equals(data.getColumnName())){
				 return data.getColumnType();
			 }

		}
		return  null;
	}

	public String getUpdateBatch(String tableBName,List<ColumnData> columnDatas){
		StringBuilder sb = new StringBuilder();
		sb.append(" <foreach collection=\"list\" item=\"item\" index=\"index\" open=\"begin\" close=\";end;\" separator=\";\">\n");
		sb.append("\tupdate  ").append(tableBName).append(" set \n");
		for(int i =1;i<columnDatas.size();i++){
			ColumnData data=  columnDatas.get(i);
			String columnName = data.getColumnName();
			sb.append("\t<if test=\"item.").append(CommUtil.formatName(columnName)).append(" != null");
			sb.append(" \">\n");
			sb.append("\t\t"+columnName + "=#{item." + CommUtil.formatName(columnName) + ",jdbcType="+data.getColumnType()+"}");
			if(i!=columnDatas.size()-1){
				sb.append(",\n");
			}
			sb.append("\n\t</if>\n");
		}
		sb.append("\n\t\twhere ").append(columnDatas.get(0).getColumnName()).append("= #{item.").
				append(columnDatas.get(0).getFormatColumnName()).append(",jdbcType=").append(columnDatas.get(0).getColumnType()).append("}\n</foreach>");
		return  sb.toString();

	}
}