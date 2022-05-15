package com.myproject.shopping.utils.formatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;

public class CurrencyFormatter extends JsonSerializer<Integer>{

    @Override
    public void serialize(Integer value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        DecimalFormat df = new DecimalFormat("###,###");
        String result = df.format(value) + "원";
        gen.writeString(result);
    }
}
