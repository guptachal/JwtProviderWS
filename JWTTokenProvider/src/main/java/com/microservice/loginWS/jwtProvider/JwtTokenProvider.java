package com.microservice.loginWS.jwtProvider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.microservice.loginWS.domain.UserPrincipal;
import com.microservice.loginWS.utils.AppConstants;

import static java.util.Arrays.stream;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secret;  // This token is used for the signing the token and it should be injected through the pipeline stored in the secret vault.
    public String generateJwtToken(UserPrincipal userPrincipal){     // This is used for the token generation
        try {
            // This will get the authorities from thUse userPrincipal then convert them to Array of strings (claims)
            String[] claims = getClaimsFromUser(userPrincipal);
            // Here we generate the Jwt Token
            return JWT.create()
                    .withIssuer(AppConstants.GET_ARRAYS_LLC)      // The Issuer of the JWT Token
                    .withAudience(AppConstants.GET_ARRAYS_ADMIN)
                    .withIssuedAt(new Date())
                    .withSubject(userPrincipal.getUsername())
                    .withArrayClaim(AppConstants.AUTHORITIES, claims)
                    .withExpiresAt(new Date(System.currentTimeMillis() + AppConstants.EXPIRATION_TIME))
                    .sign(Algorithm.HMAC512(secret.getBytes()));
        }
        catch (JWTCreationException exception){
            throw new JWTCreationException(AppConstants.TOKEN_CANNOT_BE_VERIFIED,exception);
        }
    }
    public List<GrantedAuthority> getAuthorities(String token){
        String[] claims = getClaimsFromToken(token);
        return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
    public Authentication getAuthentication(String username,
                                            List<GrantedAuthority> authorities, HttpServletRequest request){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
                UsernamePasswordAuthenticationToken(username,null,authorities);
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return usernamePasswordAuthenticationToken;
    }
    public boolean isTokenValid(@NotNull String username, String token){
        JWTVerifier verifier = getJWTVerifier();
        return !username.isEmpty()&& !isTokenExpired(verifier,token);
    }
    public String getSubject(String token){
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getSubject();
    }
    private boolean isTokenExpired(@NotNull JWTVerifier verifier, String token) {
        Date expiresAt = verifier.verify(token).getExpiresAt();
        return expiresAt.before(new Date());
    }
    private String[] getClaimsFromToken(String token) {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token)
                .getClaim(AppConstants.AUTHORITIES)
                .asArray(String.class);
    }
    private JWTVerifier getJWTVerifier() {
        JWTVerifier verifier;
        try {
            Algorithm algorithm = Algorithm.HMAC512(secret);
            verifier = JWT.require(algorithm).withIssuer(AppConstants.GET_ARRAYS_LLC).build();
            return verifier;
        }
        catch (JWTVerificationException exception){
            throw new JWTVerificationException(AppConstants.TOKEN_CANNOT_BE_VERIFIED);
        }
    }
    // This function take the authorities from the User Principal and map them to the List<String> authorities.
    private String @NotNull [] getClaimsFromUser(@NotNull UserPrincipal userPrincipal) {
        List<String> authorities = new ArrayList<>();
        // Here we are traversing through the authorities stored in the user principal in the
        // form of the GrantedAuthority.
        for(GrantedAuthority grantedAuthority: userPrincipal.getAuthorities()){
            authorities.add(grantedAuthority.getAuthority());
        }
        // Here we are returning the authorities before mapping them to the array of the string.
        return authorities.toArray(new String[0]);
    }
}