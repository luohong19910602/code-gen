package com.skg.luohong.code.gen.template;

import java.util.ArrayList;
import java.util.List;

import com.skg.luohong.code.gen.utils.XMlUtils;
import com.skg.luohong.code.gen.utils.XMlUtils.GenFile;

/**
 * 这里面是策略模式的调用中心，客户端直接调用该类来完成代码生成的功能
 * 该类是策略模式中的Invoker
 * 
 * 
 * @author 骆宏
 * @date 2015-08-11 21:49
 * */
public class FreemarkGenCode {
	private List<XMlUtils.GenFile> genFiles;
	private List<IGenCode> genCodeList;
    
	/**
	 * 注册代码生成器
	 * @param genCode 代码生成器
	 * */
	public void addGenCode(IGenCode genCode){
		if(genCode == null) return;
		genCodeList = new ArrayList<IGenCode>();

		if(!genCodeList.contains(genCode)){
			genCodeList.add(genCode);
		}
	}

	/**
	 * 注销代码生成器
	 * */
	public boolean removeGenCode(IGenCode genCode){
		return genCodeList.remove(genCode);
	}

	/**
	 * 初始化Freemarker引擎，为代码生成做好准备，同时读取配置文件信息
	 * */
	public void init() {
		genFiles = XMlUtils.genFiles();
		genCodeList = new ArrayList<IGenCode>();
        
		for(GenFile genFile: genFiles){
			GenCodeInitParam param = new GenCodeInitParam();
			param.setWorkspace(XMlUtils.getWorkSpace())
			     .setSystem(XMlUtils.getSystem())
			     .setSystemKey(XMlUtils.getSystemKey())
			     .setModule(XMlUtils.getModule())
			     .setPrefix(XMlUtils.getPrefix());
			
			param.setOverride(genFile.override);
		
			if(genFile.getPrefix() != null && genFile.getPrefix().length() > 0){
				param.setPrefix(genFile.getPrefix());
			}
			
			if(param.getPrefix() != null && genFile.key.startsWith(param.getPrefix())){	
				param.setTable(genFile.key.substring(param.getPrefix().length()));
			}else if(param.getPrefix() != null && !genFile.key.startsWith(param.getPrefix())){
				throw new IllegalArgumentException(param.getTable() + " prefix is not start with " + param.getPrefix());
			}else{  //没有设置表前缀，使用全局表前缀
				param.setTable(genFile.key);
			}
			
			
			IGenCode genCode = null;
			if(genFile.genOptions.contains("controller")){
				genCode = new ControllerGenCode();
				genCode.init(param);
				genCodeList.add(genCode);
			}
			if(genFile.genOptions.contains("service")){
				genCode = new ServiceGenCode();
				genCode.init(param);
				genCodeList.add(genCode);
			}
			if(genFile.genOptions.contains("dao")){
				genCode = new DaoGenCode();
				genCode.init(param);
				genCodeList.add(genCode);
			}
			if(genFile.genOptions.contains("entity")){
				genCode = new EntityGenCode();
				genCode.init(param);
				genCodeList.add(genCode);
			}
			if(genFile.genOptions.contains("view")){
				genCode = new ViewGenCode();
				genCode.init(param);
				genCodeList.add(genCode);
			}
			if(genFile.genOptions.contains("test")){
				genCode = new TestGenCode();
				genCode.init(param);
				genCodeList.add(genCode);
			}
		}
	}

	/**
	 * 根据源信息来生成代码
	 * */
	public void invoke(){
		if(genCodeList != null){
			for(IGenCode genCode: genCodeList){
				
				genCode.genCode();
			}
		}
	}
}
