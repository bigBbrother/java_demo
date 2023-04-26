package com.dabige;

import cn.hutool.core.io.FileUtil;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.InputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtDemo {
    //私钥
    private String securityId = "dabige";

    /**
     * 生成公钥和私钥用于非对称加密算法RS256
     */
    @Test
    public void createPublishAndPrivateSecurity() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(securityId.getBytes());
        keyPairGenerator.initialize(1024, secureRandom);
        KeyPair keyPair = keyPairGenerator.genKeyPair();

        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();

        FileUtil.writeBytes(publicKeyBytes, "D:\\后端\\jwt_test\\src\\main\\resources\\public.key");
        FileUtil.writeBytes(privateKeyBytes, "D:\\后端\\jwt_test\\src\\main\\resources\\private.key");
    }

    /**
     * 创建jwt
     */
    @Test
    public void createJwt() throws Exception {

        //先获取私钥用于生成
        PrivateKey privateKey;
        InputStream resourceAsStream =
                this.getClass().getClassLoader().getResourceAsStream("private.key");
        DataInputStream dis = new DataInputStream(resourceAsStream);
        byte[] keyBytes = new byte[resourceAsStream.available()];
        dis.readFully(keyBytes);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        privateKey = kf.generatePrivate(spec);


        Map header = new HashMap();
        header.put("alg", SignatureAlgorithm.RS256.getValue());//使用RS256非对称签名算法
        header.put("typ", "JWT");
        Map body = new HashMap();
        body.put("name", "dabige");
        body.put("password", "123");
        body.put("userId", "123456");
        //使用jjwt生成token
        String jwt = Jwts.builder().setHeader(header).setClaims(body).setId("101").
                signWith(SignatureAlgorithm.RS256, privateKey).compact();
        System.out.println(jwt);
    }

    /**
     * 解析验证jwt
     */
    @Test
    public void parseJwt() throws Exception {
        //先获取公钥
        PublicKey publicKey;
        InputStream resourceAsStream =
                this.getClass().getClassLoader().getResourceAsStream("public.key");
        DataInputStream dis = new DataInputStream(resourceAsStream);
        byte[] keyBytes = new byte[resourceAsStream.available()];
        dis.readFully(keyBytes);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        publicKey = kf.generatePublic(spec);

        Jwt jwt = Jwts.parser().setSigningKey(publicKey).parse("eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJwYXNzd29yZCI6IjEyMyIsIm5hbWUiOiJkYWJpZ2UiLCJ1c2VySWQiOiIxMjM0NTYiLCJqdGkiOiIxMDEifQ.hWYzfxs5il_0AAQX2uaVT3voYUKwCDntLZcsHE3gTmuvWIAUXiIHXBjehuCxRMZoNez9c9pq0N2BMJ85sDeXb5hN5Q4RYfRpGapwIudlJMqrC8IB9lbEc2F7gf1W8ImeIH2PP2a6KCPGRfN3FjSEVKOS-qRbH-kvZfYdIgmcVb0");
        Header header = jwt.getHeader();
        Object body = jwt.getBody();
        System.out.println(header);
        System.out.println(body);
    }

    @Test
    public void test(){
        Date date = new Date();
        System.out.println(date);
    }
}
