package com.lnu.sc.data;

import com.lnu.sc.entities.Artifact;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Bang
 *
 */
public class RepositoryData {
        private static List<Artifact> artifacts = new ArrayList<Artifact>();
	
	public static List<Artifact> getArtifact(){
		return artifacts;
		
	}
}
