package site.iurysouza.cinefilo.presentation.medias.filter;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import site.iurysouza.cinefilo.R;

/**
 * Created by Iury Souza on 14/01/2017.
 */

public enum GenderEnum {
  ACTION(R.string.genre_action, R.drawable.ic_action, 28),
  ADVENTURE(R.string.genre_adventure, R.drawable.ic_adventure, 12),
  ANIMATION(R.string.genre_animation, R.drawable.ic_animation, 16),
  COMEDY(R.string.genre_comedy, R.drawable.ic_comedy, 35),
  CRIME(R.string.genre_crime, R.drawable.ic_crime, 80),
  DOCUMENTARY(R.string.genre_documentary, R.drawable.ic_documentary, 99),
  DRAMA(R.string.genre_drama, R.drawable.ic_drama, 18),
  FAMILY(R.string.genre_family, R.drawable.ic_family, 10751),
  HISTORY(R.string.genre_history, R.drawable.ic_history, 36),
  Fantasy(R.string.genre_fantasy, R.drawable.ic_fantasy, 14),
  HORROR(R.string.genre_horror, R.drawable.ic_horror, 27),
  MUSIC(R.string.genre_music, R.drawable.ic_music, 10402),
  MYSTERY(R.string.genre_mystery, R.drawable.ic_mystery, 9648),
  ROMANCE(R.string.genre_romance, R.drawable.ic_romance, 10749),
  SCY_FI(R.string.genre_scy_fi, R.drawable.ic_scy_fi, 878),
  TV_MOVIE(R.string.genre_tv_movie, R.drawable.ic_tv_movie, 10770),
  THRILLER(R.string.genre_thriller, R.drawable.ic_thriller, 53),
  WAR(R.string.genre_war, R.drawable.ic_war, 10752),
  Western(R.string.genre_Western, R.drawable.ic_western, 37);

  private int genreId;
  @StringRes private int genreNameRes;
  @DrawableRes private int genreIconRes;

  GenderEnum(@StringRes int genreNameRes, @DrawableRes int genreIconRes, int genreId) {
    this.genreNameRes = genreNameRes;
    this.genreIconRes = genreIconRes;
    this.genreId = genreId;
  }

  public int getGenreId() {
    return genreId;
  }

   public @StringRes int getGenreNameRes() {
    return genreNameRes;
  }

  public @DrawableRes int getGenreIconRes() {
    return genreIconRes;
  }
}

