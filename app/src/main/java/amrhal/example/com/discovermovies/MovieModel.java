package amrhal.example.com.discovermovies;

/**
 * Created by Amr hal on 28/02/2018.
 */

public class MovieModel {
    //title, release date, movie poster, vote average, and plot synopsis.
    String title;
    String posterUrl;

//    String releaseDate;
//    int voteAverage;
//    String synopsis;
//

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

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }


}
