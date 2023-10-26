package com.study.webservice.config.auth.dto;

import com.study.webservice.domain.user.Role;
import com.study.webservice.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    //OAuthUser에서 반환하는 사용자 정보는 Map이기 때문에 값 하나하나를 변환해야 한다
    public static OAuthAttributes of(String registrationId,
                                     String userNameAttributeName,
                                     Map<String, Object> attributes) {

        if("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        }

        return ofGoogle(userNameAttributeName, attributes);
    }

    public static OAuthAttributes ofGoogle(String userNameAttributeName,
                                           Map<String, Object> attributes) {

        return OAuthAttributes.builder()
                .name((String)attributes.get("name"))
                .email((String)attributes.get("email"))
                .picture((String)attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public static OAuthAttributes ofNaver(String userNameAttributeName,
                                          Map<String, Object> attributes) {

        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String)response.get("name"))
                .email((String)response.get("email"))
                .picture((String)response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();

    }

    public User toEntity() {

        return User.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }
}
