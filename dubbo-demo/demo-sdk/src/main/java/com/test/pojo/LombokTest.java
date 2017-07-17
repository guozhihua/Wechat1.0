package com.test.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by :Guozhihua
 * Date： 2017/7/17.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LombokTest {
    private String id ;
    private String name  ;
}
