package site.iurysouza.cinefilo.util;

/**
 * Created by Iury Souza on 12/10/2016.
 */
public class Constants {
  public static final String CACHE_CONTROL = "Cache-Control";
  public static final String CONTENT_TYPE = "Content-Type";
  public static final String JSON_FORMAT = "application/json";
  public static final String OFFLINE_CACHE = "OFFLINE_CACHE";

  public class MOVIE_DB_API {
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w500";
    public static final String BASE_BACKDROP_URL = "http://image.tmdb.org/t/p/w720";
    public static final String API_KEY = "7ea8abd52d3a0236a7ae2d2c0d986483";
  }

  public class Movies {
    public static final int REC_MOVIES = 0;
    public static final int POP_MOVIES = 1;
    public static final int TOP_MOVIES = 2;
  }

}
