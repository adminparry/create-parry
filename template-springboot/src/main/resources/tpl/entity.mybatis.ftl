package ${packageName}.entity;

import lombok.Data;
import java.time.LocalDateTime;
import com.example.demo.utils.BaseEntityUtil;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.experimental.Accessors;

@Schema(description  =  "${tableComment!""}")
@TableName(value = "${tableName}")
@Accessors(chain = true)
@Data
<#if superClass??>public class ${className} extends ${superClass} {<#else>public class ${className} {</#if>
<#list columns as column>

    @Schema(description = "${column.columnComment!""}")
    <#if column.isPrimaryKey()>
    @TableId(type = IdType.ASSIGN_ID)
    </#if>
    private ${column.javaType} ${column.fieldName};
</#list>
}