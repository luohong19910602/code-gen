package com.skg.luohong.code.gen.main;

import com.skg.luohong.code.gen.template.FreemarkGenCode;

/**
 * 代码生成器
 * 
 * 调用入口
 * @author 骆宏
 * @date 2015-08-11 21:41
 * */
public class CodeGen {
    
	/**
	 * 配置参数请查看src/main/resources/conf下面的文件
	 * 主要用于配置数据库连接，生成的表名等信息
	 * 同样可以配置需要生曾的文件内容
	 * 
	 * */
	public static void main(String[] args){
		FreemarkGenCode genCode = new FreemarkGenCode();
        genCode.init();
        genCode.invoke();
    }
}
