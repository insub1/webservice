package com.study.webservice.web;

import com.study.webservice.config.auth.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

//스프링부트 테스트와 JUnit의 연결자 역할을 한다
@ExtendWith(SpringExtension.class)
/* WebMVC에 집중할 수 있다. @Controller만 사용 가능하며, @Service, @Repository는 사용 불가하다
   단, CustomOAuth2UserService를 스캔하지 못한다
   즉 @Service, @Repository, @Component는 스캔의 대상이 아니다.
   따라서 SecurityConfig는 읽을 수 있지만, SecurityConfig를
   생성하기 위해 필요한 CustomOAuth2UserService를 읽지 못한다
 */
@WebMvcTest(controllers = HelloController.class,
excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
                          classes = SecurityConfig.class)
}
)
class HelloControllerTest {

    @Autowired private MockMvc mockMvc; //웹 API 테스트용

    @WithMockUser(roles = "USER")
    @Test
    void return_hello() throws Exception {

        String hello = "hello";

        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                //응답 본문의 내용을 검증
                .andExpect(content().string(hello));
    }

    @WithMockUser(roles = "USER")
    @Test
    void return_helloDTO() throws Exception {

        String name = "hello";
        int amount = 1000;

        mockMvc.perform(get("/hello/dto")
                .param("name", name)
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                //json 응답값을 필드별로 검증한다. 이때 요청 파라미터는
                //문자열 형태만 가능하다(날짜나 숫자도 문자열로 변형해야함)
                .andExpect(jsonPath("$.name", is(name)))
                //$를 기준으로 필드명을 명시한다
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}