package com.dabige.httpclient.controller;

import com.dabige.httpclient.service.UnionPayService;
import com.dabige.httpclient.util.R;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@CrossOrigin
@RequestMapping("/pay")
@RestController
public class UnionPayController {
    @Resource
    private UnionPayService unionPayService;

    @GetMapping("getscheme")
    public R getScheme() throws IOException {
        unionPayService.getScheme();
        return null;
    }

    /**
     * 公众号支付接口
     */
    @GetMapping("officialaccount")
    public R OfficialAccountPay(@RequestParam String merOrderId) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        String url = unionPayService.OfficialAccountPay(merOrderId);
        return R.success(url);
    }


    @GetMapping("officialquery/{merOrderId}")
    public R OfficialQuery(@PathVariable String merOrderId) throws Exception {
        String result = unionPayService.queryPayInfo(merOrderId);
        JSONObject data = JSONObject.fromObject(result);
        return R.success(data);
    }

    @GetMapping("officialback/{merOrderId}")
    public R OfficialBack(@PathVariable String merOrderId) throws Exception {
        String result = unionPayService.backPay(merOrderId);
        return R.success(result);
    }

    @GetMapping("officialback/callback")
    public void callback(String msg) throws Exception {
        System.out.println("支付回调过来了");
        System.out.println(msg);
    }
}
