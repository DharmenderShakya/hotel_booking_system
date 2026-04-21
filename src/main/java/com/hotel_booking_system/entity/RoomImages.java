package com.hotel_booking_system.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "room_images")
@Getter
@Setter
public class RoomImages {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	@Column(name = "doc_name")
	private String docName;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;
    
	@Column(name = "doc_path")
	private String docPath;
	
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Rooms rooms;
    
	@Column(name = "doc_id")
	private String docId;
	
	@Lob
	@Column(nullable = true)
	private byte[] image;
    
}
