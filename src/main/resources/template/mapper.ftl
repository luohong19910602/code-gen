<!DOCTYPE mapper
    PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
    "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.skg.luohong.biz.${system}.${module}.entity.${mapperName}">
    
    <resultMap id="${entity}" type="com.skg.luohong.biz.${system}.${module}.entity.${poName}">
		<#list fields as field>
		    <result column="${field.column}" property="${field.name}"/>
		</#list>
	</resultMap>

	<select id="findAll" resultMap="${entity}">
		select * from ${table}
	</select>
	
	<insert id="insert" parameterType="com.skg.luohong.biz.${system}.${module}.entity.${poName}">
	    insert into users (
		    ${columns}
	    ) values (
	        ${names}
	    )
	</insert>
	
	<!-- 更新一条记录 -->  
    <update id="update${entity}" parameterType="com.skg.luohong.biz.${system}.${module}.entity.${poName}">  
        update ${table} set ${update} 
    </update> 
    
    <delete id="deleteById" statementType="PREPARED" timeout="20">
        delete from ${table} where id_ =  ${r'${id}'}
    </delete>
</mapper>