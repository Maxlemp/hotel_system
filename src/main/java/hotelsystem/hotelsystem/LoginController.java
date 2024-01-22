package hotelsystem.hotelsystem;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, methods = {RequestMethod.POST, RequestMethod.OPTIONS})

public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token-expiration-days}")
    private int tokenExpirationDays;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    public LoginController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping
    public ResponseEntity<String> loginUser(@RequestBody User user, HttpServletRequest request,
                                            HttpServletResponse response) {
        try {
            logger.info("Received login request for user: " + user.getUsername());

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

            String jwtToken = extractTokenFromRequest(request);
            logger.info("Created token: " + jwtToken);

            logger.info("Received login request for user: " + user.getUsername());
            logger.info("Created token: " + jwtToken);


            if (validateToken(jwtToken)) {

                // Provide the token in the response body for the client to handle.
                return ResponseEntity.ok(jwtToken);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
            }
        } catch (BadCredentialsException e) {
            logger.error("Invalid credentials for user: " + user.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        } catch (Exception e) {
            logger.error("Login failed: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed");
        }
    }


    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer")) {
            // Trim the token to remove leading/trailing spaces
            return bearerToken.substring(7).trim();
        }
        return null;
    }




    private boolean validateToken(String jwtToken) {
        if (jwtToken != null) {
            try {
                // Add more detailed logging
                logger.info("Validating token: " + jwtToken);

                // The parsing itself will throw an exception if the token is invalid
                Jwts.parserBuilder()
                        .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
                        .build()
                        .parseClaimsJws(jwtToken);

                return true;
            } catch (Exception e) {
                logError("Token validation failed: " + e.getMessage());
                e.printStackTrace();
                return false;
            }
        } else {
            logError("Token validation failed: Token not provided");
            return false;
        }
    }



    private void logError(String message) {
        logger.error(message);

    }

}