package hotelsystem.hotelsystem;

import lombok.Data;
import org.springframework.data.annotation.Id;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;

    private String username;
    private String password;

    public List<String> getRoles() {
        return null;
    }
}
