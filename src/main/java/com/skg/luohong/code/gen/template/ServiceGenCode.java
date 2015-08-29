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

/**
 * Service代码生成器
 * @author 骆宏
 * @date 2015-08-17 22:37
 * */
public class ServiceGenCode implements IGenCode {
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
		genService(table, override);
	}

	private void genService(String table, boolean override) {
		//实体名
		String temp = StringUtils.toJavaProperties(table);
		String serviceName = "I" + temp.substring(0, 1).toUpperCase() + temp.substring(1) + "Service"; 
		String daoName = "I" + temp.substring(0, 1).toUpperCase() + temp.substring(1) + "Dao"; 

		String entityName = temp.substring(0, 1).toUpperCase() + temp.substring(1) + "Tbl";

		if(workspace.contains("\\")){
			workspace = workspace.replaceAll("\\\\", "/");
		}

		//根据配置信息，得到要生成Entity实体类的所在路径
		String outputPath = workspace + "/biz-root/" + system + "/src/main/java/com/skg/luohong/biz/" + systemKey + "/" + module + "/service/";
		File outputPathDirectory = new File(outputPath);
		String outputFileName = null;

		//文件夹已经存在
		if(!outputPathDirectory.exists()){
			System.out.println("mk dir " + outputPath);
			outputPathDirectory.mkdirs();
		}
		outputFileName = outputPath + serviceName + ".java";

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
		datas.put("serviceName", serviceName);
		datas.put("entity", entityName);
		datas.put("daoName", daoName);
		String poName = entityName.substring(0, entityName.length() - 3) + "Po";
		datas.put("poName", poName);
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
			
			if(field.name.equalsIgnoreCase("id")){
				if(field.type.equalsIgnoreCase("String")){
					idType = "String";
				}else if(field.type.equalsIgnoreCase("Integer")){
					idType = "Integer";
				}else{
					idType = "String";
				}
			}		}
        datas.put("idType", idType); 


		FreemarkUtils.createFile(datas, 
				"src/main/resources/template/IService.ftl", outputFileName);

		genServiceImpl(datas);
	}

	private void genServiceImpl(Map<String, Object> datas) {
		String outputPath = workspace + "/biz-root/" + system + "/src/main/java/com/skg/luohong/biz/" + systemKey + "/" + module + "/service/impl/";

		File outputPathDirectory = new File(outputPath);
		String outputFileName = null;

		//文件夹已经存在
		if(!outputPathDirectory.exists()){
			System.out.println("mk dir " + outputPath);
			outputPathDirectory.mkdirs();
		}

		String serviceName = (String) datas.get("serviceName");
		String serviceImplName = serviceName.substring(1) + "Impl";
		datas.put("serviceImplName", serviceImplName);

		String entity = (String) datas.get("entity");
		String mapperName = entity.replace("Entity", "") + "Mapper";
		datas.put("mapperName", mapperName);

		outputFileName = outputPath + serviceImplName + ".java";

		File outputFile = new File(outputFileName);
		if(outputFile.exists()){
			System.out.println("delete file " + outputFileName);
			outputFile.delete();
		}

		//生成代码
		FreemarkUtils.createFile(datas, 
				"src/main/resources/template/ServiceImpl.ftl", outputFileName);
	}
}
