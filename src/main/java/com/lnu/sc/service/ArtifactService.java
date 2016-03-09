package com.lnu.sc.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lnu.sc.entities.Artifact;
import com.lnu.sc.config.RestConstants;
import com.lnu.sc.data.RepositoryData;
import org.springframework.stereotype.Component;


@Component
public class ArtifactService {
	/*
	 * a hashmap that stores all the images with ID as the key in order if we
	 * want to use any type of database for our repository this data structure
	 * will come more handy
	 */
	Map<Integer, Artifact> artifacts = RepositoryData.getArtifact();
	Map<Integer, Artifact> artifacts_backup = RepositoryData.getArtifact();
	

	/**
	 * a constructor method for generating some images manually for testing
	 */
	public ArtifactService() {

		File[] folder1 = new File(RestConstants.PATH_ONE).listFiles();
		int i = 0;
		for (File f : folder1) {
			artifacts.put(i, new Artifact(i, f));
			artifacts_backup.put(i, new Artifact(i, f));
			i++;
		}
		
		//backUp();
	}
	
	
	public Artifact search(String name){
		Artifact a = new Artifact();
		for(int i = 0; i < artifacts.size(); i++){
			if(name.equals(artifacts.get(i).getName())){
				a = artifacts.get(i);
				break;
			}
			else{
				continue;
			}
		}		
		return a;
	}
	
	/**
	 * returns a list of all images
	 * 
	 * @return ArrayList<Image>
	 */
	public List<Artifact> getAllArtifact() {

		return new ArrayList<Artifact>(artifacts.values());

	}

	/**
	 * returns an image according to its ID
	 * 
	 * @param name
	 * @return Image
	 */
	public Artifact getArtifact(String name) {
		
		
		return search(name);

	}

	public File getArtifactView(String name) {
		return search(name).getContent();		

	}

	/**
	 * adds an image
	 * 
	 * @param file
	 * @return added Image
	 */
	public Artifact addArtifact(File f) {

		File _fdest = new File(RestConstants.PATH_ONE + f.getName()); /*
																 * where ever
																 * the source
																 * file is, make
																 * a copy in our
																 * repository
																 */
		try {
			copyFileUsingJava7Files(f, _fdest); // the copy method is called
												// here
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		 * now we have the image file and its copy so we will create an Image
		 * object with it
		 */
		Artifact art = new Artifact(artifacts.size() + 1, f);
		artifacts.put(art.getId(), art);
		//backUp();
		return art;
	}

	/**
	 * update ; replace and existing image with its replacement
	 * 
	 * @param file,name
	 * @return updated image
	 */
	public Artifact updateArtifact(File file, String name) {
		search(name).setContent(file);
		//backUp();
		return search(name);
	}

	/**
	 * deletes and image where the id is given
	 * 
	 * @param name
	 * @return
	 */
	public Artifact deleteArtifact(String name) {
		Integer id = search(name).getId();
                search(name).getContent().delete();
		artifacts_backup.remove(id);
		//backUp();
		return artifacts.remove(id);
	}

	/**
	 * copy File Using Java 7 Files
	 * 
	 * @param source
	 * @param dest
	 * @throws IOException
	 */
	private static void copyFileUsingJava7Files(File source, File dest)
			throws IOException {
		if (dest.exists())
			return;
		Files.copy(source.toPath(), dest.toPath());
	}

	
}
