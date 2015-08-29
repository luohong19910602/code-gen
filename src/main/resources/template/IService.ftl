package com.skg.luohong.biz.${system}.${module}.service;

import java.util.List;

import com.skg.luohong.biz.${system}.${module}.entity.${poName};
import com.skg.luohong.base.db.dao.SqlParam;

/**
 *
 * @author ${author}
 * @date ${date}
 * @author 846705189@qq.com
 * @author 15013336884
 * @blog http://blog.csdn.net/u010469003
 * */
public interface ${serviceName} {
	public void add(${poName} entity);
    public void update(${poName} entity);
    public void delete(${idType} id);
    public List<${poName}> findAll();
    public List<${poName}> findAll(SqlParam params);
    public Integer countAll();
    public Integer countAll(SqlParam params);
}