package site.iurysouza.cinefilo.presentation.medias.filter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import me.relex.circleindicator.CircleIndicator;
import mehdi.sakout.fancybuttons.FancyButton;
import rx.Observable;
import rx.subjects.BehaviorSubject;
import site.iurysouza.cinefilo.R;

/**
 * Created by Iury Souza on 14/01/2017.
 */

public class FilterViewManager {

  BehaviorSubject<GenderEnum> filterSubject = BehaviorSubject.create();

  ViewPager viewPager;
  CircleIndicator indicator;
  FancyButton btnApply;
  FancyButton btnClose;


  public FilterViewManager(FragmentActivity activity) {

    viewPager = (ViewPager) activity.findViewById(R.id.media_list_filter_viewpager);
    indicator = (CircleIndicator) activity.findViewById(R.id.media_list_page_indicator);
    btnApply = (FancyButton) activity.findViewById(R.id.filter_btn_apply);
    btnClose = (FancyButton) activity.findViewById(R.id.filter_btn_close);

    FilterPagerAdapter filterAdater = new FilterPagerAdapter(activity.getApplicationContext());
    viewPager.setAdapter(filterAdater);
    indicator.setViewPager(viewPager);
    btnClose.setOnClickListener(v -> filterSubject.onNext(null));
  }

  public Observable<GenderEnum> getFilterSubjectAsObservable() {
    return filterSubject.asObservable();
  }

  public class FilterPagerAdapter extends PagerAdapter {

    private Context context;

    public FilterPagerAdapter(Context context) {
      this.context = context;
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

      GenreGridAdapter adapter = new GenreGridAdapter(context,
          new GenreGridAdapter.OnAdapterClickListener() {
            @Override public void onItemSelected() {
              btnApply.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
            }

            @Override public void onNoneSelected() {
              btnApply.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            }
          });

      gridList.setAdapter(adapter);
      btnApply.setOnClickListener(v -> filterSubject.onNext(adapter.getSelectedGenres()));
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
}
