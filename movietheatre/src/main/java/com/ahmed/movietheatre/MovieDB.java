package com.ahmed.movietheatre;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONPointer;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class MovieDB {

    static String KEY;

    /*
    Set up the API key for the MovieDB API
     */
    static {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("key.txt"));
            String s;
            s = br.readLine();
            if (s != null) {
                KEY = s;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MovieDB() { }

    /*
    Handles making a GET request to an endpoint on the Movie Database with a given string URI
     */
    private ResponseEntity<?> getResponseBody(String uri) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<?> result =
                restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        return result;
    }

    /*
    Makes a request to GET /discover/movie
    Returns a JSONArray of the top 10 movies sorted by reverse popularity
    Sends:
    ID
    absolute poster path url
    title
    description
    average rating (vote_average)
    title
    release date
    total reviews
    Data is parsed here since only certain data is needed for the discover feed
     */
    public String getTop10() {
        String uri = "https://api.themoviedb.org/3/discover/movie?api_key=" + MovieDB.KEY + "&language=en-US&sort_by=popularity.desc&include_adult=false";
        ResponseEntity<?> result = getResponseBody(uri);

        if(result.getStatusCodeValue() != 200) {
            return "{}";
        }

        JSONObject resultsJson = new JSONObject(result.getBody().toString());
        JSONArray top10moviesJSON = resultsJson.getJSONArray("results");

        ArrayList<Movie> top10movies = new ArrayList<Movie>();
        for(int i = 0; i < 10; i++) {
            JSONObject obj = top10moviesJSON.getJSONObject(i);
            Movie m = new Movie();
            m.setId(obj.getInt("id"));
            m.setDescription(obj.getString("overview"));
            m.setPosterPath(obj.getString("poster_path"));
            m.setRating(obj.getFloat("vote_average"));
            m.setTitle(obj.getString("title"));
            m.setReleaseDate(LocalDate.parse(obj.getString("release_date")));
            m.setTotalReviews(obj.getInt("vote_count"));
            top10movies.add(m);
        }

        JSONArray jsonArray = new JSONArray(top10movies);
        return jsonArray.toString();
    }

    /*
    Searches for a movie with a given search query
    returns all results as is from the Movie Database in JSON - the front end will parse this data
    makes more sense as it will help formatting and also allow multiple pages of results.
    Makes a request to GET /search/movie
     */
    public String searchForMovie(String query) throws UnsupportedEncodingException {
        if(query == null) {
            return "{}";
        }

        String uri = "https://api.themoviedb.org/3/search/movie?api_key=" + MovieDB.KEY +
                    "&language=en-US&query=" + URLEncoder.encode(query, StandardCharsets.UTF_8.toString()) + "&page=1&include_adult=false";
        ResponseEntity<?> result = getResponseBody(uri);

        if(result.getStatusCodeValue() != 200) {
            return "{}";
        }

        return result.getBody().toString();
    }

    /*
    Returns the details of a movie given its ID
    makes a request to GET /movie/{id}
    returns the details in JSON - front end should parse this
     */
    public String getMovieDetails(String id) {
        String uri = "https://api.themoviedb.org/3/movie/" + id + "?api_key=" + MovieDB.KEY + "&language=en-US";
        ResponseEntity<?> result = getResponseBody(uri);

        if(result.getStatusCodeValue() != 200) {
            return "{}";
        }

        return result.getBody().toString();
    }


    public String getGenres() {
        String uri = "https://api.themoviedb.org/3/genre/movie/list?api_key=" + MovieDB.KEY + "&language=en-US";
        ResponseEntity<?> result = getResponseBody(uri);
        if(result.getStatusCodeValue() != 200) {
            return "{}";
        }
        return result.getBody().toString();
    }

}
