package ${packageName}.service;

import org.springframework.data.domain.Page;
import java.util.List;
import ${packageName}.dto.${entityName}DTO;

public interface ${entityName}Service {

    List<${entityName}DTO> findAll();

    ${entityName}DTO  findById(Long id);

    ${entityName}DTO save(${entityName}DTO ${varName}DTO);

    void deleteById(Long id);

    Page<${entityName}DTO> page(int page, int size);
}
