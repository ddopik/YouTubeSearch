package ui.home.presenter

interface SearchActivityPresenter {

    fun getYouTubeSearchVideo(query: String)
    fun getNextYouTubeSearchVideo(query: String, nextPageToken: String)
}