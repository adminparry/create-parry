package ${packageName}.dto;

import ${packageName}.entity.${entityName};
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ${entityName}DTO {
<#list columns as column>
    private ${column.javaType} ${column.fieldName};
</#list>
    public ${entityName}DTO(${entityName} entity) {
        <#list columns as column>
        this.${column.fieldName} = entity.get${column.fieldDtoName}();
        </#list>
    }

}
