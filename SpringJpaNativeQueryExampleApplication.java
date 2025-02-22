package com.bezkoder.spring.jpa.query;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.bezkoder.spring.jpa.query.model.Tutorial;
import com.bezkoder.spring.jpa.query.model.User;
import com.bezkoder.spring.jpa.query.repository.TutorialRepository;
import com.bezkoder.spring.jpa.query.repository.UserRepository;

@SpringBootApplication
public class SpringJpaNativeQueryExampleApplication implements CommandLineRunner {

	@Autowired
	TutorialRepository tutorialRepository;

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringJpaNativeQueryExampleApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		tutorialRepository.deleteAll();

		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse("2022-03-11");
		Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse("2022-04-26");
		Date date3 = new SimpleDateFormat("yyyy-MM-dd").parse("2022-05-19");

		tutorialRepository.save(new Tutorial("Spring Data", "Spring Data Description", 3, true, date1));
		tutorialRepository.save(new Tutorial("Java Spring Boot", "Spring Framework Description", 1, false, date1));
		tutorialRepository.save(new Tutorial("Hibernate", "Hibernate ORM Description", 3, true, date2));
		tutorialRepository.save(new Tutorial("Spring Boot", "Spring Boot Description", 2, false, date2));
		tutorialRepository.save(new Tutorial("Spring JPA", "Spring Data JPA Description", 3, true, date3));
		tutorialRepository.save(new Tutorial("Spring Batch", "Spring Batch Description", 4, true, date3));
		tutorialRepository.save(new Tutorial("Spring Security", "Spring Security Description", 5, false, date3));

		List<Tutorial> tutorials = new ArrayList<>();

		tutorials = tutorialRepository.findAll();
		show(tutorials);

		tutorialRepository.publishTutorial(tutorials.get(0).getId());
		tutorialRepository.publishTutorial(tutorials.get(2).getId());
		tutorialRepository.publishTutorial(tutorials.get(4).getId());

		tutorials = tutorialRepository.findByPublished(true);
		show(tutorials);

		tutorials = tutorialRepository.findByTitleLike("ata");
		show(tutorials);

		tutorials = tutorialRepository.findByTitleLikeCaseInsensitive("dat");
		show(tutorials);

		tutorials = tutorialRepository.findByLevelGreaterThanEqual(3);
		show(tutorials);

		Date myDate = new SimpleDateFormat("yyyy-MM-dd").parse("2022-05-11");

		tutorials = tutorialRepository.findByDateGreaterThanEqual(myDate);
		show(tutorials);

		tutorials = tutorialRepository.findByLevelBetween(3, 5);
		show(tutorials);

		tutorials = tutorialRepository.findByLevelBetween(3, 5, true);
		show(tutorials);

		Date myDate1 = new SimpleDateFormat("yyyy-MM-dd").parse("2022-04-11");
		Date myDate2 = new SimpleDateFormat("yyyy-MM-dd").parse("2022-05-11");

		tutorials = tutorialRepository.findByDateBetween(myDate1, myDate2);
		show(tutorials);

		tutorials = tutorialRepository.findByTitleContainingOrDescriptionContainingCaseInsensitive("data");
		show(tutorials);

		tutorials = tutorialRepository.findByTitleContainingCaseInsensitiveAndPublished("spring", true);
		show(tutorials);

		tutorials = tutorialRepository.findAllOrderByLevelDesc();
		show(tutorials);

		tutorials = tutorialRepository.findByTitleOrderByLevelAsc("at");
		show(tutorials);

		tutorials = tutorialRepository.findAllPublishedOrderByCreatedDesc();
		show(tutorials);

		Pageable pageable1 = PageRequest.of(0, 1000, Sort.by("level").descending());

		tutorials = tutorialRepository.findByTitleLike("at", pageable1).getContent();
		show(tutorials);

		Pageable pageable2 = PageRequest.of(0, 1000, Sort.by("title").descending());

		tutorials = tutorialRepository.findByTitleLike("at", pageable2).getContent();
		show(tutorials);

		Pageable pageable3 = PageRequest.of(0, 1000, Sort.by("level").descending());

		tutorials = tutorialRepository.findByPublished(false, pageable3).getContent();
		show(tutorials);

		int page = 0;
		int size = 3;

		Pageable pageable = PageRequest.of(page, size);

		tutorials = tutorialRepository.findAllWithPagination(pageable).getContent();
		show(tutorials);

		pageable = PageRequest.of(page, size, Sort.by("level").descending());

		tutorials = tutorialRepository.findAllWithPagination(pageable).getContent();
		show(tutorials);

		pageable = PageRequest.of(0, 3);

		tutorials = tutorialRepository.findByTitleLike("ring", pageable).getContent();
		show(tutorials);

		pageable = PageRequest.of(page, size, Sort.by("level").descending());

		tutorials = tutorialRepository.findByPublished(false, pageable).getContent();
		show(tutorials);

		User user = new User();
		user.setActive(1);
		user.setAge(28);
		user.setEmailAddress("sony.ankit484@gmail.com");
		user.setFirstname("Ankit");
		user.setLastname("Soni");
		user.setStartDate(new Date());
		user = userRepository.save(user);

		System.out.println("-----------------------------------------------" + user.getId());

		System.out.println(user.toString());

		/*er user1 = userRepository.findByEmailAddress("skfksfksf");
		System.out.println(user1.toString());*/

		List<User> user2 = userRepository.findByFirstnameEndsWith("Ramesh");
		System.out.println(user2.toString());

	}

	private void show(List<Tutorial> tutorials) {
		tutorials.forEach(System.out::println);
	}

}
