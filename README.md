# movie-theatre

## Endpoints

### 1. GET /
Returns a JSONArray of the top 10 movies, sorted by most popular.

### 2. GET /movie/{id}
Returns a JSON of the movie details corresponding to the provided ID.

### 3. GET /genres
Returns a JSON of all possible genres for movies. For front end to parse when displaying movie details.

### 4. POST /search
Accepts a JSON post body with a parameter called "searchTerm". Returns all movies with supplied search term.
Note: I did not find any API documentation about searching in the movie descriptions.
