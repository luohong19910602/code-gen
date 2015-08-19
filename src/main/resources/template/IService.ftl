package com.skg.luohong.biz.${system}.${module}.service;

import java.util.List;

import com.skg.luohong.biz.${system}.${module}.entity.${poName};

/**
 * @Author ${author}
 * @Date ${date}
 */
public interface ${serviceName} {
	public void add(${poName} entity);
    public void update(${poName} entity);
    public void delete(${idType} id);
    public List<${poName}> findAll();
}