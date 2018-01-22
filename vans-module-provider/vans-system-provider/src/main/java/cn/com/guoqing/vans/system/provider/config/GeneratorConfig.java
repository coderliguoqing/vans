package cn.com.guoqing.vans.system.provider.config;


import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import cn.com.guoqing.vans.common.exception.VansException;
import cn.com.guoqing.vans.common.exception.VansExceptionEnum;
import cn.com.guoqing.vans.common.util.DateHelper;
import cn.com.guoqing.vans.system.api.entity.ColumnEntity;
import cn.com.guoqing.vans.system.api.entity.TableEntity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器   工具类
 *
 */
public class GeneratorConfig {

	public static List<String> getTemplates(){
		List<String> templates = new ArrayList<String>();
		templates.add("template/Entity.java.vm");
		templates.add("template/Mapper.java.vm");
		templates.add("template/Mapper.xml.vm");
		templates.add("template/Service.java.vm");
		templates.add("template/ServiceImpl.java.vm");
		return templates;
	}

	/**
	 * 生成代码
	 */
	public static void generatorCode(Map<String, String> table,
									 List<Map<String, String>> columns, ZipOutputStream zip,String allPackage,String author,String email){
		//配置信息
		Configuration config = getConfig();

		//表信息
		TableEntity tableEntity = new TableEntity();
		tableEntity.setTableName(table.get("tableName"));
		tableEntity.setComments(table.get("tableComment"));
		//表名转换成Java类名
		String className = tableToJava(tableEntity.getTableName(), config.getString("tablePrefix"));
		tableEntity.setClassName(className);
		tableEntity.setClassname(org.apache.commons.lang.StringUtils.uncapitalize(className));

		//列信息
		List<ColumnEntity> columsList = new ArrayList<>();
		for(Map<String, String> column : columns){
			ColumnEntity columnEntity = new ColumnEntity();
			columnEntity.setColumnName(column.get("columnName"));
			columnEntity.setDataType(column.get("dataType"));
			columnEntity.setComments(column.get("columnComment"));
			columnEntity.setExtra(column.get("extra"));

			//列名转换成Java属性名
			String attrName = columnToJava(columnEntity.getColumnName());
			columnEntity.setAttrName(attrName);
			columnEntity.setAttrname(org.apache.commons.lang.StringUtils.uncapitalize(attrName));

			//列的数据类型，转换成Java类型
			String attrType = config.getString(columnEntity.getDataType(), Object.class.getSimpleName());
			columnEntity.setAttrType(attrType);

			//是否主键
			if("PRI".equalsIgnoreCase(column.get("columnKey")) && tableEntity.getPk() == null){
				tableEntity.setPk(columnEntity);
			}

			columsList.add(columnEntity);
		}
		tableEntity.setColumns(columsList);

		//没主键，则第一个字段为主键
		if(tableEntity.getPk() == null){
			tableEntity.setPk(tableEntity.getColumns().get(0));
		}

		//设置velocity资源加载器
		Properties prop = new Properties();
		prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		Velocity.init(prop);

		//封装模板数据
		Map<String, Object> map = new HashMap<>();
		map.put("tableName", tableEntity.getTableName());
		map.put("comments", tableEntity.getComments());
		map.put("pk", tableEntity.getPk());
		map.put("className", tableEntity.getClassName());
		map.put("classname", tableEntity.getClassname());
		map.put("pathName", tableEntity.getClassname().toLowerCase());
		map.put("columns", tableEntity.getColumns());
//		map.put("package", config.getString("package"));
//		map.put("author", config.getString("author"));
//		map.put("email", config.getString("email"));
		//20170725  修改自定义包名,作者与邮箱
		map.put("package", allPackage);
		map.put("author", author);
		map.put("email", email);
		map.put("datetime", DateHelper.formatDateTime(new Date()));
		VelocityContext context = new VelocityContext(map);

		//获取模板列表
		List<String> templates = getTemplates();
		for(String template : templates){
			//渲染模板
			StringWriter sw = new StringWriter();
			Template tpl = Velocity.getTemplate(template, "UTF-8");
			tpl.merge(context, sw);

			try {
				//添加到zip
				zip.putNextEntry(new ZipEntry(getFileName(template, tableEntity.getClassName(), allPackage)));
				IOUtils.write(sw.toString(), zip, "UTF-8");
				IOUtils.closeQuietly(sw);
				zip.closeEntry();
			} catch (IOException e) {
				throw new VansException(VansExceptionEnum.SERVER_ERROR);
			}
		}
	}


	/**
	 * 列名转换成Java属性名
	 */
	public static String columnToJava(String columnName) {
		return org.apache.commons.lang.WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
	}

	/**
	 * 表名转换成Java类名
	 */
	public static String tableToJava(String tableName, String tablePrefix) {
		if(org.apache.commons.lang.StringUtils.isNotBlank(tablePrefix)){
			tableName = tableName.replace(tablePrefix, "");
		}
		return columnToJava(tableName);
	}

	/**
	 * 获取配置信息
	 */
	public static Configuration getConfig(){
		try {
			return new PropertiesConfiguration("generator.properties");
		} catch (ConfigurationException e) {
			throw new VansException(VansExceptionEnum.SERVER_ERROR);
		}
	}

	/**
	 * 获取文件名
	 */
	public static String getFileName(String template, String className, String packageName){
		String packagePath = "main" + File.separator + "java" + File.separator;
		if(org.apache.commons.lang.StringUtils.isNotBlank(packageName)){
			packagePath += packageName.replace(".", File.separator) + File.separator;
		}

		if(template.contains("Entity.java.vm")){
			return packagePath + "entity" + File.separator + className + "Entity.java";
		}

		if(template.contains("Mapper.java.vm")){
			return packagePath + "mapper" + File.separator + className + "Mapper.java";
		}

		if(template.contains("Service.java.vm")){
			return packagePath + "service" + File.separator + "I"+className + "Service.java";
		}
		if(template.contains("Mapper.xml.vm")){
			return "main" + File.separator + "resources" + File.separator + "mybatis" + File.separator + className + "Mapper.xml";
		}

		if(template.contains("ServiceImpl.java.vm")){
			return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
		}

		return null;
	}
}

