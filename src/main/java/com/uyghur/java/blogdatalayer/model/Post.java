package com.uyghur.java.blogdatalayer.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


//blog, Si on ne spécifie pas cet attribut, le nom de la classe sera utilisé comme nom de collection.
@Document(collection = "posts")
public class Post {
	
	@Id
	private String id;
	// définir un nom différent pour la caractéristique du document, vous pouvez utiliser l’annotation @Field(“nom”)
	@Indexed(unique = true)
	private String name;
	private String content;
	// exclure l’un des attributs de la classe afin qu’il ne soit pas interprété comme faisant partie de la structure du document,
	// vous pouvez utiliser l’annotation @Transient.
	private Date date;
	
	private Tag tag;
	
	private List<Comment> comments = new ArrayList<>();
	
	public Tag getTag() {
		return tag;
	}
	public void setTag(Tag tag) {
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "Post [id=" + id + ", name=" + name + ", content=" + content + ", date=" + date + ", tag=" + tag + "]";
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	
	
}
