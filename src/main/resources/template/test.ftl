package com.skg.luohong.biz.${system}.${module};


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.skg.luohong.biz.${system}.${module}.service.${serviceName};

/**
 *
 * @author ${author}
 * @date ${date}
 * @author 846705189@qq.com
 * @author 15013336884
 * @blog http://blog.csdn.net/u010469003
 * */
@ContextConfiguration({"classpath:conf/spring-mybatis.xml", 
	"classpath:conf/spring.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ${testName}{
    
	@Autowired
	private ${serviceName} service;
	
	@Test
	public void testCrud(){
	    System.out.println(service.countAll());
		System.out.println(service.findAll());
	}
}
