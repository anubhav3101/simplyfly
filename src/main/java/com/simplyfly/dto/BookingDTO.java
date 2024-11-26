package com.simplyfly.dto;

public class BookingDTO {

    private Long id;
    private Long userId;
    private Long flightId;
    private int seatsBooked;
    private String status;
    private double totalFare;

    // Getters and Setters
    

    public BookingDTO(Long id, Long userId, Long flightId, int seatsBooked, String status, double totalFare) {
        this.id = id;
        this.userId = userId;
        this.flightId = flightId;
        this.seatsBooked = seatsBooked;
        this.status = status;
        this.totalFare = totalFare;
    }

	public BookingDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getFlightId() {
		return flightId;
	}

	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}

	public int getSeatsBooked() {
		return seatsBooked;
	}

	public void setSeatsBooked(int seatsBooked) {
		this.seatsBooked = seatsBooked;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getTotalFare() {
		return totalFare;
	}

	public void setTotalFare(double totalFare) {
		this.totalFare = totalFare;
	}
}
