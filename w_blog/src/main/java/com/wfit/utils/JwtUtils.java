package com.wfit.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class JwtUtils {

    private static final Long JWT_TTL = 60 * 60 * 1000L; //一个小时

    private static final String JWT_KEY = "renqingjun";

    /**
     * 获取UUID对象
     * @return
     */
    private static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public static String createJWT(String subject){

        JwtBuilder jwtBuild = getJwtBuild(subject, null, getUUID());
        return jwtBuild.compact();
    }

    public static String createJWT(String subject,Long ttlMills){
        JwtBuilder jwtBuild = getJwtBuild(subject, ttlMills, getUUID());
        return jwtBuild.compact();
    }

    /**
     * 解析jwt
     * @param jwt
     * @return
     */
    public static Claims parseJWT(String jwt) throws Exception{
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

    public static void main(String[] args) throws Exception {

        String jwt = JwtUtils.createJWT("1");
        System.out.println(jwt);


        Claims claims = JwtUtils.parseJWT(jwt);
        String subject = claims.getSubject();
        System.out.println(subject);


    }



    private static JwtBuilder getJwtBuild(String subject, Long ttlMillis, String uuid) {
        SignatureAlgorithm hs256 = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMills = System.currentTimeMillis();
        Date now = new Date(nowMills);

        if(Objects.isNull(ttlMillis)){
            ttlMillis = JWT_TTL;
        }

        long expMills =nowMills + ttlMillis;
        Date expDate = new Date(expMills);

        return Jwts.builder()
                .setId(uuid)
                .setSubject(subject)
                .setIssuer("qj")
                .setIssuedAt(now)
                .signWith(hs256,secretKey)
                .setExpiration(expDate);
    }

    /**
     * 生成加密后的秘钥 secretKey
     * @return
     */
    private static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JWT_KEY);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }


}