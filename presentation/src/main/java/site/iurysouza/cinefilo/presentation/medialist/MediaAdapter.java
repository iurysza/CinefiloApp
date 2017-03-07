package site.iurysouza.cinefilo.presentation.medialist;

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
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.presentation.base.BaseActivity;
import site.iurysouza.cinefilo.presentation.moviedetail.MovieDetailActivity;
import site.iurysouza.cinefilo.presentation.medialist.entity.WatchMediaValue;
import site.iurysouza.cinefilo.domain.medialist.MediaFilter;

/**
 * Created by Iury Souza on 15/12/2016.
 */

class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.ViewHolder> {

  private final Picasso picasso;
  private List<WatchMediaValue> mediaValueList = Collections.emptyList();

  MediaAdapter(Picasso picasso) {
    this.picasso = picasso;
    setHasStableIds(true);
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
    MediaItemView view = (MediaItemView) LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.media_list_item, viewGroup, false);
    return new ViewHolder(view);
  }

  @Override public void onBindViewHolder(ViewHolder viewHolder, int i) {
    viewHolder.bindTo(mediaValueList.get(i));
  }

  @Override public long getItemId(int position) {
    return position;
  }

  @Override public int getItemCount() {
    return mediaValueList.size();
  }

  void addAllMedia(List<WatchMediaValue> mediaValues) {

    if (mediaValueList.isEmpty()) {
      mediaValueList = mediaValues;
      notifyDataSetChanged();
      return;
    }

    appendMedia(mediaValues);
  }

  public void clear() {
    mediaValueList.clear();
    notifyDataSetChanged();
  }

  private void appendMedia(List<WatchMediaValue> mediaList) {
    int positionStart = mediaValueList.size() - 1;
    mediaValueList.addAll(mediaList);
    notifyItemRangeInserted(positionStart, mediaList.size());
  }

  public void replaceList(List<WatchMediaValue> mediaValues) {
    //final MediaDiffCallBack diffCallback = new MediaDiffCallBack(mediaValueList, mediaValues);
    //final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
    //diffResult.dispatchUpdatesTo(this);

    this.mediaValueList.clear();
    this.mediaValueList.addAll(mediaValues);
    notifyDataSetChanged();
  }

  List<WatchMediaValue> filterAdapter(MediaFilter filter) {
    List<WatchMediaValue> filteredList = new ArrayList<>();
    boolean isGenderFilterValid = filter.getGenderList() != null;
    for (WatchMediaValue media : mediaValueList) {
      if (isGenderFilterValid) {
        for (Integer gender : filter.getGenderList()) {
          if (media.genre() == gender) {
            filterByScore(filter, filteredList, media);
          }
        }
      } else {
        filterByScore(filter, filteredList, media);
      }
    }
    return filteredList;
  }

  private void filterByScore(MediaFilter filter, List<WatchMediaValue> filteredList,
      WatchMediaValue media) {
    if (media.voteAverage() >= filter.getMinScore()) {
      isDateValid(filter, filteredList, media);
    }
  }

  private void isDateValid(MediaFilter filter, List<WatchMediaValue> filteredList,
      WatchMediaValue media) {
    int releaseYear = media.releaseDate().getYear();
    if (releaseYear >= filter.getStartDate()
        && releaseYear <= filter.getEndDate()) {
      filteredList.add(media);
    }
  }

  final class ViewHolder extends RecyclerView.ViewHolder {
    final MediaItemView itemView;

    ViewHolder(MediaItemView itemView) {
      super(itemView);
      this.itemView = itemView;
      this.itemView.setOnClickListener(v -> {
        Context context = v.getContext();
        WatchMediaValue movie = mediaValueList.get(getAdapterPosition());
        openDetailActivityWithSharedElements(movie, context);
      });
    }

    private void openDetailActivityWithSharedElements(WatchMediaValue movie, Context context) {
      Intent startIntent = MovieDetailActivity.getStartIntent(context, movie);
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        View posterView = itemView.findViewById(R.id.movie_item_picture_card);
        String posterTransition = context.getResources().getString(R.string.poster_card_transition);
        Pair<View, String> posterPair = new android.util.Pair<>(posterView, posterTransition);
        View titleView = itemView.findViewById(R.id.movie_item_title_text);
        String titleTransition = context.getResources().getString(R.string.media_title_transition);
        Pair<View, String> titlePair = new android.util.Pair<>(titleView, titleTransition);
        View cardView = itemView.findViewById(R.id.movie_item_card);
        String cardTransition = context.getResources().getString(R.string.detail_card_transition);
        Pair<View, String> cardPair = new android.util.Pair<>(cardView, cardTransition);
        View genreView = itemView.findViewById(R.id.movie_item_genre);
        String genreTransition = context.getResources().getString(R.string.detail_genre_transition);
        Pair<View, String> genrePair = new android.util.Pair<>(genreView, genreTransition);
        View statusBar = ((BaseActivity) context).findViewById(android.R.id.statusBarBackground);
        Pair<View, String> statusBarPair =
            Pair.create(statusBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME);

        ActivityOptions options = ActivityOptions
            .makeSceneTransitionAnimation((Activity) context, posterPair, cardPair, titlePair,statusBarPair,
                genrePair);
        context.startActivity(startIntent, options.toBundle());
      } else {
        context.startActivity(startIntent);
      }
    }

    void bindTo(WatchMediaValue movie) {
      itemView.bindTo(movie, picasso);
    }
  }
}