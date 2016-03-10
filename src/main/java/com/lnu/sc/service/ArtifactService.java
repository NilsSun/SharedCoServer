package com.lnu.sc.service;

import static com.lnu.sc.Application.artifacts;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import com.lnu.sc.entities.Artifact;
import com.lnu.sc.config.RestConstants;
import org.springframework.stereotype.Component;


@Component
public class ArtifactService {
	
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
	 * returns a list of all artifacts
	 * 
	 * @return ArrayList<Artifact>
	 */
	public List<Artifact> getAllArtifact() {
            return artifacts;

	}

	/**
	 * returns an artifact according to its ID
	 * 
	 * @param name
	 * @return Artifact
	 */
	public Artifact getArtifact(String name) {
		
		
		return search(name);

	}

	public File getArtifactView(String name) {
		return search(name).getContent();		

	}

	/**
	 * adds an artifact
	 * 
	 * @param file
	 * @return added Artifact
	 */
	public Artifact addArtifact(File f) {

		File _fdest = new File(RestConstants.PATH_ONE + f.getName()); 
                /*
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
		 * now we have the Artifact file and its copy so we will create an Image
		 * object with it
		 */
		Artifact art = new Artifact(artifacts.size() + 1, f);
		artifacts.add(art);
		return art;
	}

	/**
	 * update ; replace and existing artifact with its replacement
	 * 
	 * @param file,name
	 * @return updated artifact
	 */
	public Artifact updateArtifact(File file, String name) {
		search(name).setContent(file);
		return search(name);
	}

	/**
	 * deletes and artifact where the id is given
	 * 
	 * @param name
	 * @return
	 */
	public void deleteArtifact(String name) {
            Artifact searchResult = search(name);
            Integer id = searchResult.getId();
            searchResult.getContent().delete();
            artifacts.remove(searchResult);
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
