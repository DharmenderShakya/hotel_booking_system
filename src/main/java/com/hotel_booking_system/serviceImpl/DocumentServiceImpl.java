package com.hotel_booking_system.serviceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.hotel_booking_system.entity.HotelDocument;
import com.hotel_booking_system.entity.Hotels;
import com.hotel_booking_system.entity.RoomImages;
import com.hotel_booking_system.entity.Rooms;
import com.hotel_booking_system.exception.BadRequestException;
import com.hotel_booking_system.repository.HotelDocumentRepository;
import com.hotel_booking_system.repository.HotelRepository;
import com.hotel_booking_system.repository.RoomImagesRepository;
import com.hotel_booking_system.repository.RoomRepository;
import com.hotel_booking_system.request.DocumentRequest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DocumentServiceImpl {
private static final SecureRandom secureRandom = new SecureRandom();
		
	@Autowired
	private HotelDocumentRepository hRepository;
	
	@Autowired
	private HotelRepository hoteRepository;
	
	@Autowired
	private RoomRepository rooRepository;
	
	@Autowired
	private RoomImagesRepository roomRepository;
	
	@Value("${file.upload-dir}")
    private String uploadDir;
	
	List<String> allowedExtensions = List.of( "jpg", "jpeg", "png", "pdf");
	
	public String storeFileRooms(DocumentRequest documentDTO) {
		
		Rooms rooms=rooRepository.findById(documentDTO.getRoomId()).orElseThrow(()->new BadRequestException("Hotel Not Found"));
    	
    	Path fileStorageLocation = Paths.get(uploadDir+"user_"+(documentDTO.getLoggedUserId()==null ? "id" : documentDTO.getLoggedUserId()))
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(fileStorageLocation);
        } catch (Exception ex) {
        	return "file saved successfully";
        }
    	
        // Normalize file name
        String fileName = StringUtils.cleanPath(documentDTO.getFile().getOriginalFilename());
        System.out.println(fileName+"  is fileNamefileNamefileNamefileNamefileName");
        String msg = isValidFileName(fileName);
        if(msg != null) {
        	return msg;
        }
        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                return "Sorry! Filename contains invalid path sequence ";
            }

            // Copy file to the target location (Replacing existing file with the same name)
           
            String filePath= saveFileToTargetLocation(documentDTO.getFile(), fileName, fileStorageLocation);
            System.out.println(filePath+"  is filePath");
            RoomImages hDocument = new RoomImages();
            hDocument.setDocId(generateDocId());
            //labDocument.setDocId(documentDTO.getLoggedUserId());
            hDocument.setDocName(fileName);
            hDocument.setDocPath(filePath);
            hDocument.setRooms(rooms);
            //labDocument.setImage(documentDTO.getFile().getBytes());
            roomRepository.save(hDocument);
            hDocument.setDocPath(null);
            hDocument.setId(null);
            return "document successfully saved";
        } catch (IOException ex) {
        	return "Sorry! failed to save File ";
        }
	}
	
    public String storeFile(DocumentRequest documentDTO) {
    	
    	Hotels hotels=hoteRepository.findById(documentDTO.getHotelId()).orElseThrow(()->new BadRequestException("Hotel Not Found"));
    	
    	Path fileStorageLocation = Paths.get(uploadDir+"user_"+(documentDTO.getLoggedUserId()==null ? "id" : documentDTO.getLoggedUserId()))
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(fileStorageLocation);
        } catch (Exception ex) {
        	return "file saved successfully";
        }
    	
        // Normalize file name
        String fileName = StringUtils.cleanPath(documentDTO.getFile().getOriginalFilename());
        System.out.println(fileName+"  is fileNamefileNamefileNamefileNamefileName");
        String msg = isValidFileName(fileName);
        if(msg != null) {
        	return msg;
        }
        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                return "Sorry! Filename contains invalid path sequence ";
            }

            // Copy file to the target location (Replacing existing file with the same name)
           
            String filePath= saveFileToTargetLocation(documentDTO.getFile(), fileName, fileStorageLocation);
            System.out.println(filePath+"  is filePath");
            HotelDocument hDocument = new HotelDocument();
            hDocument.setDocId(generateDocId());
            //labDocument.setDocId(documentDTO.getLoggedUserId());
            hDocument.setDocName(fileName);
            hDocument.setDocPath(filePath);
            hDocument.setHotel(hotels);
            //labDocument.setImage(documentDTO.getFile().getBytes());
            
            hRepository.save(hDocument);
            hDocument.setDocPath(null);
            hDocument.setId(null);
            return "document successfully saved";
        } catch (IOException ex) {
        	return "Sorry! failed to save File ";
        }
    }

    private String saveFileToTargetLocation(MultipartFile file, String fileName, Path fileStorageLocation) throws IOException {
    	String ext = fileName.split("\\.")[1]; 
    	Path targetLocation = fileStorageLocation.resolve(generateDocId()+"."+ext);
         Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		return targetLocation.toString();
		
	}

	private synchronized String generateDocId() {
		byte[] randomBytes = new byte[16];
        secureRandom.nextBytes(randomBytes);
        
        long timestamp = System.currentTimeMillis();
        StringBuilder docIdBuilder = new StringBuilder("d");
        docIdBuilder.append(timestamp);
        for (byte b : randomBytes) {
            docIdBuilder.append(String.format("%02x", b)); // Convert to hex
        }
        return docIdBuilder.toString();
	}

	private Resource loadFileAsResource(HttpServletResponse response, String docId) {
		
        try {
    		HotelDocument labDocument = hRepository.findHotelDocumentByDocId(docId);
        	Path fileStorageLocation = Paths.get(labDocument.getDocPath());
        	Resource resource = new UrlResource(fileStorageLocation.toUri());
            if(resource.exists()) {
                return resource;
            } else {
            	//sendFileNotFoundErr(response);
            }
        } 
        catch (Exception ex) {
        	//sendFileNotFoundErr(response);
        }
		return null;
    }
