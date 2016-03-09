package com.lnu.sc.controller;

import com.lnu.sc.config.RestConstants;
import com.lnu.sc.entities.Artifact;
import com.lnu.sc.service.ArtifactService;
import java.io.File;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArtifactController {
    
        
	ArtifactService artifactService = new ArtifactService();
        
        @RequestMapping(value = RestConstants.ARTIFACT, method = RequestMethod.GET)
        public List<Artifact> getAllArtifacts() {
            //artifactService.deleteArtifact("Koala.jpg");
            return artifactService.getAllArtifact();
	}
        
        @RequestMapping(value = RestConstants.ARTIFACT_NAME, method = RequestMethod.GET)
        public File getArtifact(@PathVariable(value = "ArtifactName") String name) {
            return artifactService.getArtifactView(name);
	}
        
        @RequestMapping(value = RestConstants.ARTIFACT_NAME, method = RequestMethod.DELETE)
        public Artifact deleteArtifact(@PathVariable(value = "ArtifactName") String name) {
            return artifactService.deleteArtifact(name);
	}
        
        
}
