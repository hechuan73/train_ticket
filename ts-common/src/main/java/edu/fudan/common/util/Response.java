package edu.fudan.common.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Response<T> {
    Integer status; // 1 true, 0 false
    String msg;
    T data;
}
