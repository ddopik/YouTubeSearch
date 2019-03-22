package ui.home.view

import ui.home.model.Item
import ui.home.model.YouTubeVideoSearchResponse

interface SearchActivityView {

    fun viewYouTubeResult(youTubeVideoSearchResponse : YouTubeVideoSearchResponse)
    fun viewProgress(state: Boolean)

}