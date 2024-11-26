package com.simplyfly.services;

import com.simplyfly.model.Booking;

import java.util.List;

public interface BookingService {
    Booking createBooking(Booking booking);
    List<Booking> getUserBookings(Long userId);
    void cancelBooking(Long bookingId);
}
