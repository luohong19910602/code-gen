package com.skg.luohong.code.gen.template;

/**
 * 代码生成器接口
 * 
 * 这里面将代码按照如下格式进行组织
 * 
 * controller（控制器）
 *     vo（将po转化为vo，用于前台显示数据）
 * 
 * service（处理事务以及业务）
 *     impl（service实现）
 * 
 * dao（数据库访问层）
 *     impl（dao实现）
 *     
 * entity（实体类）
 *     po（entity增强）
 *     
 * view（界面，这里使用freemarker来充当展示层，两个界面：list,edit）
 * 
 * test（单元测试）
 * 
 * @author 骆宏
 * @date 2015-08-11 21:43
 * */
public interface IGenCode {
	public void genCode();
}
