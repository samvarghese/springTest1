package com.springboot.study.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadService {
	
	@Autowired
	private Environment env;
	
	public HashMap<String, String> storeFile(MultipartFile file, String applicationNumber, String documentName){

		HashMap<String, String> returnValue = new HashMap<String, String>();

		try{
			
			applicationNumber = (String) applicationNumber.replace("/","-");
			String baseUploadPath = env.getProperty("file.upload.base.path");
			String extension = file.getOriginalFilename().split("\\.")[1];
			String uniqueID = UUID.randomUUID().toString();
			String targetFileName = uniqueID + "." + extension;
			String targetDirectory = baseUploadPath + "/" + applicationNumber;
			String completeUploadPath = targetDirectory + "/" + targetFileName;
			
			if(! new File(targetDirectory).exists()){
				
				new File(targetDirectory).mkdirs();
			}
			
//			Files.createDirectories(Paths.get(targetDirectory));
			
			File dest = new File(completeUploadPath);
            file.transferTo(dest);
			
//			byte[] fileData = file.getBytes();
//			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("")));
//          stream.write(fileData);
//          stream.close();
            
            returnValue.put("fileLocator", applicationNumber + "/" + uniqueID);
            returnValue.put("status", "Ok");
            returnValue.put("message", "File Successfully Uploaded");

		}catch (Exception e) {

			returnValue.put("fileLocator", "");
            returnValue.put("status", "Not Ok");
            returnValue.put("message", e.toString());
		}

		return returnValue;
	}
}
