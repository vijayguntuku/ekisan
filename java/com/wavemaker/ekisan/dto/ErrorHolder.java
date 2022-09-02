package com.wavemaker.ekisan.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorHolder {
    List<FieldError> errorList= new ArrayList<>();

    public boolean hasErrors(){
        return errorList.size()>0;
    }

    public List<FieldError> addError(final String field, final String value, final String reason) {
        errorList.add(new FieldError(field, value, reason));
        return  errorList;
    }

}
