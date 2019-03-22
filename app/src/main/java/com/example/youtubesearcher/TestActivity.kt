package com.example.youtubesearcher

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import util.Utilities

class TestActivity :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        Utilities.watchYoutubeVideo(baseContext,"kAgMPwVEXQQ")
    }
}