package com.uyghur.java.blogdatalayer.model;

import java.util.List;

import org.springframework.data.annotation.Id;

public class TutoAggregate {
	
	private @Id String category;
	private List<String> names;
	private List<String> shortDescriptions;
	
	public String getCategory() {
		return category;
	}
	public List<String> getNames() {
		return names;
	}
	public List<String> getShortDescriptions() {
		return shortDescriptions;
	}


}
