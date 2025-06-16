package ${packageName}.entity;

import javax.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import com.example.demo.utils.BaseEntityUtil;
import org.hibernate.annotations.GenericGenerator;

/**
 * ${tableComment!""}
 */
@Entity
@Table(name = "${tableName}")
@Data
<#if superClass??>public class ${className} extends ${superClass} {<#else>public class ${className} {</#if>
<#list columns as column>

    /**
     * ${column.columnComment!""}
     */
    <#if column.isPrimaryKey()>
    @Id
    <#if column.isAutoIncrement()>
    @GeneratedValue(generator = "snowflake")
    @GenericGenerator(name = "snowflake", strategy = "com.example.demo.utils.SnowflakeUtil")
    </#if>
    </#if>
    @Column(name = "${column.columnName}"<#if column.columnType??>, columnDefinition = "${column.columnType}"</#if>)
    private ${column.javaType} ${column.fieldName};
</#list>
}