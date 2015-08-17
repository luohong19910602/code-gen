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
 * 这里面使用策略设计模式，每种类为一个策略，比如：dao,service,view,test等
 * 
 * @author 骆宏
 * @date 2015-08-11 21:43
 * */
public interface IGenCode {
	/**
	 * 初始化，提供必要的初始化信息，比如：代码生成器所在的路径，需要生成代码的table，是否覆盖等信息
	 * 这些信息保存在genCode.xml中
	 * 
	 * init方法必须保证在genCode前调用
	 * */
	public void init(GenCodeInitParam param);
	
	/**
	 * 根据源信息来完成代码生成器
	 * @param table 数据库表名
	 * @param override是否覆盖已经存在的文件
	 * */
	public void genCode();
}
