import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response,

                                 FilterChain chain) throws IOException, ServletException {
        // get the encrypted token string from the authorization request header
        String encryptedToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        // block the request if no token was found
        if (encryptedToken == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No token provided.");
            return;
        }

        try {

            // decode the encoded and signed token, after removing optional Bearer prefix
            JWToken jwToken = JWToken.decode(encryptedToken.replace("Bearer ", ""),
            this.apiConfig.getPassphrase());

            // pass-on the token info as an attribute for the request
            request.setAttribute(JWToken.JWT_ATTRIBUTE_NAME, jwToken);
            // proceed along the chain of filters towards the REST controller
            chain.doFilter(request, response);


        } catch (RuntimeException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    e.getMessage() + " You need to logon first.");
            return;
        }
    }