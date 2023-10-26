package com.study.webservice.config.auth.dto;

import com.study.webservice.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    /* User클래스를 그대로 사용하지 않고, DTO를 사용하는 이유 :
       User클래스를 세션에 저장하려고 하여 직렬화를 구현하지 않았다는
       이유로 에러가 발생한다. User클래스는 엔티티이기 때문에
       언제라도 다른 엔티티와 관계를 맺을 수 있다.
       그럴 경우 직렬화 대상에 자식들까지 포함됨으로써 성능 이슈,
       부작용이 발생할 수 있다.
       때문에 직렬화 기능을 가진 DTO를 추가하는 것이다.
     */
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
