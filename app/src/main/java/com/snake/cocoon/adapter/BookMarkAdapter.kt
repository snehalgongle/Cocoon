package com.snake.cocoon.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.snake.cocoon.BrowserActivity
import com.snake.cocoon.R
import com.snake.cocoon.data.local.TopStoriesTable
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class BookMarkAdapter(mContext: Context, topStoriesList: List<TopStoriesTable>) :
    RecyclerView.Adapter<BookMarkAdapter.Holder>() {
    private val mContext: Context
    private var topStoriesList = ArrayList<TopStoriesTable>()

    init {
        this.mContext = mContext
        this.topStoriesList = topStoriesList as ArrayList<TopStoriesTable>
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageView: ImageView
        val textViewTitle: TextView
        val textViewDate: TextView
        val cardView: CardView

        init {
            imageView = itemView.findViewById(R.id.image)
            textViewTitle = itemView.findViewById(R.id.txtTitle)
            textViewDate = itemView.findViewById(R.id.txtDate)
            cardView = itemView.findViewById(R.id.cardView)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(mContext).inflate(
                R.layout.layout_bookmark,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: Holder, position: Int) {
        val topStoriesTable: TopStoriesTable = topStoriesList[position]
        holder.textViewDate.setText(
            "published: " + covertTimeToText(topStoriesTable.publishDate)
        )
        holder.textViewTitle.setText(
            topStoriesTable.title
        )
        try {
            Glide.with(mContext)
                .load(topStoriesTable.image)
                .into(holder.imageView)
        } catch (ex: Exception) {
        }

        holder.cardView.setOnClickListener(View.OnClickListener { view: View? ->
            val intent = Intent(mContext, BrowserActivity::class.java)
            intent.putExtra(mContext.resources.getString(R.string.extra_main_url), topStoriesTable.mainUrl)
            mContext.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return topStoriesList.size
    }

    fun covertTimeToText(dataDate: String?): String? {
        var convTime: String? = null
        val prefix = ""
        val suffix = "Ago"
        try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val pasTime: Date = dateFormat.parse(dataDate)
            val nowTime = Date()
            val dateDiff: Long = nowTime.getTime() - pasTime.getTime()
            val second: Long = TimeUnit.MILLISECONDS.toSeconds(dateDiff)
            val minute: Long = TimeUnit.MILLISECONDS.toMinutes(dateDiff)
            val hour: Long = TimeUnit.MILLISECONDS.toHours(dateDiff)
            val day: Long = TimeUnit.MILLISECONDS.toDays(dateDiff)
            if (second < 60) {
                convTime = "$second Seconds $suffix"
            } else if (minute < 60) {
                convTime = "$minute Minutes $suffix"
            } else if (hour < 24) {
                convTime = "$hour Hours $suffix"
            } else if (day >= 7) {
                convTime = if (day > 360) {
                    (day / 360).toString() + " Years " + suffix
                } else if (day > 30) {
                    (day / 30).toString() + " Months " + suffix
                } else {
                    (day / 7).toString() + " Week " + suffix
                }
            } else if (day < 7) {
                convTime = "$day Days $suffix"
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return convTime
    }
}