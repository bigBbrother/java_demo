package com.dabige.java_email;

import com.dabige.java_email.service.EmailSend;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class JavaEmailApplicationTests {
    @Autowired
    private EmailSend emailSend;

    @Test
    void contextLoads() {
        emailSend.sendSimpleTextMail("437759286@qq.com",
                "来自大B哥的代码邮件",
                "亲爱的老班长周六早上好，今天下午能不能麻将，很无聊很难耐-----爱你的B");
    }


    @Test
    public void sendImgTest() throws MessagingException {
        String to = "437759286@qq.com";
        String subject = "大B哥的爱";
        String content = "   <div id=\"app\"\n" +
                "        style=\"max-width: 600px; font-family: “Microsoft YaHei” !important ;\">\n" +
                "        <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"font-family: '微软雅黑'\">\n" +
                "            <tbody>\n" +
                "                <tr>\n" +
                "                    <td>\n" +
                "                        <table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\">\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                    <td>\n" +
                "                                        <div class=\"header\" style=\"\n" +
                "                        height: 50px;\n" +
                "                        text-align: right;\n" +
                "                        width: 95%;\n" +
                "                        margin: 0 auto;\n" +
                "                      \"><img src=\"\"></img></div>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                                <tr>\n" +
                "                                    <td>\n" +
                "                                        <table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\"\n" +
                "                                            style=\"width: 90%; margin: 0 auto\">\n" +
                "                                            <tbody>\n" +
                "                                                <tr>\n" +
                "                                                    <td>\n" +
                "                                                        <span style=\"\n" +
                "                                font-size: 14px;\n" +
                "                                color: #333;\n" +
                "                                font-weight: bold;\n" +
                "                                margin-top: 20px;\n" +
                "                              \">\n" +
                "                                                            班长您好，<br />Hello,\n" +
                "                                                        </span>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                                <tr>\n" +
                "                                                    <td>\n" +
                "                                                        <table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\"\n" +
                "                                                            style=\"\n" +
                "                                font-size: 12px;\n" +
                "                                color: #818181;\n" +
                "                                padding-bottom: 20px;\n" +
                "                              \">\n" +
                "                                                            <tbody>\n" +
                "                                                                <tr>\n" +
                "                                                                    <td>\n" +
                "                                                                        <span>欢迎订阅《大逼哥独家报道》！</span><br />\n" +
                "                                                                        <span>\n" +
                "                                                                            今后我们将为您带来生活的最新动态、幕后花絮、独家情报，以及其他更多内容。\n" +
                "                                                                        </span><br />\n" +
                "                                                                        <span>请点击按钮，确认订阅。</span>\n" +
                "                                                                        <button\n" +
                "                                                                            style=\"background-color: skyblue;\">订阅</button>\n" +
                "                                                                    </td>\n" +
                "                                                                </tr>\n" +
                "                                                            </tbody>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </tbody>\n" +
                "                                        </table>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "        <img style=\"width: 100%;\" src=\"cid:img01\" alt=\"\">\n" +
                "        <img style=\"width: 100%;\" src=\"cid:img02\" alt=\"\">\n" +
                "        <img style=\"width: 100%;\" src=\"cid:img03\" alt=\"\">\n" +
                "    </div>";

        //"<h2>Hi~</h2><p>第一封 Springboot HTML 图片邮件</p><br/><img src=\"cid:img01\" /><img src=\"cid:img02\" />";


        String imgPath1 = "D:\\Demo2\\1.jpg";
        String imgPath2 = "D:\\Demo2\\2.jpg";
        String imgPath3 = "D:\\Demo2\\3.jpg";
        Map<String, String> imgMap = new HashMap<>();
        imgMap.put("img01", imgPath1);
        imgMap.put("img02", imgPath2);
        imgMap.put("img03", imgPath3);
        emailSend.sendImgMail(to, subject, content, imgMap);
    }


    @Test
    public void sendVideoImgTest() throws Exception {
        String to = "437759286@qq.com";
        String subject = "大B哥的爱";
        String content = "   <div id=\"app\" style=\"max-width: 600px; font-family: “Microsoft YaHei” !important ;\">\n" +
                "                <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"font-family: '微软雅黑'\">\n" +
                "            <tbody>\n" +
                "                <tr>\n" +
                "                    <td>\n" +
                "                        <table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\">\n" +
                "                            <tbody>\n" +
                "                                <tr>\n" +
                "                                    <td>\n" +
                "                                        <div class=\"header\" style=\"\n" +
                "                        height: 50px;\n" +
                "                        text-align: right;\n" +
                "                        width: 95%;\n" +
                "                        margin: 0 auto;\n" +
                "                      \"><img src=\"\"></img></div>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                                <tr>\n" +
                "                                    <td>\n" +
                "                                        <table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\"\n" +
                "                                            style=\"width: 90%; margin: 0 auto\">\n" +
                "                                            <tbody>\n" +
                "                                                <tr>\n" +
                "                                                    <td>\n" +
                "                                                        <span style=\"\n" +
                "                                font-size: 14px;\n" +
                "                                color: #333;\n" +
                "                                font-weight: bold;\n" +
                "                                margin-top: 20px;\n" +
                "                              \">\n" +
                "                                                            我丢哦，老班长，<br />happy,\n" +
                "                                                        </span>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                                <tr>\n" +
                "                                                    <td>\n" +
                "                                                        <table width=\"100%\" border=\"0\" cellpadding=\"5\" cellspacing=\"0\"\n" +
                "                                                            style=\"\n" +
                "                                font-size: 12px;\n" +
                "                                color: #818181;\n" +
                "                                padding-bottom: 20px;\n" +
                "                              \">\n" +
                "                                                            <tbody>\n" +
                "                                                                <tr>\n" +
                "                                                                    <td>\n" +
                "                                                                        <span>欢迎订阅《大逼哥独家报道》！</span><br />\n" +
                "                                                                        <span>\n" +
                "                                                                           今天带来的精彩内容都在附件当中,爽的。\n" +
                "                                                                        </span><br />\n" +
                "                                                                        <span>请点击附件查看视频。</span>\n" +
                "                                                                    </td>\n" +
                "                                                                </tr>\n" +
                "                                                            </tbody>\n" +
                "                                                        </table>\n" +
                "                                                    </td>\n" +
                "                                                </tr>\n" +
                "                                            </tbody>\n" +
                "                                        </table>\n" +
                "                                    </td>\n" +
                "                                </tr>\n" +
                "                            </tbody>\n" +
                "                        </table>\n" +
                "                    </td>\n" +
                "                </tr>\n" +
                "            </tbody>\n" +
                "        </table>\n" +
                "        <video style=\"width: 100%;\" autoplay=\"true\" src=\"cid:sucks\"></video>\n" +
                "    </div>";

        String imgPath1 = "D:\\Demo2\\relax.mp4";

        Map<String, String> imgMap = new HashMap<>();
        imgMap.put("sucks", imgPath1);

        emailSend.sendImgMail(to, subject, content, imgMap);
    }
}
