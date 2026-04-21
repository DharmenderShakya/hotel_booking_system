package com.hotel_booking_system;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.hotel_booking_system.repository.UsersRepository;

@SpringBootTest
class HotelBookingSystemApplicationTests {

    @MockBean
    private UsersRepository usersRepository;
	
	@Test
	void contextLoads() {
	}

}
