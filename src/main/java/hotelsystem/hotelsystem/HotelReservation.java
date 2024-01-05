package hotelsystem.hotelsystem;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Document(collection = "reservations")
public class HotelReservation {
    @Id
    private String id;

    @NotBlank(message = "Guest name is required")
    private String guestName;

    @NotNull(message = "Check-in date is required")
    private LocalDate checkInDate;

    @NotNull(message = "Check-out date is required")
    private LocalDate checkOutDate;

    @NotBlank(message = "Room type is required")
    private String roomType;

    @NotNull(message = "Number of nights is required")
    private Integer numberOfNights;
}
