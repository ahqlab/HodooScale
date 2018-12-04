package com.animal.scale.hodoo.activity.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data                   //Getter/Setter를 만든다.
@Builder                //빌더패턴을 만든다.
@AllArgsConstructor     //생성자를 자동으로 주입한다.
public class TestDomain {
    private String name;
    private int age;
    public TestDomain () {}
}
