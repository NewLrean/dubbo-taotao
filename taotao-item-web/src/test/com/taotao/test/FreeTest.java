package com.taotao.test;

import com.taotao.pojo.TbItem;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 蒋志鹏 on 2018/7/12.
 */
public class FreeTest {


    @Test
    public void demo1() throws IOException, TemplateException {
        Configuration configuration=new Configuration(Configuration.getVersion());
        configuration.setDefaultEncoding("utf-8");
        configuration.setDirectoryForTemplateLoading(new File("D:\\IDEASave\\dubbo-taotao\\taotao-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
        Template template=configuration.getTemplate("test.ftl");
        TbItem tbItem=new TbItem();
        tbItem.setId(15l);
        tbItem.setTitle("斗破苍穹");
        tbItem.setCreated(new Date());
        tbItem.setStatus((byte)1);
        Map map=new HashMap();
        map.put("tbitem",tbItem);
        template.process(map,new FileWriter(new File("D:\\15321.html")));
    }

    @Test
    public void demo2(){
        System.out.println();
    }
}
