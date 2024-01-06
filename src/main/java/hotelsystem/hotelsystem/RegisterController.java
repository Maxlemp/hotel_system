package hotelsystem.hotelsystem;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.util.List;

@RestController
@RequestMapping("/api/register")
public class RegisterController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;


    private final String secretKey;

    public RegisterController(UserRepository userRepository, PasswordEncoder passwordEncoder,
                              AuthenticationManager authenticationManager, JwtConfig jwtConfig) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtConfig = jwtConfig;


        this.secretKey = jwtConfig.getSecretKey();
    }

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is already taken");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        String token = generateToken(user.getUsername(), user.getRoles());

        return ResponseEntity.status(HttpStatus.CREATED).body(token);
    }

    private String generateToken(String username, List<String> roles) {
        try {
            Claims claims = Jwts.claims().setSubject(username);
            claims.put("roles", roles);


            SecretKey secretKey = Keys.hmacShaKeyFor(this.secretKey.getBytes());

            String token = Jwts.builder()
                    .setClaims(claims)
                    .signWith(secretKey, SignatureAlgorithm.HS256)
                    .compact();


            System.out.println("Generated Token: " + token);

            return token;
        } catch (Exception e) {

            System.err.println("Failed to generate JWT token: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to generate JWT token");
        }
    }
}
