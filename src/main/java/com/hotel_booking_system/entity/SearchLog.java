package com.hotel_booking_system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "search_logs")
@Getter
@Setter
public class SearchLog {

    @Id
    @Column(name = "log_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @Column(name = "location", length = 255)
    private String location;

    @Column(name = "check_in")
    private LocalDate checkIn;

    @Column(name = "check_out")
    private LocalDate checkOut;

    @Column(name = "filters", columnDefinition = "JSON")
    private String filters; // stored as JSON string

    @Column(name = "searched_at")
    private LocalDateTime searchedAt;

}
