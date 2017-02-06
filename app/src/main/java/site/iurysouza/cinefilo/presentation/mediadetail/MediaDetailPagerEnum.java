package site.iurysouza.cinefilo.presentation.mediadetail;

import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import site.iurysouza.cinefilo.R;

/**
 * Created by Iury Souza on 31/01/2017.
 */

public enum MediaDetailPagerEnum {
  MEDIA_OVERVIEW(R.string.detail_page_overview_title, R.layout.media_detail_page_overview_layout),
  MEDIA_CAST(R.string.detail_page_people_title, R.layout.media_detail_page_cast_layout);

  private int titleResId;
  private int layoutResId;

  MediaDetailPagerEnum(int titleResId, int layoutResId) {
    this.titleResId = titleResId;
    this.layoutResId = layoutResId;
  }

  public @StringRes int getTitleResId() {
    return titleResId;
  }

  public @LayoutRes int getLayoutResId() {
    return layoutResId;
  }
}

