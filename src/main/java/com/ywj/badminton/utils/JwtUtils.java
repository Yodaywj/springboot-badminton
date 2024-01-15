package com.ywj.badminton.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtils {
    private static final String sighKey = "YangWenJun";
    private static final Long expire = 30*24*3600*1000L;
    public static String generateJwt(ResultMessage resultMessage){
        return Jwts.builder()
                .addClaims(resultMessage)
                .signWith(SignatureAlgorithm.HS256,sighKey)
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .compact();
    }
    public static Claims parseJwt(String jwt){
        return Jwts.parser()
                .setSigningKey(sighKey)
                .parseClaimsJws(jwt)
                .getBody();
    }
}
