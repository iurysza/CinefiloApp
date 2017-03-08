
package site.iurysouza.cinefilo.model.entities.pojo;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Result {
    @SerializedName("id")
    private String id;
    @SerializedName("iso_3166_1")
    private String iso31661;
    @SerializedName("iso_639_1")
    private String iso6391;
    @SerializedName("key")
    private String key;
    @SerializedName("name")
    private String name;
    @SerializedName("site")
    private String site;
    @SerializedName("size")
    private Long size;
    @SerializedName("type")
    private String type;
}
