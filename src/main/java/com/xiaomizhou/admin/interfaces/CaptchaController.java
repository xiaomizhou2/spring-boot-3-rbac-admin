package com.xiaomizhou.admin.interfaces;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.CircleCaptcha;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import java.io.IOException;

/**
 * @author zhangyaxi
 * @email 521jx123@gmail.com
 * @date 2023/12/17
 */
@Controller
public class CaptchaController {

    @GetMapping("/captcha.jpg")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(80, 40, 4, 10);
        request.getSession().setAttribute(request.getSession().getId(), captcha.getCode());
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream out = response.getOutputStream();
        captcha.write(out);
        IOUtils.closeQuietly(out);
    }

}
