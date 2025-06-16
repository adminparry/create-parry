package com.example.demo.utils;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class PaginationVoUtil {
    @NotNull
    private Long pageNo;
    @NotNull
    private Long pageSize;
}
