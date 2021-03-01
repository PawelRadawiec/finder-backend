package com.info.finder.service.validation.user;

import com.google.common.base.Joiner;
import org.passay.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class SystemPasswordValidator  {

    public PasswordValid validate(String password) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                new LengthRule(8, 30),
                new UppercaseCharacterRule(1),
                new DigitCharacterRule(1),
                new SpecialCharacterRule(1),
                new WhitespaceRule()));
        RuleResult result = validator.validate(new PasswordData(password));
        return new PasswordValid(Joiner.on(" ").join(validator.getMessages(result)), result.isValid());
    }

}
