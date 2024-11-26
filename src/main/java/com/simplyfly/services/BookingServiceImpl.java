package com.simplyfly.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simplyfly.model.Booking;
import com.simplyfly.model.Flight;
import com.simplyfly.repository.BookingRepository;
import com.simplyfly.repository.FlightRepository;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Override
    public Booking createBooking(Booking booking) {
        // Validate flight and seat availability
        Flight flight = booking.getFlight();
        if (flight.getSeatsAvailable() < booking.getSeatsBooked()) {
            throw new RuntimeException("Not enough seats available.");
        }

        // Update seat availability
        flight.setSeatsAvailable(flight.getSeatsAvailable() - booking.getSeatsBooked());
        flightRepository.save(flight);

        // Save the booking
        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getUserBookings(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    @Override
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found."));
        booking.setStatus(Booking.BookingStatus.CANCELLED);

        // Restore seat availability
        Flight flight = booking.getFlight();
        flight.setSeatsAvailable(flight.getSeatsAvailable() + booking.getSeatsBooked());
        flightRepository.save(flight);

        bookingRepository.save(booking);
    }
}
