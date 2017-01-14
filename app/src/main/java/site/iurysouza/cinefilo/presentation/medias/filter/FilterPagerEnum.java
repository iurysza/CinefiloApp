package site.iurysouza.cinefilo.presentation.medias.filter;

import site.iurysouza.cinefilo.R;

/**
 * Created by Iury Souza on 14/01/2017.
 */

public enum FilterPagerEnum {
  RED(R.string.genre_page_title, R.layout.genre_filter_layout),
  BLUE(R.string.filter_page_title, R.layout.filter_layout);

  private int titleResId;
  private int layoutResId;

  FilterPagerEnum(int titleResId, int layoutResId) {
    this.titleResId = titleResId;
    this.layoutResId = layoutResId;
  }

  public int getTitleResId() {
    return titleResId;
  }

  public int getLayoutResId() {
    return layoutResId;
  }
}

