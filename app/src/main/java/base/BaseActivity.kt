package base

import android.os.Bundle
import android.provider.SyncStateContract
import android.support.annotation.Nullable
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.youtubesearcher.R


import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import util.Const
import util.rxeventbus.RxEventBus

/**
 * Created by abdalla-maged on 3/27/18.
 */

abstract class BaseActivity : AppCompatActivity() {

    private val disposables = CompositeDisposable()

    //    protected abstract void addFragment(Fragment fragment,String title,String tag);


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    abstract fun initView()
    abstract fun initPresenter()
    open fun  showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }


    fun addFragment(containerID: Int, fragment: Fragment, tag: String, addBackStack: Boolean) {
        if (addBackStack) {
            getSupportFragmentManager().beginTransaction().replace(containerID, fragment, tag).addToBackStack(tag).commit()
        } else {
            getSupportFragmentManager().beginTransaction().replace(containerID, fragment, tag).commit()

        }

    }

    override fun onResume() {
        super.onResume()
        val disposable = RxEventBus.getInstance().getSubject().subscribe({ event ->
            when (event.getType()) {

                Const.Stats.CONNECTIVITY -> {
                    val connected = event.getObject() as Boolean
                    if (!connected) {
                        val snackbar = Snackbar.make(findViewById(android.R.id.content), R.string.connection_problem_check_network, Snackbar.LENGTH_LONG)
                        snackbar.getView().setBackgroundColor(getResources().getColor(R.color.colorRed))
                        snackbar.show()
                    }
                }
            }
        }, { throwable -> Log.e("MainActivity-Noti", throwable.message) })
        disposables.add(disposable)
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }


}
