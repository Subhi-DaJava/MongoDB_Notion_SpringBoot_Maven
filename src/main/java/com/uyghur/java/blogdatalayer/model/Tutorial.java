package com.uyghur.java.blogdatalayer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tutorials")
public class Tutorial {
	
	@Id
	private String id;
	// définir un nom différent pour la caractéristique du document, vous pouvez utiliser l’annotation @Field(“nom”)
	@Indexed(unique = true)
	private String name;

	// exclure l’un des attributs de la classe afin qu’il ne soit pas interprété comme faisant partie de la structure du document,
	// vous pouvez utiliser l’annotation @Transient.
	private String shortDescription;
	private String content;
	private String category;
	
	private Tag tag;
	
	
	public Tutorial() {
		super();
	}
	public Tutorial(String name, String shortDescription, String content, String category, Tag tag) {
		super();
		this.name = name;
		this.shortDescription = shortDescription;
		this.content = content;
		this.category = category;
		this.tag = tag;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	@Override
	public String toString() {
		return "Tutorial [id=" + id + ", name=" + name + ", shortDescription=" + shortDescription + ", content="
				+ content + ", category=" + category + ", tag=" + tag + "]";
	}
	
	
}
