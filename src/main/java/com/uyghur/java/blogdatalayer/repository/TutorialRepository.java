package com.uyghur.java.blogdatalayer.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.uyghur.java.blogdatalayer.model.LightTuto;
import com.uyghur.java.blogdatalayer.model.TutoAggregate;
import com.uyghur.java.blogdatalayer.model.Tutorial;
import com.uyghur.java.blogdatalayer.model.TutorialAggregate;

/**
 * Spring Data MongoDB fournit une interface nommée MongoRepository et des opérations déjà implémentées.
 * @author asus
 *
 */
//indique que la classe a pour rôle de communiquer avec une source de données (en l'occurrence, la base de données).

@Repository
public interface TutorialRepository extends MongoRepository<Tutorial, String> {
	
	List<Tutorial> findByName(String name);

	List<Tutorial> findByShortDescriptionContaining(String text);
	List<Tutorial> findByShortDescriptionContains(String text);
	
	List<LightTuto> findByOrderByCategoryAsc();
	
	@Query(value="{}", fields="{_id:1, name:1, shortDescription:1}")
	List<Tutorial> findByNameShortDescription();

	@Aggregation("{ $group: { _id : $category, names : { $addToSet : $name }, shortDescriptions : { $addToSet : $shortDescription} } }")
	List<TutoAggregate> findByCategoryWithNameAndShortDescription();
	
	
	@Aggregation("{ $group: { _id : $category, nameAndDesc : {$addToSet : {'name': '$name', 'shortDescription':'$shortDescription'} } } }")
	List<TutorialAggregate> findByCategoryAggregateNameAndShortDescription();
	
	
	void deleteByName(String name);
	
}
