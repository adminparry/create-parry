package ${packageName}.service.impl;
import ${packageName}.dto.${entityName}DTO;
import ${packageName}.entity.${entityName};
import ${packageName}.repository.${entityName}Repository;
import ${packageName}.service.${entityName}Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import ${packageName}.vo.${entityName}Vo;


@Service
@Transactional
public class ${entityName}ServiceImpl implements ${entityName}Service {
    @Autowired
    private ${entityName}Repository ${varName}Repository;

   @Override
   public Page<${entityName}DTO> page(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ${varName}Repository.findAll(pageable)
                .map(this::convertToDTO);
    }
    @Override
    public List<${entityName}DTO> findAll() {
        return ${varName}Repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    @Override
    public ${entityName}DTO findById(Long id) {
        return ${varName}Repository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }
    @Override
    public ${entityName}DTO save(${entityName}DTO ${varName}DTO) {
        ${entityName} ${varName} = convertToEntity(${varName}DTO);
        ${entityName} saved${entityName} = ${varName}Repository.save(${varName});
        return convertToDTO(saved${entityName});
    }
    @Override
    public void deleteById(Long id) {
        ${varName}Repository.deleteById(id);
    }
    private ${entityName}DTO convertToDTO(${entityName} ${varName}) {
        // 实现转换逻辑
        return new ${entityName}DTO();
    }
    private ${entityName} convertToEntity(${entityName}DTO ${varName}DTO) {
        // 实现转换逻辑
        return new ${entityName}();
    }
};