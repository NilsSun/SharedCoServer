package com.lnu.sc.service;

import static com.lnu.sc.Application.artifacts;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Calendar;
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
	 * @param InputStream, FileName
	 * @return boolean
	 */
	public boolean addArtifact(InputStream is, String fileName) {
		boolean ok = false;
		String filePath = RestConstants.PATH_ONE+"/"+fileName;
		File uploadDir = new File(RestConstants.PATH_ONE);
		File uploadedFile = new File(filePath);
		try {
			if (!uploadDir.exists()) {
				uploadDir.mkdir();
			}
			if (!uploadedFile.exists()) {
				uploadedFile.createNewFile();
			} else {
				return ok;
			}
			OutputStream os = new FileOutputStream(uploadedFile);
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = is.read(bytes)) != -1) {
				os.write(bytes);
				bytes = new byte[1024];
			}
			os.flush();
			os.close();
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (uploadedFile.exists() && uploadedFile.length() > 0) {
			ok = true;
		}

		Artifact art = new Artifact(artifacts.size() + 1, uploadedFile);
		artifacts.add(art);
		return ok;
	}
	
	/**
	 * updates an artifact
	 * 
	 * @param InputStream, FileName
	 * @return boolean
	 */
	public boolean updateArtifact(InputStream is, String fileName) {
		boolean ok = false;
		String filePath = RestConstants.PATH_ONE+"/"+fileName;
		System.out.println("================================节点1=========================");
		File updatedDir = new File(RestConstants.PATH_ONE);
		File updatedFile = new File(filePath);
		try {
			if (!updatedDir.exists()) {
				updatedDir.mkdir();
			}
			if (!updatedFile.exists()) {
				System.err.println("No such file!!");
				return ok;
			} else {
				updatedFile.delete();
				updatedFile.createNewFile();
			}
			OutputStream os = new FileOutputStream(updatedFile);
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = is.read(bytes)) != -1) {
				os.write(bytes);
				bytes = new byte[1024];
			}
			os.flush();
			os.close();
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (updatedFile.exists() && updatedFile.length() > 0) {
			ok = true;
		}

		search(fileName).setContent(updatedFile);
		return ok;
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

}
