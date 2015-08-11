package com.ocean.test.validateCode;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.github.cage.Cage;
import com.github.cage.GCage;

/**
 *
 * @Description :
 * @author : ocean
 * @date : 2014-5-13 上午10:32:49
 * @email : zhangjunfang0505@163.com
 * @Copyright : newcapec zhengzhou
 */
public class QuickStart {
  public static void main(String[] args) throws IOException {
    Cage cage = new GCage();
    OutputStream os = new FileOutputStream("captcha.jpg", false);
    try {
      cage.draw(cage.getTokenGenerator().next(), os);
    } finally {
      os.close();
    }
  }
}