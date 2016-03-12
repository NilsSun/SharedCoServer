package com.lnu.sc.data;

import com.lnu.sc.entities.Artifact;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * 
 * @author Bang
 *
 */
public class RepositoryData {
        private static List<Artifact> artifacts = new ArrayList<Artifact>();
        private static JSONObject currentCollections = new JSONObject();
        private static JSONArray collections = new JSONArray();
        private static JSONArray collectionRelations = new JSONArray();
	
	public static List<Artifact> getArtifacts(){
		return artifacts;
		
	}
        
        public static JSONObject getCurrentCollections() {
                return currentCollections;
        }
        
        public static JSONArray getCollections(){
                return collections;
        }
        
        public static JSONArray getCollectionsRelations() {
                return collectionRelations;
        }
        
        
}
