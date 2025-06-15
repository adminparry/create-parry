package com.example.demo.utils;

import com.example.demo.auth.entity.User;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class GenerateCodeUtil {
    /**
     *  数据库表名转实体类名称
     */
    private static String tableNameToEntityName(String input){
        return StringUtils.capitalize(input.toLowerCase().replaceAll("_([a-z])", "$1").toUpperCase());
    }
    /**
     * 数据库类型转java类型
     * @param dbType
     * @return
     */
    private static String mapToJavaType(String dbType) {
//        System.out.println(dbType);
        switch (dbType.toUpperCase()) {
            case "VARCHAR":
            case "CHAR":
            case "TEXT":
                return "String";
            case "INT":
            case "INTEGER":
                return "Integer";
            case "BIGINT":
                return "Long";
            case "DECIMAL":
            case "NUMERIC":
                return "BigDecimal";
            case "TIMESTAMP":
            case "TIME":
            case "DATETIME":
                return "LocalDateTime";
            case "DATE":
                return "LocalDate";
            case "BOOL":
            case "BOOLEAN":
                return "Boolean";
            default:
                return "Object";
        }
    }
    /**
     * 从resources中获取模版文件
     * @return
     */
    /**
     * 元数据
     */
    private static final String username = "postgres";
    private static final String password = "123456";
    private static final String jbdcUrl = "jdbc:postgresql://localhost:5432/public?useSSL=false&serverTimezone=UTC";
    private static final String[] tables = {"users"};
    static final String packageRoot = "template-springboot.src.main.java.";
    private static final String packageName = "com.example.demo.curd";
    private static final String utilPackageName = "com.example.demo.utils";

    public static void main(String[] args) throws IOException, SQLException {

        Arrays.stream(tables).forEach(item -> {

            try {
                List<EntityTemplateData.ColumnData>  ret = getTableColumns(item);

                // 生成CRUD代码
                GenerateCodeUtil.generateCRUD(
                        ret,
                        item,
                        getJavaSourcePath(packageRoot + packageName)
                );
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public static String getJavaSourcePath(String pkName){
        String projectRoot = System.getProperty("user.dir");
        String javaSourcePath = projectRoot + File.separator ;
        return  javaSourcePath + pkName.replaceAll("\\.", File.separator);
    }




    private static List<EntityTemplateData.ColumnData> getTableColumns(String tableName) throws SQLException {
        List<EntityTemplateData.ColumnData> columns = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(jbdcUrl, username, password)) {
            DatabaseMetaData metaData = conn.getMetaData();

            // 获取主键信息
            ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, tableName);
            List<String> pkColumns = new ArrayList<>();
            while (primaryKeys.next()) {
                pkColumns.add(primaryKeys.getString("COLUMN_NAME"));
            }

            // 获取列信息
            ResultSet rs = metaData.getColumns(null, null, tableName, null);
            while (rs.next()) {
                EntityTemplateData.ColumnData column = new EntityTemplateData.ColumnData();
                String columnName = rs.getString("COLUMN_NAME");
                column.setColumnName(columnName);
                column.setFieldName(underscoreToCamel(columnName));
                column.setFieldDtoName(toUpperFirst(underscoreToCamel(columnName)));
                column.setJavaType(mapToJavaType(rs.getString("TYPE_NAME")));
                column.setColumnComment(rs.getString("REMARKS"));
                column.setColumnType(rs.getString("TYPE_NAME"));
                column.setPrimaryKey(pkColumns.contains(columnName));
                column.setAutoIncrement("YES".equals(rs.getString("IS_AUTOINCREMENT")));

                columns.add(column);
            }
        }

        return columns;
    }

    private static String underscoreToCamel(String columnName) {
        return columnName.replaceAll("_([a-z])", "$1".toUpperCase());
    }

    public String readTemplateFromModule(String templatePath) {
        try (InputStream inputStream = getClass().getModule().getResourceAsStream(templatePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read template file: " + templatePath, e);
        }
    }
    /**
     * 根据表名生成实体类
     */
    public static void generateCURDFromDataSource(){

    }


    public static void generateCRUD(List<EntityTemplateData.ColumnData> data,String tableName, String basePath) throws IOException {


        // 创建目录结构
        new File(basePath + "/controller").mkdirs();
        new File(basePath + "/service").mkdirs();
        new File(basePath + "/repository").mkdirs();
        new File(basePath + "/dto").mkdirs();
        new File(basePath + "/entity").mkdirs();

        EntityGenerator generator = new EntityGenerator();

        Map<String, Object> dataModel = new HashMap<>();

        String entityName = toUpperFirst(tableName);
        String varName = toLowerFirst(entityName);
        dataModel.put("entityName", entityName);
        dataModel.put("varName",  varName);
        dataModel.put("packageName", packageName);
        dataModel.put("ApiResponseUtil", utilPackageName);

        dataModel.put("className", entityName);
        dataModel.put("tableName", tableName);
        dataModel.put("tableComment", "");
        dataModel.put("superClass", "BaseEntity");
        dataModel.put("columns", data);
//        System.out.println(data.toString());

        generator.generateEntity(dataModel, basePath +File.separator +
        "entity" + File.separator+ entityName  + ".java");

        generator.generateRepository(dataModel, basePath + File.separator
        +"repository" + File.separator + entityName + "Repository.java");

        generator.generateService(dataModel, basePath + File.separator
        + "service" + File.separator + entityName + "Service.java");

        generator.generateServiceImpl(dataModel, basePath +File.separator
                +"service"+ File.separator
                +"impl"+ File.separator
                + entityName  + "ServiceImpl.java");

        generator.generateController(dataModel, basePath + File.separator
        +"controller" + File.separator + entityName + "Controller.java");

        generator.generateDto(dataModel, basePath + File.separator
        +"dto" + File.separator + entityName + "DTO.java");



    }

    private static String toLowerFirst(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }
    private static String toUpperFirst(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
}