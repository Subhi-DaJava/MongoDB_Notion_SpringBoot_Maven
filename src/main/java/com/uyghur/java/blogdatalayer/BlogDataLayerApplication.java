package com.uyghur.java.blogdatalayer;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Example;

import com.uyghur.java.blogdatalayer.model.Comment;
import com.uyghur.java.blogdatalayer.model.LightPost;
import com.uyghur.java.blogdatalayer.model.LightTuto;
import com.uyghur.java.blogdatalayer.model.Post;
import com.uyghur.java.blogdatalayer.model.PostAggregate;
import com.uyghur.java.blogdatalayer.model.Tag;
import com.uyghur.java.blogdatalayer.model.TutoAggregate;
import com.uyghur.java.blogdatalayer.model.Tutorial;
import com.uyghur.java.blogdatalayer.model.TutorialAggregate;
import com.uyghur.java.blogdatalayer.repository.PostRepository;
import com.uyghur.java.blogdatalayer.repository.TutorialRepository;

/**
 * MongoDB offre 2 fonctionnalités très intéressantes pour la lecture de
 * données, les projections et les agrégations.
 * 
 * @author Subhi
 *
 */
@SpringBootApplication
public class BlogDataLayerApplication implements CommandLineRunner {
	private final Logger logger = LoggerFactory.getLogger(BlogDataLayerApplication.class);

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private TutorialRepository tutorialRepository;

