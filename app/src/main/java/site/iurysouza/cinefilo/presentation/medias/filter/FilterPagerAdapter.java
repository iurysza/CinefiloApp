package site.iurysouza.cinefilo.presentation.medias.filter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import site.iurysouza.cinefilo.R;

/**
 * Created by Iury Souza on 20/01/2017.
 */

public class FilterPagerAdapter extends PagerAdapter {

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
      bindGeneralFilterLayout(layout);
    }
    return layout;
  }

  private void bindGeneralFilterLayout(ViewGroup layout) {

  }

  private void bindGenreFilterLayout(ViewGroup layout) {
    RecyclerView gridList = (RecyclerView) layout.findViewById(R.id.genre_filter_list);
    gridList.addItemDecoration(new GridSpacingItemDecoration(3, 50, true));
    gridList.setHasFixedSize(true);
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