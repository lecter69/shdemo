package com.example.shdemo.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({ 
	@NamedQuery(name = "actorMovie.all", query = "Select p from ActorMovie p")
})
public class ActorMovie {

	private Long id;
	private Actor actor;
	private Movie movie;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	public Actor getActor() {
		return actor;
	}
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	public Movie getMovie() {
		return movie;
	}
	
	public void setActor(Actor actor) {
		this.actor = actor;
	}
	
	public void setMovie(Movie movie) {
		this.movie = movie;
	}

}
