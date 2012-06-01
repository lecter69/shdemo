package com.example.shdemo.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.shdemo.domain.Actor;
import com.example.shdemo.domain.Movie;

@Component
@Transactional
public class FilmwebMangerHibernateImpl implements FilmwebManager {

	@Autowired
	private SessionFactory sessionFactory;
	Transaction tx;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addActor(Actor actor) {
		actor.setId(null);
		sessionFactory.getCurrentSession().persist(actor);	
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Actor> getAllActors() {
		return sessionFactory.getCurrentSession().getNamedQuery("actor.all").list();
	}

	@Override
	public void deleteActor(Actor actor) {
		try {
			tx = sessionFactory.openSession().beginTransaction();
			actor = (Actor) sessionFactory.getCurrentSession().get(Actor.class, actor.getId());

			sessionFactory.getCurrentSession().delete(actor);
		    tx.commit();
		} catch (Exception e) {
			if (tx!=null) tx.rollback();
		} finally {
			sessionFactory.close();
		}
	}

	@Override
	public void addMovie(Movie movie) {
		movie.setId(null);
		sessionFactory.getCurrentSession().persist(movie);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Movie> getAllMovies() {
		return sessionFactory.getCurrentSession().getNamedQuery("movie.all").list();
	}

	@Override
	public void deleteMovie(Movie movie) {
		try {
			tx = sessionFactory.openSession().beginTransaction();
			movie = (Movie) sessionFactory.getCurrentSession().get(Movie.class, movie.getId());

			sessionFactory.getCurrentSession().delete(movie);
			tx.commit();
		} catch (Exception e) {
			if (tx!=null) tx.rollback();
		} finally {
			sessionFactory.close();
		}
	}

	@Override
	public Actor findActorById(Long actorId) {
		return (Actor) sessionFactory.getCurrentSession().get(Actor.class, actorId);
	}

	@Override
	public Movie findMovieById(Long movieId) {
		return (Movie) sessionFactory.getCurrentSession().get(Movie.class, movieId);
	}

	@Override
	public Long getLatestActorId() {
		return (Long) sessionFactory.getCurrentSession().createQuery("select max(id) from Actor").uniqueResult();
	}

	@Override
	public Long getLatestMovieId() {
		return (Long) sessionFactory.getCurrentSession().createQuery("select max(id) from Movie").uniqueResult();
	}

	@Override
	public void updateActor(Actor actor) {
		Actor actor2 = (Actor) sessionFactory.getCurrentSession().get(Actor.class, actor.getId());
		actor2.setFirstName(actor.getFirstName());
		actor2.setLastName(actor.getLastName());
		actor2.setMovies(actor.getMovies());
	}

	@Override
	public void updateMovie(Movie movie) {
		Movie movie2 = (Movie) sessionFactory.getCurrentSession().get(Movie.class, movie.getId());
		
		movie2.setName(movie.getName());
		movie2.setYear(movie.getYear());
		movie2.setGenre(movie.getGenre());
		movie2.setTime(movie.getTime());
	}

	@Override
	public HashSet<Movie> getMovies(Long actorId) {
		Actor actor = (Actor) sessionFactory.getCurrentSession().get(Actor.class, actorId);
		
		return new HashSet<Movie> (actor.getMovies());
	}

}
