package hotelsystem.hotelsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return reservationRepository.save(reservation);
    }


}