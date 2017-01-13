package site.iurysouza.cinefilo.model.entities.realm;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;
import java.util.Date;
import lombok.Data;

/**
 * Created by Iury Souza on 12/10/2016.
 */
@Data
@RealmClass
public class RealmSeason implements RealmModel {
  public static final String ID = "id";
  public static final String QUERY_DATE = "queryDate";
  @PrimaryKey
  private Long id;
  private Long queryDate;
  private Date airDate;
  private Integer episodeCount;
  private String posterPath;
  private Integer seasonNumber;
}
