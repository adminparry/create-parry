package com.example.demo.utils;

import lombok.Data;

import java.util.List;

@Data
public class PaginationUtil <T>{

    private Long pageNo;
    private Long pageSize;
    private Long total;
    private List<T> data;

    public PaginationUtil(Long pageNo, Long pageSize, Long total, List<T> data) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.total = total;
        this.data = data;
    }

    public static <T> PaginationUtil<T> of(List<T>  data, Long pageNo, Long pageSize, Long total){
        return new PaginationUtil<>(pageNo, pageSize, total, data);
    }
}
