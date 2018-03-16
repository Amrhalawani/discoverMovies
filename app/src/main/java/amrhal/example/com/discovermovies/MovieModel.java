package amrhal.example.com.discovermovies;

/**
 * Created by Amr hal on 3/03/2018.
 */

public class MovieModel {
    //title, release date, movie poster, vote average, and plot synopsis.
    String title;
    String posterUrl;
    String releaseDate;
    int voteAverage;
    String synopsis;

    public MovieModel(String title, String posterUrl, String releaseDate, int voteAverage, String synopsis) {
        this.title = title;
        this.posterUrl = posterUrl;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.synopsis = synopsis;
    }


    public MovieModel(String title, String posterUrl) {
        this.title = title;
        this.posterUrl = posterUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(int voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
}
