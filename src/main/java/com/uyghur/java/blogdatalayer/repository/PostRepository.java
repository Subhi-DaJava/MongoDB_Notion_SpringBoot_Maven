package com.uyghur.java.blogdatalayer.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.uyghur.java.blogdatalayer.model.LightPost;
import com.uyghur.java.blogdatalayer.model.Post;
import com.uyghur.java.blogdatalayer.model.PostAggregate;

/**
 * @ Repository est une spécialisation de l’annotation @ Component. Tout comme @
 * Component, elle permet de déclarer auprès de Spring qu’une classe est un bean
 * à exploiter. Par son nom, elle fournit au développeur une indication
 * sémantique sur le rôle de la classe. Vous pourriez bien sûr utiliser
 * l’annotation @ Component, cela fonctionnerait très bien, mais je vous
 * recommande d’utiliser les annotations qui offrent cette valeur sémantique
 * supplémentaire. Lorsque l’application sera exécutée, Spring va fournir une
 * implémentation de notre interface PostRepository, qui contiendra également
 * l’implémentation des méthodes de l’interface mère MongoRepository. De ce
 * fait, on accède à une liste de méthodes prêtes à l’emploi sans rien avoir à
 * implémenter !
 * 
 * @author Subhi
 *
 */
@Repository
public interface PostRepository extends MongoRepository<Post, String> {

	List<Post> findByName(String name);

	/*
	 * La méthode findByOrderByDateDesc() a comme particularité au niveau du type
	 * retour de spécifier l’interface de notre projection, à savoir LightPost.
	 */
	List<LightPost> findByOrderByDateDesc();

	// value=”{}” : signifie qu’il n’y a pas de filtres et que tous les documents
	// seront récupérés ;
	// fields=”{_id : 1; name :1}” : signifie qu’on veut récupérer les champs _id et
	// name
	// (donc pour récupérer un autre champ il suffit d’ajouter [nom du champ] : 1 ;
	// sort = “{date : -1}” : signifie qu’on trie par le champ date et l’indicatif
	// -1 en mode décroissant
	// (à l’inverse, l’indicatif 1 indiquerait un tri croissant).

	// Native Queries
	@Query(value = "{}", fields = "{_id : 1, name : 1}", sort = "{ date : -1 }")
	List<Post> findIdAndNameExcludeOthers();

	// l’extraction de toutes les valeurs de champ name
	@Aggregation("{ '$project' : {'_id' : '$name'} }")
	List<String> findAllName();

	/*
	 * Aggregate post sort articles by date : L’instruction $group va regrouper vos
	 * documents par la valeur du champ $date. Ensuite pour chaque date, j’extrais
	 * du groupe de documents le champ $name de chaque document. Je crée ainsi une
	 * liste des noms des articles grâce à l’instruction $addToSet.
	 * 
	 */
	@Aggregation(" { $group: { _id : $date, names : { $addToSet : $name } } }")
	List<PostAggregate> getArticlesByDate();
	
	void deleteByName(String name);

}
