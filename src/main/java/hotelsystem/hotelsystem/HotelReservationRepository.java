package hotelsystem.hotelsystem;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface HotelReservationRepository extends MongoRepository<HotelReservation, String> {
}
