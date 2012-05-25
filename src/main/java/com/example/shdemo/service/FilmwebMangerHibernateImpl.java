package com.example.shdemo.service;

import java.util.List;

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
			
			/*List<ActorMovie> retrievedActorMovies = getAllActorMovies();
			for (ActorMovie actorMovie : retrievedActorMovies) {
				if (actor.getId().equals(actorMovie.getActor().getId())) {
					sessionFactory.getCurrentSession().delete(actorMovie);
				}
			}*/

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
			
			/*List<ActorMovie> retrievedActorMovies = getAllActorMovies();
			for (ActorMovie actorMovie : retrievedActorMovies) {
				if (movie.getId().equals(actorMovie.getMovie().getId())) {
					sessionFactory.getCurrentSession().delete(actorMovie);
				}
			}*/
	
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

	/*@Override
	public void addActorMovie(Long actorId, Long movieId) {
		ActorMovie actorMovie = new ActorMovie();
		
		Actor actor = (Actor) sessionFactory.getCurrentSession().get(Actor.class, actorId);
		Movie movie = (Movie) sessionFactory.getCurrentSession().get(Movie.class, movieId);

		actorMovie.setId(null);
		actorMovie.setActor(actor);
		actorMovie.setMovie(movie);
		
		sessionFactory.getCurrentSession().persist(actorMovie);
	}*/

	/*@Override
	public void deleteActorMovie(Long actorId, Long movieId) {
		// TODO
	}*/

	@Override
	public Movie findMovieById(Long movieId) {
		return (Movie) sessionFactory.getCurrentSession().get(Movie.class, movieId);
	}

	/*@SuppressWarnings("unchecked")
	@Override
	public List<ActorMovie> getAllActorMovies() {
		return sessionFactory.getCurrentSession().getNamedQuery("actorMovie.all").list();
	}*/

	@Override
	public Long getLatestActorId() {
		return (Long) sessionFactory.getCurrentSession().createQuery("select max(id) from Actor").uniqueResult();
	}

	@Override
	public Long getLatestMovieId() {
		return (Long) sessionFactory.getCurrentSession().createQuery("select max(id) from Movie").uniqueResult();
	}

	/*@Override
	public ActorMovie findActorMovieById(Long actorMovieId) {
		return (ActorMovie) sessionFactory.getCurrentSession().get(ActorMovie.class, actorMovieId);
	}*/

	/*@Override
	public Long getLatestActorMovieId() {
		return (Long) sessionFactory.getCurrentSession().createQuery("select max(id) from ActorMovie").uniqueResult();
	}*/

}
