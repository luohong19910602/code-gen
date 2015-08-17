package com.skg.luohong.code.gen.template;

/**
 * controller代码生成器
 * */
public class ControllerGenCode implements IGenCode {
    private String table;
    private boolean override;
	
	@Override
	public void init(GenCodeInitParam param) {
	}

	@Override
	public void genCode() {
		genController(table, override);
	}
	
	private void genController(String table, boolean override) {
	}

}
