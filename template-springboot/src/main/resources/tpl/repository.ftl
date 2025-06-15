package ${packageName}.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ${packageName}.entity.${entityName} ;
public interface ${entityName}Repository extends JpaRepository<${entityName}, Long> {
    // 自定义查询方法可以在这里添加
    Page<${entityName}> findAll(Pageable pageable);
};