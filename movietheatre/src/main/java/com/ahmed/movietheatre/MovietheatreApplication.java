package com.ahmed.movietheatre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@SpringBootApplication
@RestController
public class MovietheatreApplication {

	public static void main(String[] args) {

		SpringApplication.run(MovietheatreApplication.class, args);
	}

	@GetMapping
	public String getDiscoverPage() throws UnsupportedEncodingException {
		MovieDB tmp = new MovieDB();
		return tmp.getTop10();
		//return tmp.searchForMovie("turning red");

	}

	@PostMapping("/search")
	public String searchMovies(@RequestBody String searchQuery) {
		return searchMovies(searchQuery);
	}

	@GetMapping("/movie/{id}")
	public String getMovieDetails(@PathVariable String id) {
		MovieDB tmp = new MovieDB();
		return tmp.getMovieDetails(id);
	}

}
