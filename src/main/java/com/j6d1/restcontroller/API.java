package com.j6d1.restcontroller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.j6d1.service.FileManagerService;

@CrossOrigin("*")
@RestController
public class API {
	@Autowired
	FileManagerService fileService;

	//tao rest API để lấy dữ liệu
	@GetMapping("/get_json")
    public List<Map<String,Object>> getJsonBy(@RequestParam("company_name") Optional<String> companyName,@RequestParam("date_download") Optional<String> dateDownload) throws IOException{
		List<Map<String,Object>> model = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> datas = fileService.getJson();
		
		for(Map<String,Object> data : datas ) {
			if(data.get("company_name").equals(companyName.get()) && fileService.getDate(data.get("date_download").toString()).equals(dateDownload.get())) {
				model.add(data);
			}
			else if("".equals(companyName.get()) && fileService.getDate(data.get("date_download").toString()).equals(dateDownload.get())) {
				model.add(data);
			}
			else if(data.get("company_name").equals(companyName.get()) && "".equals(dateDownload.get())) {
				model.add(data);
			}
			else if("".equals(companyName.get()) && fileService.getDate(data.get("date_download").toString()).equals(dateDownload.get())){
				model.add(data);
			}
		}
   		return model;
    }	
	
	//link test: http://localhost:8080/get_json?company_name=news_Binance&date_download=2021-10-18
}
