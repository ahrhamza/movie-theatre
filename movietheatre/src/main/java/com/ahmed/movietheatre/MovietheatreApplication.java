package com.ahmed.movietheatre;

import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@SpringBootApplication
@RestController
public class MovietheatreApplication {
	MovieDB mdb = new MovieDB();

	public static void main(String[] args) {

		SpringApplication.run(MovietheatreApplication.class, args);
	}

	@GetMapping
	public String getDiscoverPage() throws UnsupportedEncodingException {
		return mdb.getTop10();
		//return tmp.searchForMovie("turning red");

	}

	@PostMapping(value = "/search", consumes = "application/json")
	public String searchMovies(@RequestBody String searchQuery) throws UnsupportedEncodingException {
		String query = (new JSONObject(searchQuery)).getString("searchTerm");
		return mdb.searchForMovie(query);
	}

	@GetMapping("/movie/{id}")
	public String getMovieDetails(@PathVariable String id) {
		return mdb.getMovieDetails(id);
	}

	@GetMapping("/genres")
	public String getGenres() {
		return mdb.getGenres();
	}

}
