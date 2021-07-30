package com.franc.restful.user;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.*;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;


@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonFilter("UserInfoV2")
public class UserV2 extends User{
    private String grade;
}
