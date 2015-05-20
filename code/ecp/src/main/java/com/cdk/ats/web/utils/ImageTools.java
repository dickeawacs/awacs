package com.cdk.ats.web.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

/**
 * 
 * 描述： 获取图片的宽高
 * 
 * @author dingkai 2013-12-22
 * 
 */
public class ImageTools {

	private static Logger logger = Logger.getLogger(ImageTools.class);

	public static boolean imagesType(String type) {
		return type.equalsIgnoreCase(".jpg") || type.equalsIgnoreCase(".gif")
				|| type.equalsIgnoreCase(".bmp")
				|| type.equalsIgnoreCase(".png");

	}

	/***
	 * 
	 * 描述： 获取文档的宽度与高度
	 * 
	 * @createBy dingkai
	 * @createDate 2013-12-22
	 * @lastUpdate 2013-12-22
	 * @param input
	 * @return int[]｛宽，高｝
	 * @throws IOException
	 */
	public static int[] getImgWidthAndHeight(InputStream input)
			throws IOException {
		int[] style = new int[] { -1, -1 };
		BufferedImage image = ImageIO.read(input);
		style[0] = image.getWidth();
		style[1] = image.getHeight();
		return style;

	}

	/***
	 * 
	 * 描述： 获取文档的宽度与高度
	 * 
	 * @createBy dingkai
	 * @createDate 2013-12-22
	 * @lastUpdate 2013-12-22
	 * @param input
	 * @return int[]｛宽，高｝
	 * @throws IOException
	 */
	public static int[] getImgWidthAndHeight(File file) {

		int[] style = new int[] { -1, -1 };
		BufferedImage image = null;
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			image = ImageIO.read(is);
			style[0] = image.getWidth();
			style[1] = image.getHeight();
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		} finally {
			if (image != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.error(e.getMessage(),e);
					is = null;
				}
			}

		}
		return style;

	}

	public static void main(String[] args) {
		File file = new File(ImageTools.class.getResource("hello.gif")
				.getPath().replaceAll("%20", " "));
		System.out.println(file.exists());
		System.out.println(file.getPath());
		if (file.exists()) {
			int[] style = ImageTools.getImgWidthAndHeight(file);
			System.out.println("width:" + style[0] + ",height:" + style[1]);
		}
	}

	/**
	 * 获取图片宽度
	 * 
	 * @param file
	 *            图片文件
	 * @return 宽度
	 */
	public static int getImgWidth(File file) {
		InputStream is = null;
		BufferedImage src = null;
		int ret = -1;
		try {
			is = new FileInputStream(file);
			src = javax.imageio.ImageIO.read(is);
			ret = src.getWidth(null); // 得到源图宽
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 获取图片高度
	 * 
	 * @param file
	 *            图片文件
	 * @return 高度
	 */
	public static int getImgHeight(File file) {
		InputStream is = null;
		BufferedImage src = null;
		int ret = -1;
		try {
			is = new FileInputStream(file);
			src = javax.imageio.ImageIO.read(is);
			ret = src.getHeight(null); // 得到源图高
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

}
