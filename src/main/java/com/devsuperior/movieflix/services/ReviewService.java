package com.devsuperior.movieflix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.repositories.UserRepository;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository repository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Transactional
	public ReviewDTO saveReview(ReviewDTO dto){
		
		Review review = new Review();
		
		User user = new User();
		
		user.setEmail(dto.getUserEmail());
		user.setId(dto.getUserId());
		user.setName(dto.getUserName());
		
		user = userRepository.save(user);
		
		Movie movie = movieRepository.getReferenceById(dto.getMovieId());

		review.setMovie(movie);
		review.setText(dto.getText());
		review.setUser(user);
		
		review = repository.save(review);
		
		return new ReviewDTO(review);
		
	}
	
}
