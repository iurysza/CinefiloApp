package site.iurysouza.cinefilo.presentation.medias.filter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import io.apptik.widget.MultiSlider;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.domain.watchmedialist.SortingMethod;

import static site.iurysouza.cinefilo.domain.watchmedialist.SortingMethod.ALPHABETIC_BOTTOM;
import static site.iurysouza.cinefilo.domain.watchmedialist.SortingMethod.ALPHABETIC_TOP;
import static site.iurysouza.cinefilo.domain.watchmedialist.SortingMethod.DATE_BOTTOM;
import static site.iurysouza.cinefilo.domain.watchmedialist.SortingMethod.DATE_TOP;
import static site.iurysouza.cinefilo.domain.watchmedialist.SortingMethod.POPULAR_BOTTOM;
import static site.iurysouza.cinefilo.domain.watchmedialist.SortingMethod.POPULAR_TOP;
import static site.iurysouza.cinefilo.domain.watchmedialist.SortingMethod.RATING_BOTTOM;
import static site.iurysouza.cinefilo.domain.watchmedialist.SortingMethod.RATING_TOP;

/**
 * Created by Iury Souza on 20/01/2017.
 */

public class FilterPagerAdapter extends PagerAdapter {

  public static final int DEFAULT_END_DATE = 2017;
  private static final int DEFAULT_INITIAL_DATE = 1930;
  private Context context;
  private FilterViewManager.OnAdapterClickListener listener;

  public FilterPagerAdapter(Context context, FilterViewManager.OnAdapterClickListener listener) {
    this.context = context;
    this.listener = listener;
  }

  @Override
  public Object instantiateItem(ViewGroup collection, int position) {
    FilterPagerEnum currentFilter = FilterPagerEnum.values()[position];
    ViewGroup layout = getView(collection, currentFilter.getLayoutResId());

    if (currentFilter.getLayoutResId() == FilterPagerEnum.GENRE_FILTER.getLayoutResId()) {
      bindGenreFilterLayout(layout);
    } else {
      bindSliderFilterLayout(layout);
    }
    return layout;
  }

  private void bindSliderFilterLayout(ViewGroup layout) {
    MultiSlider ratingSlider = (MultiSlider) layout.findViewById(R.id.filter_min_rate_slider);
    TextView minRate = (TextView) layout.findViewById(R.id.filter_min_rate);
    ratingSlider.setOnThumbValueChangeListener((multiSlider, thumb, thumbIndex, value) -> {
      minRate.setText(String.valueOf(value));
      listener.onMinScoreChanged(value);
    });
    ratingSlider.getThumb(0).setValue(6);
    MultiSlider dateSlider = (MultiSlider) layout.findViewById(R.id.filter_date_slider);
    TextView initialDate = (TextView) layout.findViewById(R.id.filter_init_date);
    TextView finalDate = (TextView) layout.findViewById(R.id.filter_final_date);
    setupSortButtons(layout);
    dateSlider.getThumb(0).setValue(70);
    dateSlider.getThumb(1).setValue(100);

    dateSlider.setOnThumbValueChangeListener((multiSlider, thumb, thumbIndex, value) -> {
      int dateValue = convertSliderValueToDate(value);
      if (thumbIndex == 0) {
        initialDate.setText(String.valueOf(dateValue));
        listener.onStartDateChanged(dateValue);
      } else {
        finalDate.setText(String.valueOf(dateValue));
        listener.onEndDateChanged(dateValue);
      }
    });
  }

  private void setupSortButtons(ViewGroup layout) {
    SortButton alphaBtn = (SortButton) layout.findViewById(R.id.filter_btn_alphabetic);
    SortButton popularBtn = (SortButton) layout.findViewById(R.id.filter_btn_popular);
    SortButton ratingBtn = (SortButton) layout.findViewById(R.id.filter_btn_rating);
    SortButton dateBtn = (SortButton) layout.findViewById(R.id.filter_btn_date);
    setListener(alphaBtn, popularBtn, ratingBtn, dateBtn, ALPHABETIC_TOP,
        ALPHABETIC_BOTTOM);
    setListener(popularBtn, alphaBtn, ratingBtn, dateBtn, POPULAR_TOP,
        POPULAR_BOTTOM);
    setListener(ratingBtn, alphaBtn, popularBtn, dateBtn, RATING_TOP,
        RATING_BOTTOM);
    setListener(dateBtn, alphaBtn, ratingBtn, popularBtn, DATE_TOP,
        DATE_BOTTOM);
  }

  private void setListener(SortButton sortButton, SortButton popularBtn, SortButton ratingBtn,
      SortButton dateBtn, SortingMethod sortByTop,
      SortingMethod sortByBottom) {
    sortButton.setOnClickListener(v -> {
      SortButton.SORT sortState = sortButton.getClickedState();
      if (sortState == SortButton.SORT.TOP) {
        listener.onSortingMethodChanged(sortByTop);
        popularBtn.setSortState(SortButton.SORT.OFF);
        ratingBtn.setSortState(SortButton.SORT.OFF);
        dateBtn.setSortState(SortButton.SORT.OFF);
      }
      if (sortState == SortButton.SORT.BOTTOM) {
        listener.onSortingMethodChanged(sortByBottom);
      }
      if (sortState == SortButton.SORT.OFF &&
          popularBtn.getSortState() == SortButton.SORT.OFF &&
          ratingBtn.getSortState() == SortButton.SORT.OFF &&
          dateBtn.getSortState() == SortButton.SORT.OFF
          ) {
        listener.onSortingMethodChanged(null);
      }
    });
  }

  private int convertDateToSliderValue(double dateToConvert) {
    return (int) (100 - (DEFAULT_END_DATE - dateToConvert) * (DEFAULT_END_DATE - 1930));
  }

  private int convertSliderValueToDate(double value) {
    return (int) (DEFAULT_INITIAL_DATE + ((value / 100) * (DEFAULT_END_DATE
        - DEFAULT_INITIAL_DATE)));
  }

  private void bindGenreFilterLayout(ViewGroup layout) {
    RecyclerView gridList = (RecyclerView) layout.findViewById(R.id.genre_filter_list);
    gridList.setNestedScrollingEnabled(false);
    gridList.setHasFixedSize(true);
    gridList.addItemDecoration(new GridSpacingItemDecoration(3, 25, true));
    gridList.setLayoutManager(new GridLayoutManager(context, 3));
    GenreGridAdapter adapter = new GenreGridAdapter(context, listener);
    gridList.setAdapter(adapter);
  }

  private ViewGroup getView(ViewGroup collection, @LayoutRes int layoutResId) {
    LayoutInflater inflater = LayoutInflater.from(context);
    ViewGroup layout = (ViewGroup) inflater.inflate(layoutResId, collection, false);
    collection.addView(layout);
    return layout;
  }

  @Override
  public void destroyItem(ViewGroup collection, int position, Object view) {
    collection.removeView((View) view);
  }

  @Override
  public int getCount() {
    return FilterPagerEnum.values().length;
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  @Override
  public CharSequence getPageTitle(int position) {
    FilterPagerEnum customPagerEnum = FilterPagerEnum.values()[position];
    return context.getString(customPagerEnum.getTitleResId());
  }
}