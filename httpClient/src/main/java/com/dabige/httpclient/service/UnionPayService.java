package com.dabige.httpclient.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface UnionPayService {
    String OfficialAccountPay(String merOrderId) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException;

    void getScheme() throws IOException;

    String queryPayInfo(String merOrderId) throws Exception;

    String backPay(String merOrderId) throws Exception;
}
