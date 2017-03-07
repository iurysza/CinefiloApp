package site.iurysouza.cinefilo.util;

import site.iurysouza.cinefilo.model.entities.pojo.Movie;

/**
 * Created by Iury Souza on 22/02/2017.
 */

public class MappingUtils {
  public static boolean isInvalid(Movie movie) {
    return (isEmptyString(movie.getOriginalTitle()) ||
        isEmptyString(movie.getPosterPath()) ||
        isEmptyString(movie.getOverview()) ||
        movie.getReleaseDate() == null);
  }
  public static boolean isEmptyString(final String s) {
    // Null-safe, short-circuit evaluation.
    return s == null || s.trim().isEmpty();
  }

  public static int getIntValueSafely(Long longValue) {
    if (longValue != null) {
      return longValue.intValue();
    } else {
      return 0;
    }
  }

}
