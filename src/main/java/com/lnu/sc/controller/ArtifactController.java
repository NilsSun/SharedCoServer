package com.lnu.sc.controller;

import com.lnu.sc.config.RestConstants;
import com.lnu.sc.entities.Artifact;
import com.lnu.sc.file.FileIO;
import com.lnu.sc.service.ArtifactDataResService;
import com.lnu.sc.util.Info;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Service
@RestController
public class ArtifactController {
    
        
	ArtifactDataResService artifactDataResService = new ArtifactDataResService();
        
        @Autowired
	private  JavaMailSender  javaMailService;
        
        private final Info info = new Info();
        
        @RequestMapping(value = RestConstants.ARTIFACTS, method = RequestMethod.GET)
        public List<Artifact> getAllArtifacts(HttpServletResponse response) {
            response.addHeader("Access-Control-Allow-Origin","*");
            return artifactDataResService.getAllArtifact();
	}
        
        @RequestMapping(value = RestConstants.ARTIFACT_NAME, method = RequestMethod.GET)
        public HttpEntity<byte[]> getArtifact(@PathVariable(value = "ArtifactName") String name) throws IOException {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.IMAGE_JPEG);
            httpHeaders.add("Access-Control-Allow-Origin","*");
            Path path = Paths.get(artifactDataResService.getArtifactView(name).toString());
            
            FileIO fio = new FileIO();
            String filename = "E:\\Study_Advanced\\workspace\\temp\\test-many.txt";
            JSONArray jsonArray = fio.readFile(filename);

            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                System.out.println("id: " + jsonObject.get("id") + "----" + jsonObject.toJSONString());
            }
            
            String outputname = RestConstants.PATH_TWO + "/output.txt";
            fio.writeFile(outputname, jsonArray);
            
            
//            Thread task = new Thread()
//            {
//                @Override
//                public void run()
//                {
//                    // Autowired, which has to in controller level
//                    SimpleMailMessage mailMessage=new SimpleMailMessage();
//                    mailMessage.setTo("xuebo.sun@gmail.com");
//                    mailMessage.setSubject("shared collection test");
//                    mailMessage.setText("shared collection test");
//                    javaMailService.send(mailMessage);
//                }
//            };
//            task.start();
            
            return new ResponseEntity<byte[]>(Files.readAllBytes(path), httpHeaders, HttpStatus.OK);
	}
        
        @RequestMapping(value = RestConstants.ARTIFACT_DELETE, method = RequestMethod.GET)
        public void deleteArtifact(@PathVariable(value = "ArtifactName") String name, HttpServletResponse response) {
            response.addHeader("Access-Control-Allow-Origin","*");
            artifactDataResService.deleteArtifact(name);
	}

        @RequestMapping(value = RestConstants.ARTIFACT, method = RequestMethod.POST)
    	public @ResponseBody Info Upload(@RequestParam(value="file", required=true) MultipartFile file, HttpServletResponse response
        )  {
            boolean uploadok = false;
            info.setErrorInfo("No errors");
            try {
                    InputStream is = file.getInputStream(); //new FileInputStream(file);//
                    String fileName = file.getOriginalFilename(); //file.getName();//
                    uploadok = artifactDataResService.addArtifact(is, fileName);
                    if (!uploadok) {
                        response.setStatus(400);
                        info.setErrorInfo("Error: file '" + fileName + "' exist!");
                        System.out.println("info: " + info.getErrorInfo());
                    }
                    response.addHeader("Access-Control-Allow-Origin","*");
            } catch (IOException e) {
                    String message = "Error while uploading file";
                    throw new RuntimeException(message, e);
            }
            //String reply = "File confirmed. File name is : " + file.getName();//file.getOriginalFilename();
            return info;
    	}
        
        @RequestMapping(value = RestConstants.ARTIFACT_NAME, method = RequestMethod.POST)
        public @ResponseBody Info Update(@RequestParam(value="file", required=true) MultipartFile file,
        		@PathVariable(value = "ArtifactName") String name, HttpServletResponse response) 
    			{
        	boolean updateok = false;
        	//File file = new File("d:\\pics\\0.png");
                info.setErrorInfo("No errors");
        	
        	try {
			InputStream is = file.getInputStream();
			//String fileName = file.getOriginalFilename();
			updateok = artifactDataResService.updateArtifact(is, name);
                        if (!updateok) {
                                response.setStatus(400);
                                info.setErrorInfo("Error: file '" + name + "' exist!");
                                System.out.println("info: " + info.getErrorInfo());                            
                        } 
                        
			response.addHeader("Access-Control-Allow-Origin","*");
		} catch (IOException e) {
			String message = "Error while inserting document";
	                throw new RuntimeException(message, e);
		}
                return info;	
        }
        
        @RequestMapping(value = RestConstants.COLLECTION, method = RequestMethod.POST)
    	public @ResponseBody Info CreateCollection(@RequestParam(value="name", required=true) String collectionName, 
            HttpServletResponse response)  {
            
            info.setErrorInfo("No errors");
            boolean createresult = artifactDataResService.createCollection(collectionName);

            //sychronize later

            return info;
        }        

        
        //sychronize controllers
        
        
        
}
