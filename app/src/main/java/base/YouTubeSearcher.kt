package base

import android.app.Application
import android.content.Context
import android.util.Log
import com.androidnetworking.AndroidNetworking
import java.io.File


class YouTubeSearcher : Application() {

    val TAG = YouTubeSearcher::class.java.simpleName

    companion object {

        var app: YouTubeSearcher? = null
    }

    override fun onCreate() {
        super.onCreate()

        app = this
        initFastAndroidNetworking()

    }

    /**
     * use this method in case initializing object by --new ()-- keyword
     *
     * @param app app Context
     */
    fun init(app: Application) {
        YouTubeSearcher.app = app as YouTubeSearcher
    }


    /**
     * delete App Cache and NetWork Cache
     */
    fun deleteCache(context: Context) {
        try {
            val dir = context.cacheDir
            deleteDir(dir)
        } catch (e: Exception) {
        }

    }

    fun deleteDir(dir: File?): Boolean {
        if (dir != null && dir.isDirectory) {
            val children = dir.list()
            for (i in children.indices) {
                val success = deleteDir(File(dir, children[i]))
                if (!success) {
                    return false
                }
            }
            return dir.delete()
        } else return if (dir != null && dir.isFile) {
            dir.delete()
        } else {
            false
        }
    }


    fun initFastAndroidNetworking() {

        /**
         * initializing block to add authentication to your Header Request
         */
        //        BasicAuthInterceptor basicAuthInterceptor = new BasicAuthInterceptor(getApplicationContext());
        //        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
        //                .addNetworkInterceptor(basicAuthInterceptor)
        //                .build();
        //        AndroidNetworking.initialize(this, okHttpClient);


        AndroidNetworking.initialize(applicationContext)
        AndroidNetworking.enableLogging()
        AndroidNetworking.setConnectionQualityChangeListener { currentConnectionQuality, currentBandwidth ->
            Log.e(TAG, "onChange: currentConnectionQuality : $currentConnectionQuality currentBandwidth : $currentBandwidth")
        }

        ///////////////////////
        /**
         * default initialization
         */
//        AndroidNetworking.initialize(this)
        //
    }

}