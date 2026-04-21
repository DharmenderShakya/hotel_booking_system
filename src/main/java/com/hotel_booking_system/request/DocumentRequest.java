package com.hotel_booking_system.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentRequest {
	
	private String loggedUserId;
	private String loggedUserEmail;
	private String loggedUserType;
	
	private MultipartFile file;
	private MultipartFile[] files;
	
	private String docId;
	private String uploadedfilename;
	private String path;
	private String[] zipfiles;
	
	private Long hotelId;
	
	private Long roomId;
	
}
