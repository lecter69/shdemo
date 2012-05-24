package com.example.shdemo.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.example.shdemo.domain.Actor;
import com.example.shdemo.domain.ActorMovie;
import com.example.shdemo.domain.Movie;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/beans.xml" })
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class FilmwebManagerTest {

	@Autowired
	FilmwebManager filmwebManager;

	private final static String FIRSTNAME_1 = "Andrzej";
	private final static String LASTNAME_1 = "Kiełbasa";
	
	private final static String NAME_1 = "Iron Sky";
	private final static int YEAR_1 = 2012;
	private final static String GENRE_1 = "SF";
	private final static int TIME_1 = 90;	

	@Test
	public void addActorCheck() {
		Actor actor = new Actor();
		actor.setFirstName(FIRSTNAME_1);
		actor.setLastName(LASTNAME_1);

		filmwebManager.addActor(actor);

		Actor retrievedActor = filmwebManager.findActorById(filmwebManager.getLatestActorId());

		assertEquals(FIRSTNAME_1, retrievedActor.getFirstName());
		assertEquals(LASTNAME_1, retrievedActor.getLastName());
	}
	
	@Test
	public void addMovieCheck() {
		Movie movie = new Movie();
		movie.setName(NAME_1);
		movie.setYear(YEAR_1);
		movie.setGenre(GENRE_1);
		movie.setTime(TIME_1);

		filmwebManager.addMovie(movie);

		Movie retrievedMovie = filmwebManager.findMovieById(filmwebManager.getLatestMovieId());

		assertEquals(NAME_1, retrievedMovie.getName());
		assertEquals(YEAR_1, retrievedMovie.getYear());
		assertEquals(GENRE_1, retrievedMovie.getGenre());
		assertEquals(YEAR_1, retrievedMovie.getYear());
	}
	
	@Test
	public void addActorMovieCheck() {
		Actor actor = new Actor();
		actor.setFirstName(FIRSTNAME_1);
		actor.setLastName(LASTNAME_1);

		filmwebManager.addActor(actor);
		
		Movie movie = new Movie();
		movie.setName(NAME_1);
		movie.setYear(YEAR_1);
		movie.setGenre(GENRE_1);
		movie.setTime(TIME_1);

		filmwebManager.addMovie(movie);
		
		long actorId = filmwebManager.getLatestActorId();
		long movieId = filmwebManager.getLatestMovieId();
		filmwebManager.addActorMovie(actorId, movieId);
		
		ActorMovie retrievedActorMovie = filmwebManager.findActorMovieById(filmwebManager.getLatestActorMovieId());
		long retrievedActorId = retrievedActorMovie.getActor().getId();
		long retrievedMovieId = retrievedActorMovie.getMovie().getId();
		assertEquals(actorId, retrievedActorId);
		assertEquals(movieId, retrievedMovieId);
	}

	@Test
	public void deleteActorCheck() {
		Actor actor = new Actor();
		actor.setFirstName(FIRSTNAME_1);
		actor.setLastName(LASTNAME_1);

		filmwebManager.addActor(actor);
		
		long latestActorId = filmwebManager.getLatestActorId();

		filmwebManager.deleteActor(actor);

		assertNull(filmwebManager.findMovieById(latestActorId));		
	}
	
	@Test
	public void cascadeCheck() {
		Actor actor = new Actor();
		actor.setFirstName(FIRSTNAME_1);
		actor.setLastName(LASTNAME_1);

		filmwebManager.addActor(actor);
		
		Movie movie = new Movie();
		movie.setName(NAME_1);
		movie.setYear(YEAR_1);
		movie.setGenre(GENRE_1);
		movie.setTime(TIME_1);

		filmwebManager.addMovie(movie);
		
		long actorId = filmwebManager.getLatestActorId();
		long movieId = filmwebManager.getLatestMovieId();
		filmwebManager.addActorMovie(actorId, movieId);
		
		long lastActorMovieId = filmwebManager.getLatestActorMovieId();
		
		filmwebManager.deleteActor(actor);
		
		assertNull(filmwebManager.findActorMovieById(lastActorMovieId));
	}
	
}
