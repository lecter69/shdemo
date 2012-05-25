package com.example.shdemo.service;

import java.util.List;

import com.example.shdemo.domain.Actor;
import com.example.shdemo.domain.Movie;

public interface FilmwebManager {
	
	void addActor(Actor actor);
	List<Actor> getAllActors();
	void deleteActor(Actor actor);
	Actor findActorById(Long actorId);
	Long getLatestActorId();
	
	void addMovie(Movie movie);
	List<Movie> getAllMovies();
	void deleteMovie(Movie movie);
	Movie findMovieById(Long movieId);
	Long getLatestMovieId();
	
	/*void addActorMovie(Long actorId, Long movieId);
	void deleteActorMovie(Long actorId, Long movieId);
	List<ActorMovie> getAllActorMovies();
	ActorMovie findActorMovieById(Long actorMovieId);
	Long getLatestActorMovieId();*/

}
