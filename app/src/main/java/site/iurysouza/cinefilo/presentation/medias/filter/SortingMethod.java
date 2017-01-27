package site.iurysouza.cinefilo.presentation.medias.filter;

/**
 * Created by Iury Souza on 25/01/2017.
 */

public enum SortingMethod {
  RATING_TOP("vote_average.desc"),
  RATING_BOTTOM("vote_average.desc"),
  ALPHABETIC_BOTTOM("original_title.desc"),
  ALPHABETIC_TOP("original_title.asc"),
  POPULAR_TOP("popularity.asc"),
  POPULAR_BOTTOM("popularity.desc"),
  DATE_TOP("release_date.desc"),
  DATE_BOTTOM("release_date.asc");

  private String sortMethod;

  SortingMethod(String sortMethod) {

    this.sortMethod = sortMethod;
  }

  public String getSortMethod() {
    return sortMethod;
  }
}