package com.hotel_booking_system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="users_document")
public class UsersDocument {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "doc_name")
	private String docName;
	
	@Column(name = "doc_path")
	private String docPath;
	
	@Column(name = "doc_id")
	private String docId;
	
	@Lob
	@Column(nullable = true)
	private byte[] image;
}
