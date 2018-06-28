package com.taotao.controller;

import java.io.*;
import java.util.Map;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.utils.FtpClientUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.service.PictrueService;

@Controller
public class PictrueController {

	
	@Reference
	PictrueService pictrueService;

	/**
	 * 图片的上传
	 * @param uploadFile 图片本身信息
	 * @return 返回json数据
	 */
	@RequestMapping(value = "/pic/upload",method = RequestMethod.POST)
	@ResponseBody
	public Map uploadPic(MultipartFile uploadFile){

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int n = 0;
		try {
			InputStream input = uploadFile.getInputStream();
			while (-1 != (n = input.read(buffer))) {
				output.write(buffer, 0, n);

			}
			output.close();
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}


		Map updatePictrue = pictrueService.updatePictrue(uploadFile.getOriginalFilename(),output.toByteArray());

		return updatePictrue;

	}
}
