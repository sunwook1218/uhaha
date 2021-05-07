package com.sun.uhaha.account;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor // Lombok이 제공하는 Annotation, private final 의 필드만을 포함하는 생성자를 만들어준다.
public class SignUpFormValidator implements Validator {

    // spring 2.4 이후부터 Autowird 생략 가능
    private final AccountRepository accountRepository; // @RequiredArgsConstructor를 받는 생성자를 만들어줌

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        // email 이랑 nickname 중복되는지
        SignUpForm signUpForm = (SignUpForm) errors;
        if(accountRepository.existsByEmail(signUpForm.getEmail())) {
            errors.rejectValue("email", "invaild.email", new Object[]{signUpForm.getEmail()}, "이미 사용중인 이메일입니다.");
        }

        if(accountRepository.existsByNickname(signUpForm.getNickname())) {
            errors.rejectValue("nickname", "invaild.nickname", new Object[]{signUpForm.getNickname()}, "이미 사용중인 닉네임입니다.");
        }
    }
}
