package ui.home.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Snippet {
    @SerializedName("publishedAt")
    @Expose
    var publishedAt: String? = null
    @SerializedName("channelId")
    @Expose
    var channelId: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("thumbnails")
    @Expose
    var thumbnails: Thumbnails? = null
    @SerializedName("channelTitle")
    @Expose
    var channelTitle: String? = null
    @SerializedName("liveBroadcastContent")
    @Expose
    var liveBroadcastContent: String? = null

}