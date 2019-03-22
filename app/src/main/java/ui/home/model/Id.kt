package ui.home.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Id {
    @SerializedName("kind")
    @Expose
    var kind: String? = null
    @SerializedName("videoId")
    @Expose
    var videoId: String? = null
    @SerializedName("channelId")
    @Expose
    var channelId: String? = null
}