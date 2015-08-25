package com.skg.luohong.code.gen.template;

/**
 * 初始化参数，这里使用Builder设计模式
 * @author 骆宏
 * @date 2015-08-17 23:16
 * */
public class GenCodeInitParam {
    private String workspace;
    private String system;
    private String module;
    private String table;
    private String systemKey;
    private boolean override;
    
    public GenCodeInitParam setWorkspace(String workspace){
    	this.workspace = workspace;
    	return this;
    }
    
    public GenCodeInitParam setSystem(String system){
    	this.system = system;
    	return this;
    }
    
    public GenCodeInitParam setTable(String table){
    	this.table = table;
    	return this;
    }
    
    public GenCodeInitParam setSystemKey(String systemKey){
    	this.systemKey = systemKey;
    	return this;
    }
    
    public GenCodeInitParam setModule(String module){
    	this.module = module;
    	return this;
    }
    
    public GenCodeInitParam setOverride(boolean override){
    	this.override = override;
    	return this;
    }

	public boolean override() {
		return override;
	}

	public String getSystemKey() {
		return systemKey;
	}

	public String getWorkspace() {
		return workspace;
	}

	public String getSystem() {
		return system;
	}

	public String getModule() {
		return module;
	}

	public String getTable() {
		return table;
	}
	
	@Override
	public GenCodeInitParam clone(){
		return new GenCodeInitParam().setModule(module).setWorkspace(workspace).setSystemKey(systemKey).setSystem(system);
	}
}
