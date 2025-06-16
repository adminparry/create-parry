package ${packageName}.controller;
import ${packageName}.dto.${entityName}DTO;
import ${packageName}.service.${entityName}Service;
import org.springframework.beans.factory.annotation.Autowired;
import ${ApiResponseUtil}.ApiResponseUtil;
import com.example.demo.utils.PaginationUtil;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import ${packageName}.vo.${entityName}VO;
import com.example.demo.foundation.annotation.Permission;
import com.example.demo.foundation.annotation.PermissionAction;
import com.example.demo.foundation.enums.PermissionsTypes;


@RestController
@RequestMapping("${prefix}/${entityName}")
@Permission(code = "${varName}", name = "${entityName}")
public class ${entityName}Controller {
    @Autowired
    private ${entityName}Service ${varName}Service;

    @PermissionAction(PermissionsTypes.R)
    @GetMapping
    public ApiResponseUtil<List<${entityName}DTO>> findAll() {
        return ApiResponseUtil.success(${varName}Service.findAll());
    }

    @PermissionAction(PermissionsTypes.R)
    @GetMapping("/page")
    public ApiResponseUtil<PaginationUtil<${entityName}DTO>> page(@RequestParam(defaultValue = "0") Long pageNo,
                                                 @RequestParam(defaultValue = "10") Long pageSize) {
        return ApiResponseUtil.success(${varName}Service.page(pageNo,pageSize));
    }

    @PermissionAction(PermissionsTypes.R)
    @GetMapping("/{id}")
    public ApiResponseUtil<${entityName}DTO> findById(@PathVariable Long id) {
        return ApiResponseUtil.success(${varName}Service.findById(id));
    }

    @PermissionAction(PermissionsTypes.C)
    @PostMapping
    public ApiResponseUtil<Long> save(@RequestBody ${entityName}VO ${varName}VO) {
        return ApiResponseUtil.success(${varName}Service.save(${varName}VO));
    }

    @PermissionAction(PermissionsTypes.D)
    @DeleteMapping("/{id}")
    public ApiResponseUtil deleteById(@PathVariable Long id) {
        ${varName}Service.deleteById(id);
       return ApiResponseUtil.success(id);
    }

    @PermissionAction(PermissionsTypes.U)
    @PutMapping("/{id}")
    public ApiResponseUtil putById(@PathVariable Long id, ${entityName}VO ${varName}VO) {
        ${varName}Service.updateById(id, ${varName}VO);
        return ApiResponseUtil.success(id);
    }
}