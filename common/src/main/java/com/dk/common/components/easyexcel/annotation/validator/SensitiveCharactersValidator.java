package com.dk.common.components.easyexcel.annotation.validator;

import com.dk.common.components.easyexcel.annotation.SensitiveCharactersValidate;
import com.dk.common.mapper.FinancingAssetContractMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

/**
 * 敏感字符 校验者类
 *
 * @author dongkai
 */
public class SensitiveCharactersValidator implements ConstraintValidator<SensitiveCharactersValidate, String> {

    List<String> sensitiveCharacters = Arrays.asList("第一", "最高");

    @Override
    public void initialize(SensitiveCharactersValidate idcardValidate) {

    }

    @Override
    public boolean isValid(String characters, ConstraintValidatorContext context) {
        //null时不进行校验
        if (characters != null && characters.trim().length() != 0) {
            for (String sensitiveCharacter : sensitiveCharacters) {
                if (characters.contains(sensitiveCharacter)) {
                    return false;
                }
            }
            return true;
        }
        return true;
    }
}


