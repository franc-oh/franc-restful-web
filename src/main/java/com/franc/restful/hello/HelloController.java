package com.franc.restful.hello;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class HelloController {

    // 국제화를 제공하는 인터페이스 (messages로 시작하는 프로퍼티들을 Bundle로 인식.)
    private final MessageSource messageSource;

    /**
     * 로케일 테스트
     *  1. 다국어 프로퍼티 구현 (디폴트, '_국가' 파일들)
     *  2. Http Header -> Accept-Language를 통해 로케일을 변경할 수 있다. (없으면 디폴트_한국어)
     * @param locale
     * @return
     */
    @GetMapping(path = "/hello-world-internationalized")
    public String helloWorldInternationalized(
            @RequestHeader(name="Accept-Language", required = false) Locale locale) {

        return messageSource.getMessage("greeting.message", null, locale);
    }
}
