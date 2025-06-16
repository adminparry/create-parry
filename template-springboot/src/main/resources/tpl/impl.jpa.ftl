package ${packageName}.service.impl;
import ${packageName}.dto.${entityName}DTO;
import ${packageName}.vo.${entityName}VO;
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
                .map(${entityName}DTO::new);
    }
    @Override
    public List<${entityName}DTO> findAll() {
        return ${varName}Repository.findAll().stream()
                .map(${entityName}DTO::new)
                .collect(Collectors.toList());
    }
    @Override
    public ${entityName}DTO findById(Long id) {
        return ${varName}Repository.findById(id)
                .map(${entityName}DTO::new)
                .orElse(null);
    }
    @Override
    public Long save(${entityName}VO ${varName}VO) {
        ${entityName} ${varName} = new ${entityName}();
        BeanUtils.copyProperties(${varName}VO, ${varName});
        ${entityName} saved${entityName} = ${varName}Repository.save(${varName});
        return Long.valueOf(saved${entityName}.getId());
    }
     @Override
    public Long updateById(Long id, ${entityName}VO ${varName}VO) {
        ${entityName} ${varName} = new ${entityName}();
        BeanUtils.copyProperties(${varName}VO, ${varName});
        ${entityName} saved${entityName} = ${varName}Repository.save(${varName});
        return id;
    }
    @Override
    public Long deleteById(Long id) {
        ${varName}Repository.deleteById(id);
        return id;
    }
   
};