package com.example.demo.utils;

import com.example.demo.auth.entity.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;

public class GenerateCodeUtil {

    private static String getJavaSorucePath(){
        String projectRoot = System.getProperty("user.dir");
        String javaSourcePath = projectRoot + File.separator + "template-springboot" + File.separator + "src" + File.separator + "main" + File.separator + "java";

        return  javaSourcePath;
    }
    public static void main(String[] args) throws IOException {


        // 生成CRUD代码
        GenerateCodeUtil.generateCRUD(
                User.class,
                "com.example.demo.curd",
                getJavaSorucePath()
        );
    }
    /**
     * 生成完整的CRUD代码结构
     * @param entityClass 实体类
     * @param packageName 基础包名(如: com.example.demo)
     * @param outputDir 输出目录
     */
    public static void generateCRUD(Class<?> entityClass, String packageName, String outputDir) {
        String entityName = entityClass.getSimpleName();
        String varName = toLowerFirst(entityName);
        
        // 创建目录结构
        String basePath = outputDir + "/" + packageName.replace(".", "/");
        new File(basePath + "/controller").mkdirs();
        new File(basePath + "/service").mkdirs();
        new File(basePath + "/repository").mkdirs();
        new File(basePath + "/dto").mkdirs();
        new File(basePath + "/entity").mkdirs();

        // 生成Repository接口
        generateRepository(entityClass, packageName, basePath);
        
        // 生成Service接口和实现
        generateService(entityClass, packageName, basePath);
        
        // 生成Controller
        generateController(entityClass, packageName, basePath);
        
        // 生成DTO
        generateDTO(entityClass, packageName, basePath);


    }

    private static void generateRepository(Class<?> entityClass, String packageName, String basePath) {
        String entityName = entityClass.getSimpleName();
        String content = "package " + packageName + ".repository;\n\n" +
                "import org.springframework.data.jpa.repository.JpaRepository;\n" +
                "import org.springframework.data.domain.Page;\n" +
                "import org.springframework.data.domain.Pageable;\n" +
                "import " + entityClass.getName() + ";\n\n" +
                "public interface " + entityName + "Repository extends JpaRepository<" + entityName + ", Long> {\n" +
                "    // 自定义查询方法可以在这里添加\n" +
                    "Page<"+entityName+"> findAll(Pageable pageable);\n"+
                "}\n";
        
        writeFile(basePath + "/repository/" + entityName + "Repository.java", content);
    }

    private static void generateService(Class<?> entityClass, String packageName, String basePath) {
        String entityName = entityClass.getSimpleName();
        String varName = toLowerFirst(entityName);
        
        // 接口
        String interfaceContent = "package " + packageName + ".service;\n\n" +
                "import " + packageName + ".dto." + entityName + "DTO;\n" +
                "import org.springframework.data.domain.Page;\n" +
                "import java.util.List;\n\n" +
                "public interface " + entityName + "Service {\n" +
                "    List<" + entityName + "DTO> findAll();\n" +
                "    " + entityName + "DTO findById(Long id);\n" +
                "    " + entityName + "DTO save(" + entityName + "DTO " + varName + "DTO);\n" +
                "    void deleteById(Long id);\n" +
                "    Page<"+entityName+"DTO> page(int page, int size);" +
                "}\n";
        
        writeFile(basePath + "/service/" + entityName + "Service.java", interfaceContent);
        
        // 实现类
        String implContent = "package " + packageName + ".service.impl;\n\n" +
                "import " + packageName + ".dto." + entityName + "DTO;\n" +
                "import " + packageName + ".entity." + entityName + ";\n" +
                "import " + packageName + ".repository." + entityName + "Repository;\n" +
                "import " + packageName + ".service." + entityName + "Service;\n" +
                "import org.springframework.data.domain.Page;\n" +
                "import org.springframework.data.domain.PageRequest;\n" +
                "import org.springframework.data.domain.Pageable;\n" +
                "import org.springframework.beans.factory.annotation.Autowired;\n" +
                "import org.springframework.stereotype.Service;\n" +
                "import java.util.List;\n" +
                "import java.util.stream.Collectors;\n\n" +
                "@Service\n" +
                "public class " + entityName + "ServiceImpl implements " + entityName + "Service {\n\n" +
                "    @Autowired\n" +
                "    private " + entityName + "Repository " + varName + "Repository;\n\n" +
                "" +
                "   @Override" +
                "   public Page<UserDTO> page(int page, int size) {\n" +
                "        Pageable pageable = PageRequest.of(page, size);\n" +
                "        return userRepository.findAll(pageable)\n" +
                "                .map(this::convertToDTO);\n" +
                "    }" +
                "    @Override\n" +
                "    public List<" + entityName + "DTO> findAll() {\n" +
                "        return " + varName + "Repository.findAll().stream()\n" +
                "                .map(this::convertToDTO)\n" +
                "                .collect(Collectors.toList());\n" +
                "    }\n\n" +
                "    @Override\n" +
                "    public " + entityName + "DTO findById(Long id) {\n" +
                "        return " + varName + "Repository.findById(id)\n" +
                "                .map(this::convertToDTO)\n" +
                "                .orElse(null);\n" +
                "    }\n\n" +
                "    @Override\n" +
                "    public " + entityName + "DTO save(" + entityName + "DTO " + varName + "DTO) {\n" +
                "        " + entityName + " " + varName + " = convertToEntity(" + varName + "DTO);\n" +
                "        " + entityName + " saved" + entityName + " = " + varName + "Repository.save(" + varName + ");\n" +
                "        return convertToDTO(saved" + entityName + ");\n" +
                "    }\n\n" +
                "    @Override\n" +
                "    public void deleteById(Long id) {\n" +
                "        " + varName + "Repository.deleteById(id);\n" +
                "    }\n\n" +
                "    private " + entityName + "DTO convertToDTO(" + entityName + " " + varName + ") {\n" +
                "        // 实现转换逻辑\n" +
                "        return new " + entityName + "DTO();\n" +
                "    }\n\n" +
                "    private " + entityName + " convertToEntity(" + entityName + "DTO " + varName + "DTO) {\n" +
                "        // 实现转换逻辑\n" +
                "        return new " + entityName + "();\n" +
                "    }\n" +
                "}\n";
        
        writeFile(basePath + "/service/impl/" + entityName + "ServiceImpl.java", implContent);
    }

