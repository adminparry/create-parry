package com.example.demo.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class EntityGenerator {


    private final Configuration configuration;

    public EntityGenerator() throws IOException {
        configuration = new Configuration(Configuration.VERSION_2_3_31);
        configuration.setClassForTemplateLoading(getClass(), "/tpl");
        configuration.setDefaultEncoding("UTF-8");
    }


    private void generate(String outputPath, Map dataModel, String tpl) throws IOException {

        Template template = configuration.getTemplate(tpl);

        File outputFile = new File(outputPath);
        try (Writer out = new OutputStreamWriter(new FileOutputStream(outputFile), StandardCharsets.UTF_8)) {
            template.process(dataModel, out);
        } catch (TemplateException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void generateServiceImpl(Map map, String p) throws IOException {
        String model = map.get("model").toString();

        switch (model) {
            case "jpa":
                generate(p, map, "impl.jpa.ftl");
                break;
            default:
                generate(p, map, "impl.mybatis.ftl");

        }
    }

    public void generateService(Map map, String p) throws IOException {

        generate(p, map, "service.ftl");
    }

    public void generateRepository(Map map, String p) throws IOException {

        generate(p, map, "repository.ftl");
    }

    public void generateMapper(Map map, String p) throws IOException {

        generate(p, map, "mapper.ftl");
    }
    public void generateMapperXML(Map map, String p) throws IOException {

        generate(p, map, "mapper.xml.ftl");
    }

    public void generateDto(Map map, String p) throws IOException {

        generate(p, map, "dto.ftl");
    }

    public void generateController(Map map, String p) throws IOException {

        generate(p, map, "controller.ftl");
    }

    public void generateEntity(Map map, String p) throws IOException {
        String model = map.get("model").toString();

        switch (model) {
            case "jpa":
                generate(p, map, "entity.jpa.ftl");
                break;
            default:
                generate(p, map, "entity.mybatis.ftl");

        }
    }

    public void generateVo(Map map, String p) throws IOException {
        generate(p, map, "vo.ftl");
    }
}