package com.example.gestionAcueducto.security.jwt;

import com.example.gestionAcueducto.entity.RefreshToken;
import com.example.gestionAcueducto.entity.User;
import com.example.gestionAcueducto.repository.RefreshTokenRepository;
import com.example.gestionAcueducto.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;


import org.springframework.web.util.WebUtils;


import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Function;


@Component
public class JwtUtils {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Value("${jwt.access.cookie.name}")
    private String JWT_ACCESS_COOKIE_NAME;

    @Value("${jwt.refresh.cookie.name}")
    private String JWT_REFRESH_COOKIE_NAME;

    @Value("${jwt.secret}")
    private String JWT_SECRET;
    @Value("${jwt.accessToken.expirationMs}")
    private String JWT_ACCESS_EXPIRATION_MS;

    @Value("${jwt.refreshToken.expirationMs}")
    private String JWT_REFRESH_EXPIRATION_MS;


    public String getAccessTokenFromCookie(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, JWT_ACCESS_COOKIE_NAME);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    public String getRefreshTokenFromCookie(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, JWT_REFRESH_COOKIE_NAME);
        if (cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    public ResponseCookie cleanAccessToken(){
        return ResponseCookie.from(JWT_ACCESS_COOKIE_NAME, null)
                .path("/")
                .maxAge(0)
                .sameSite("Lax")  // Required for cross-site cookies (must also use Secure)
                .secure(false)
                .build();
    }

    public ResponseCookie cleanRefreshToken(User user){

        refreshTokenRepository.findByUser(user).ifPresent(
                refreshToken -> refreshTokenRepository.delete(refreshToken)
        );

        return ResponseCookie.from(JWT_REFRESH_COOKIE_NAME, null)
                .path("/auth/refresh")
                .maxAge(0)
                .sameSite("Lax")  // Required for cross-site cookies (must also use Secure)
                .secure(false)
                .build();
    }

    public ResponseCookie generateAccessCookie(String email) {
        String accessToken = generateAccessToken(email);
        return ResponseCookie.from(JWT_ACCESS_COOKIE_NAME, accessToken)
                .path("/")
                .httpOnly(true)
                .sameSite("Lax")  // Required for cross-site cookies (must also use Secure)
                .secure(false)
                .build();
    }

    public ResponseCookie generateRefreshCookie(String email) {
        String refreshToken = generateRefreshToken(email);
        return ResponseCookie.from(JWT_REFRESH_COOKIE_NAME, refreshToken)
                .path("/auth/refresh")
                .httpOnly(true)
                .sameSite("Lax")  // Required for cross-site cookies (must also use Secure)
                .secure(false)
                .build();
    }



    public String generateAccessToken(String email) {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        calendar.add(Calendar.MILLISECOND, Integer.parseInt(JWT_ACCESS_EXPIRATION_MS));
        Date expiryDate = calendar.getTime();
        return Jwts
                .builder()
                .claim("type", "ACCESS_TOKEN")
                .subject(email)
                .issuedAt(currentDate)
                .expiration(expiryDate)
                .signWith(getKey())
                .compact();
    }



    /***/
    public String generateRefreshToken(String email) {

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        calendar.add(Calendar.MILLISECOND, Integer.parseInt(JWT_REFRESH_EXPIRATION_MS));
        Date expiryDate = calendar.getTime();

        String refreshToken = Jwts.builder()
                .claim("type", "REFRESH_TOKEN")
                .subject(email)
                .issuedAt(currentDate)
                .expiration(expiryDate) // 3 horas de sesión activa
                .signWith(getKey())
                .compact();

        User user = userRepository.findByEmail(email).orElse(null);
        RefreshToken existingToken = refreshTokenRepository.findByUser(user).orElse(null);

        if (existingToken != null) {
            refreshTokenRepository.delete(existingToken);
        }

        refreshTokenRepository.save(
                RefreshToken.builder()
                        .token(refreshToken)
                        .user(user)
                        .expirationDate(getClaim(refreshToken, Claims::getExpiration))
                        .build()
        );

        return refreshToken;
    }


    public boolean isAccessToken(String token) {
        Claims claims = getAllClaims(token);
        return "ACCESS_TOKEN".equals(claims.get("type"));
    }

    public boolean isRefreshToken(String token) {
        Claims claims = getAllClaims(token);
        return "REFRESH_TOKEN".equals(claims.get("type"));
    }

    public boolean isTokenExpired(String token) {
        return getExpiration(token).before(new Date());
    }


    private SecretKey getKey() {
        byte[] keyBytes= Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims getAllClaims(String token) {
        try {
            return Jwts
                    .parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();  // Return the claims even if the token is expired
        }
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }


    public Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    public String getEmail(String token) {
        return getClaim(token, Claims::getSubject);
    }





}
