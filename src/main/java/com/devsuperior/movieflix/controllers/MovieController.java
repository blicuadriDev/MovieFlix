package com.devsuperior.movieflix.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.services.MovieService;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {

	@Autowired
	private MovieService service;
	
    @PreAuthorize("hasAnyRole('VISITOR', 'MEMBER')")
	@GetMapping
	public ResponseEntity<Page<MovieCardDTO>> getMovies(
			@RequestParam (value="genreId", defaultValue="0") Long genreId, 
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam (value = "size", defaultValue = "4") Integer size,
			@RequestParam (value = "direction", defaultValue = "ASC") String direction,
			@RequestParam (value = "orderBy", defaultValue = "title") String orderBy) {
    	
		
    	PageRequest pageRequest = PageRequest.of(page, size,  Direction.valueOf(direction), orderBy);
		
		Page<MovieCardDTO> dto = service.pagedMovies(genreId, pageRequest);
		return ResponseEntity.ok().body(dto);
	}
    
    
    @PreAuthorize("hasAnyRole('VISITOR', 'MEMBER')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<MovieCardDTO> getMovie(@PathVariable Long id) {
    	MovieCardDTO dto = service.getMovie(id);
		return ResponseEntity.ok().body(dto);
	}
    
    
    
}
