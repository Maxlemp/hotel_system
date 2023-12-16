package hotelsystem.hotelsystem;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "reservations")
public class HotelReservation {
    @Id
    private String id;
    private String guestName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

}