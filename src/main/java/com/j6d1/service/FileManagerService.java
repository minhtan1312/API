package com.j6d1.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class FileManagerService {
	 @Autowired
	 ServletContext app;
	
	 //lấy tên các forder (tên công ty) 
	 public List<String> listNameFoder() {
			List<String> fodernames = new ArrayList<String>();
			File dir = Paths.get(app.getRealPath("/")).toFile();
			if (dir.exists()) {
				File[] files = dir.listFiles();
				for (File file : files) {
					fodernames.add(file.getName());
				}
			}
			return fodernames;
	} 
	 
	 //lấy các tên file json có trong forder công ty
	 public List<String> listNameFileJson(String folder) {
		List<String> filenames = new ArrayList<String>();
		File dir = Paths.get(app.getRealPath("/"+folder)).toFile();
		if (dir.exists()) {
			File[] files = dir.listFiles();
			for (File file : files) {
				filenames.add(file.getName());
			}
		}
		return filenames;
	}	
	
	//Lấy tất cả dữ liệu json
	public List<Map<String,Object>> getJson() throws IOException{
		String filePath = new File("").getAbsolutePath();
		
		List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
    	List<String> listFNameFoders = listNameFoder();
    	
    	for(String listFNameFoder : listFNameFoders) {
    		List<String> listForders = listNameFileJson(listFNameFoder);
        	for(int i = 0; i < listForders.size(); i++) {
        		String path = filePath+"/src/main/webapp/"+listFNameFoder+"/"+listForders.get(i);
        		ObjectMapper mapper = new ObjectMapper();
        		Map<String,Object> data= mapper.readValue(new File(path),Map.class);    
        		data.put("company_name", listFNameFoder);
        		datas.add(data);
        	}   
    	}
    	
   		return datas;
    }
	
	//cắt chuỗi ngày
	public String getDate(String DateTime) {
		String[] splits = DateTime.split(" ");
		return splits[0];
	}
}
