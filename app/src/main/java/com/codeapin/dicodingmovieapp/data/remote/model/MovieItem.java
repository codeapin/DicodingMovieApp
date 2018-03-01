package com.codeapin.dicodingmovieapp.data.remote.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static com.codeapin.dicodingmovieapp.data.local.DatabaseContract.MovieColumns.COLUMN_NAME_ADULT;
import static com.codeapin.dicodingmovieapp.data.local.DatabaseContract.MovieColumns.COLUMN_NAME_BACKDROP_PATH;
import static com.codeapin.dicodingmovieapp.data.local.DatabaseContract.MovieColumns.COLUMN_NAME_GENRE_IDS;
import static com.codeapin.dicodingmovieapp.data.local.DatabaseContract.MovieColumns.COLUMN_NAME_ORIGINAL_LANGUAGE;
import static com.codeapin.dicodingmovieapp.data.local.DatabaseContract.MovieColumns.COLUMN_NAME_ORIGINAL_TITLE;
import static com.codeapin.dicodingmovieapp.data.local.DatabaseContract.MovieColumns.COLUMN_NAME_OVERVIEW;
import static com.codeapin.dicodingmovieapp.data.local.DatabaseContract.MovieColumns.COLUMN_NAME_POPULARITY;
import static com.codeapin.dicodingmovieapp.data.local.DatabaseContract.MovieColumns.COLUMN_NAME_POSTER_PATH;
import static com.codeapin.dicodingmovieapp.data.local.DatabaseContract.MovieColumns.COLUMN_NAME_RELEASE_DATE;
import static com.codeapin.dicodingmovieapp.data.local.DatabaseContract.MovieColumns.COLUMN_NAME_TITLE;
import static com.codeapin.dicodingmovieapp.data.local.DatabaseContract.MovieColumns.COLUMN_NAME_VIDEO;
import static com.codeapin.dicodingmovieapp.data.local.DatabaseContract.MovieColumns.COLUMN_NAME_VOTE_AVERAGE;
import static com.codeapin.dicodingmovieapp.data.local.DatabaseContract.MovieColumns.COLUMN_NAME_VOTE_COUNT;
import static com.codeapin.dicodingmovieapp.data.local.DatabaseContract.getColumnDouble;
import static com.codeapin.dicodingmovieapp.data.local.DatabaseContract.getColumnInt;
import static com.codeapin.dicodingmovieapp.data.local.DatabaseContract.getColumnString;

public class MovieItem implements Parcelable {

    @SerializedName("overview")
    private String overview;

    @SerializedName("original_language")
    private String originalLanguage;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("video")
    private boolean video;

    @SerializedName("title")
    private String title;

    @SerializedName("genre_ids")
    private List<Integer> genreIds;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("id")
    private int id;

    @SerializedName("adult")
    private boolean adult;

    @SerializedName("vote_count")
    private int voteCount;

    public MovieItem(Cursor cursor) {
        boolean adult = getColumnInt(cursor, COLUMN_NAME_ADULT) == 1;
        setAdult(adult);
        setBackdropPath(getColumnString(cursor, COLUMN_NAME_BACKDROP_PATH));

        String genreString = getColumnString(cursor, COLUMN_NAME_GENRE_IDS);
        List<Integer> ids = new ArrayList<>();
        for (String s : genreString.split("\\s*,\\s*")) {
            ids.add(Integer.parseInt(s));
        }
        setGenreIds(ids);

        setId(getColumnInt(cursor, _ID));
        setOriginalLanguage(getColumnString(cursor, COLUMN_NAME_ORIGINAL_LANGUAGE));
        setOriginalTitle(getColumnString(cursor, COLUMN_NAME_ORIGINAL_TITLE));
        setOverview(getColumnString(cursor, COLUMN_NAME_OVERVIEW));
        setPopularity(getColumnDouble(cursor, COLUMN_NAME_POPULARITY));
        setPosterPath(getColumnString(cursor, COLUMN_NAME_POSTER_PATH));
        setReleaseDate(getColumnString(cursor, COLUMN_NAME_RELEASE_DATE));
        setTitle(getColumnString(cursor, COLUMN_NAME_TITLE));

        boolean video = getColumnInt(cursor, COLUMN_NAME_VIDEO) == 1;
        setVideo(video);
        setVoteAverage(getColumnDouble(cursor, COLUMN_NAME_VOTE_AVERAGE));
        setVoteCount(getColumnInt(cursor, COLUMN_NAME_VOTE_COUNT));
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOverview() {
        return overview;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public boolean isVideo() {
        return video;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public int getVoteCount() {
        return voteCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.overview);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.originalTitle);
        dest.writeByte(this.video ? (byte) 1 : (byte) 0);
        dest.writeString(this.title);
        dest.writeList(this.genreIds);
        dest.writeString(this.posterPath);
        dest.writeString(this.backdropPath);
        dest.writeString(this.releaseDate);
        dest.writeDouble(this.voteAverage);
        dest.writeDouble(this.popularity);
        dest.writeInt(this.id);
        dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
        dest.writeInt(this.voteCount);
    }

    public MovieItem() {
    }

    protected MovieItem(Parcel in) {
        this.overview = in.readString();
        this.originalLanguage = in.readString();
        this.originalTitle = in.readString();
        this.video = in.readByte() != 0;
        this.title = in.readString();
        this.genreIds = new ArrayList<Integer>();
        in.readList(this.genreIds, Integer.class.getClassLoader());
        this.posterPath = in.readString();
        this.backdropPath = in.readString();
        this.releaseDate = in.readString();
        this.voteAverage = in.readDouble();
        this.popularity = in.readDouble();
        this.id = in.readInt();
        this.adult = in.readByte() != 0;
        this.voteCount = in.readInt();
    }

    public static final Parcelable.Creator<MovieItem> CREATOR = new Parcelable.Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel source) {
            return new MovieItem(source);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };

    public ContentValues getContentValues() {
        ContentValues initialValues = new ContentValues();
        initialValues.put(_ID, getId());
        initialValues.put(COLUMN_NAME_OVERVIEW, getOverview());
        initialValues.put(COLUMN_NAME_ORIGINAL_LANGUAGE, getOriginalLanguage());
        initialValues.put(COLUMN_NAME_ORIGINAL_TITLE, getOriginalTitle());
        initialValues.put(COLUMN_NAME_VIDEO, isVideo());
        initialValues.put(COLUMN_NAME_TITLE, getTitle());

        String ids = getGenreIds().toString();
        if (ids != null) {
            ids = ids.substring(1, ids.length() - 1);
        }
        initialValues.put(COLUMN_NAME_GENRE_IDS, ids);
        initialValues.put(COLUMN_NAME_POSTER_PATH, getPosterPath());
        initialValues.put(COLUMN_NAME_BACKDROP_PATH, getBackdropPath());
        initialValues.put(COLUMN_NAME_RELEASE_DATE, getReleaseDate());
        initialValues.put(COLUMN_NAME_VOTE_AVERAGE, getVoteAverage());
        initialValues.put(COLUMN_NAME_POPULARITY, getPopularity());
        initialValues.put(COLUMN_NAME_ADULT, isAdult());
        initialValues.put(COLUMN_NAME_VOTE_COUNT, getVoteCount());

        return initialValues;
    }
}