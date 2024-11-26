package com.simplyfly;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.simplyfly.model.Booking;
import com.simplyfly.model.Flight;
import com.simplyfly.repository.BookingRepository;
import com.simplyfly.repository.FlightRepository;
import com.simplyfly.services.BookingServiceImpl;

class BookingServiceImplTest {

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private FlightRepository flightRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBooking_Success() {
        // Arrange
        Flight flight = new Flight();
        flight.setId(1L);
        flight.setSeatsAvailable(50);

        Booking booking = new Booking();
        booking.setFlight(flight);
        booking.setSeatsBooked(5);

        when(flightRepository.save(any(Flight.class))).thenReturn(flight);
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        // Act
        Booking result = bookingService.createBooking(booking);

        // Assert
        assertEquals(booking, result);
        verify(flightRepository).save(flight);
        verify(bookingRepository).save(booking);
    }

    @Test
    void testCreateBooking_Failure_NotEnoughSeats() {
        // Arrange
        Flight flight = new Flight();
        flight.setId(1L);
        flight.setSeatsAvailable(3);

        Booking booking = new Booking();
        booking.setFlight(flight);
        booking.setSeatsBooked(5);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            bookingService.createBooking(booking);
        });
        assertEquals("Not enough seats available.", exception.getMessage());

        verify(flightRepository, never()).save(any());
        verify(bookingRepository, never()).save(any());
    }

    @Test
    void testGetUserBookings() {
        // Arrange
        Long userId = 1L;
        List<Booking> bookings = List.of(new Booking(), new Booking());

        when(bookingRepository.findByUserId(userId)).thenReturn(bookings);

        // Act
        List<Booking> result = bookingService.getUserBookings(userId);

        // Assert
        assertEquals(2, result.size());
        verify(bookingRepository).findByUserId(userId);
    }

    @Test
    void testCancelBooking_Success() {
        // Arrange
        Flight flight = new Flight();
        flight.setId(1L);
        flight.setSeatsAvailable(40);

        Booking booking = new Booking();
        booking.setId(1L);
        booking.setSeatsBooked(5);
        booking.setFlight(flight);

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(flightRepository.save(any(Flight.class))).thenReturn(flight);
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        // Act
        bookingService.cancelBooking(1L);

        // Assert
        assertEquals(45, flight.getSeatsAvailable()); // Seats restored
        assertEquals(Booking.BookingStatus.CANCELLED, booking.getStatus());
        verify(flightRepository).save(flight);
        verify(bookingRepository).save(booking);
    }

    @Test
    void testCancelBooking_Failure_BookingNotFound() {
        // Arrange
        when(bookingRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            bookingService.cancelBooking(1L);
        });
        assertEquals("Booking not found.", exception.getMessage());

        verify(flightRepository, never()).save(any());
        verify(bookingRepository, never()).save(any());
    }
}
