package site.iurysouza.cinefilo.presentation.mediadetail;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.model.data.entity.MovieDetailValue;

/**
 * Created by Iury Souza on 31/01/2017.
 */

public class MediaDetailPagerAdapter extends PagerAdapter {

  private Context context;
  private TextView overviewText;
  private TextView revenueText;
  private TextView budgetText;
  private TextView languageText;

  public MediaDetailPagerAdapter(Context context) {
    this.context = context;
  }

  @Override public int getCount() {
    return MediaDetailPagerEnum.values().length;
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  @Override public Object instantiateItem(ViewGroup container, int position) {
    MediaDetailPagerEnum currentPage = MediaDetailPagerEnum.values()[position];
    ViewGroup layout = getView(container, currentPage.getLayoutResId());

    if (currentPage.getLayoutResId() == MediaDetailPagerEnum.MEDIA_OVERVIEW.getLayoutResId()) {
      bindOverviewLayout(layout);
    } else {
      bindPeopleLayout(layout);
    }
    return layout;
  }

  private void bindPeopleLayout(ViewGroup layout) {
  }

  private void bindOverviewLayout(ViewGroup layout) {
    overviewText = (TextView) layout.findViewById(R.id.overview_page_overview);
    revenueText = (TextView) layout.findViewById(R.id.overview_page_revenue);
    budgetText = (TextView) layout.findViewById(R.id.overview_page_budget);
    languageText = (TextView) layout.findViewById(R.id.overview_page_original_language);

  }

  void updateOverViewPage(MovieDetailValue movieDetailValue) {
    overviewText.setText(movieDetailValue.overview());
    revenueText.setText(String.valueOf(movieDetailValue.revenue()));
    budgetText.setText(String.valueOf(movieDetailValue.budget()));
    languageText.setText(movieDetailValue.originalLanguage());

  }
  @Override
  public void destroyItem(ViewGroup collection, int position, Object view) {
    collection.removeView((View) view);
  }

  @Override
  public CharSequence getPageTitle(int position) {
    MediaDetailPagerEnum customPagerEnum = MediaDetailPagerEnum.values()[position];
    return context.getString(customPagerEnum.getTitleResId());
  }

  private ViewGroup getView(ViewGroup collection, @LayoutRes int layoutResId) {
    LayoutInflater inflater = LayoutInflater.from(context);
    ViewGroup layout = (ViewGroup) inflater.inflate(layoutResId, collection, false);
    collection.addView(layout);
    return layout;
  }
}
