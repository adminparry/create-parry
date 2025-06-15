package ${packageName}.entity;

import javax.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * ${tableComment!""}
 */
@Entity
@Table(name = "${tableName}")
@Data
<#if superClass??>public class ${className} extends ${superClass} {<#else>public class ${className} {</#if>
<#list columns as column>

    /**
    *
     * ${column.columnComment!""}
     */
    <#if column.isPrimaryKey()>
    @Id
    <#if column.isAutoIncrement()>
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    </#if>
    </#if>
    private ${column.javaType} ${column.fieldName};
</#list>
}