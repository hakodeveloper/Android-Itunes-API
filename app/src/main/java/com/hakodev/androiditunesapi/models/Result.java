
package com.hakodev.androiditunesapi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("wrapperType")
    @Expose
    private String wrapperType;
    @SerializedName("artistType")
    @Expose
    private String artistType;
    @SerializedName("artistName")
    @Expose
    private String artistName;
    @SerializedName("artistLinkUrl")
    @Expose
    private String artistLinkUrl;
    @SerializedName("artistId")
    @Expose
    private Long artistId;
    @SerializedName("amgArtistId")
    @Expose
    private Long amgArtistId;
    @SerializedName("primaryGenreName")
    @Expose
    private String primaryGenreName;
    @SerializedName("primaryGenreId")
    @Expose
    private Long primaryGenreId;

    public String getWrapperType() {
        return wrapperType;
    }

    public void setWrapperType(String wrapperType) {
        this.wrapperType = wrapperType;
    }

    public String getArtistType() {
        return artistType;
    }

    public void setArtistType(String artistType) {
        this.artistType = artistType;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistLinkUrl() {
        return artistLinkUrl;
    }

    public void setArtistLinkUrl(String artistLinkUrl) {
        this.artistLinkUrl = artistLinkUrl;
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public Long getAmgArtistId() {
        return amgArtistId;
    }

    public void setAmgArtistId(Long amgArtistId) {
        this.amgArtistId = amgArtistId;
    }

    public String getPrimaryGenreName() {
        return primaryGenreName;
    }

    public void setPrimaryGenreName(String primaryGenreName) {
        this.primaryGenreName = primaryGenreName;
    }

    public Long getPrimaryGenreId() {
        return primaryGenreId;
    }

    public void setPrimaryGenreId(Long primaryGenreId) {
        this.primaryGenreId = primaryGenreId;
    }

}
