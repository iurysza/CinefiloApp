package site.iurysouza.cinefilo.presentation.medialist;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.util.Utils;

/**
 * Created by Iury Souza on 27/01/2017.
 */

public enum EmptyListCharacter {
  PulpFiction(R.drawable.empty_pulpfiction, R.string.empty_pulpfiction),
  ClockWorkOrange(R.drawable.empty_cloworkorange, R.string.empty_clockworkorange),
  TaxiDriver(R.drawable.empty_taxidriver, R.string.empty_taxidriver),
  Nemo(R.drawable.empty_nemo, R.string.empty_nemo),
  ToyStory(R.drawable.empty_toyalien, R.string.empty_toyalien);

  private final int characterArt;
  private final int characterQuote;

  EmptyListCharacter(@DrawableRes int characterArt, @StringRes int characterQuote) {
    this.characterArt = characterArt;
    this.characterQuote = characterQuote;
  }

  public @DrawableRes int getCharacterArt() {
    return characterArt;
  }

  public static EmptyListCharacter getRandomCharacter() {
    int randomNumber= Utils.getRandomNumberInRange(EmptyListCharacter.values().length-1);
    return EmptyListCharacter.values()[randomNumber];
  }
  public int getCharacterQuote() {
    return characterQuote;
  }
}
