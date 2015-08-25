package com.skg.luohong.biz.${system}.${module}.dao.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.skg.luohong.biz.${system}.${module}.dao.${daoName};
import com.skg.luohong.biz.${system}.${module}.entity.${poName};
import com.skg.luohong.biz.${system}.${module}.entity.${mapperName};
import com.skg.luohong.base.db.dao.mybatis.AbstractDao;
import com.skg.luohong.base.db.dao.IMapper;

@Repository
public class ${daoImplName} extends AbstractDao<${idType}, ${poName}> implements ${daoName}{
	
	@Autowired
	private ${mapperName} mapper;
	
	public IMapper<${idType}, ${poName}> getMapper(){
	    return mapper;
	}
	
}