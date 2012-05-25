package com.example.shdemo.service;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.example.shdemo.domain.Actor;
import com.example.shdemo.domain.Movie;
import com.example.shdemo.service.FilmwebManager;

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
		Movie movie = new Movie();
		movie.setName(NAME_1);
		movie.setYear(YEAR_1);
		movie.setGenre(GENRE_1);
		movie.setTime(TIME_1);

		filmwebManager.addMovie(movie);
		
		Actor actor = new Actor();
		actor.setFirstName(FIRSTNAME_1);
		actor.setLastName(LASTNAME_1);
		
		Movie odebranyFilm = filmwebManager.findMovieById(filmwebManager.getLatestMovieId());
		
		HashSet<Movie> cos = new HashSet<Movie>(0);
		cos.add(odebranyFilm);
		
		actor.setMovies(cos);

		filmwebManager.addActor(actor);
		
		assertEquals(true, filmwebManager.findActorById(filmwebManager.getLatestActorId()).getMovies().contains(odebranyFilm));
	}
	
}
