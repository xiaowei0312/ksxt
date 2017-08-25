package com.sxsram.ssm.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sxsram.ssm.service.ExcelStudentService;
import com.sxsram.ssm.util.ImportExcelUtil;
import com.sxsram.ssm.util.JsonResult;

@Controller()
@RequestMapping(value = "/excel", method = { RequestMethod.GET, RequestMethod.POST })
public class ExcelStuController {
	@Autowired
	private ExcelStudentService studentService;

	private String uploadImg(MultipartFile img, String path) throws IllegalStateException, IOException {
		String orginalFilename = img.getOriginalFilename();
		String filename = UUID.randomUUID() + orginalFilename.substring(orginalFilename.lastIndexOf('.'));
		File file = new File(path);
		if (!file.exists())
			file.mkdirs();
		File newFile = new File(path + filename);
		img.transferTo(newFile);
		return filename;
	}

	@RequestMapping(value = "/importStusAjax", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String importStusAjax(HttpSession session, Model model, MultipartFile uploadFile) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
		JsonResult jsonResult = new JsonResult("0", "0");

		InputStream in = null;
		List<List<Object>> listob = null;
		if (uploadFile.isEmpty()) {
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = "文件不存在";
			return gson.toJson(jsonResult);
		}
		try {
			in = uploadFile.getInputStream();
			listob = new ImportExcelUtil().getBankListByExcel(in, uploadFile.getOriginalFilename());
			in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = e.getMessage();
			return gson.toJson(jsonResult);
		}

		try {
			studentService.batchAddStudent(listob);
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.logicCode = "-1";
			jsonResult.resultMsg = e.getMessage();
			return gson.toJson(jsonResult);
		}
		return gson.toJson(jsonResult);
	}
}