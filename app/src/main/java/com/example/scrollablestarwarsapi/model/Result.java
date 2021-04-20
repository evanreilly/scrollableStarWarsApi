package com.example.scrollablestarwarsapi.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Result {

	@SerializedName("next")
	private String next;

	@SerializedName("previous")
	private Object previous;

	@SerializedName("count")
	private int count;

	@SerializedName("results")
	private List<Person> persons;

	public String getNext(){
		return next;
	}

	public Object getPrevious(){
		return previous;
	}

	public int getCount(){
		return count;
	}

	public List<Person> getPersons(){
		return persons;
	}
}