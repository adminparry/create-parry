package ${packageName}.service.impl;
import ${packageName}.dto.${entityName}DTO;
import ${packageName}.entity.${entityName};

import ${packageName}.service.${entityName}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.utils.PaginationUtil;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import ${packageName}.vo.${entityName}VO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import ${packageName}.mapper.${entityName}Mapper;

@Service
@Transactional
public class ${entityName}ServiceImpl implements ${entityName}Service {
    @Autowired
    private ${entityName}Mapper ${varName}Mapper;


   @Override
   public PaginationUtil<${entityName}DTO> page(Long pageNo, Long pageSize) {
        Page<${entityName}> page = new Page(pageNo,pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        IPage<${entityName}> pagination = ${varName}Mapper.selectPage(page, queryWrapper);
        List<${entityName}DTO> record = pagination.getRecords()
        .stream().map(${entityName}DTO::new)
        .collect(Collectors.toList());

        return PaginationUtil.of(record, pagination.getCurrent(),pagination.getSize(),pagination.getTotal());
    }

    @Override
    public List<${entityName}DTO> findAll() {
        return ${varName}Mapper.selectList(null).stream()
                .map(${entityName}DTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public ${entityName}DTO findById(Long id) {
        return Optional.of(${varName}Mapper.selectById(id))
                .map(${entityName}DTO::new)
                .orElse(null);
    }

    @Override
    public Long save(${entityName}VO ${varName}VO) {
        ${entityName} entity = new ${entityName}();
        BeanUtils.copyProperties(${varName}VO, entity);
        return Long.valueOf(${varName}Mapper.insert(entity));
    }

    @Override
    public Long deleteById(Long id) {
        return Long.valueOf(${varName}Mapper.deleteById(id));
    }

    @Override
    public Long updateById(Long id, ${entityName}VO ${varName}VO) {
        ${entityName} entity = new ${entityName}();
        BeanUtils.copyProperties(${varName}VO, entity);
        ${varName}Mapper.updateById(entity);
        return id;
    }

    
};