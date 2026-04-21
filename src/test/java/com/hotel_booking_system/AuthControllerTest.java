package com.hotel_booking_system;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel_booking_system.configuration.JwtFilter;
import com.hotel_booking_system.configuration.SocialAppService;
import com.hotel_booking_system.configuration.UserInfoService;
import com.hotel_booking_system.controller.AuthController;
import com.hotel_booking_system.request.RegistrationRequest;
import com.hotel_booking_system.response.ApiResponse;
import com.hotel_booking_system.response.LoginReponse;
import com.hotel_booking_system.service.UserService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AuthController.class,
excludeAutoConfiguration = {
    org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
    org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration.class,
    org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientWebSecurityAutoConfiguration.class

}
)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

@Autowired
private MockMvc mockMvc;

@MockBean private UserService userService;
@MockBean private JwtFilter jwtFilter;
@MockBean private UserInfoService userInfoService;
@MockBean private SocialAppService socialAppService;

@MockBean private com.hotel_booking_system.service.BookingService bookingService;

@Autowired
private ObjectMapper objectMapper;

//  User Registration
@Test
void testUserRegistration_Success() throws Exception {

    RegistrationRequest request = new RegistrationRequest();
    request.setUserName("jhondeo");
    request.setPassword("123456");
    request.setRoleId(1L);          //  REQUIRED
    request.setEmail("test@gmail.com");   // VERY LIKELY REQUIRED
    request.setMobile("9876543210"); // if exists

    Mockito.doNothing().when(userService).addUser(Mockito.any(RegistrationRequest.class));

    mockMvc.perform(post("/api/auth/userRegistration")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(request)))
            .andDo(print()) // MUST KEEP
            .andExpect(status().isOk());
}

//  Login Success
@Test
void testLogin_Success() throws Exception {

    RegistrationRequest request = new RegistrationRequest();
    request.setUserName("test@gmail.com");
    request.setPassword("123456");

    LoginReponse loginResponse = new LoginReponse();
    loginResponse.setUserToken("token123");

    ApiResponse<LoginReponse> apiResponse =
            new ApiResponse<>(true, "Login successful", loginResponse);

    Mockito.when(userService.doLogin(Mockito.any()))
            .thenReturn(ResponseEntity.ok(apiResponse));

    mockMvc.perform(post("/api/auth/login")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value(true))
            .andExpect(jsonPath("$.message").value("Login successful"))
            .andExpect(jsonPath("$.data.userToken").value("token123"));
}

// Validation Failure
@Test
void testUserRegistration_ValidationFail() throws Exception {

    RegistrationRequest request = new RegistrationRequest();

    mockMvc.perform(post("/api/auth/userRegistration")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
}
}