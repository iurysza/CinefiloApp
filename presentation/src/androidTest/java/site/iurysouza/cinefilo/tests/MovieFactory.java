package site.iurysouza.cinefilo.tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import site.iurysouza.cinefilo.model.entities.pojo.Movie;
import site.iurysouza.cinefilo.model.entities.pojo.MovieResults;

/**
 * Created by Iury Souza on 10/02/2017.
 */
public class MovieFactory {
  private static Long MOVIE_ID = 0L;
  private static String TITLE = "movie ";
  private static String OVERVIEW = "overview ";
  private static String POSTER_PATH = "poster path ";
  private static String STATUS = "status ";
  private static String TAG_LINE = "tag line ";

  /**
   * @return a typical movie returned inside a {@link MovieResults}
   * Each call increments it's id value
   */
  public static Movie createValidMovieStub() {
    MOVIE_ID++;
    return Movie.builder()
        .id(MOVIE_ID)
        .originalTitle(TITLE + MOVIE_ID)
        .overview(OVERVIEW + MOVIE_ID)
        .posterPath(POSTER_PATH + MOVIE_ID)
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

  /**
   * @return A movie Pojo based on the Fight Club Json
   */
  public static Movie createRealMovie() {
    return Movie.builder()
        .id(550L)
        .originalTitle("Fight Club")
        .overview("A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \\\"fight clubs\\\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.")
        .posterPath("/fCayJrkfRaCRCTh8GqN30f8oyQF.jpg")
        .releaseDate(new Date())
        .spokenLanguageList(Collections.emptyList())
        .video(false)
        .voteCount(0L)
        .voteAverage(7.8)
        .adult(false)
        .backdropPath("")
        .budget(63000000L)
        .genreIds(new Integer[0])
        .genreList(Collections.emptyList())
        .homepage("")
        .imdbId("")
        .originalLanguage("")
        .popularity(0.0)
        .productionCompanyList(Collections.emptyList())
        .productionCountryList(Collections.emptyList())
        .revenue(100853753L)
        .runtime(139L)
        .status("")
        .tagline("")
        .build();
  }

  /**
   * @return a typical movie returned inside a {@link MovieResults}
   * Mosts of its fake data is based on the id argument
   */
  public static Movie createMovieWithId(int id) {
    return Movie.builder()
        .id((long) id)
        .originalTitle(TITLE + (long) id)
        .overview(OVERVIEW + (long) id)
        .posterPath(POSTER_PATH + (long) id)
        .releaseDate(new Date())
        .spokenLanguageList(Collections.emptyList())
        .video(false)
        .voteCount(0L)
        .voteAverage(0.0)
        .adult(false)
        .backdropPath("")
        .budget((long) id)
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

  /**
   * @return a typical movie with all valid fields
   * Mosts of its fake data is based on the id argument
   */
  public static Movie createMovieDetailWithId(int id) {
    long longId = (long) id;
    return Movie.builder()
        .id(longId)
        .originalTitle(TITLE + id)
        .overview(OVERVIEW + id)
        .posterPath(POSTER_PATH + id)
        .releaseDate(new Date())
        .spokenLanguageList(Collections.emptyList())
        .video(false)
        .voteCount(longId)
        .voteAverage((double) longId)
        .adult(false)
        .backdropPath("")
        .budget(longId)
        .genreIds(new Integer[0])
        .genreList(Collections.emptyList())
        .homepage("")
        .imdbId("")
        .originalLanguage("")
        .popularity((double) longId)
        .productionCompanyList(Collections.emptyList())
        .productionCountryList(Collections.emptyList())
        .revenue(longId)
        .runtime(longId)
        .status(STATUS + id)
        .tagline(TAG_LINE + id)
        .build();
  }

  public static Movie createStubWithInvalidDate() {
    MOVIE_ID++;
    return Movie.builder()
        .id(MOVIE_ID)
        .originalTitle(TITLE + MOVIE_ID)
        .overview(OVERVIEW + MOVIE_ID)
        .posterPath(POSTER_PATH + MOVIE_ID)
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

  public static Movie createStubWithInvalidTitle() {
    MOVIE_ID++;
    return Movie.builder()
        .id(MOVIE_ID)
        .originalTitle(null)
        .overview(OVERVIEW + MOVIE_ID)
        .posterPath(POSTER_PATH + MOVIE_ID)
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

  public static Movie createStubWithInvalidOverView() {
    MOVIE_ID++;
    return Movie.builder()
        .id(MOVIE_ID)
        .originalTitle(TITLE + MOVIE_ID)
        .overview(null)
        .posterPath(POSTER_PATH + MOVIE_ID)
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
    MOVIE_ID++;
    return Movie.builder()
        .id(MOVIE_ID)
        .originalTitle(TITLE + MOVIE_ID)
        .overview(OVERVIEW + MOVIE_ID)
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

  public static MovieResults createMovieResultsByPage(int page) {
    MovieResults movieResults = new MovieResults();
    List<Movie> movieList = new ArrayList<>();
    for (int i = 0; i < 20; i++) {
      movieList.add(createValidMovieStub());
    }
    movieResults.setPage(page);
    movieResults.setMovieList(movieList);
    return movieResults;
  }
}
