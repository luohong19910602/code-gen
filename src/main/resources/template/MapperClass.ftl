package com.skg.luohong.biz.${system}.${module}.entity;

import java.util.List;

/**
 *
 * @author ${author}
 * @date ${date}
 * @author 846705189@qq.com
 * @author 15013336884
 * @blog http://blog.csdn.net/u010469003
 * */
public interface ${mapperName}{
    public List<${entity}> findAll();
    public void update(${entity} ${firstLowwerMapperName});
    public void insert(${entity} ${firstLowwerMapperName});
    public void delete(String id);
}