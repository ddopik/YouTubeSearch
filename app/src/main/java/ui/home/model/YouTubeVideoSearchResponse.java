package ui.home.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class YouTubeVideoSearchResponse {
    @SerializedName("kind")
    @Expose
    public String kind;
    @SerializedName("etag")
    @Expose
    public String etag;
    @SerializedName("nextPageToken")
    @Expose
    public String nextPageToken;
    @SerializedName("regionCode")
    @Expose
    public String regionCode;
    @SerializedName("pageInfo")
    @Expose
    public PageInfo pageInfo;
    @SerializedName("items")
    @Expose
    public List<Item> items = null;
}
