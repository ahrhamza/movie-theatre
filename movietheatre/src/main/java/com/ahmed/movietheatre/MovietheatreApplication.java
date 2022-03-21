package com.ahmed.movietheatre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@SpringBootApplication
@RestController
public class MovietheatreApplication {

	public static void main(String[] args) {

		SpringApplication.run(MovietheatreApplication.class, args);
	}

	@GetMapping
	public String getKey() throws UnsupportedEncodingException {
		MovieDB tmp = new MovieDB();
		//return tmp.getTop10();
		return tmp.getMovieDetails("turning red");
	}

}
