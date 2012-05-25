package com.example.shdemo.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({ 
	@NamedQuery(name = "actor.all", query = "Select p from Actor p"),
})
@Table(name = "ACTOR")
public class Actor {

	private Long id;
	private String firstName;
	private String lastName;
	private Set<Movie> movies = new HashSet<Movie>(0);

	public Actor () {
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ACTOR_ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "ACTOR_MOVIE", joinColumns = { @JoinColumn(name = "ACTOR_ID") }, inverseJoinColumns = { @JoinColumn(name = "MOVIE_ID") })
	public Set<Movie> getMovies() {
		return this.movies;
	}
	
	
	public void setMovies(Set<Movie> movies) {
		this.movies = movies;
	}

}
