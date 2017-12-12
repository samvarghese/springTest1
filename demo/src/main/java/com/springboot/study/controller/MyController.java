package com.springboot.study.controller;

import java.util.ArrayList;
import java.util.HashMap;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.springboot.study.service.FileUploadService;


@Controller
public class MyController {
	
	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	FileUploadService fileUploadService;
	
	// Returning json Object
	
	// Return json response using HashMap
	@RequestMapping(value = "jsonresponse", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public HashMap<String, Object> jsonResponse(){
		HashMap<String, Object> map = new HashMap<>();
	    map.put("key", "value");
	    map.put("foo", "bar");
	    map.put("aa", "bb");
	    
	    ArrayList<String> list = new ArrayList<String>();
	    list.add("String 1");
	    list.add("String 2");
	    map.put("list", list);
	    return map;
	}
	
	
	// Return JsonResponse using Object node
	@RequestMapping(value = "jsonObjectMapperResponse", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ObjectNode jsonObjectMapperResponse(){
		ObjectNode objectNode = mapper.createObjectNode();
	    objectNode.put("key", "value");
	    objectNode.put("foo", "bar");
	    objectNode.put("number", 42);
	    
	    return objectNode;
	}
	
	
	// Getting 406 Error
	@RequestMapping(value = "jsonObjectResponse", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<JSONObject> jsonObjectResponse(){
		JSONObject objectNode = new JSONObject();
	    objectNode.put("key", "value");
	    objectNode.put("foo", "bar");
	    objectNode.put("number", 42);
	    
	    ArrayList<String> list = new ArrayList<String>();
	    list.add("String 1");
	    list.add("String 2");
	    objectNode.put("list", list);
	    
	    return new ResponseEntity<JSONObject>(objectNode, HttpStatus.OK);
	}
	
	
	// Parsing Incomming Json Object
	
	@RequestMapping(value = "parseInputJson", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public HashMap<String, Object> parseJsonInput(@RequestBody HashMap<String, Object> incommingRequest){
		
		System.out.println(incommingRequest.get("Age").getClass().getName());
		System.out.println(incommingRequest.get("Age"));
		System.out.println(incommingRequest.get("Name").getClass().getName());
		System.out.println(incommingRequest.get("Name"));
		System.out.println(incommingRequest.get("One More").getClass().getName());
		System.out.println(incommingRequest.get("One More"));
        return incommingRequest;
	}
	
	
	
	
//	http://www.mkyong.com/spring-boot/spring-boot-file-upload-example/
//	https://spring.io/guides/gs/uploading-files/
//	https://www.mkyong.com/spring-boot/spring-boot-file-upload-example-ajax-and-rest/
	
	
	// Parsing Incoming File Multipart form
	
	@RequestMapping(value = "fileInput", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public String parseFileInput(@RequestPart("file") MultipartFile file){
		
		try{
			byte[] bytes = file.getBytes();
			System.out.println("The file content is");
			System.out.println(bytes);
		}
		catch(Exception e){
			System.out.println("Unable to read the file");

		}
		
        return "Success";
	}
	
	
	// Parsing Multiple Incomming File Multipart form
	
		@RequestMapping(value = "multiplefileInput", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
		@ResponseBody
		public String parseMultipleFileInput(@RequestPart("file") MultipartFile[] files){
			
			try{
				System.out.println("No of files = " + String.valueOf(files.length));
				
				byte[] bytes;
				for( MultipartFile file: files){
					System.out.println("Inside for loop");
					bytes = file.getBytes();
					System.out.println("The file name is");
					System.out.println(file.getOriginalFilename());
					System.out.println("The file content is");
					System.out.println(bytes);
					
				}
			}
			catch(Exception e){
				System.out.println("Unable to read the file");

			}
			
	        return "Success";
		}
	
//	http://javasampleapproach.com/java-integration/upload-multipartfile-spring-boot
	
	// Parse input file and data
	@RequestMapping(value = "parseFileInputAndData", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public HashMap<String, Object> parseFileInputAndData(MultipartHttpServletRequest request ){
		
		try{
			MultipartFile file;
//			file = request.getFile("file");
			HashMap<String, MultipartFile> files = (HashMap) request.getFileMap();
			
			byte[] bytes;
			for(String key: files.keySet()){
				System.out.println("Inside for loop");
				file = (MultipartFile) files.get(key);
				bytes =  file.getBytes();
				System.out.println("The file name is");
				System.out.println(file.getName());
				System.out.println("The file content is");
				System.out.println(bytes);
				
			}
			
		}
		catch(Exception e){
			System.out.println("Unable to read the file");

		}
		
		HashMap<String, Object> returnvalue = new HashMap<String, Object>();
		
		// Parameters other than file can be read from the request object as follows.
		String Name = request.getParameter("Name");
		returnvalue.put("name", Name);
		System.out.println(Name);
		
		String Age = request.getParameter("Age");
		returnvalue.put("age", Age);
		System.out.println(Age);
		
		String OneMore = request.getParameter("ONe More");
		returnvalue.put("One More", OneMore);
		System.out.println(OneMore);
		
        return returnvalue;
	}
	
	// Upload file to specified location
	@RequestMapping(value = "uploadDocument", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public HashMap<String, String> uploadDocument(MultipartHttpServletRequest request ){
		
		HashMap<String, String> returnValue = new HashMap<String, String>();
		
		try{

			MultipartFile file = request.getFile("file");
			String applicationNumber = request.getParameter("applicationNumber");
			String documentName = request.getParameter("documentName");
			
			returnValue = fileUploadService.storeFile(file, applicationNumber, documentName);
			
		}
		catch(Exception e){

			returnValue.put("fileLocator", "");
            returnValue.put("status", "Not Ok");
            returnValue.put("message", e.toString());
		}

		return returnValue;
	}
	
}
