package proje.v1.api.common.util;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import proje.v1.api.exception.BadRequestExcepiton;

import java.util.ArrayList;
import java.util.List;

@Service
public class BindingValidator {

    public static void validate(BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            List<String> errors = new ArrayList<>();
            bindingResult.getFieldErrors().forEach(error -> errors.add(error.getField()));
            throw new BadRequestExcepiton("UnValidType : "+errors.toString());
        }
    }
}