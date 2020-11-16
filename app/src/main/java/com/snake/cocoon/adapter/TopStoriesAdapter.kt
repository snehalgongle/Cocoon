package com.snake.cocoon.adapter

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.snake.cocoon.BrowserActivity
import com.snake.cocoon.R
import com.snake.cocoon.data.local.AppDatabase
import com.snake.cocoon.data.local.TopStoriesTable
import com.snake.cocoon.data.network.Results
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class TopStoriesAdapter(mContext: Context, resultsList: List<Results>) :
    RecyclerView.Adapter<TopStoriesAdapter.Holder>() {
    private val mContext: Context
    private var resultsList = ArrayList<Results>()

    init {
        this.mContext = mContext
        this.resultsList = resultsList as ArrayList<Results>
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView
        val textViewTitle: TextView
        val textViewDescription: TextView
        val textViewDate: TextView
        val imageBookmark: ImageView
        val cardView: CardView

        init {
            imageView = itemView.findViewById(R.id.image)
            textViewTitle = itemView.findViewById(R.id.txtTitle)
            textViewDescription = itemView.findViewById(R.id.txtDescription)
            textViewDate = itemView.findViewById(R.id.txtDate)
            imageBookmark = itemView.findViewById(R.id.imageBookmark)
            cardView = itemView.findViewById(R.id.cardView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(mContext).inflate(
                R.layout.layout_top_stories,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val result: Results = resultsList[position]
        holder.textViewDate.setText(
            "published: " + covertTimeToText(result.published_date)
        )
        holder.textViewTitle.setText(
            result.title
        )
        holder.textViewDescription.setText(
            result.abstract
        )
        try {
            Glide.with(mContext)
                .load(result.multimedia.get(0).url)
                .into(holder.imageView)
        } catch (ex: Exception) {
        }

        holder.cardView.setOnClickListener(View.OnClickListener { view: View? ->
            val intent = Intent(mContext, BrowserActivity::class.java)
            intent.putExtra(mContext.resources.getString(R.string.extra_main_url), result.url)
            mContext.startActivity(intent)

        })
        holder.imageBookmark.setOnClickListener(View.OnClickListener { view: View? ->

            val topStories = TopStoriesTable(
                title = result.title,
                description = result.abstract,
                publishDate = result.published_date,
                image = result.multimedia.get(0).url,
                mainUrl=result.url
            )
            saveData(topStories)
        })
    }

    private fun saveData(topStoriesTable: TopStoriesTable) {

        class DataSave: AsyncTask<Void, Void, Void>(){
            override fun doInBackground(vararg p0: Void?): Void? {
                val dao = AppDatabase.getInstance(mContext).topStoriesDao()
                dao.insert(topStoriesTable)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)
                Toast.makeText(mContext , "Saved", Toast.LENGTH_SHORT).show()
            }

        }
        DataSave().execute()

    }


    override fun getItemCount(): Int {
        return resultsList.size
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
