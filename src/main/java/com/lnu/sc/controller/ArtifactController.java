package com.lnu.sc.controller;

import com.lnu.sc.config.RestConstants;
import com.lnu.sc.entities.Artifact;
import com.lnu.sc.service.ArtifactService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ArtifactController {
    
        
	ArtifactService artifactService = new ArtifactService();
        
        @RequestMapping(value = RestConstants.ARTIFACT, method = RequestMethod.GET)
        public List<Artifact> getAllArtifacts(HttpServletResponse response) {
            response.addHeader("Access-Control-Allow-Origin","*");
            return artifactService.getAllArtifact();
	}
        
        @RequestMapping(value = RestConstants.ARTIFACT_NAME, method = RequestMethod.GET)
        public HttpEntity<byte[]> getArtifact(@PathVariable(value = "ArtifactName") String name) throws IOException {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.IMAGE_JPEG);
            httpHeaders.add("Access-Control-Allow-Origin","*");
            Path path = Paths.get(artifactService.getArtifactView(name).toString());
            return new ResponseEntity<byte[]>(Files.readAllBytes(path), httpHeaders, HttpStatus.OK);
	}
        
        @RequestMapping(value = RestConstants.ARTIFACT_DELETE, method = RequestMethod.GET)
        public void deleteArtifact(@PathVariable(value = "ArtifactName") String name, HttpServletResponse response) {
            response.addHeader("Access-Control-Allow-Origin","*");
            artifactService.deleteArtifact(name);
	}
        
        @RequestMapping(value = RestConstants.UPLOAD, method = RequestMethod.POST)
    	public @ResponseBody boolean Upload(@RequestParam(value="file", required=true) MultipartFile file
    			)  {
        	boolean ok = false;
    			//File file = new File("d:\\pics\\0.png");
    			try {
    				InputStream is = file.getInputStream();
    				String fileName = file.getOriginalFilename();
    				ok = artifactService.addArtifact(is, fileName);
    				
    			} catch (IOException e) {
    				String message = "Error while inserting document";
    	            throw new RuntimeException(message, e);
    			}
    			//String reply = "File confirmed. File name is : " + file.getName();//file.getOriginalFilename();

    			return ok;
    	}
        
        @RequestMapping(value = RestConstants.UPDATE, method = RequestMethod.PUT)
        public @ResponseBody boolean Update(@RequestParam(value="file", required=true) MultipartFile file,
        		@PathVariable(value = "ArtifactName") String name) 
    			{
        	
        	boolean ok = false;
        	//File file = new File("d:\\pics\\0.png");
        	
        	try {
				InputStream is = file.getInputStream();
				//String fileName = file.getOriginalFilename();
				ok = artifactService.updateArtifact(is, name);
				
			} catch (IOException e) {
				String message = "Error while inserting document";
	            throw new RuntimeException(message, e);
			}
        return ok;	
        }
}
