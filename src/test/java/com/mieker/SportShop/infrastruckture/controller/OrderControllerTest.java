package com.mieker.SportShop.infrastruckture.controller;

import com.mieker.SportShop.domain.model.order.Order;
import com.mieker.SportShop.infrastruckture.repository.OrderRepository;
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
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
            .withCopyFileToContainer(MountableFile.forClasspathResource("db/populate_data.js"), "/docker-entrypoint-initdb.d/populate_data.js")
            .waitingFor(Wait.forHttp("/"));

    private static final String API_URL = "/api/orders";
    private static final String ADMIN_ENCODED_CREDENTIALS = "Basic Q2hyaXN0aW5hOjEyMzRjaHJpc3RpbmE=";
    private static final String CUSTOMER_JOHN_ENCODED_CREDENTIALS = "Basic Sm9objpKb2hubnlCR29vZA==";
    private static final String CUSTOMER_LASZLO_ENCODED_CREDENTIALS = "Basic TGFzemxvOmxldE1lSW4h";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrderRepository orderRepository;

    @DynamicPropertySource
    static void mongoDbProperties(DynamicPropertyRegistry registry) {
        String credentials = "mongokeeper:mySecret!@";
        String mongoUri = "mongodb://" + credentials + mongoDBContainer.getHost() + ":" + mongoDBContainer.getMappedPort(27017) + "/shop?authSource=admin";
        registry.add("spring.data.mongodb.uri", () -> mongoUri);
    }

    @BeforeAll
    static void setUp() {
        mongoDBContainer.start();
    }

    @AfterAll
    static void cleanUp() {
        mongoDBContainer.stop();
    }

    @Test
    void callingEndpointByUnknownUserShouldReturnStatusUnauthorized() throws Exception {
        String unauthorizedUserCredentials = "Basic Q3Jpc3RpbmE6MTIzNGNocmlzdGluYQ==";

        mockMvc.perform(
                        get(API_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", unauthorizedUserCredentials)
                                .param("customerId", "user1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void adminWithValidCredentialsShouldBeAuthorizedToGetUserOrders() throws Exception {
        String expectedResponse = "\"customerId\":\"user1\",\"orderItems\":[{\"product\":{\"id\":\"pball1\",\"name\":\"Ball\",\"description\":\"Brand new soccer ball.\",\"price\":35.99},\"quantity\":3},{\"product\":{\"id\":\"pbicycle1\",\"name\":\"Bicycle\",\"description\":\"Red bicycle with big basket on the front.\",\"price\":220},\"quantity\":1}]";

        mockMvc.perform(
                        get(API_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", ADMIN_ENCODED_CREDENTIALS)
                                .param("customerId", "user1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expectedResponse)));
    }

    @Test
    void userWithValidCredentialsShouldBeAuthorizedToGetOwnedOrders() throws Exception {
        String expectedResponse = "{\"id\":\"648217d3a061235b73beafb5\",\"customerId\":\"user2\",\"orderItems\":[{\"product\":{\"id\":\"prope1\",\"name\":\"Jumping rope\",\"description\":\"Super fast jumping rope.\",\"price\":8.99},\"quantity\":1}]}";

        mockMvc.perform(
                        get(API_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", CUSTOMER_LASZLO_ENCODED_CREDENTIALS)
                                .param("customerId", ""))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expectedResponse)));
    }

    @Test
    void adminWithValidCredentialsShouldBeAuthorizedToGetAllOrders() throws Exception {
        String expectedResponse1 = "{\"id\":\"648217d3a061235b73beafb5\",\"customerId\":\"user2\",\"orderItems\":[{\"product\":{\"id\":\"prope1\",\"name\":\"Jumping rope\",\"description\":\"Super fast jumping rope.\",\"price\":8.99},\"quantity\":1}]}";
        String expectedResponse2 = "\"customerId\":\"user1\",\"orderItems\":[{\"product\":{\"id\":\"pball1\",\"name\":\"Ball\",\"description\":\"Brand new soccer ball.\",\"price\":35.99},\"quantity\":3},{\"product\":{\"id\":\"pbicycle1\",\"name\":\"Bicycle\",\"description\":\"Red bicycle with big basket on the front.\",\"price\":220},\"quantity\":1}]";

        mockMvc.perform(
                        get(API_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", ADMIN_ENCODED_CREDENTIALS)
                                .param("customerId", ""))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expectedResponse1)))
                .andExpect(content().string(containsString(expectedResponse2)));
    }

    @Test
    void attemptOfDeletingOrderByCustomerShouldReturnForbiddenStatus() throws Exception {
        String orderId = "648217d3a061235b73beafb4";

        mockMvc.perform(
                        delete(API_URL + "/" + orderId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", CUSTOMER_JOHN_ENCODED_CREDENTIALS))
                .andExpect(status().isForbidden());
    }

    @Test
    void attemptOfDeletingNonExistingOrderShouldReturnBadRequest() throws Exception {
        String nonExistingOrderId = "1234orderThatNeverExist1234";

        mockMvc.perform(
                        delete(API_URL + "/" + nonExistingOrderId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", ADMIN_ENCODED_CREDENTIALS))
                .andExpect(status().isBadRequest());
    }

    @Test
    void authorizedUserShouldBeAbleToCreateOrder() throws Exception {
        String expectedResponse = "\"customerId\":\"user1\",\"orderItems\":[{\"product\":{\"id\":\"pball1\",\"name\":\"Ball\",\"description\":\"Brand new soccer ball.\",\"price\":35.99},\"quantity\":4},{\"product\":{\"id\":\"pbicycle1\",\"name\":\"Bicycle\",\"description\":\"Red bicycle with big basket on the front.\",\"price\":220},\"quantity\":4},{\"product\":{\"id\":\"prope1\",\"name\":\"Jumping rope\",\"description\":\"Super fast jumping rope.\",\"price\":8.99},\"quantity\":4}]";
        String requestBody = "{\"orderItems\":[{\"productId\":\"pball1\",\"quantity\":\"4\"},{\"productId\":\"pbicycle1\",\"quantity\":4},{\"productId\":\"prope1\",\"quantity\":4}]}";

        mockMvc.perform(
                        post(API_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", CUSTOMER_JOHN_ENCODED_CREDENTIALS)
                                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expectedResponse)));
    }

    @Test
    void unauthorizedUserShouldNotBeAbleToCreateOrder() throws Exception {
        String unauthorizedUserCredentials = "Basic somedummycredentials";
        String requestBody = "{\"orderItems\":[{\"productId\":\"pball1\",\"quantity\":\"4\"},{\"productId\":\"pbicycle1\",\"quantity\":4},{\"productId\":\"prope1\",\"quantity\":4}]}";

        mockMvc.perform(
                        post(API_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", unauthorizedUserCredentials)
                                .content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void authorizedUserShouldNotBeAbleToCreateOrderWithInvalidData() throws Exception {
        String unauthorizedUserCredentials = "Basic somedummycredentials";
        String requestBody = "{\"orderItems\":[{\"productId\":\"\",\"quantity\":\"0\"},{\"productId\":\"pbicycle1\",\"quantity\":2000000},{\"productId\":\"prope1\",\"quantity\":4}]}";

        mockMvc.perform(
                        post(API_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", unauthorizedUserCredentials)
                                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Please provide valid product Id.")))
                .andExpect(content().string(containsString("Product quantity cannot be less than 1.")))
                .andExpect(content().string(containsString("Maximum value of product quantity is 1000000.")));
    }

    @Test
    void adminShouldBeAbleToDeleteOrder() throws Exception {
        String orderId = "648217d3a061235b73beafb5";

        Optional<Order> order = orderRepository.findById(orderId);
        assertThat(order.isPresent()).isTrue();

        mockMvc.perform(
                        delete(API_URL + "/" + orderId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", ADMIN_ENCODED_CREDENTIALS))
                .andExpect(status().isOk());

        Optional<Order> orderAfterDeletion = orderRepository.findById(orderId);
        assertThat(orderAfterDeletion.isPresent()).isFalse();
    }

    @Test
    void adminWithInvalidPasswordShouldNotBeAbleToDeleteOrder() throws Exception {
        String orderId = "648217d3a061235b73beafb5";

        mockMvc.perform(
                        delete(API_URL + "/" + orderId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Basic Q2hyaXN0aW5hOmNocmlzdGluYQ=="))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void incorrectRequestMethodShouldReturnBadRequest() throws Exception {
        mockMvc.perform(
                        put(API_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Basic Q2hyaXN0aW5hOmNocmlzdGluYQ=="))
                .andExpect(status().isBadRequest());
    }

    @Test
    void requestWithInvalidQueryParamShouldReturnBadRequest() throws Exception {
        mockMvc.perform(
                        get(API_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", ADMIN_ENCODED_CREDENTIALS)
                                .param("notAllowedParam", "someValue"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void requestWithInvalidRequestBodyFormatShouldReturnBadRequest() throws Exception {
        String requestBody = "{\"orderItems\":[{\"productId\":\"\"\"quantity\":\"0\"}";

        mockMvc.perform(
                        post(API_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", ADMIN_ENCODED_CREDENTIALS)
                                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Request body malformed.")));
    }

}