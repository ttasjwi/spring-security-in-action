package com.ttasjwi.ssia.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc // SpringBoot + AutoConfigureMockMvc는 프로젝트 내의 모든 스프링 빈을 등록
class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Authentication 없이 /hello 엔드포인트를 호출하면 unauthorized 상태코드를 반환한다.")
    public void helloUnauthenticated() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isUnauthorized());
    }

    /**
     * WithMockUser의 사용법 : https://gaemi606.tistory.com/entry/Spring-Boot-Spring-Security-Test-WithMockUser%EB%A5%BC-%EC%BB%A4%EC%8A%A4%ED%84%B0%EB%A7%88%EC%9D%B4%EC%A7%95-%ED%95%B4%EC%84%9C-%EC%82%AC%EC%9A%A9%ED%95%98%EC%9E%90
     */

    @Test
    @DisplayName("MockUser로 /hello 엔드포인트를 호출하면 200 상태코드와 함께 Hello! 를 반환한다.")
    @WithMockUser
    public void helloAuthenticated() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(content().string("Hello!"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("실제 사용자가 /hello 엔드포인트를 호출하면 200 상태코드와 함께 Hello! 를 반환한다.")
    public void helloAuthenticatedWithUser() throws Exception {
        mockMvc.perform(get("/hello")
                        .with(user("mary")))
                .andExpect(content().string("Hello!"))
                .andExpect(status().isOk());
    }
}
