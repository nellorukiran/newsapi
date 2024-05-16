package com.news.newsapi.exception;

import lombok.Data;

import java.util.Date;

@Data
public class ApiError {
    private Integer errorCode;
    private String errDesc;
    private Date date;

    public ApiError(Integer errorCode, String errDesc, Date date) {
        this.errorCode = errorCode;
        this.errDesc = errDesc;
        this.date = date;
    }
}
