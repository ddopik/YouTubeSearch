package ui.home.view

import android.os.Bundle
import android.view.View
import android.widget.EditText
import base.BaseActivity
import base.widgets.PagingController
import com.example.youtubesearcher.R
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_search.*
import ui.home.model.Item
import ui.home.model.YouTubeVideoSearchResponse
import ui.home.presenter.SearchActivityPresenter
import ui.home.presenter.SearchActivityPresenterImpl
import util.Const.Companion.QUERY_SEARCH_TIME_OUT
import util.Utilities
import java.util.concurrent.TimeUnit

/**
 * Created by abdalla_maged on 10/29/2018.
 */
class SearchActivity : BaseActivity(), SearchActivityView {

    private var searchView: EditText? = null
    private val disposable = CompositeDisposable()
    private val videoList: MutableList<Item> = ArrayList()
    private var youTubeVideoListAdapter: YouTubeVideoListAdapter? = null
    private var searchActivityPresenter: SearchActivityPresenter? = null
    var pagingController: PagingController? = null
    private var nextPageToken: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initPresenter()
        initView()
        initListener()

    }


    override fun initView() {

        searchView = findViewById(R.id.search_view)
        youTubeVideoListAdapter = YouTubeVideoListAdapter(videoList, object : YouTubeVideoListAdapter.OnItemClickListener {
            override fun onItemClicked(item: Item) {
                Utilities.watchYoutubeVideo(baseContext, item.id?.videoId)
            }

        })
        search_container.adapter = youTubeVideoListAdapter


    }

    override fun initPresenter() {
        searchActivityPresenter = SearchActivityPresenterImpl(baseContext, this)
    }

    private fun initListener() {


        disposable.add(

                RxTextView.textChangeEvents(search_view)
                        .skipInitialValue()
                        .debounce(QUERY_SEARCH_TIME_OUT.toLong(), TimeUnit.MILLISECONDS)
                        .distinctUntilChanged()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(searchQuery())
//                        .subscribe {  searchQuery()}
        );


        pagingController = object : PagingController(search_container) {
            override fun getPagingControllerCallBack(page: Int) {
                if (search_view.text.isNotEmpty()) {
                    nextPageToken = ""
                    searchActivityPresenter?.getNextYouTubeSearchVideo(search_view.text.toString().trim({ it <= ' ' }), nextPageToken)
                }
            }
        }


    }

    private fun searchQuery(): DisposableObserver<TextViewTextChangeEvent> {
        return object : DisposableObserver<TextViewTextChangeEvent>() {
            override fun onNext(textViewTextChangeEvent: TextViewTextChangeEvent) {


                // user cleared search get default data
                videoList.clear()
                nextPageToken = ""
                searchActivityPresenter?.getYouTubeSearchVideo(search_view.text.toString().trim { it <= ' ' })


            }

            override fun onError(e: Throwable) {

            }

            override fun onComplete() {

            }
        }
    }

    override fun showToast(msg: String) {
        super.showToast(msg)
    }

    override fun viewYouTubeResult(youTubeVideoSearchResponse: YouTubeVideoSearchResponse) {

        nextPageToken = youTubeVideoSearchResponse.nextPageToken
        videoList.addAll(youTubeVideoSearchResponse.items)
        search_result.text = """${videoList.size}  ${resources.getString(R.string.result)}"""
        youTubeVideoListAdapter?.notifyDataSetChanged()
        Utilities.hideKeyboard(this)
    }

    override fun viewProgress(state: Boolean) {
        if (state) {
            search_activity_progress.visibility = View.VISIBLE
        } else {
            search_activity_progress.visibility = View.INVISIBLE
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

}