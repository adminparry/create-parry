package com.example.demo.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.crud.entity.Permissions;
import com.example.demo.crud.mapper.PermissionsMapper;
import com.example.demo.crud.service.PermissionsService;
import com.example.demo.foundation.annotation.Permission;
import com.example.demo.foundation.annotation.PermissionAction;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PermissionInitializerUtil implements PriorityOrdered {

    @Autowired
    private ApplicationContext applicationContext;

    public void getAnnotatedBeans() {
        // 获取所有带有@Permission注解的Bean
        Map<String, Object> beans =
                applicationContext.getBeansWithAnnotation(Permission.class);
        HashSet<Permissions>  permissions =  new HashSet<>();
        // 获取特定注解的方法
        for (Object bean : beans.values()) {
            Method[] methods = bean.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(PermissionAction.class)) {
                    PermissionAction annotation = method.getAnnotation(PermissionAction.class);
                    if(null != annotation){
                        Permissions permissions1 = new Permissions();
                        Permission l1 = bean.getClass().getAnnotation(Permission.class);
                        permissions1.setPermissionName(l1.name());

                        permissions1.setPermissionCode(l1.code() + "." + annotation.value().name());
                        permissions.add(permissions1);

                    }
                    // 处理注解信息
                }
            }
        }
        syncPermissions(permissions);
    }
    @Autowired
    private PermissionsMapper permissionsMapper;

//    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
        // 不在此处处理，因为依赖的服务还未准备好
    }

//    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        // 确保PermissionService已准备好
        this.permissionsMapper = beanFactory.getBean(PermissionsMapper.class);

        // 扫描注解并同步到数据库
        scanAndSyncPermissions("com.example.demo");
    }

    private void scanAndSyncPermissions(String basePackage) {
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);

        scanner.addIncludeFilter(new AnnotationTypeFilter(Permission.class));

        Set<Permissions> permissions = new HashSet<>();

        // 扫描类级别注解
        for (BeanDefinition beanDefinition : scanner.findCandidateComponents(basePackage)) {
            try {
                Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());
                processClassAnnotations(clazz, permissions);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Failed to load class", e);
            }
        }

        // 扫描方法级别注解 不好使
//        scanMethodLevelAnnotations(basePackage, permissions);

        // 同步到数据库
//        syncPermissions(permissions);
    }

    public void syncPermissions(Collection<Permissions> permissions) {

        Map<String, Permissions> existingPermissionMap = new HashMap<>();
        // 1. 查询现有权限
        QueryWrapper queryWrapper = new QueryWrapper<>();
        List<Permissions> existingPermissions = permissionsMapper.selectList(queryWrapper);
        if(null != existingPermissions){

            existingPermissionMap = existingPermissions.stream()
                    .collect(Collectors.toMap(Permissions::getPermissionCode, Function.identity()));
        }
        int i = 0;
        // 2. 同步处理
        for (Permissions info : permissions) {
            if(info.getPermissionCode() == null){
                System.out.println(i);
            }
            i++;
            Permissions permission = existingPermissionMap.get(info.getPermissionCode());
            if (permission == null) {
                // 新增权限
                permission = new Permissions();
                permission.setPermissionCode(info.getPermissionCode());
                permission.setPermissionName(info.getPermissionName());
                permission.setDescription(info.getDescription());

                permission.setCreatedAt(LocalDateTime.now());
                permissionsMapper.insert(permission);
            } else {
                // 更新权限信息
                if (!StringUtils.equals(permission.getPermissionName(), info.getPermissionName()) ||
                        !StringUtils.equals(permission.getPermissionCode(), info.getPermissionCode())
                   ) {
                    permission.setPermissionName(info.getPermissionName());
                    permission.setPermissionCode(info.getPermissionCode());

                    permission.setUpdatedAt(LocalDateTime.now());
                    permissionsMapper.updateById(permission);
                }
            }
        }

        // 3. 清理无效权限(可选)
        Set<String> currentCodes = permissions.stream()
                .map(Permissions::getPermissionCode)
                .collect(Collectors.toSet());

        List<Integer> toDeleteIds = existingPermissions.stream()
                .filter(p -> !currentCodes.contains(p.getPermissionCode()))
                .map(Permissions::getPermissionId)
                .collect(Collectors.toList());

        if (!toDeleteIds.isEmpty()) {
            permissionsMapper.deleteBatchIds(toDeleteIds);
        }
    }
    private void processClassAnnotations(Class<?> clazz, Set<Permissions> permissions) {
        Permission classAnnotation = clazz.getAnnotation(Permission.class);
        Arrays.stream(clazz.getMethods()).forEach(item ->  {
            PermissionAction code = item.getAnnotation(PermissionAction.class);
            if(null != code ){

            System.out.println(code.value());
                Permissions permissions1 = new Permissions();
                permissions1.setPermissionName(classAnnotation.name());

                permissions1.setPermissionCode(classAnnotation.code());
                permissions.add(permissions1);
            }
        });

    }

    private void scanMethodLevelAnnotations(String basePackage, Set<Permissions> permissions) {
        Reflections reflections = new Reflections(basePackage);
        Set<Method> methods = reflections.getMethodsAnnotatedWith(PermissionAction.class);

        for (Method method : methods) {
            PermissionAction methodAnnotation = method.getAnnotation(PermissionAction.class);
            if(null != methodAnnotation){

            System.out.println(methodAnnotation.value());
            }
//            permissions.add((Permissions) methodAnnotation);
        }
    }



//    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE - 100; // 确保在最后执行
    }
}