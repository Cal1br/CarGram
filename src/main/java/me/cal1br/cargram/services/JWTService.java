package me.cal1br.cargram.services;

import io.fusionauth.jwt.JWTDecoder;
import io.fusionauth.jwt.JWTEncoder;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import io.fusionauth.jwt.hmac.HMACVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Service
public class JWTService{
    private final HMACSigner singer;
    private final HMACVerifier verifier;
    private final JWTEncoder encoder;
    private final JWTDecoder decoder;

    public JWTService(@Value("${jwt.secret:defaultsecret}") final String jwtSecret) {
        this.singer = HMACSigner.newSHA256Signer(jwtSecret);
        this.verifier = HMACVerifier.newVerifier(jwtSecret);
        this.encoder = JWT.getEncoder();
        this.decoder = JWT.getDecoder();
    }

    /**Creates a jwt token from usedid
     */
    public String sign(final long id,final int hours){
        final JWT jwt = new JWT()
                .setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusHours(hours))
                .setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC));
        jwt.getOtherClaims().put("id",id+"");
        return encoder.encode(jwt,singer);
    }

    public Long getIdFromToken(final String token){
        final JWT decode;
        try{
            decode = decoder.decode(token,verifier);
        }catch (IllegalArgumentException illegalArgumentException){
            return null;
        }
        if(decode.isExpired()){
            return null;
        }
        return Long.parseLong((String) decode.getOtherClaims().get("id"));
    }
}
