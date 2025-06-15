package ${packageName};

<#list importList as import>
import ${import};
</#list>

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
    <#if column.isPrimaryKey>
    @Id
    <#if column.isAutoIncrement>
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    </#if>
    </#if>
    @Column(name = "${column.columnName}"<#if column.columnType??>, columnDefinition = "${column.columnType}"</#if>)
    private ${column.javaType} ${column.fieldName};
</#list>
}