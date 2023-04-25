package com.uyghur.java.blogdatalayer.model;
/**
 * Cet article léger, comme on le comprend par le nom LightPost,
 * est composé uniquement d’un accesseur (getter) pour l’id et d’un autre pour le name. 
 *
 * Les projections peuvent être codées grâce à des interfaces (interface based projection) ou grâce à l’annotation @ Query.
 * 
 * @author Subhi
 *
 */
public interface LightPost {
	public String getId();
	public String getName();

}
