package com.uyghur.java.blogdatalayer.model;

import java.util.List;

import org.springframework.data.annotation.Id;

public class TutorialAggregate {
	
	private @Id String category;
	
	List<TutorialExtract> nameAndDesc;

	public String getCategory() {
		return category;
	}

	public List<TutorialExtract> getNameAndDesc() {
		return nameAndDesc;
	}
	

}
