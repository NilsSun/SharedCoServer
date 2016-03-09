package com.lnu.sc.data;

import com.lnu.sc.entities.Artifact;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Bang
 *
 */
public class RepositoryData {
    	private static Map<Integer, Artifact> artifacts = new HashMap<Integer, Artifact>();
	
	public static Map<Integer, Artifact> getArtifact(){
		return artifacts;
		
	}
}
