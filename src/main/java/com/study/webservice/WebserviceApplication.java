package com.study.webservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/* EnableJpaAuditing 삭제하고 JpaConfig 파일에 생성한다
   이유: EnableJpaAuditing를 사용하기 위해선 최소 하나의 @Entity가
   필요한데 @WebMvcTest에는 이게 없다
 */
@SpringBootApplication
public class WebserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebserviceApplication.class, args);
	}

}
