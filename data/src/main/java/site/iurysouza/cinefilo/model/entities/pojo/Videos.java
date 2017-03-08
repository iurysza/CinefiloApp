
package site.iurysouza.cinefilo.model.entities.pojo;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;

@Data
public class Videos {
  @SerializedName("results")
  public List<Result> results;
}