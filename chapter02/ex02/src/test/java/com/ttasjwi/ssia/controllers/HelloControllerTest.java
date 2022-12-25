package com.ttasjwi.ssia.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
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

    @Test
    @DisplayName("실제 사용자가 /hello 엔드포인트를 호출하면 200 상태코드와 함께 Hello! 를 반환한다.")
    @WithUserDetails("user")
    public void helloAuthenticatedWithUser() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(content().string("Hello!"))
                .andExpect(status().isOk());
    }
}
