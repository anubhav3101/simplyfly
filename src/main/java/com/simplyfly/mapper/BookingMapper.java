package com.simplyfly.mapper;

import com.simplyfly.dto.BookingDTO;
import com.simplyfly.model.Booking;

import java.util.List;
import java.util.stream.Collectors;

public class BookingMapper {

    // Convert Booking entity to BookingDTO
    public static BookingDTO toDTO(Booking booking) {
        return new BookingDTO(
                booking.getId(),
                booking.getUser().getId(),
                booking.getFlight().getId(),
                booking.getSeatsBooked(),
                booking.getStatus().toString(),
                booking.getTotalFare()
        );
    }

    // Convert List of Booking entities to List of BookingDTOs
    public static List<BookingDTO> toDTOList(List<Booking> bookings) {
        return bookings.stream().map(BookingMapper::toDTO).collect(Collectors.toList());
    }

    // Convert BookingDTO to Booking entity (optional, if needed)
    public static Booking toEntity(BookingDTO bookingDTO) {
        Booking booking = new Booking();
        booking.setId(bookingDTO.getId());
        booking.setSeatsBooked(bookingDTO.getSeatsBooked());
        booking.setTotalFare(bookingDTO.getTotalFare());
        // Relationships (User and Flight) can be set separately in the service layer
        return booking;
    }
}
