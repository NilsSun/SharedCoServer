package com.lnu.sc.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;
import com.lnu.sc.config.RestConstants;
import com.lnu.sc.entities.Artifact;
import com.lnu.sc.entities.Collection;

import static com.lnu.sc.Application.collections;

@Component
public class CollectionService {
	private int sequence = 1;
	
	public Collection search(String key){
		Collection c = new Collection();
		for(int i = 0; i < collections.size(); i++){
			if(key.equals(collections.get(i).getKey())){
				c =collections.get(i);
				break;
			}
			else{
				continue;
			}
		}		
		return c;
	}
	
	public List<Collection> getAllCollection(){
		return collections;
	}
	
	public Collection getCollection(String key){
		return search (key);
	}
	
	public Collection addCollection(int sequence,String name, Collection root){
		//boolean ok = false;
		
		Collection c = new Collection(this.sequence, name, root);
		collections.add(c);
		if(root != null){
			root.addSub(c);
		}
		String sep = "\"";
		String str;
		if(c.getRoot() != null){
			str = "{" +
					sep +root.getKey() + sep + " : " + sep + c.getKey()+ sep  + ""
					+"}";//relationship
		}
		else{
			str = c.getKey();
		}
			
		try {
			File file = new File("relationship.json");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			BufferedReader br = new BufferedReader(new FileReader("relationship.json"));
			boolean mark = true;
			String currentLine;
			while((currentLine = br.readLine()) != null){
				if(currentLine == str){//check if the relationship already wrote in the file
					mark = false;
					break;
				}
			}
			br.close();
			if(mark){//if the relationship not wrote, create new relation in the file
				FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
				fw.write("\n"+str);
				fw.close();
			}

			//System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			File file = new File("collections.json");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			BufferedReader br = new BufferedReader(new FileReader("collections.json"));
			str = c.toStr();
			
			boolean mark = true;
			String currentLine;
			while((currentLine = br.readLine()) != null){
				if(currentLine == str){//check if the relationship already wrote in the file
					mark = false;
					break;
				}
			}
			br.close();
			if(mark){//if the relationship not wrote, create new relation in the file
				FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
				fw.write("\n"+str);
				fw.close();
			}

			//System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}

		this.sequence++;
		return c;
	}
	
	public void load(){
		try {
			File file = new File(RestConstants.PATH_TWO);
			BufferedReader br = new BufferedReader(new FileReader(RestConstants.PATH_TWO));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		
	}
	
	
}
