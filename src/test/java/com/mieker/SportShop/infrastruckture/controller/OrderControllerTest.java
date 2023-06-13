package com.mieker.SportShop.infrastruckture.controller;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class OrderControllerTest {

    public static final GenericContainer<?> mongoDBContainer = new GenericContainer<>(DockerImageName.parse("mongo:latest"))
            .withExposedPorts(27017)
            .withEnv("MONGO_INITDB_DATABASE", "shop")
            .withEnv("MONGO_INITDB_ROOT_USERNAME", "mongokeeper")
            .withEnv("MONGO_INITDB_ROOT_PASSWORD", "mySecret!")
            .withCopyFileToContainer(MountableFile.forClasspathResource("db/populate_data.js"), "/docker-entrypoint-initdb.d/populate_data.js");

    private static final String API_URL = "/api/orders";
    private static final String ADMIN_ENCODED_CREDENTIALS = "Basic Q2hyaXN0aW5hOjEyMzRjaHJpc3RpbmE=";
    private static final String CUSTOMER_JOHN_ENCODED_CREDENTIALS = "Basic Sm9objpKb2hubnlCR29vZA==";

    @Autowired
    private MockMvc mockMvc;

    @DynamicPropertySource
    static void mongoDbProperties(DynamicPropertyRegistry registry) {
        String credentials = "mongokeeper:mySecret!@";
        String mongoUri = "mongodb://" + credentials + mongoDBContainer.getHost() + ":" + mongoDBContainer.getMappedPort(27017) + "/shop?authSource=admin";
        registry.add("spring.data.mongodb.uri", () -> mongoUri);
        System.out.println("## " + mongoUri);
    }

    @BeforeAll
    static void setUp() throws InterruptedException {
        mongoDBContainer.start();
        Thread.sleep(10000);
    }

    @AfterAll
    static void cleanUp() {
        mongoDBContainer.stop();
    }

    @Test
    void callingEndpointByUnknownUserShouldReturnStatusUnauthorized() throws Exception {
        //given
        String unauthorizedUserCredentials = "Basic somedummycredentials";

        //then
        mockMvc.perform(
                        get(API_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", unauthorizedUserCredentials)
                                .param("customerId", "user1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void adminWithValidCredentialsShouldBeAuthorizedToGetUserOrders() throws Exception {
        //given
        String expectedResponse = "\"customerId\":\"user1\",\"orderItems\":[{\"product\":{\"id\":\"pball1\",\"name\":\"Ball\",\"description\":\"Brand new soccer ball.\",\"price\":35.99},\"quantity\":3},{\"product\":{\"id\":\"pbicycle1\",\"name\":\"Bicycle\",\"description\":\"Red bicycle with big basket on the front.\",\"price\":220},\"quantity\":1}]";

        //then
        mockMvc.perform(
                        get(API_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", ADMIN_ENCODED_CREDENTIALS)
                                .param("customerId", "user1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expectedResponse)));
    }

    @Test
    void attemptOfDeletingOrderByCustomerShouldReturnForbiddenStatus() throws Exception {
        String orderId = "648217d3a061235b73beafb4";

        //then
        mockMvc.perform(
                        delete(API_URL + "/" + orderId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", CUSTOMER_JOHN_ENCODED_CREDENTIALS))
                .andExpect(status().isForbidden());
    }

    @Test
    void attemptOfDeletingNonExistingOrderShouldReturnBadRequest() throws Exception {
        //given
        String nonExistingOrderId = "1234orderThatNeverExist1234";

        //then
        mockMvc.perform(
                        delete(API_URL + "/" + nonExistingOrderId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", ADMIN_ENCODED_CREDENTIALS))
                .andExpect(status().isBadRequest());
    }


}