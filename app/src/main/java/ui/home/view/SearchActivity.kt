package ui.home.view

import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.TextView
import base.BaseActivity
import base.widgets.CustomRecyclerView
import base.widgets.CustomTextView
import base.widgets.PagingController
import com.example.youtubesearcher.R
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_search.*
import util.Const.Companion.QUERY_SEARCH_TIME_OUT
import java.util.concurrent.TimeUnit

/**
 * Created by abdalla_maged on 10/29/2018.
 */
class SearchActivity : BaseActivity() {

    private var searchView: EditText? = null
    private val disposable = CompositeDisposable()
    var pagingController: PagingController? = null
    var searchRv: CustomRecyclerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initPresenter()
        initView()
        initListener()

    }


    override fun initView() {

        searchView = findViewById(R.id.search_view)


    }

    override fun initPresenter() {

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


        pagingController = object : PagingController(searchRv) {
            override fun getPagingControllerCallBack(page: Int) {
//                if (albumSearch.getText().length > 0) {
//                    promptView.setVisibility(View.GONE)
//                    albumSearchPresenter.getAlbumSearchQuery(albumSearch.getText().toString().trim({ it <= ' ' }), page)
//                }
            }
        }


    }

    private fun searchQuery(): DisposableObserver<TextViewTextChangeEvent> {
        return object : DisposableObserver<TextViewTextChangeEvent>() {
            override fun onNext(textViewTextChangeEvent: TextViewTextChangeEvent) {


                // user cleared search get default data

//                albumSearchList.clear()
//                albumSearchPresenter.getAlbumSearchQuery(albumSearch.getText().toString().trim({ it <= ' ' }), 0)
//                Log.e(TAG, "search string: " + albumSearch.getText().toString())

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

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}