package cn.com.guoqing.vans.admin.web.controller.system;

import cn.com.guoqing.vans.common.redis.RedisRepository;
import cn.com.guoqing.vans.common.util.VerifyCodeUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 
 * @Description  验证码生成
 *
 * @author Guoqing
 * @Date 2018年1月16日
 */
@Controller
@RequestMapping("/kaptcha")
public class KaptchaController {
    
    @Autowired
    private RedisRepository redisRepository;

    /**
     * 生成验证码
     */
    @RequestMapping("/generate")
    public void index(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        String capText = VerifyCodeUtil.createText();

        // store the text in the session
        //session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        //基于多节点部署的考虑，store the text in the redis
        if( redisRepository.exists("key_rand_"+session.getId()) ){
        	redisRepository.del("key_rand_"+session.getId());
        }
        //存入redis 设置有效时间为300S
        redisRepository.setExpire("key_rand_"+session.getId(), capText, 300);
        
        // create the image with the text
        BufferedImage bi = VerifyCodeUtil.createImage(capText);
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // write the data out
        try {
            ImageIO.write(bi, "jpg", out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            try {
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
