package ${packageName}.service;

import com.example.demo.utils.PaginationUtil;
import java.util.List;
import ${packageName}.dto.${entityName}DTO;
import ${packageName}.vo.${entityName}VO;


public interface ${entityName}Service {

    List<${entityName}DTO> findAll();

    ${entityName}DTO  findById(Long id);

    Long save(${entityName}VO ${varName}VO);

    Long deleteById(Long id);

    Long updateById(Long id, ${entityName}VO ${varName}VO);

    PaginationUtil<${entityName}DTO> page(Long page, Long size);
}
