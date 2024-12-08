package safa.safepaws.websocket;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import safa.safepaws.security.JwtService;

@Service
@AllArgsConstructor
public class WebSocketSecurityService {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public void setSecurityContext(String token) {
        String username = jwtService.extractUsername(token);

        if (username != null) {
            final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(token, userDetails)) {
                final Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
    }

}
