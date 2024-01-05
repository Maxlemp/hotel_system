package hotelsystem.hotelsystem;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HotelReservationService {
    private final HotelReservationRepository reservationRepository;

    @Autowired
    public HotelReservationService(HotelReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<HotelReservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public HotelReservation createReservation(HotelReservation reservation) {
        validateReservation(reservation);
        return reservationRepository.save(reservation);
    }

    public Optional<HotelReservation> getReservationById(String reservationId) {
        return reservationRepository.findById(reservationId);
    }

    public HotelReservation updateReservation(String reservationId, HotelReservation updatedReservation) {
        Optional<HotelReservation> existingReservationOptional = reservationRepository.findById(reservationId);

        if (existingReservationOptional.isPresent()) {
            HotelReservation existingReservation = existingReservationOptional.get();


            existingReservation.setGuestName(updatedReservation.getGuestName());
            existingReservation.setCheckInDate(updatedReservation.getCheckInDate());
            existingReservation.setCheckOutDate(updatedReservation.getCheckOutDate());
            existingReservation.setRoomType(updatedReservation.getRoomType());
            existingReservation.setNumberOfNights(updatedReservation.getNumberOfNights());


            return reservationRepository.save(existingReservation);
        } else {
            throw new IllegalArgumentException("Reservation not found with ID: " + reservationId);
        }
    }

    public void deleteReservation(String reservationId) {
        reservationRepository.deleteById(reservationId);
    }

    private void validateReservation(HotelReservation reservation) {
        if (reservation.getGuestName() == null) {
            throw new IllegalArgumentException("Guest name is required");
        }
        if (reservation.getCheckInDate() == null) {
            throw new IllegalArgumentException("Check-in date is required");
        }
        if (reservation.getCheckOutDate() == null) {
            throw new IllegalArgumentException("Check-out date is required");
        }
        if (reservation.getRoomType() == null) {
            throw new IllegalArgumentException("Room type is required");
        }
        if (reservation.getNumberOfNights() == null) {
            throw new IllegalArgumentException("Number of nights is required");
        }
    }
}
