package com.financialpilot.util;

import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public final class JwtService {
    private static final String ISSUER = "financial-pilot-ai";
    private static final Duration TOKEN_LIFETIME = Duration.ofMinutes(15);

    private JwtService() {}

    public static String createAccessToken(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("userId must be positive");
        }

        Instant now = Instant.now();

        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(Integer.toString(userId))
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(now.plus(TOKEN_LIFETIME)))
                .sign(Algorithm.HMAC256(secretKey()));
    }

    public static int verifyAccessToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey());
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build();

        DecodedJWT jwt = verifier.verify(token);

        try {
            int userId = Integer.parseInt(jwt.getSubject());
            if (userId <= 0) {
                throw new NumberFormatException();
            }
            return userId;
        } catch (NumberFormatException e) {
            throw new JWTVerificationException("Invalid token subject");
        }
    }

    private static byte[] secretKey() {
        String configuredSecret = System.getenv("JWT_SECRET");
        if (configuredSecret == null || configuredSecret.isBlank()) {
            throw new IllegalStateException("JWT_SECRET environment variable is required");
        }

        try {
            byte[] key = Base64.getDecoder().decode(configuredSecret);
            if (key.length < 32) {
                throw new IllegalStateException("JWT_SECRET must decode to at least 32 bytes");
            }
            return key;
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("JWT_SECRET must be Base64 encoded", e);
        }
    }
}