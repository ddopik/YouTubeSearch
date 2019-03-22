package ui.home.presenter

import android.annotation.SuppressLint
import android.content.Context
import com.example.youtubesearcher.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import network.ApiHelper
import ui.home.view.SearchActivityView
import util.ErrorUtils

class SearchActivityPresenterImpl(val context: Context, val searchActivityView: SearchActivityView) : SearchActivityPresenter {

    private val TAG = SearchActivityPresenterImpl::class.java.simpleName


    @SuppressLint("CheckResult")
    override fun getYouTubeSearchVideo(query: String) {

        searchActivityView.viewProgress(true)
        ApiHelper.getVideoList(query, context.resources.getString(R.string.youTubeApiKey))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ youTubeVideoSearchResponse ->
                    searchActivityView.viewProgress(false)
                    searchActivityView.viewYouTubeResult(youTubeVideoSearchResponse)
                },
                        { throwable ->
                            searchActivityView.viewProgress(false)
                            ErrorUtils.setError(TAG, throwable)
                        });


    }

    @SuppressLint("CheckResult")
    override fun getNextYouTubeSearchVideo(query: String,nextPageToken:String) {

        searchActivityView.viewProgress(true)
        ApiHelper.getNextVideoList(query, context.resources.getString(R.string.youTubeApiKey),nextPageToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ youTubeVideoSearchResponse ->
                    searchActivityView.viewProgress(false)
                    searchActivityView.viewYouTubeResult(youTubeVideoSearchResponse)
                },
                        { throwable ->
                            searchActivityView.viewProgress(false)
                            ErrorUtils.setError(TAG, throwable)
                        });


    }
}