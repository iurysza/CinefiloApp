package site.iurysouza.cinefilo.presentation.mediadetail.similarmovies;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.ArrayList;
import java.util.List;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.domain.entity.WatchMediaValue;
import site.iurysouza.cinefilo.presentation.base.BaseActivity;
import site.iurysouza.cinefilo.presentation.mediadetail.MediaDetailActivity;

/**
 * Created by Iury Souza on 03/02/2017.
 */

public class SimilarMoviesAdapter
    extends RecyclerView.Adapter<SimilarMoviesAdapter.SimilarMovieViewHolder> {


  List<WatchMediaValue> movieList = new ArrayList<>();
  private Context context;

  public SimilarMoviesAdapter(Context context) {
    this.context = context;
  }

  @Override
  public SimilarMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater
        .from(parent.getContext())
        .inflate(R.layout.similar_movies_list_item, parent, false);

    SimilarMovieViewHolder vh = new SimilarMoviesAdapter.SimilarMovieViewHolder(view);
    vh.itemView.setOnClickListener(v -> vh.onItemSelected());

    return vh;
  }

  @Override public void onBindViewHolder(SimilarMovieViewHolder holder, int position) {
    holder.bindDataTo(movieList.get(position));
  }

  @Override public int getItemCount() {
    return movieList.size();
  }

  public void addSimilarMovies(List<WatchMediaValue> mediaValues) {
    if (movieList.isEmpty()) {
      movieList.addAll(mediaValues);
      notifyDataSetChanged();
    } else {
      movieList.addAll(movieList.size() - 1, mediaValues);
      notifyItemRangeInserted(movieList.size() - 1, movieList.size());
    }
  }

  class SimilarMovieViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.similar_movie_poster) ImageView moviePoster;
    @BindView(R.id.similar_movie_title) TextView movieTitle;
    @BindView(R.id.similar_movie_overlay) FrameLayout movieOverlay;
    private WatchMediaValue watchMediaValue;

    SimilarMovieViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    void bindDataTo(WatchMediaValue watchMediaValue) {
      moviePoster.setTransitionName(null);
      this.watchMediaValue = watchMediaValue;
      movieTitle.setText(watchMediaValue.name());
      ItemColorManager
          .builder()
          .context(context)
          .imageView(moviePoster)
          .overlay(movieOverlay)
          .posterPath(watchMediaValue.posterPath())
          .build()
          .loadBitmapAndCreateColorPallete();
    }

    void onItemSelected() {
      openDetailActivityWithSharedElements(watchMediaValue, context);
    }

    private void openDetailActivityWithSharedElements(WatchMediaValue movie, Context context) {
      Intent startIntent = MediaDetailActivity.getStartIntentFromSimMovies(context, movie);
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        String posterTransition = context.getResources().getString(R.string.poster_card_transition);
        Pair<View, String> posterPair = new android.util.Pair<>(moviePoster, posterTransition);
        View statusBar = ((BaseActivity) context).findViewById(android.R.id.statusBarBackground);
        Pair<View, String> statusBarPair =
            Pair.create(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME);
        ActivityOptions options = ActivityOptions
            .makeSceneTransitionAnimation((Activity) context, posterPair,statusBarPair);
        context.startActivity(startIntent, options.toBundle());
      } else {
        context.startActivity(startIntent);
      }
    }
  }
}
