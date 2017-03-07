package site.iurysouza.cinefilo.model.data.entity;

import java.util.Collections;
import java.util.Date;
import site.iurysouza.cinefilo.model.entities.pojo.Movie;

/**
 * Created by Iury Souza on 10/02/2017.
 */
class MovieFactory {
  private static Long movieId = 0L;
  private static String title = "movie ";
  private static String overview = "overview ";
  private static String posterPath = "poster path ";

  static Movie createValidMovieStub() {
    movieId++;
    return Movie.builder()
        .id(movieId)
        .originalTitle(title + movieId)
        .overview(overview + movieId)
        .posterPath(posterPath + movieId)
        .releaseDate(new Date())
        .spokenLanguageList(Collections.emptyList())
        .video(false)
        .voteCount(0L)
        .voteAverage(0.0)
        .adult(false)
        .backdropPath("")
        .budget(0L)
        .genreIds(new Integer[0])
        .genreList(Collections.emptyList())
        .homepage("")
        .imdbId("")
        .originalLanguage("")
        .popularity(0.0)
        .productionCompanyList(Collections.emptyList())
        .productionCountryList(Collections.emptyList())
        .revenue(0L)
        .runtime(0L)
        .status("")
        .tagline("")
        .build();
  }

  static Movie createStubWithInvalidDate() {
    movieId++;
    return Movie.builder()
        .id(movieId)
        .originalTitle(title + movieId)
        .overview(overview + movieId)
        .posterPath(posterPath + movieId)
        .releaseDate(null)
        .spokenLanguageList(Collections.emptyList())
        .video(false)
        .voteCount(0L)
        .voteAverage(0.0)
        .adult(false)
        .backdropPath("")
        .budget(0L)
        .genreIds(new Integer[0])
        .genreList(Collections.emptyList())
        .homepage("")
        .imdbId("")
        .originalLanguage("")
        .popularity(0.0)
        .productionCompanyList(Collections.emptyList())
        .productionCountryList(Collections.emptyList())
        .revenue(0L)
        .runtime(0L)
        .status("")
        .tagline("")
        .build();
  }

  static Movie createStubWithInvalidTitle() {
    movieId++;
    return Movie.builder()
        .id(movieId)
        .originalTitle(null)
        .overview(overview + movieId)
        .posterPath(posterPath + movieId)
        .releaseDate(new Date())
        .spokenLanguageList(Collections.emptyList())
        .video(false)
        .voteCount(0L)
        .voteAverage(0.0)
        .adult(false)
        .backdropPath("")
        .budget(0L)
        .genreIds(new Integer[0])
        .genreList(Collections.emptyList())
        .homepage("")
        .imdbId("")
        .originalLanguage("")
        .popularity(0.0)
        .productionCompanyList(Collections.emptyList())
        .productionCountryList(Collections.emptyList())
        .revenue(0L)
        .runtime(0L)
        .status("")
        .tagline("")
        .build();
  }

  static Movie createStubWithInvalidOverView() {
    movieId++;
    return Movie.builder()
        .id(movieId)
        .originalTitle(title + movieId)
        .overview(null)
        .posterPath(posterPath + movieId)
        .releaseDate(new Date())
        .spokenLanguageList(Collections.emptyList())
        .video(false)
        .voteCount(0L)
        .voteAverage(0.0)
        .adult(false)
        .backdropPath("")
        .budget(0L)
        .genreIds(new Integer[0])
        .genreList(Collections.emptyList())
        .homepage("")
        .imdbId("")
        .originalLanguage("")
        .popularity(0.0)
        .productionCompanyList(Collections.emptyList())
        .productionCountryList(Collections.emptyList())
        .revenue(0L)
        .runtime(0L)
        .status("")
        .tagline("")
        .build();
  }

  static Movie createStubWithInvalidPosterPath() {
    movieId++;
    return Movie.builder()
        .id(movieId)
        .originalTitle(title + movieId)
        .overview(overview + movieId)
        .posterPath(null)
        .releaseDate(new Date())
        .spokenLanguageList(Collections.emptyList())
        .video(false)
        .voteCount(0L)
        .voteAverage(0.0)
        .adult(false)
        .backdropPath("")
        .budget(0L)
        .genreIds(new Integer[0])
        .genreList(Collections.emptyList())
        .homepage("")
        .imdbId("")
        .originalLanguage("")
        .popularity(0.0)
        .productionCompanyList(Collections.emptyList())
        .productionCountryList(Collections.emptyList())
        .revenue(0L)
        .runtime(0L)
        .status("")
        .tagline("")
        .build();
  }
}
