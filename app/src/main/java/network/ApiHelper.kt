package network

import com.androidnetworking.common.Priority
import com.rx2androidnetworking.Rx2AndroidNetworking
import ui.home.model.YouTubeVideoSearchResponse
import util.Utilities

class ApiHelper {


    companion object {
        private val YOUTUBE_VEDIO_SEARCH_LIST_URL = "https://www.googleapis.com/youtube/v3/search"
        private val QUERY_PARAM_KEY = "q"
        private val QUERY_PARAM_API_KEY = "key"
        private val QUERY_PARAM_API_PART = "part"
        private val QUERY_PARAM_API_MAX_RESULT = "maxResults"
        private val QUERY_PARAM_API_MAX_ORDER = "order"
        private val QUERY_PARAM_API_PAGGING_TOKEN ="pageToken"


        fun getVideoList(key: String, youTubeApiKey: String): io.reactivex.Observable<YouTubeVideoSearchResponse> {

            if (Utilities.isNetworkConnected()) {

                return Rx2AndroidNetworking.get(YOUTUBE_VEDIO_SEARCH_LIST_URL)
                        .addQueryParameter(QUERY_PARAM_API_KEY, youTubeApiKey)
                        .addQueryParameter(QUERY_PARAM_API_PART, "snippet,id")
                        .addQueryParameter(QUERY_PARAM_API_MAX_ORDER, "date")
                        .addQueryParameter(QUERY_PARAM_API_MAX_RESULT, "20")
                        .addQueryParameter(QUERY_PARAM_KEY, key)
                        .setPriority(Priority.HIGH)
                        .build()
                        .getObjectObservable<YouTubeVideoSearchResponse>(YouTubeVideoSearchResponse::class.java)
            } else {
                return Rx2AndroidNetworking.get(YOUTUBE_VEDIO_SEARCH_LIST_URL)
                        .addQueryParameter(QUERY_PARAM_API_KEY, youTubeApiKey)
                        .addQueryParameter(QUERY_PARAM_API_PART, "snippet,id")
                        .addQueryParameter(QUERY_PARAM_API_MAX_ORDER, "date")
                        .addQueryParameter(QUERY_PARAM_API_MAX_RESULT, "20")
                        .addQueryParameter(QUERY_PARAM_KEY, key)
                        .setPriority(Priority.HIGH)
                        .responseOnlyIfCached
                        .build()
                        .getObjectObservable<YouTubeVideoSearchResponse>(YouTubeVideoSearchResponse::class.java)
            }

        }

            fun getNextVideoList(key: String, youTubeApiKey: String,nextPageToken: String): io.reactivex.Observable<YouTubeVideoSearchResponse> {

                if (Utilities.isNetworkConnected()) {

                    return Rx2AndroidNetworking.get(YOUTUBE_VEDIO_SEARCH_LIST_URL)
                            .addQueryParameter(QUERY_PARAM_API_KEY, youTubeApiKey)
                            .addQueryParameter(QUERY_PARAM_API_PART, "snippet,id")
                            .addQueryParameter(QUERY_PARAM_API_MAX_ORDER, "date")
                            .addQueryParameter(QUERY_PARAM_API_MAX_RESULT, "20")
                            .addQueryParameter(QUERY_PARAM_KEY, key)
                            .addQueryParameter(QUERY_PARAM_API_PAGGING_TOKEN, nextPageToken)
                            .setPriority(Priority.HIGH)
                            .build()
                            .getObjectObservable<YouTubeVideoSearchResponse>(YouTubeVideoSearchResponse::class.java)
                } else {
                    return Rx2AndroidNetworking.get(YOUTUBE_VEDIO_SEARCH_LIST_URL)
                            .addQueryParameter(QUERY_PARAM_API_KEY, youTubeApiKey)
                            .addQueryParameter(QUERY_PARAM_API_PART, "snippet,id")
                            .addQueryParameter(QUERY_PARAM_API_MAX_ORDER, "date")
                            .addQueryParameter(QUERY_PARAM_API_MAX_RESULT, "20")
                            .addQueryParameter(QUERY_PARAM_KEY, key)
                            .addQueryParameter(QUERY_PARAM_API_PAGGING_TOKEN, nextPageToken)
                            .setPriority(Priority.HIGH)
                            .responseOnlyIfCached
                            .build()
                            .getObjectObservable<YouTubeVideoSearchResponse>(YouTubeVideoSearchResponse::class.java)
                }

            }


        }

    }