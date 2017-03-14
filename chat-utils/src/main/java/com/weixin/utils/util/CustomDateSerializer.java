package com.weixin.utils.util;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: dingran
 * Date: 2015/11/18
 * Description:
 */
public class CustomDateSerializer extends JsonSerializer<Date> {
    @Override
    public void serialize(Date value,
                          JsonGenerator jsonGenerator,
                          SerializerProvider provider)
            throws IOException, JsonProcessingException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        jsonGenerator.writeString(sdf.format(value));
    }
}
