package com.example.demo.utils;

import lombok.Data;

import java.util.List;

@Data
public class EntityTemplateData {
    private String packageName;        // 包名
    private String className;          // 类名
    private String tableName;          // 表名
    private String tableComment;       // 表注释
    private String superClass;         // 父类(可选)
    private List<String> importList;   // 需要导入的类
    private List<ColumnData> columns;  // 列数据

    @Data
    // 列数据内部类
    public static class ColumnData {
        private String columnName;     // 列名
        private String fieldName;      // 字段名(驼峰)
        private String fieldDtoName;      // dto的名称

        private String javaType;       // Java类型
        private String columnComment;  // 列注释
        private String columnType;     // 列类型(可选)
        private boolean isPrimaryKey;  // 是否主键
        private boolean isAutoIncrement; // 是否自增


        // getters and setters
    }

    // getters and setters
}
