package cn.com.guoqing.vans.common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 
 * @Description  图片验证码生成工具类
 *
 * @author Guoqing
 * @Date 2018年1月17日
 */
public final class VerifyCodeUtil {
	
	public static final int height = 25;	//图片宽度
	public static final int width = 90;		//图片高度
	public static final int font_size = 24;	//字体大小
	public static final String font_family = "Times New Roman"; 	//字体
	public static final int code_length = 4;	//验证码长度 
	
	/**
	 * 生成图片中的验证码数字
	 * @param length
	 * @return
	 */
	public static String createText(){
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < code_length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
	}
	
	/**
	 * create the image with the text
	 * @param text
	 * @return
	 */
	public static BufferedImage createImage(String text){
		//创建图像
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		//获取图形上下文
		Graphics graphics = image.getGraphics();
		//设置背景色
		graphics.setColor(getRandColor(200, 250));
		graphics.fillRect(0, 0, width, height);
		//设置字体
		graphics.setFont(new Font(font_family, Font.PLAIN, font_size));
		//产生随机干扰线条
		graphics.setColor(getRandColor(160, 200));
		Random random = new Random();
		for (int i = 0; i < 155; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			graphics.drawLine(x, y, x + xl, y + yl);
		}
		for( int j = 0; j < text.length(); j ++ ){
			String rand = text.substring(j, j+1);
			//将认证码显示到图像中
			graphics.setColor(new Color(20+random.nextInt(110), 20+random.nextInt(110), 20+random.nextInt(110)));
			//调整图片内容的位置
			graphics.drawString(rand, 21*j+6, 20);
		}
		//图像生成
		graphics.dispose();
		return image;
	}
	
	/**
	 * 给定返回获得随机颜色
	 * @param fc
	 * @param bc
	 * @return
	 */
	public static Color getRandColor(int fc, int bc){
		Random random = new Random();
		if (fc > 255){
			fc = 255;
		}
		if (bc > 255){
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

}
