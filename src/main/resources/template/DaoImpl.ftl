package com.skg.luohong.biz.${system}.${module}.dao.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.skg.luohong.biz.${system}.${module}.dao.${daoName};
import com.skg.luohong.biz.${system}.${module}.entity.${poName};
import com.skg.luohong.biz.${system}.${module}.entity.${mapperName};
import com.skg.luohong.base.db.dao.mybatis.AbstractDao;
import com.skg.luohong.base.db.dao.IMapper;

/**
 *
 * @author ${author}
 * @date ${date}
 * @author 846705189@qq.com
 * @author 15013336884
 * @blog http://blog.csdn.net/u010469003
 * */
@Repository
public class ${daoImplName} extends AbstractDao<${idType}, ${poName}> implements ${daoName}{
	
	@Autowired
	private ${mapperName} mapper;
	
	public IMapper<${idType}, ${poName}> getMapper(){
	    return mapper;
	}
	
}