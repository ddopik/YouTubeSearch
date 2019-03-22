package ui.home.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Item {
    @SerializedName("kind")
    @Expose
    var kind: String? = null
    @SerializedName("etag")
    @Expose
    var etag: String? = null
    @SerializedName("id")
    @Expose
    var id: Id? = null
    @SerializedName("snippet")
    @Expose
    var snippet: Snippet? = null

}