	public static void main(String[] args) {
		SpringApplication.run(BlogDataLayerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Get content of one post
		logger.info("App Starts !");

		Optional<Post> post = postRepository.findById("6177a31824f1d205e0b0692d");
		logger.info("Post findById Starts !");
		if (post.isPresent()) {
			logger.info("This is the post : " + post.get().getContent());
		} else {
			logger.info("This post does not exit in DB!");
		}

		// Get content and shorDescription of one tuto
		Optional<Tutorial> tuto = tutorialRepository.findById("6192c22d783f4a2a0a7d9bf3");
		logger.info("Tutorial findById Starts !");
		if (tuto.isPresent()) {
			logger.info("This is the tuto content: " + tuto.get().getContent());
			// logger.info("This is the tuto shortDescription: " +
			// tuto.get().getShortDescription());
		} else {
			logger.info("This tuto does not exit in DB!");
		}

		// Iterate all posts
		List<Post> posts = postRepository.findAll();
		posts.stream().forEach(post_unit -> logger.info(post_unit.getName()));

		// Iterate all tutos
		List<Tutorial> tutorials = tutorialRepository.findAll();
		tutorials.stream().forEach(tuto_unit -> logger.info(tuto_unit.getContent()));

		// findOne() method, by name with Example
		logger.info("findOne starts!");
//		Post p1 = new Post();
//		p1.setName("Welcome!");
//		Example<Post> example = Example.of(p1);
//
//		Optional<Post> p = postRepository.findOne(example);
//
//		if (p.isPresent()) {
//			logger.info("Find by Name: " + p.get().getContent());
//		} else {
//			logger.info("Post not found");
//		}

		// Post findByName(name) method repository
		logger.info("Post findByName starts!");
		List<Post> posts_byName = postRepository.findByName("Welcome!");
		posts_byName.stream().forEach(
				post_byName -> logger.info("Find by Name : " + post_byName.getName() + ", id :" + post_byName.getId()));

		// Tutorial findByName(name) method repository
		logger.info("Toturial findByName starts!");
		List<Tutorial> tutos_byName = tutorialRepository.findByName("Welcome!");
		tutos_byName.stream().forEach(tuto_byName -> logger.info("Find by Name : " + tuto_byName.getName()));

		// Tutorial findByShordDescriptionContainingSomeText(some Text) method
		// repository
		logger.info("Toturial findByShortDiscriptionContaining starts!");
		List<Tutorial> tutos_byShortDescription = tutorialRepository.findByShortDescriptionContaining("This");
		tutos_byShortDescription.stream()
				.forEach(tuto_byShDesc -> logger.info("Find by shortDescription : " + tuto_byShDesc.getName()));

		// Tutorial findByShordDescriptionContainsSomeText(some Text) method repository
		logger.info("Toturial findByShortDiscriptionContains starts!");
		List<Tutorial> tutos_byShortDescriptionContains = tutorialRepository.findByShortDescriptionContains("This");
		tutos_byShortDescriptionContains.stream().forEach(
				tuto_byShDescContains -> logger.info("Find by shortDescription : " + tuto_byShDescContains.getName()));

		// Get the names of the posts by date desc
		logger.info("Posts name by date order starts!");
		List<LightPost> lightPostList = postRepository.findByOrderByDateDesc();
		lightPostList.forEach(
				postByDate -> logger.info("Date order: " + postByDate.getName() + ", id : " + postByDate.getId()));

		// Get the names of the posts by date desc by natives queries
		logger.info("Posts name by date by natives queries order starts!");
		List<Post> postsByNavtiesQueries = postRepository.findIdAndNameExcludeOthers();
		postsByNavtiesQueries.stream().forEach(
				postByNativeQuery -> logger.info("Post by name from nativeQuery: " + postByNativeQuery.getName()));

		// Get tutos the name and shortdescription by Native Query
		logger.info("Tutos only name and short description starts!");
		List<Tutorial> lightTutos = tutorialRepository.findByNameShortDescription();
		lightTutos.stream().forEach(lightTuto -> logger.info("Light tuto's name : " + lightTuto.getName()
				+ " and Light tuto's shortDescription : " + lightTuto.getShortDescription()));

		// Get tutos the name and shortdescription
		logger.info("Tutos only name and short description by projection starts!");
		List<LightTuto> lightTutos_ = tutorialRepository.findByOrderByCategoryAsc();
		lightTutos_.stream().forEach(lightTuto_ -> logger.info("Light tuto's name : " + lightTuto_.getName()
				+ " and Light tuto's shortDescription : " + lightTuto_.getShortDescription()));

		// Get all names
		logger.info("Get all Names starts !");
		List<String> names = postRepository.findAllName();
		names.forEach(logger::info);

		// Get articles by group order by date
		logger.info("aggrage by date starts!");
		List<PostAggregate> postAggregates = postRepository.getArticlesByDate();

		postAggregates.stream().forEach(aggregate -> logger.info(

				aggregate.getDate()

						+ " : "

						+ aggregate.getNames().size()));

		// Aggregate the category with names and short discription
		logger.info("aggrege by category starts!");
		List<TutoAggregate> tutoAggregates = tutorialRepository.findByCategoryWithNameAndShortDescription();

		tutoAggregates.stream()
				.forEach(tutoAggregate -> logger
						.info(tutoAggregate.getCategory() + " : names.size :" + tutoAggregate.getNames().size()
								+ ", short disc.size : " + tutoAggregate.getShortDescriptions().size()));

		// Aggregate the category with names and short discription in map
		logger.info("aggrege by category with Map starts!");
		List<TutorialAggregate> tutorialAggregates = tutorialRepository
				.findByCategoryAggregateNameAndShortDescription();

		tutorialAggregates.stream().forEach(tutoAggregates_ -> logger.info(
				tutoAggregates_.getCategory() + " : nameAndDesc : " + tutoAggregates_.getNameAndDesc().toString())); // .get(0).getShorDiscription()
																														// =
																														// null

		tutorialAggregates.stream().forEach(tutoAggregateBy -> logger
				.info(tutoAggregateBy.getCategory() + " : " + String.valueOf(tutoAggregateBy.getNameAndDesc().size())));

		// Insert un Post
		logger.info("Insert a Post starts!");

//		Post newP = new Post();
//		newP.setName("My new blog post");
//		newP.setDate(new Date());
//		newP.setContent("A new blog post, amazing!");
//		postRepository.insert(newP);

		// Insert somes Posts at same time
		logger.info("Insert the Posts starts!");

//		Post newP1 = new Post();
//		newP1.setName("Welcome on board!");
//		newP1.setDate(new Date());
//		newP1.setContent("My blog is really useful !");
//
//		Post newP2 = new Post();
//		newP2.setName("See you soon");
//		newP2.setDate(new Date());
//		newP2.setContent("I will be back soon - going on holidays!");
//
//		postRepository.insert(List.of(newP1, newP2));
		
		logger.info("Insert a Tuto starts!");
//		Tutorial newTuto1 = new Tutorial("tutoNew1","shortDescription1","tutoJava_Spring", "informatique");
//		tutorialRepository.insert(newTuto1);
//		
		logger.info("Insert the Tutos starts!");
//		Tutorial newTuto2 = new Tutorial("tutoNew2","shortDescription2","tutoJava_Spring1", "informatique1");
//		Tutorial newTuto3 = new Tutorial("tutoNew3","shortDescription3","tutoJava_Spring2", "informatique2");
//		Tutorial newTuto4 = new Tutorial("tutoNew4","shortDescription4","tutoJava_Spring3", "informatique3");
//		
//		List<Tutorial> newlistTutos = List.of(newTuto2, newTuto3, newTuto4);
//		tutorialRepository.insert(newlistTutos);
		
		// Create a Tag class and add this Tag to a new Post, slug-c’est un label court unique pour chaque étiquette
		logger.info("New Post with Tag created and insert into DB starts!");
//		Tag tag = new Tag("Spring Data MongoDB", "spring-data_mongodb",
//					"Module Spring Data MongoDB du framework Spring pour l'interaction avec une base MongoDB.");
//		Post newPWithTag= new Post();
//		newPWithTag.setName("Adding Tag to document");
//		newPWithTag.setDate(new Date());
//		newPWithTag.setContent("This object has a tag!");
//		newPWithTag.setTag(tag);
//		postRepository.insert(newPWithTag);
		
		List<Post> allPostWithTags = postRepository.findAll();
		allPostWithTags.stream().forEach(postTag -> logger.info(postTag.toString()));
		
		// Assign a tag to this document Post with id = 618244a55ec6eb500c8b5be0
		logger.info("Assign a tag to a document in DB starts!");
		
		//Tag tagAssign = new Tag("Hibernate", "hibernate", 
				//"Framework ORM pour l'interaction avec des bases de données en Java");
		//Optional<Post> postToAssign = postRepository.findById("618244a55ec6eb500c8b5be0");
	//	 if(postToAssign.isPresent()) {
				//postToAssign.get().setTag(tagAssign);
			//postRepository.save(postToAssign.get());
//		 } else {
//			 logger.info("Post not found!")
//		 }
//	
		// update 
		
		
		Optional<Post> postToAssignCheck = postRepository.findById("618244a55ec6eb500c8b5be0");
		if(postToAssignCheck.isPresent()) {
			logger.info(postToAssignCheck.get().toString());
		} else {
			logger.info("Post not found!");
		}
		
		// Create a Tuto and tag, assign this tag to this Tuto
		logger.info("Assign a tag to a document Tutorial in DB starts!");
//		Tag tagTuto = new Tag("Spring Data MongoDB Tuto", "spring-data-mongo-db",
//				"Description Tutorial for Spring Data MongoDb");
//		
//		Tutorial newTutoWithTag = 
//				new Tutorial("Tuto Spring Mongo", "Spring Data MongoDb 20 hours", "5 Sections", "Java", tagTuto);
//		tutorialRepository.insert(newTutoWithTag);
		
		List<Tutorial> tutoGetTag = tutorialRepository.findByName("Tuto Spring Mongo");
		
		tutoGetTag.stream().forEach(tutoTag -> logger.info(tutoTag.toString()));
		
		
		// Update a Post existing  with Example findOne is not goog idea 
		logger.info("Update a existing Post");
		 // Creating a Post object with the name we are looking for
//        Post existingPost = new Post();
//        existingPost.setName("Welcome!");
//        
//        Example<Post> exampleNew = Example.of(existingPost);
//
//        // Getting the searched Post
//        existingPost = postRepository.findOne(exampleNew).get();
//
//        // Building a new name and updating the object Post
//        String newName = existingPost.getName() + " [updated]";
//        existingPost.setName(newName);
//
//        // Saving this change in database
//        postRepository.save(existingPost);
		
//        List<Post> existingPosts = postRepository.findByName(existingPost.getName());
//        existingPosts.stream().forEach(postExists -> logger.info(postExists.toString()));
		
		logger.info("Update a tuto starts! Example is foundOne is not good Idea");
//		Tutorial tutoUpdate = new Tutorial();
//		tutoUpdate.setName("How to use MongoRepository");
//		Example<Tutorial> exampleTutuUpdate = Example.of(tutoUpdate);
//		
//		Optional<Tutorial> tu = tutorialRepository.findOne(exampleTutuUpdate);
//		if(tu.isPresent()) {
//			String tutoNewName = tutoUpdate.getName() + " [updated]";
//			tutoUpdate.setName(tutoNewName);
//			
//			tutorialRepository.save(tutoUpdate);
//		} else {
//			logger.info("Tutorial not found");
//		}
		
		// Adding a new comment
		logger.info("Add comment to Post find by id starts");
//		Comment newComment = new Comment();
//		newComment.setPseudo("Your fans");
//		newComment.setContent("My First content!");
//		
//		Optional<Post> postAddComment = postRepository.findById("6177a3648207e0587345067a");
//		if(postAddComment.isPresent()) {
//			postAddComment.get().getComments().add(newComment);
//			postRepository.save(postAddComment.get());
//		} else {
//			logger.info("Post not found");
//		}
//		
		// delete by name, requête dérivée dans le Repository
		logger.info("DeleteByName Post starts!");
		//postRepository.deleteByName("Adding Tag to document");
		
		// delete by id
		logger.info("DeleteById Post starts!");
		//postRepository.deleteById("63ae35187b3dd46e78c8109d");
		
		// delete by name requête dérivée dans le Repository
		logger.info("DeleteByName Tuto starts!");
		//tutorialRepository.deleteByName("Tuto Spring Mongo");
		
		logger.info("DeleteById Tuto starts!");
		//tutorialRepository.deleteById("63ae36de2d2c4d306876581b");
		
	}

}
