package com.hotel_booking_system;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel_booking_system.configuration.JwtFilter;
import com.hotel_booking_system.configuration.SocialAppService;
import com.hotel_booking_system.configuration.UserInfoService;
import com.hotel_booking_system.controller.AuthController;
import com.hotel_booking_system.controller.PaymentController;
import com.hotel_booking_system.request.PaymentRequest;
import com.hotel_booking_system.request.RefundRequest;
import com.hotel_booking_system.response.PaymentResponse;
import com.hotel_booking_system.response.RefundResponse;
import com.hotel_booking_system.service.PaymentService;
import com.hotel_booking_system.service.UserService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PaymentController.class,
excludeAutoConfiguration = {
    org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
    org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration.class,
    org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientWebSecurityAutoConfiguration.class

}
)
@AutoConfigureMockMvc(addFilters = false)
public class PaymentTestController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean private UserService userService;
    @MockBean private JwtFilter jwtFilter;
    @MockBean private UserInfoService userInfoService;
    @MockBean private SocialAppService socialAppService;
    @MockBean
    private PaymentService paymentService;

    @Autowired
    private ObjectMapper objectMapper;

    // ✅ Mock logged-in user
    private Principal mockPrincipal() {
        return () -> "test@gmail.com";
    }

    // ✅ Initiate Payment
    @Test
    void testMakePayment() throws Exception {

        PaymentRequest request = new PaymentRequest();
        request.setAmount(BigDecimal.valueOf(1000.00)); // ✅ FIX
        request.setPaymentMethod("UPI");

        Mockito.doNothing().when(paymentService)
                .makePaymentByUser(Mockito.eq(1L), Mockito.any(), Mockito.anyString());

        mockMvc.perform(post("/api/payment/{bookingId}", 1L)
                .principal(mockPrincipal())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Payment Done Successfully"));
    }

    // ✅ Get Payment By ID
    @Test
    void testGetPaymentById() throws Exception {

        Mockito.when(paymentService.getUserPaymentById(Mockito.eq(1L), Mockito.anyString()))
                .thenReturn(new PaymentResponse());

        mockMvc.perform(get("/api/payment/{paymentId}", 1L)
                .principal(mockPrincipal()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // ✅ Refund Request
    @Test
    void testRefundRequest() throws Exception {

        RefundRequest request = new RefundRequest();
        request.setAmount(BigDecimal.valueOf(500.00)); // ✅ FIX
        request.setRefundDate(LocalDateTime.now());

        Mockito.when(paymentService.paymentRefundRequest(Mockito.eq(1L), Mockito.any(), Mockito.anyString()))
                .thenReturn(new RefundResponse());

        mockMvc.perform(post("/api/payment/refund/{paymentId}", 1L)
                .principal(mockPrincipal())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // ✅ Get Refund By ID
    @Test
    void testGetRefundById() throws Exception {

        Mockito.when(paymentService.getRefundById(Mockito.eq(1L), Mockito.isNull(), Mockito.anyString()))
                .thenReturn(new RefundResponse());

        mockMvc.perform(get("/api/payment/refund/{refundId}", 1L)
                .principal(mockPrincipal()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // ✅ Get Payments By Owner
    @Test
    void testGetPaymentsByOwner() throws Exception {

        List<PaymentResponse> list = List.of(new PaymentResponse());

        Mockito.when(paymentService.getPaymentByOwnerId(Mockito.eq(1L), Mockito.isNull(), Mockito.anyString()))
                .thenReturn(list);

        mockMvc.perform(get("/api/payment/owner/{ownerId}", 1L)
                .principal(mockPrincipal()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    // ✅ Exception Case
    @Test
    void testPayment_NotFound() throws Exception {

        Mockito.when(paymentService.getUserPaymentById(Mockito.eq(1L), Mockito.anyString()))
                .thenThrow(new RuntimeException("Payment not found"));

        mockMvc.perform(get("/api/payment/{paymentId}", 1L)
                .principal(mockPrincipal()))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }
}