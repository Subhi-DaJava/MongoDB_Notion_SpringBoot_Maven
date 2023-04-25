package com.uyghur.java.blogdatalayer.model;
/**
 * Les agrégations peuvent être codées grâce à l’annotation @ Aggregation, 
 * et requièrent la création d’une nouvelle classe pour obtenir le résultat de l’agrégation.
 */
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

public class PostAggregate {
	// date, annoté @Id, qui correspond à la valeur utilisée pour regrouper nos documents ;
	private @Id Date date;
	// names, qui reçoit la liste des noms des articles pour la date définie dans l’attribut précédent.
	private List<String> names;
	
	public Date getDate() {
		return date;
	}
	public List<String> getNames() {
		return names;
	}
	
	

}
