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

/**
 * Created by Iury Souza on 20/01/2017.
 */

public class FilterPagerAdapter extends PagerAdapter {

  private static final int DEFAULT_INITIAL_DATE = 1930;
  public static final int DEFAULT_END_DATE = 2017;
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
    MultiSlider slider = (MultiSlider) layout.findViewById(R.id.filter_date_slider);
    TextView initialDate = (TextView) layout.findViewById(R.id.filter_init_date);
    TextView finalDate = (TextView) layout.findViewById(R.id.filter_final_date);
    slider.getThumb(0).setValue(70);
    slider.getThumb(1).setValue(100);

    slider.setOnThumbValueChangeListener(new MultiSlider.OnThumbValueChangeListener() {
      @Override
      public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int thumbIndex,
          int value) {
        int dateValue = convertSliderValueToDate(value);
        if (thumbIndex == 0) {
          initialDate.setText(String.valueOf(dateValue));
          listener.onStartDateChanged(dateValue);
        } else {
          finalDate.setText(String.valueOf(dateValue));
          listener.onEndDateChanged(dateValue);
        }
      }
    });
  }

  private int convertDateToSliderValue(double dateToConvert) {
    return (int) (100 - (DEFAULT_END_DATE - dateToConvert)*(DEFAULT_END_DATE-1930));
  }

  private int convertSliderValueToDate(double value) {
    return (int) (DEFAULT_INITIAL_DATE + ((value / 100) * (DEFAULT_END_DATE - DEFAULT_INITIAL_DATE)));
  }

  private void bindGenreFilterLayout(ViewGroup layout) {
    RecyclerView gridList = (RecyclerView) layout.findViewById(R.id.genre_filter_list);
    gridList.setHasFixedSize(true);

    gridList.addItemDecoration(new GridSpacingItemDecoration(3, 50, true));
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