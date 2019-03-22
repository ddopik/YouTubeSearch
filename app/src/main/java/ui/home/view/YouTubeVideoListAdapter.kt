package ui.home.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import base.GlideApp
import com.example.youtubesearcher.R
import ui.home.model.Item

class YouTubeVideoListAdapter(val videoList: List<Item>, val onItemClickListener:OnItemClickListener) : RecyclerView.Adapter<YouTubeVideoListAdapter.YouTubeVedioItemViewHolder>() {


    private var context: Context? = null


    override fun onCreateViewHolder(viewGroup: ViewGroup, positions: Int): YouTubeVedioItemViewHolder {
        context = viewGroup.context;
        val layoutInflater = LayoutInflater.from(context)
        return YouTubeVedioItemViewHolder(layoutInflater.inflate(R.layout.view_holder_youtube_video_item, viewGroup, false))

    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    override fun onBindViewHolder(youTubeVedioItemViewHolder: YouTubeVedioItemViewHolder, position: Int) {


        videoList?.get(position)?.let {
            GlideApp.with(context!!)
                    .load(it.snippet?.thumbnails?._default?.url)
                    .placeholder(R.drawable.default_place_holder)
                    .error(R.drawable.default_error_img)
                    .into(youTubeVedioItemViewHolder.mainReviewThumbImage)

            youTubeVedioItemViewHolder.channelTitle.text = it.snippet?.channelTitle
            youTubeVedioItemViewHolder.mainReviewDate.text = it.snippet?.publishedAt
            youTubeVedioItemViewHolder.descriptionText.text = it.snippet?.description
        }

        (youTubeVedioItemViewHolder as View ).setOnClickListener {
            onItemClickListener.onItemClicked(videoList[position])
        }
    }

    class YouTubeVedioItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var mainReviewThumbImage: ImageView = view.findViewById(R.id.main_review_thumb_image)
        var channelTitle: TextView = view.findViewById(R.id.channel_title)
        var mainReviewDate: TextView = view.findViewById(R.id.main_review_date);
        var descriptionText: TextView = view.findViewById(R.id.description_text);
    }

    interface  OnItemClickListener{
        fun onItemClicked(item:Item)
    }
}