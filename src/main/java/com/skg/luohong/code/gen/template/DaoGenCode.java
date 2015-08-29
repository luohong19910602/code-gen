package com.skg.luohong.code.gen.template;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.skg.luohong.code.gen.utils.FreemarkUtils;
import com.skg.luohong.code.gen.utils.JdbcUtils;
import com.skg.luohong.code.gen.utils.StringUtils;

public class DaoGenCode implements IGenCode {

	private String workspace;  //工作目录
	private String system;  //系统
	private String systemKey;  //系统的简称
	private String module;  //模块
	private String table;  
	private String prefix; 
	private boolean override;

	@Override
	public void init(GenCodeInitParam param) {
		this.workspace = param.getWorkspace();
		this.system = param.getSystem();
		this.module = param.getModule();
		this.systemKey = param.getSystemKey();
		this.table = param.getTable();
		this.override = param.override();
		this.prefix = param.getPrefix();
		if(this.workspace == null){
			throw new IllegalArgumentException("workspace can't be null");
		}
		if(this.system == null){
			throw new IllegalArgumentException("system can't be null");
		}
		if(this.systemKey == null){
			throw new IllegalArgumentException("system key can't be null");
		}
		if(this.module == null){
			throw new IllegalArgumentException("module can't be null");
		}
		if(this.table == null){
			throw new IllegalArgumentException("table can't be null");
		}

		//为了确保代码生成的路劲正确，这里面workspace必须存在
		if(!new File(workspace).exists()){
			throw new IllegalArgumentException("workspace must be exists");
		}

	}

	@Override
	public void genCode() {
		genDao(table, override);
	}

	private void genDao(String table, boolean override) {
		String temp = StringUtils.toJavaProperties(table);

		String daoName = "I" + temp.substring(0, 1).toUpperCase() + temp.substring(1) + "Dao"; 
		String entityName = temp.substring(0, 1).toUpperCase() + temp.substring(1) + "Tbl";

		if(workspace.contains("\\")){
			workspace = workspace.replaceAll("\\\\", "/");
		}

		//根据配置信息，得到要生成Entity实体类的所在路径
		String outputPath = workspace + "/biz-root/" + system + "/src/main/java/com/skg/luohong/biz/" + systemKey + "/" + module + "/dao/";
		File outputPathDirectory = new File(outputPath);
		String outputFileName = null;

		//文件夹已经存在
		if(!outputPathDirectory.exists()){
			System.out.println("mk dir " + outputPath);
			outputPathDirectory.mkdirs();
		}
		outputFileName = outputPath + daoName + ".java";

		File outputFile = new File(outputFileName);

		//删除文件
		if(override && outputFile.exists()){
			System.out.println("delete file " + outputFileName);
			outputFile.delete();
		}

		//系统，模块，实体名字等信息
		Map<String, Object> datas = new HashMap<String, Object>();
		datas.put("system", systemKey);
		datas.put("module", module);
		datas.put("author", "骆宏");
		datas.put("daoName", daoName);
		datas.put("entity", entityName);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		datas.put("date", sdf.format(new Date()));

		List<String> columns = null;
		try {
			columns = JdbcUtils.columns(prefix + table);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String idType = "";
		for(int i=0; i<columns.size(); i++){
			String column = columns.get(i);
			String[] col = column.split(" ");
			String name = col[0];
			String type = col[1];
			Field field = new Field(name, name, type);

			/**
			 * id字段的类型
			 * 目前为数字或者字符串
			 * */
			if(field.name.equalsIgnoreCase("id")){
				if(field.type.equalsIgnoreCase("String")){
					idType = "String";
				}else if(field.type.equalsIgnoreCase("Integer")){
					idType = "Integer";
				}else{
					idType = "String";
				}
			}
		}

		datas.put("idType", idType); 
		//po类名，去掉Tbl
		String poName = entityName.substring(0, entityName.length() - 3) + "Po";
		datas.put("poName", poName);

		FreemarkUtils.createFile(datas, 
				"src/main/resources/template/IDao.ftl", outputFileName);

		genDaoImpl(datas);
	}

	/**
	 * 生成dao实现类
	 * */
	private void genDaoImpl(Map<String, Object> datas) {
		String outputPath = workspace + "/biz-root/" + system + "/src/main/java/com/skg/luohong/biz/" + systemKey + "/" + module + "/dao/impl/";

		File outputPathDirectory = new File(outputPath);
		String outputFileName = null;

		//文件夹已经存在
		if(!outputPathDirectory.exists()){
			System.out.println("mk dir " + outputPath);
			outputPathDirectory.mkdirs();
		}

		String daoName = (String) datas.get("daoName");
		String daoImplName = daoName.substring(1) + "Impl";
		datas.put("daoImplName", daoImplName);

		String entity = (String) datas.get("entity");
		String mapperName = entity.substring(0, entity.length() - 3) + "Mapper";
		datas.put("mapperName", mapperName);

		outputFileName = outputPath + daoImplName + ".java";

		File outputFile = new File(outputFileName);
		if(outputFile.exists() && override){
			System.out.println("delete file " + outputFileName);
			outputFile.delete();
		}

		//生成代码
		FreemarkUtils.createFile(datas, 
				"src/main/resources/template/DaoImpl.ftl", outputFileName);
	}
}
