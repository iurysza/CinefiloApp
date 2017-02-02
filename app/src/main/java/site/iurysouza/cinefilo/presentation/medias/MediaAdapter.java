package site.iurysouza.cinefilo.presentation.medias;

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
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import site.iurysouza.cinefilo.R;
import site.iurysouza.cinefilo.domain.MediaFilter;
import site.iurysouza.cinefilo.domain.entity.WatchMediaValue;
import site.iurysouza.cinefilo.presentation.mediadetail.MediaDetailActivity;
import site.iurysouza.cinefilo.presentation.medias.filter.GenderEnum;

/**
 * Created by Iury Souza on 15/12/2016.
 */

class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.ViewHolder> {

  private final Picasso picasso;
  private final OnAdapterClickListener movieClickListener;
  private List<WatchMediaValue> mediaValueList = Collections.emptyList();

  MediaAdapter(Picasso picasso, OnAdapterClickListener movieClickListener) {
    this.picasso = picasso;
    this.movieClickListener = movieClickListener;
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
    this.mediaValueList.clear();
    this.mediaValueList.addAll(mediaValues);
    notifyDataSetChanged();
    //diffResult.dispatchUpdatesTo(this);
  }

  WatchMediaValue getFeauturedMovie() {
    return getRandomMovieWithBackDrop(mediaValueList);
  }

  private WatchMediaValue getRandomMovieWithBackDrop(List<WatchMediaValue> movieList) {
    if (movieList.isEmpty()) {
      return null;
    }
    WatchMediaValue movie = mediaValueList.get((new Random()).nextInt(mediaValueList.size()));
    if (movie.backdropPath() == null) {
      return getRandomMovieWithBackDrop(movieList);
    } else {
      return movie;
    }
  }

  public List<WatchMediaValue> getAdapterListFilteredBy(List<GenderEnum> genderEnumList) {
    List<WatchMediaValue> filteredList = new ArrayList<>();
    for (WatchMediaValue media : mediaValueList) {
      for (GenderEnum gender : genderEnumList) {
        if (media.genre() == gender.getGenreId()) {
          filteredList.add(media);
        }
      }
    }
    return filteredList;
  }

  public List<WatchMediaValue> filterAdapter(MediaFilter filter) {
    List<WatchMediaValue> filteredList = new ArrayList<>();

    boolean isGenderFilterValid = filter.getGenderList() != null;

    for (WatchMediaValue media : mediaValueList) {
      if (isGenderFilterValid) {
        for (GenderEnum gender : filter.getGenderList()) {
          if (media.genre() == gender.getGenreId()) {
            if (media.voteAverage() >= filter.getMinScore()) {
              isDateValid(filter, filteredList, media);
            }
          }
        }
      } else {
        if (media.voteAverage() >= filter.getMinScore()) {
          isDateValid(filter, filteredList, media);
        }
      }
    }
    return filteredList;
  }

  private void isDateValid(MediaFilter filter, List<WatchMediaValue> filteredList,
      WatchMediaValue media) {
    int releaseYear = media.releaseDate().getYear();
    if (releaseYear >= filter.getStartDate()
        && releaseYear <= filter.getEndDate()) {
      filteredList.add(media);
    }
  }

  interface OnAdapterClickListener {
    void onItemClicked(WatchMediaValue mediaValue);
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
      Intent startIntent = MediaDetailActivity.getStartIntent(context, movie);
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

        ActivityOptions options = ActivityOptions
            .makeSceneTransitionAnimation((Activity) context, posterPair, cardPair, titlePair, genrePair);
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