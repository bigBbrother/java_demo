package com.dabige.httpclient.service.impl;

import com.dabige.httpclient.service.UnionPayService;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class UnionPayServiceImpl implements UnionPayService {
    static String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    static String nonce = UUID.randomUUID().toString().replace("-", "");
    static String appId = "10037e6f6a4e6da4016a62a47e51000c";
    static String appKey = "b4b70d123a724972bc21f445b0b9f75c";
    static String authorization;

    /**
     * 获取微信接口凭证
     */
    @Override
    public void getScheme() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx9479a4ee85c605e7&secret=9bfbb785f4bb4d6384b92e8ee2747fab");
        CloseableHttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity1 = response.getEntity();
        String resStr = null;
        if (entity1 != null) {
            resStr = EntityUtils.toString(entity1, "UTF-8");
        }
        System.out.println(resStr);
        httpClient.close();
        response.close();
    }

    /**
     * 查询
     *
     * @param merOrderId
     * @return
     * @throws Exception
     */
    @Override
    public String queryPayInfo(String merOrderId) throws Exception {
        JSONObject json = new JSONObject();
        json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));    // 报文请求时间
        json.put("merOrderId", merOrderId); // 商户订单号
        json.put("mid", "898460107420248"); // 商户号
        json.put("tid", "00000001");    // 终端号
        json.put("instMid", "YUEDANDEFAULT"); // 业务类型
        json.put("srcReserve", "webpay"); // 业务类型
        json.put("totalAmount", 1);
        String url = "https://test-api-open.chinaums.com/v1/netpay/query";
        String result = sendPost(url, json.toString());
        return result;
    }

    /**
     * 退款
     *
     * @param merOrderId
     * @return
     */
    @Override
    public String backPay(String merOrderId) throws Exception {
        JSONObject json = new JSONObject();
        json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));    // 报文请求时间
        json.put("merOrderId", merOrderId); // 商户订单号
        json.put("mid", "898460107420248"); // 商户号
        json.put("tid", "00000001");    // 终端号
        json.put("instMid", "YUEDANDEFAULT"); // 业务类型
        json.put("srcReserve", "webpay"); // 业务类型
        json.put("refundAmount", 1);
        json.put("refundDesc", "测试公司接口啦嘿嘿嘿");
        String url = "https://test-api-open.chinaums.com/v1/netpay/refund";
        String result = sendPost(url, json.toString());
        return result;
    }


    /**
     * 支付
     *
     * @param merOrderId
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    @Override
    public String OfficialAccountPay(String merOrderId) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        JSONObject json = new JSONObject();
        json.put("requestTimestamp", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));    // 报文请求时间
        json.put("merOrderId", merOrderId); // 商户订单号
        json.put("mid", "898460107420248"); // 商户号
        json.put("tid", "00000001");    // 终端号
        json.put("instMid", "YUEDANDEFAULT"); // 业务类型
        json.put("totalAmount", 1);
        json.put("notifyUrl", "http://39.107.203.79:8090/pay/officialback/callback");
        System.out.println("请求报文:\n" + json);
        String param = getOpenBodySigForNetpay(appId, appKey, timestamp, nonce, json.toString());
        String url = "https://test-api-open.chinaums.com/v1/netpay/webpay/pay";
        System.out.println("URL:\n" + url + "?" + param);
        return url+"?" + param;
    }


    public static String getOpenBodySigForNetpay(String appId, String appKey, String timestamp, String nonce, String content) throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException {
        String bodyDigest = DigestUtils.sha256Hex(URLDecoder.decode(content, "UTF-8")); //eg:cc18f43baa87fe658146221d3f16e3b5a50d30f0c984407f74913ef6dcda8ee1
        String str1_C = appId + timestamp + nonce + bodyDigest;
        byte[] localSignature = hmacSHA256(str1_C.getBytes(), appKey.getBytes());
        String signature = Base64.encodeBase64String(localSignature);
        System.out.println(signature);
        return ("authorization=OPEN-FORM-PARAM" + "&appId=" + appId + "&timestamp=" + timestamp + "&nonce=" + nonce + "&content=" + URLEncoder.encode(content, "UTF-8") + "&signature=" + URLEncoder.encode(signature, "UTF-8"));
    }

    public static byte[] hmacSHA256(byte[] data, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeyException {
        String algorithm = "HmacSHA256";
        Mac mac = Mac.getInstance(algorithm);
        mac.init(new SecretKeySpec(key, algorithm));
        return mac.doFinal(data);
    }

    public static String sendPost(String url, String entity) throws Exception {
        authorization = getOpenBodySig(appId, appKey, entity);
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Authorization", authorization);
        StringEntity se = new StringEntity(entity, "UTF-8");
        se.setContentType("application/json");
        httpPost.setEntity(se);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity1 = response.getEntity();
        String resStr = null;
        if (entity1 != null) {
            resStr = EntityUtils.toString(entity1, "UTF-8");
        }
        httpClient.close();
        response.close();
        return resStr;
    }

    public static String getOpenBodySig(String appId, String appKey, String body) throws Exception {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());   // eg:20190227113148
        String nonce = UUID.randomUUID().toString().replace("-", ""); // eg:be46cd581c9f46ecbd71b9858311ea12
        byte[] data = body.getBytes("UTF-8");
        //System.out.println("data:\n" + body);
        InputStream is = new ByteArrayInputStream(data);
        String bodyDigest = testSHA256(is); // eg:d60bc3aedeb853e2a11c0c096baaf19954dd9b752e48dea8e919e5fb29a42a8d
        //System.out.println("bodyDigest:\n" + bodyDigest);
        String str1_C = appId + timestamp + nonce + bodyDigest; // eg:f0ec96ad2c3848b5b810e7aadf369e2f + 20190227113148 + be46cd581c9f46ecbd71b9858311ea12 + d60bc3aedeb853e2a11c0c096baaf19954dd9b752e48dea8e919e5fb29a42a8d

        //System.out.println("str1_C:" + str1_C);

//        System.out.println("appKey_D:\n" + appKey);

        byte[] localSignature = hmacSHA256(str1_C.getBytes(), appKey.getBytes());

        String localSignatureStr = Base64.encodeBase64String(localSignature);   // Signature
        System.out.println("Authorization:\n" + "OPEN-BODY-SIG AppId=" + "\"" + appId + "\"" + ", Timestamp=" + "\"" + timestamp + "\"" + ", Nonce=" + "\"" + nonce + "\"" + ", Signature=" + "\"" + localSignatureStr + "\"\n");
        return ("OPEN-BODY-SIG AppId=" + "\"" + appId + "\"" + ", Timestamp=" + "\"" + timestamp + "\"" + ", Nonce=" + "\"" + nonce + "\"" + ", Signature=" + "\"" + localSignatureStr + "\"");
    }

    private static String testSHA256(InputStream is) {
        try {
//            System.out.println(is.hashCode());
            return DigestUtils.sha256Hex(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
