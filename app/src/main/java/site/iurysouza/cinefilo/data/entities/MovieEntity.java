
package site.iurysouza.cinefilo.data.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class MovieEntity {

    public static final String ID = "id";

    @SerializedName("adult")
    @Expose
    private Boolean adult;

    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;

    @SerializedName("belongs_to_collection")
    @Expose
    private MovieCollectionEntity belongsToCollection;

    @SerializedName("budget")
    @Expose
    private Integer budget;

    @SerializedName("genreEntities")
    @Expose
    private List<GenreEntity> genreEntities = new ArrayList<GenreEntity>();

    @SerializedName("homepage")
    @Expose
    private String homepage;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("imdb_id")
    @Expose
    private String imdbId;

    @SerializedName("original_language")
    @Expose
    private String originalLanguage;

    @SerializedName("original_title")
    @Expose
    private String originalTitle;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("popularity")
    @Expose
    private Double popularity;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("production_companies")
    @Expose
    private List<ProdCompanyEntity> productionCompanies = new ArrayList<ProdCompanyEntity>();

    @SerializedName("production_countries")
    @Expose
    private List<ProdCountryEntity> productionCountries = new ArrayList<ProdCountryEntity>();

    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @SerializedName("revenue")
    @Expose
    private Integer revenue;

    @SerializedName("runtime")
    @Expose
    private Integer runtime;

    @SerializedName("spoken_languages")
    @Expose
    private List<SpokenLangEntity> spokenLangEntities = new ArrayList<SpokenLangEntity>();

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("tagline")
    @Expose
    private String tagline;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("video")
    @Expose
    private Boolean video;

    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;

    @SerializedName("vote_count")
    @Expose
    private Integer voteCount;

    /**
     * 
     * @return
     *     The adult
     */
    public Boolean getAdult() {
        return adult;
    }

    /**
     * 
     * @param adult
     *     The adult
     */
    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public MovieEntity withAdult(Boolean adult) {
        this.adult = adult;
        return this;
    }

    /**
     * 
     * @return
     *     The backdropPath
     */
    public String getBackdropPath() {
        return backdropPath;
    }

    /**
     * 
     * @param backdropPath
     *     The backdrop_path
     */
    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public MovieEntity withBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
        return this;
    }

    /**
     * 
     * @return
     *     The belongsToCollection
     */
    public Object getBelongsToCollection() {
        return belongsToCollection;
    }

    /**
     * 
     * @param belongsToCollection
     *     The belongs_to_collection
     */
    public void setBelongsToCollection(MovieCollectionEntity belongsToCollection) {
        this.belongsToCollection = belongsToCollection;
    }

    public MovieEntity withBelongsToCollection(MovieCollectionEntity belongsToCollection) {
        this.belongsToCollection = belongsToCollection;
        return this;
    }

    /**
     * 
     * @return
     *     The budget
     */
    public Integer getBudget() {
        return budget;
    }

    /**
     * 
     * @param budget
     *     The budget
     */
    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public MovieEntity withBudget(Integer budget) {
        this.budget = budget;
        return this;
    }

    /**
     * 
     * @return
     *     The genreEntities
     */
    public List<GenreEntity> getGenreEntities() {
        return genreEntities;
    }

    /**
     * 
     * @param genreEntities
     *     The genreEntities
     */
    public void setGenreEntities(List<GenreEntity> genreEntities) {
        this.genreEntities = genreEntities;
    }

    public MovieEntity withGenres(List<GenreEntity> genreEntities) {
        this.genreEntities = genreEntities;
        return this;
    }

    /**
     * 
     * @return
     *     The homepage
     */
    public String getHomepage() {
        return homepage;
    }

    /**
     * 
     * @param homepage
     *     The homepage
     */
    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public MovieEntity withHomepage(String homepage) {
        this.homepage = homepage;
        return this;
    }

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public MovieEntity withId(Integer id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * @return
     *     The imdbId
     */
    public String getImdbId() {
        return imdbId;
    }

    /**
     * 
     * @param imdbId
     *     The imdb_id
     */
    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public MovieEntity withImdbId(String imdbId) {
        this.imdbId = imdbId;
        return this;
    }

    /**
     * 
     * @return
     *     The originalLanguage
     */
    public String getOriginalLanguage() {
        return originalLanguage;
    }

    /**
     * 
     * @param originalLanguage
     *     The original_language
     */
    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public MovieEntity withOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
        return this;
    }

    /**
     * 
     * @return
     *     The originalTitle
     */
    public String getOriginalTitle() {
        return originalTitle;
    }

    /**
     * 
     * @param originalTitle
     *     The original_title
     */
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public MovieEntity withOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
        return this;
    }

    /**
     * 
     * @return
     *     The overview
     */
    public String getOverview() {
        return overview;
    }

    /**
     * 
     * @param overview
     *     The overview
     */
    public void setOverview(String overview) {
        this.overview = overview;
    }

    public MovieEntity withOverview(String overview) {
        this.overview = overview;
        return this;
    }

    /**
     * 
     * @return
     *     The popularity
     */
    public Double getPopularity() {
        return popularity;
    }

    /**
     * 
     * @param popularity
     *     The popularity
     */
    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public MovieEntity withPopularity(Double popularity) {
        this.popularity = popularity;
        return this;
    }

    /**
     * 
     * @return
     *     The posterPath
     */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * 
     * @param posterPath
     *     The poster_path
     */
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public MovieEntity withPosterPath(String posterPath) {
        this.posterPath = posterPath;
        return this;
    }

    /**
     * 
     * @return
     *     The productionCompanies
     */
    public List<ProdCompanyEntity> getProductionCompanies() {
        return productionCompanies;
    }

    /**
     * 
     * @param productionCompanies
     *     The production_companies
     */
    public void setProductionCompanies(List<ProdCompanyEntity> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public MovieEntity withProductionCompanies(List<ProdCompanyEntity> productionCompanies) {
        this.productionCompanies = productionCompanies;
        return this;
    }

    /**
     * 
     * @return
     *     The productionCountries
     */
    public List<ProdCountryEntity> getProductionCountries() {
        return productionCountries;
    }

    /**
     * 
     * @param productionCountries
     *     The production_countries
     */
    public void setProductionCountries(List<ProdCountryEntity> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public MovieEntity withProductionCountries(List<ProdCountryEntity> productionCountries) {
        this.productionCountries = productionCountries;
        return this;
    }

    /**
     * 
     * @return
     *     The releaseDate
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * 
     * @param releaseDate
     *     The release_date
     */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public MovieEntity withReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    /**
     * 
     * @return
     *     The revenue
     */
    public Integer getRevenue() {
        return revenue;
    }

    /**
     * 
     * @param revenue
     *     The revenue
     */
    public void setRevenue(Integer revenue) {
        this.revenue = revenue;
    }

    public MovieEntity withRevenue(Integer revenue) {
        this.revenue = revenue;
        return this;
    }

    /**
     * 
     * @return
     *     The runtime
     */
    public Integer getRuntime() {
        return runtime;
    }

    /**
     * 
     * @param runtime
     *     The runtime
     */
    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public MovieEntity withRuntime(Integer runtime) {
        this.runtime = runtime;
        return this;
    }

    /**
     * 
     * @return
     *     The spokenLangEntities
     */
    public List<SpokenLangEntity> getSpokenLangEntities() {
        return spokenLangEntities;
    }

    /**
     * 
     * @param spokenLangEntities
     *     The spoken_languages
     */
    public void setSpokenLangEntities(List<SpokenLangEntity> spokenLangEntities) {
        this.spokenLangEntities = spokenLangEntities;
    }

    public MovieEntity withSpokenLanguages(List<SpokenLangEntity> spokenLangEntities) {
        this.spokenLangEntities = spokenLangEntities;
        return this;
    }

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public MovieEntity withStatus(String status) {
        this.status = status;
        return this;
    }

    /**
     * 
     * @return
     *     The tagline
     */
    public String getTagline() {
        return tagline;
    }

    /**
     * 
     * @param tagline
     *     The tagline
     */
    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public MovieEntity withTagline(String tagline) {
        this.tagline = tagline;
        return this;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public MovieEntity withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * 
     * @return
     *     The video
     */
    public Boolean getVideo() {
        return video;
    }

    /**
     * 
     * @param video
     *     The video
     */
    public void setVideo(Boolean video) {
        this.video = video;
    }

    public MovieEntity withVideo(Boolean video) {
        this.video = video;
        return this;
    }

    /**
     * 
     * @return
     *     The voteAverage
     */
    public Double getVoteAverage() {
        return voteAverage;
    }

    /**
     * 
     * @param voteAverage
     *     The vote_average
     */
    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public MovieEntity withVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
        return this;
    }

    /**
     * 
     * @return
     *     The voteCount
     */
    public Integer getVoteCount() {
        return voteCount;
    }

    /**
     * 
     * @param voteCount
     *     The vote_count
     */
    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public MovieEntity withVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
        return this;
    }

}
