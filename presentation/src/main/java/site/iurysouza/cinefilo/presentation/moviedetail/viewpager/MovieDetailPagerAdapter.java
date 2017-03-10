package site.iurysouza.cinefilo.presentation.moviedetail.viewpager;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.transition.Fade;
import android.support.transition.TransitionManager;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Set;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.domain.moviedetail.Credits;
import site.iurysouza.cinefilo.presentation.moviedetail.entity.MovieDetailValue;

/**
 * Created by Iury Souza on 31/01/2017.
 */

public class MovieDetailPagerAdapter extends PagerAdapter {

  private Context context;
  private TextView overviewText;
  private TextView revenueText;
  private TextView budgetText;
  private TextView languageText;
  private TextView tagLineText;
  private LinearLayout langContainer;
  private LinearLayout budgetContainer;
  private LinearLayout revContainer;
  private LinearLayout overviewRoot;
  private TextView castTitleText;
  private RecyclerView castList;

  public MovieDetailPagerAdapter(Context context) {
    this.context = context;
  }

  @Override public int getCount() {
    return MovieDetailPagerEnum.values().length;
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  @Override
  public void destroyItem(ViewGroup collection, int position, Object view) {
    collection.removeView((View) view);
  }

  @Override
  public CharSequence getPageTitle(int position) {
    MovieDetailPagerEnum customPagerEnum = MovieDetailPagerEnum.values()[position];
    return context.getString(customPagerEnum.getTitleResId());
  }

  private ViewGroup getView(ViewGroup collection, @LayoutRes int layoutResId) {
    LayoutInflater inflater = LayoutInflater.from(context);
    ViewGroup layout = (ViewGroup) inflater.inflate(layoutResId, collection, false);
    collection.addView(layout);
    return layout;
  }

  @Override public Object instantiateItem(ViewGroup container, int position) {
    MovieDetailPagerEnum currentPage = MovieDetailPagerEnum.values()[position];
    ViewGroup layout = getView(container, currentPage.getLayoutResId());

    if (currentPage.getLayoutResId() == MovieDetailPagerEnum.MEDIA_OVERVIEW.getLayoutResId()) {
      bindOverviewLayout(layout);
    } else {
      bindPeopleLayout(layout);
    }
    return layout;
  }

  private void bindPeopleLayout(ViewGroup layout) {
    castTitleText = (TextView) layout.findViewById(R.id.cast_page_title);
    castList = (RecyclerView) layout.findViewById(R.id.cast_page_list);
  }

  private void bindOverviewLayout(ViewGroup layout) {
    overviewText = (TextView) layout.findViewById(R.id.overview_page_overview);
    overviewRoot = (LinearLayout) layout.findViewById(R.id.overview_page_root);
    revenueText = (TextView) layout.findViewById(R.id.overview_page_revenue);
    budgetText = (TextView) layout.findViewById(R.id.overview_page_budget);
    revContainer = (LinearLayout) layout.findViewById(R.id.overview_page_revenue_container);
    budgetContainer = (LinearLayout) layout.findViewById(R.id.overview_page_budget_container);
    langContainer = (LinearLayout) layout.findViewById(R.id.overview_page_language_container);
    languageText = (TextView) layout.findViewById(R.id.overview_page_original_language);
    tagLineText = (TextView) layout.findViewById(R.id.overview_page_tagline);
  }

  public void updateOverViewPage(MovieDetailValue movieDetailValue) {
    Integer revenue = movieDetailValue.revenue();
    Integer budget = movieDetailValue.budget();

    TransitionManager.beginDelayedTransition(overviewRoot, new Fade().setDuration(350));
    budgetText.setText(getFormattedMoneyValue(budget));
    revenueText.setText(getFormattedMoneyValue(revenue));
    overviewText.setText(movieDetailValue.overview());
    tagLineText.setText(movieDetailValue.tagLine());
    if (revenue == 0) {
      revContainer.setVisibility(View.GONE);
    } else {
      revContainer.setVisibility(View.VISIBLE);
    }
    if (budget == 0) {
      budgetContainer.setVisibility(View.GONE);
    } else {
      budgetContainer.setVisibility(View.VISIBLE);
    }

    String langText = getLanguageListText(movieDetailValue);
    if (!langText.isEmpty()) {
      langContainer.setVisibility(View.VISIBLE);
      languageText.setText(langText);
    } else {
      langContainer.setVisibility(View.GONE);
    }
  }

  private String getLanguageListText(MovieDetailValue movieDetailValue) {
    String langText = "";
    Set<String> langSet = movieDetailValue.spokenLanguageList().keySet();
    if (!langSet.isEmpty()) {
      if (langSet.size() == 1) {
        langText = langSet.iterator().next();
      } else {
        for (String language : langSet) {
          langText += language + ", ";
        }
        langText = langText.substring(0, langText.length() - 2);
      }
    }
    return langText + ".";
  }

  private String getFormattedMoneyValue(long value) {
    DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
    DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
    symbols.setGroupingSeparator(' ');
    formatter.setDecimalFormatSymbols(symbols);
    return "$ " + formatter.format(value);
  }

  public void updateCreditsPage(Credits credits) {
    castList.setLayoutManager(new GridLayoutManager(context, 3));
    CastAdapter castAdapter = new CastAdapter(context, credits.getCast());
    castList.setAdapter(castAdapter);
  }
}