package com.uyghur.java.blogdatalayer.model;

public class Comment {
	private String pseudo;
	private String content;
	
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return "Comment [pseudo=" + pseudo + ", content=" + content + "]";
	}
	
	
}