    private static void generateController(Class<?> entityClass, String packageName, String basePath) {
        String entityName = entityClass.getSimpleName();
        String varName = toLowerFirst(entityName);
        
        String content = "package " + packageName + ".controller;\n\n" +
                "import " + packageName + ".dto." + entityName + "DTO;\n" +
                "import " + packageName + ".service." + entityName + "Service;\n" +
                "import org.springframework.beans.factory.annotation.Autowired;\n" +
                "import com.example.demo.curd.dto.CommonResponse;\n" +
                "import org.springframework.data.domain.Page;\n" +

                "import org.springframework.web.bind.annotation.*;\n" +
                "import java.util.List;\n\n" +
                "@RestController\n" +
                "@RequestMapping(\"/api/" + toLowerFirst(entityName) + "s\")\n" +
                "public class " + entityName + "Controller {\n\n" +
                "    @Autowired\n" +
                "    private " + entityName + "Service " + varName + "Service;\n\n" +
                "    @GetMapping\n" +
                "    public CommonResponse<List<" + entityName + "DTO>> findAll() {\n" +
                "        return CommonResponse.success(" + varName + "Service.findAll());\n" +
                "    }\n\n" +
                "    @GetMapping(\"/page\")\n" +
                "    public CommonResponse<Page<" + entityName + "DTO>> page(@RequestParam(defaultValue = \"0\") int page,\n" +
                "                                                 @RequestParam(defaultValue = \"10\") int size) {\n" +
                "        return CommonResponse.success(" + varName + "Service.page(page,size));\n" +
                "    }\n\n" +
                "    @GetMapping(\"/{id}\")\n" +
                "    public CommonResponse<" + entityName + "DTO> findById(@PathVariable Long id) {\n" +
                "        return CommonResponse.success(" + varName + "Service.findById(id));\n" +
                "    }\n\n" +
                "    @PostMapping\n" +
                "    public CommonResponse<" + entityName + "DTO> save(@RequestBody " + entityName + "DTO " + varName + "DTO) {\n" +
                "        return CommonResponse.success(" + varName + "Service.save(" + varName + "DTO));\n" +
                "    }\n\n" +
                "    @DeleteMapping(\"/{id}\")\n" +
                "    public CommonResponse deleteById(@PathVariable Long id) {\n" +
                "        " + varName + "Service.deleteById(id);\n" +
                "       return CommonResponse.success(" + "id);\n" +
                "    }\n" +
                "}\n";
        
        writeFile(basePath + "/controller/" + entityName + "Controller.java", content);
    }

    private static void generateDTO(Class<?> entityClass, String packageName, String basePath) {
        String entityName = entityClass.getSimpleName();
        StringBuilder fields = new StringBuilder();
        
        // 获取实体类的所有字段
        for (Field field : entityClass.getDeclaredFields()) {
            fields.append("    private ").append(field.getType().getSimpleName())
                 .append(" ").append(field.getName()).append(";\n");
        }
        
        String content = "package " + packageName + ".dto;\n\n" +
                "import lombok.Data;\n\n" +
                "@Data\n" +
                "public class " + entityName + "DTO {\n" +
                fields.toString() +
                "}\n";
        
        writeFile(basePath + "/dto/" + entityName + "DTO.java", content);
    }

    private static void writeFile(String filePath, String content) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String toLowerFirst(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }
}