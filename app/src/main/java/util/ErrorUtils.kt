package util

import android.content.Context
import android.util.Log
import android.widget.Toast
import base.YouTubeSearcher
import base.model.ErrorMessageResponse
import com.androidnetworking.error.ANError

import com.example.youtubesearcher.R
import com.google.gson.Gson
import util.Utilities.isNetworkConnected

/**
 * Created by abdalla_maged on 11/6/2018.
 */
class ErrorUtils {

    companion object {

        val STATE_SUCCEED = 200
        val STATUS_BAD_REQUEST = 400
        val STATUS_CUSTOM_ERROR_REQUEST = 0
        val STATUS_401 = 401
        val STATUS_404 = 404
        val STATUS_500 = 500
        private val TAG = ErrorUtils::class.java.simpleName

        //Bad RequestHandling
        fun setError(context: Context?, contextTAG: String, error: String) {
            Log.e(TAG, "$contextTAG------>$error")
            Toast.makeText(context, "Request error", Toast.LENGTH_SHORT).show()
        }

        //Universal Error State From Server
        fun setError(contextTAG: String, throwable: Throwable?) {
            try {
                takeIf { throwable is ANError }.apply {
                    val errorData = (throwable as ANError).errorBody
                    val statusCode = throwable.errorCode
                    val gson = Gson()
                    when (statusCode) {
                        STATUS_BAD_REQUEST -> {
                            var errorMessageResponse: ErrorMessageResponse = gson.fromJson(errorData, ErrorMessageResponse::class.java)
                            Toast.makeText(YouTubeSearcher.app, errorMessageResponse.message, Toast.LENGTH_LONG).show()
//                            viewError(context, contextTAG,errorMessageResponse)
                        }
                        STATUS_404 -> {
                            Log.e(TAG, contextTAG + "------>" + STATUS_404 + "---" + throwable.response)
                        }
                        STATUS_401 -> {
                            Log.e(TAG, contextTAG + "------>" + STATUS_401 + "---" + throwable.response)
                        }
                        STATUS_500 -> {
                            Log.e(TAG, contextTAG + "------>" + STATUS_500 + "---" + throwable.response)
                        }
                        else -> {
                            Log.e(TAG, contextTAG + "--------------->" + throwable.errorDetail)
                            if (!isNetworkConnected()) {
                                SingleToast.INSTANCE.show(YouTubeSearcher.app, YouTubeSearcher.app?.resources?.getString(R.string.no_internet_conniction), Toast.LENGTH_LONG)
                                return
                            }
                            Toast.makeText(YouTubeSearcher.app, throwable.errorDetail, Toast.LENGTH_LONG).show();

                        }
                    }

                }
            } catch (e: ClassCastException) {
                Log.e(TAG, contextTAG + "--------------->" + throwable?.message)

            }


        }

        ///PreDefined Error Code From Server
//        private fun viewError(context: Context, contextTAG: String, errorMessageResponse: ErrorMessageResponse) {
//            for (i in errorMessageResponse.errors.indices) {
//                if (errorMessageResponse.errors[i].code != null)
//                    when (errorMessageResponse.errors[i].code) {
//                        ERROR_STATE_1 -> {
//                            Toast.makeText(context, errorMessageResponse.errors[i].message, Toast.LENGTH_SHORT).show()
//                        }
//                        else -> {
//                            Log.e(TAG, contextTAG + "------>" + errorMessageResponse.errors[i].message)
//                        }
//                    }
//            }
//
//        }
    }
}


//ErrorUtils.setError(context, TAG, experienceInnerResponse?.errors.toString(), experienceInnerResponse?.status.toString())
//ErrorUtils.setError(context, TAG, t?.message.toString())