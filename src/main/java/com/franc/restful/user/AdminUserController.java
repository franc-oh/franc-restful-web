package com.franc.restful.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.franc.restful.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminUserController {

    private final UserDaoService userDaoService;


    @GetMapping("/admin/users")
    public MappingJacksonValue retrieveAllUsers() {
        List<User> users = userDaoService.findAll();

        // Filter 구현 -> Json에 포함할 필드 정의
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "password");

        // 도메인의 JsonFilter와 위에 구현한 Filter를 가지고 Jackson객체에 담을수 있도록 변환
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        // Jackson으로 필터적용하여 데이터처리
        MappingJacksonValue mapping = new MappingJacksonValue(users);
        mapping.setFilters(filters);

        return mapping;
    }

    //@GetMapping("/admin/v1/users/{id}")
    //@GetMapping(value = "/admin/users/{id}", params = "version=1") // Request Param을 통한 Version 분리 [/admin/users/1/?version=1]
    //@GetMapping(value = "/admin/users/{id}", headers = "X-API-VERSION=1") // Header를 통한 Version 관리
    @GetMapping(value = "/admin/users/{id}", produces = "application/vnd.company.appv1+json") // MIME 타입을 통한 Version 관리
    public MappingJacksonValue retrieveUserV1(@PathVariable int id) {
        User user = userDaoService.findOne(id);

        // 통신 예외처리[1] -- null 대신 예외를 던져본다. (예외 클래스 작성)
        if(user == null) {
            // 통신 예외처리[2] -- 사용자정의로 Exception(Handler) 구현
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // Filter 구현 -> Json에 포함할 필드 정의
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "ssn");

        // 도메인의 JsonFilter와 위에 구현한 Filter를 가지고 Jackson객체에 담을수 있도록 변환
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfo", filter);

        // Jackson으로 필터적용하여 데이터처리
        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters);

        return mapping;
    }


    //@GetMapping("/admin/v2/users/{id}")
    //@GetMapping(value = "/admin/users/{id}", params = "version=2") // Request Param을 통한 Version 분리 [/admin/users/1/?version=2]
    //@GetMapping(value = "/admin/users/{id}", headers = "X-API-VERSION=2") // Header를 통한 Version 관리
    @GetMapping(value = "/admin/users/{id}", produces = "application/vnd.company.appv2+json") // MIME 타입을 통한 Version 관리
    public MappingJacksonValue retrieveUserV2(@PathVariable int id) {
        User user = userDaoService.findOne(id);

        // 통신 예외처리[1] -- null 대신 예외를 던져본다. (예외 클래스 작성)
        if(user == null) {
            // 통신 예외처리[2] -- 사용자정의로 Exception(Handler) 구현
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // User -> UserV2
        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user, userV2);
        userV2.setGrade("VIP");

        // Filter 구현 -> Json에 포함할 필드 정의
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "grade");

        // 도메인의 JsonFilter와 위에 구현한 Filter를 가지고 Jackson객체에 담을수 있도록 변환
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        // Jackson으로 필터적용하여 데이터처리
        MappingJacksonValue mapping = new MappingJacksonValue(userV2);
        mapping.setFilters(filters);

        return mapping;
    }

}
