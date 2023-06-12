package com.mieker.SportShop.infrastruckture.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    private static final String API_URL = "/api/orders";
    private static final String ADMIN_ENCODED_CREDENTIALS = "Basic Q2hyaXN0aW5hOjEyMzRjaHJpc3RpbmE=";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void adminWithValidCredentialsShouldBeAuthorizedToGetResponseWithStatusOk() throws Exception {
        mockMvc.perform(
                        get(API_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", ADMIN_ENCODED_CREDENTIALS)
                                .param("customerId", "user1"))
                .andExpect(status().isOk());
    }

    @Test
    void authorizedUserShouldGetHisOrders() {
        //given

        //when

        //then
    }

}