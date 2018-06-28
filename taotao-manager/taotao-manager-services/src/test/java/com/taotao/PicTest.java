package com.taotao;

import com.taotao.utils.FtpClientUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by 蒋志鹏 on 2018/6/26.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations ={"classpath:mybatis/spring-mybatis.xml"} )
public class PicTest {

    @Test
    public void uploadpic(){

        try {
            InputStream inputStream=new FileInputStream(new File("E:\\python图片\\02.jpg"));
            FtpClientUtils.setByFtp("192.168.25.150",21,"ftpuser","123456"
                    ,"/usr/local/nginx/html/images","2018-06-01","02.jpg",inputStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        }
}
