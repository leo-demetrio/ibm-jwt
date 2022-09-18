package ibm.grupo2.helloBank.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Log4j2
public class JWTValidateFilter extends BasicAuthenticationFilter {

    public static final String HEADER_ATRIBUT = "Authorization";
    public static final String HEADER_BEARER = "Bearer ";


    public JWTValidateFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        String atribut = request.getHeader(HEADER_ATRIBUT);
        if(atribut == null) {
            chain.doFilter(request,response);
            return;
        }
        if(!atribut.startsWith(HEADER_BEARER)) {
            chain.doFilter(request,response);
            return;
        }
        String token = atribut.replace(HEADER_BEARER, "");
        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(token);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }
    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token){
        String customer = JWT.require(Algorithm.HMAC512(JWTAuthenticateFilter.TOKEN_PASSWORD))
                .build()
                .verify(token)
                .getSubject();

        return new UsernamePasswordAuthenticationToken(customer, null, new ArrayList<>());
    }
}
