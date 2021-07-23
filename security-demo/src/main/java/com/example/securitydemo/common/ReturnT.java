package com.example.securitydemo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: wangzg
 * @Time: 2021/7/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnT {

    private Integer code;
    private String message;
    private Object data;

}
