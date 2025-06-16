<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}.mapper.${entityName}Mapper">

        <sql id="Raw_${entityName}_List">
            <#list columns as column>
            ${column.columnName},
            </#list>
        </sql>

        <resultMap id = "${entityName}ResultMap" type = "${packageName}.entity.${entityName}">
            <#list columns as column>
            <result column = "${column.columnName}" property = "${column.fieldName}"/>
            </#list>
<!--    一对一 一对多 -->
    <!--    <association property="tClass" javaType="com.example.aaa.test.entity.TClass">-->
    <!--        <id column="cid" property="id"></id>-->
    <!--        <result column="cname" property="name" />-->
    <!--        <result column="code" property="code" />-->
    <!--    </association>-->

    <!--    <collection property="studentList"-->
    <!--                column="{sid=id}"-->
    <!--                select="getStudentByCid"-->
    <!--                ofType="com.example.aaa.test.entity.TStudent">-->
    <!--        <id column="id" property="id" />-->
    <!--        <result column="name" property="name" />-->
    <!--        <result column="card" property="card" />-->
    <!--        <result column="classid" property="classid" />-->
    <!--    </collection>-->
        </resultMap>


</mapper>