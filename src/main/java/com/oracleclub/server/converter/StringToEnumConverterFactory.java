package com.oracleclub.server.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

/**
 * @author :RETURN
 * @date :2021/3/24 0:10
 */
@Component
public class StringToEnumConverterFactory implements ConverterFactory<String,Enum<?>> {

    @Override
    public <T extends Enum<?>> Converter<String, T> getConverter(Class<T> aClass) {
        return new StringToEnumConverter(aClass);
    }

    private static class StringToEnumConverter<T extends Enum<T>>
            implements Converter<String ,T>{

        private final Class<T> enumType;

        public StringToEnumConverter(Class<T> aClass) {
            this.enumType = aClass;
        }

        @Override
        public T convert(String s) {
            return Enum.valueOf(enumType,s.toUpperCase());
        }
    }
}
