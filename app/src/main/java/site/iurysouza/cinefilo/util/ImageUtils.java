package site.iurysouza.cinefilo.util;

/**
 * Created by Iury Souza on 20/12/2016.
 */

public class ImageUtils {
  public static String getPosterUrl(String url) {
    return Constants.MOVIE_DB_API.BASE_IMAGE_URL + url;
  }
  public static String getBackDropUrl(String url) {
    return Constants.MOVIE_DB_API.BASE_IMAGE_URL + url;
  }
}
