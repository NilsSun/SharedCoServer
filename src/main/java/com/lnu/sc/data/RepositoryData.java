package com.lnu.sc.data;

import com.lnu.sc.entities.Artifact;
import com.lnu.sc.entities.Collection;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Bang
 *
 */
public class RepositoryData {
        private static List<Artifact> artifacts = new ArrayList<Artifact>();
        private static List<Collection> collections  = new ArrayList<Collection>();
        
	public static List<Artifact> getArtifact(){
		return artifacts;
		
	}
	
	public static List<Collection> getCollection(){
		return collections;
		
	}
}
