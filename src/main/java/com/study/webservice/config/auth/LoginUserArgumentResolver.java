package com.study.webservice.config.auth;

import com.study.webservice.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@Component
@RequiredArgsConstructor
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;

    //컨트롤러 메서드의 특정 파라미터를 지원하는지 판단한다
    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        boolean isLoginUserAnnotaion = parameter
          .getParameterAnnotation(LoginUser.class) != null;

        boolean isUserClass = SessionUser.class
                .equals(parameter.getParameterType());

        //파라미터에 @LoginUser 어노테이션이 붙어있고,
        //파라미터 클래스 타입이 SessionUser.class인 경우 true
        return isLoginUserAnnotaion && isUserClass;
    }

    //파라미터에 전달할 객체를 생성한다
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        return httpSession.getAttribute("user");
    }
}
