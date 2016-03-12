package com.lnu.sc.entities;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Collection {
	
	private int sequence;
	private String key;
	private String name;
	private ArrayList<Collection> sub = new ArrayList<Collection>();
	private Collection  root;
	private ArrayList<Artifact> contains = new ArrayList<Artifact>();
	public boolean toBeDeleted;
	
	public Collection(){
		
	}
	
	public Collection(int sequence,String name, Collection root){
		super();
		this.sequence = sequence;
		this.name = name;
		this.key = Long.toString(System.currentTimeMillis(), 36).toString();
		this.sub = null;
		this.root = root;
		this.contains = null;
		this.toBeDeleted = false;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getSequence(){
		return this.sequence;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getKey(){
		return this.key;
	}
	
	public void setKey(String key){
		this.key = key;
	}
	
	public void addSub(Collection collection){
		this.sub.add(collection);
	}
	
	public Collection getOneSub(int i){
		return this.sub.get(i);	
	}
	
	public ArrayList<Collection> getAllSub(){
		return this.sub;
	}
	
	public ArrayList<Artifact> getAllContains(){
		return this.contains;
	}
	
	public Collection getRoot(){
		return this.root;
	}
	
	public String checkType(){
		if(this.root == null)
			return "Root";
		else
			return "Leaves";
	}
	
	public String toDelete(){
		if(this.toBeDeleted)
			return "True";
		else 
			return "False";
	}
	
	public String toStr(){
		String str = "";
		
		str = "{\n"+
				"\t\"sequence\" : \"" + this.getSequence() + "\"\n" +
				"\t\"name\" : \"" + this.getName() + "\"\n" +
				"\t\"key\" : \"" + this.getKey() + "\"\n" +
				"\t\"type\" : \"" + this.checkType() + "\"\n" +
				"\t\"delete mark\" : " + this.toDelete() + "\"\n" +
				"}\n";
		
		return str;
	}
	
}
