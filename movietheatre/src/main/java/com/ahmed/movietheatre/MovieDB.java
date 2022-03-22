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

    public MovieDB() {
    }

    private ResponseEntity<?> getResponseBody(String uri) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
        ResponseEntity<?> result =
                restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        return result;
    }

    public String getTop10() {
        String uri = "https://api.themoviedb.org/3/discover/movie?api_key=" + MovieDB.KEY + "&language=en-US&sort_by=popularity.desc&include_adult=false";
        ResponseEntity<?> result = getResponseBody(uri);
        //return result.getBody().toString();

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
        //return top10movies.get(0).getPosterPath();

    }

    public String searchForMovie(String query) throws UnsupportedEncodingException {
        String uri = "https://api.themoviedb.org/3/search/movie?api_key=" + MovieDB.KEY +
                    "&language=en-US&query=" + URLEncoder.encode(query, StandardCharsets.UTF_8.toString()) + "&page=1&include_adult=false";
        ResponseEntity<?> result = getResponseBody(uri);

        return result.getBody().toString();

        //return "";
    }

    public String getMovieDetails(String id) {
        String uri = "https://api.themoviedb.org/3/movie/" + id + "?api_key=" + MovieDB.KEY + "&language=en-US";
        ResponseEntity<?> result = getResponseBody(uri);

        return result.getBody().toString();
    }

}
