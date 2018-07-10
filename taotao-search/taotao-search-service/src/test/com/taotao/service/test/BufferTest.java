package com.taotao.service.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蒋志鹏 on 2018/7/9.
 */
public class BufferTest {


    @Test
    public void show(){
        List<Long> list=new ArrayList<Long>();
        list.add(1l);
        list.add(5l);
        list.add(16l);

        StringBuffer buffer=new StringBuffer();
        for(Long id :list){
            buffer.append(String.valueOf(id));
        }
        System.out.println(buffer);
    }
}
