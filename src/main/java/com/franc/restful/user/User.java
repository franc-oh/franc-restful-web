package com.franc.restful.user;

import lombok.*;

import java.util.Date;


@Getter @Setter
@ToString
@AllArgsConstructor
@Builder
public class User {

    private Integer id;

    private String name;

    private Date joinDate;
}
