package com.dabige;


import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Hei {
    @Test
    public void createPublishAndPrivateSecurity() throws NoSuchAlgorithmException {

        String reqStr = "msgType=wx.orderQuery&connectSys=OPENCHANNEL&sign=182DA12EF14&merName=%E5%90";
        List<String> list = new ArrayList<String>();
        Map<String, String> map = new HashMap<>();
        for (String pair : reqStr.split("&")) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                map.put(keyValue[0], keyValue[1]);
            }
        }
        System.out.println(map);
        map.remove("sign");
        Set<String> keySet = map.keySet();
        System.out.println(keySet);
        for (String item : keySet) {
            list.add(item + "=" + map.get(item));
        }
        System.out.println(list);
        Collections.sort(list);
        System.out.println(list);
        String result = "";
        for (String item : list) {
            result = result + item + "&";
        }
        System.out.println(result.substring(0, result.length() - 1));

    }


}
