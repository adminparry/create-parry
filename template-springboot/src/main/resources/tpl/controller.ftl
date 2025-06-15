package ${packageName}.controller;
import ${packageName}.dto.${entityName}DTO;
import ${packageName}.service.${entityName}Service;
import org.springframework.beans.factory.annotation.Autowired;
import ${ApiResponseUtil}.ApiResponseUtil;
import org.springframework.data.domain.Page;

import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/${entityName}")
public class ${entityName}Controller {
    @Autowired
    private ${entityName}Service ${varName}Service;
    @GetMapping
    public ApiResponseUtil<List<${entityName}DTO>> findAll() {
        return ApiResponseUtil.success(${varName}Service.findAll());
    }
    @GetMapping("/page")
    public ApiResponseUtil<Page<${entityName}DTO>> page(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        return ApiResponseUtil.success(${varName}Service.page(page,size));
    }
    @GetMapping("/{id}")
    public ApiResponseUtil<${entityName}DTO> findById(@PathVariable Long id) {
        return ApiResponseUtil.success(${varName}Service.findById(id));
    }
    @PostMapping
    public ApiResponseUtil<${entityName}DTO> save(@RequestBody ${entityName}DTO ${varName}DTO) {
        return ApiResponseUtil.success(${varName}Service.save(${varName}DTO));
    }
    @DeleteMapping("/{id}")
    public ApiResponseUtil deleteById(@PathVariable Long id) {
        ${varName}Service.deleteById(id);
       return ApiResponseUtil.success(id);
    }
}