//	private void sendFileNotFoundErr(HttpServletResponse response) {
//		
//		try {
//			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Document Not Found");
//		} catch (IOException e) {
//			//log.error("err", e);
//		}
//	}
	public String storeFiles(DocumentRequest documentDTO) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private boolean isExtensionAllowed(String extension) {
		return allowedExtensions.contains(extension.toLowerCase());
	}
	
	private String isValidFileName(String originalFileName) {
		
		String[] splittedFileName = originalFileName.split("\\.");
		if(splittedFileName.length>2 || splittedFileName.length==1) {
			return "Issue occured while uploading document! InvalidFileName";
		}                                                                                                                                                                                                                                     
		
		String onlyFileName =splittedFileName[0];
		if(onlyFileName.length()>100) {
			return "File name length sholud be less than 100 characters.";
		}
//		if(!hasMetacharacters(onlyFileName)) {
//			return ResponseData.generateResponseData("0", "Issue occured while uploading document!", "InvalidFileName");	
//		}
		String ext =splittedFileName[1];
		if (!isExtensionAllowed(ext)) {
			return "Issue occured while uploading document! extension not allowed "+ext;
		}
		return null;
		
	}

	public ResponseEntity<Resource> downloadFile(HttpServletRequest request, HttpServletResponse response, String docId) {
		Resource resource = loadFileAsResource(response, docId);
		if(resource == null) {
			return ResponseEntity.status(HttpServletResponse.SC_GONE).body(new ByteArrayResource("Document not found".getBytes()));
		}
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			// logger.info("Could not determine file type.");
		}
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	public ResponseEntity<Resource> viewFile(HttpServletRequest request, HttpServletResponse response, String docId) {
		Resource resource = loadFileAsResource(response, docId);
		if(resource == null) {
			return ResponseEntity.status(HttpServletResponse.SC_GONE).body(new ByteArrayResource("Document not found".getBytes()));
			//return ResponseEntity.noContent().build();
		}
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			// logger.info("Could not determine file type.");
		}
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	public String getUploadedFile(HttpServletRequest request, HttpServletResponse response, String docId) {
    	Resource resource = loadFileAsResource(response, docId);
    	if(resource == null) {
			return "Document not found";
		}
    	byte[] byteArray;
		try {
			byteArray = Files.readAllBytes(Path.of(resource.getURI()));
		} catch (Exception e) {
			return "Server Error";
		}
    	
    	Map<String, String> image=new HashMap<>();
    	image.put("image", new String(byteArray));
		return "success";
	}
}
