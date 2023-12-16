package hotelsystem.hotelsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class HotelReservationController {
    private final HotelReservationService reservationService;

    @Autowired
    public HotelReservationController(HotelReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<HotelReservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @PostMapping
    public HotelReservation createReservation(@RequestBody HotelReservation reservation) {
        return reservationService.createReservation(reservation);
    }

    // Add other controller methods as needed
}