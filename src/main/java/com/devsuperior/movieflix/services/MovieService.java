package com.devsuperior.movieflix.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class MovieService {

	@Autowired
	private MovieRepository repository;
	
	@Autowired
	private GenreRepository genreRepository;
	
	@Transactional(readOnly = true)
	public Page<MovieCardDTO> pagedMovies(Long genreId, PageRequest pageRequest){
		
		if(genreId == 0 ) {
			Page<Movie> entity = repository.findAll(pageRequest);
			return entity.map( x -> new MovieCardDTO(x));
		}
		else {
			Genre genre = genreRepository.getReferenceById(genreId);
			Page<Movie> entity = repository.findByGenreId(genre, pageRequest);
			return entity.map( x -> new MovieCardDTO(x));
		}
		
	}
	
	

	@Transactional(readOnly = true)
	public MovieDetailsDTO getMovie(Long id) {
		Optional<Movie> aux = repository.findById(id);
		Movie entity = aux.orElseThrow(() -> new ResourceNotFoundException("Movie not found!"));
		return new MovieDetailsDTO(entity);
	}

	public Page<MovieCardDTO> getMovieByGenre(Long genreId, Pageable pageable) {
		

		return null;
	}
	
}
