package br.com.insidesoftwares.exception.utils;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class ExceptionUtils {

    public static List<String> findValuesAnnotation(Annotation annotation){
        List<String> values = new ArrayList<>();
        if(annotation instanceof Max max){
            values.add(String.valueOf(max.value()));
        }
        if(annotation instanceof Min min){
            values.add(String.valueOf(min.value()));
        }
        if(annotation instanceof Size size){
            values.add(String.valueOf(size.min()));
            values.add(String.valueOf(size.max()));
        }
        return values;
    }



}
