package com.franc.restful.user;

import lombok.*;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;


@Getter @Setter
@ToString
@AllArgsConstructor
@Builder
public class User {

    private Integer id;

    @Size(min = 2, message = "Name은 2글자 이상 입력하세요.")
    private String name;

    @Past
    private Date joinDate;
}
