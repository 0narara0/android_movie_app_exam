package com.narara.android_movie_app_exam.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Objects;


@Entity
public class Favorites {
    @PrimaryKey
    private int id;
    private double vote_average;
    private String title;
    private String poster_path;
    private String overview;
    private String release_date;

    public Favorites() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Favorites favorites = (Favorites) o;
        return id == favorites.id &&
                Double.compare(favorites.vote_average, vote_average) == 0 &&
                Objects.equals(title, favorites.title) &&
                Objects.equals(poster_path, favorites.poster_path) &&
                Objects.equals(overview, favorites.overview) &&
                Objects.equals(release_date, favorites.release_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vote_average, title, poster_path, overview, release_date);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Favorites{");
        sb.append("id=").append(id);
        sb.append(", vote_average=").append(vote_average);
        sb.append(", title='").append(title).append('\'');
        sb.append(", poster_path='").append(poster_path).append('\'');
        sb.append(", overview='").append(overview).append('\'');
        sb.append(", release_date='").append(release_date).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
}

