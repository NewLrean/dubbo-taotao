package com.taotao.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.service.PictrueService;
import com.taotao.utils.FtpClientUtils;
import com.taotao.utils.IdGenrtor;


@Service
public class PictrueServiceImpl implements PictrueService {

	@Value("${FTP_ADDRESS}")
	private String ADDRESS;

	@Value("${FTP_PROT}")
	private String PROT;

	@Value("${FTP_USERNAME}")
	private String USERNAME;

	@Value("${FTP_PASSWORD}")
	private String PASSWORD;

	@Value("${FTP_BASEPATH}")
	private String BASEPATH;

	@Value("${FTP_IMGPATH}")
	private String IMGPATH;
	@Override
	public Map updatePictrue(String fileName,byte[] buffer) {
		// TODO Auto-generated method stub

		InputStream inputStream =new ByteArrayInputStream(buffer);
		Date date=new Date();
		Map map=new HashMap<String,Object>();
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
		String format = dateFormat.format(date);
		
		String guid = IdGenrtor.getGUID();
		
		
		try {
			boolean setByFtp = FtpClientUtils.setByFtp(ADDRESS, Integer.valueOf(PROT), USERNAME, PASSWORD, BASEPATH, format, guid+fileName, inputStream);
			
			if(!setByFtp){
				map.put("error", 1);
				map.put("message", "文件上传失败");
			}else{
			map.put("error", 0);
			map.put("url",IMGPATH+"/"+format+"/"+guid+fileName);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("error", 1);
			map.put("message", "文件上传失败");
		}
		return map;
	}

}
