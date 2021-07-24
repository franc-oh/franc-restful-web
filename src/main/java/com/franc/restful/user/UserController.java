package com.franc.restful.user;

import com.franc.restful.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserDaoService userDaoService;


    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userDaoService.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id) {
        User one = userDaoService.findOne(id);

        // 통신 예외처리[1] -- null 대신 예외를 던져본다. (예외 클래스 작성)
        if(one == null) {
            // 통신 예외처리[2] -- 사용자정의로 Exception(Handler) 구현
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        return one;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {

        /* 리턴 결과를 제대로 전달하지 못함 (void)
        userDaoService.save(user);
        */

        /* Response 정보 리턴하기 (코드 및 데이터 ResponseEntity) */
        User createUser = userDaoService.save(user);

        // 신규 유저의 location Url 빌드
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(createUser.getId())
                            .toUri();

        // 201(Created) code + Header에 해당 유저의 location Url 반환
        return ResponseEntity.created(location).build();

    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        User user = userDaoService.deleteById(id);

        if(user == null)
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
    }
}
