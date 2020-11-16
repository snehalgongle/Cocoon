package com.snake.cocoon.fragment

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codingwithmitch.mviexample.api.MyRetrofitBuilder
import com.snake.cocoon.R
import com.snake.cocoon.adapter.TopStoriesAdapter
import com.snake.cocoon.data.network.Results
import kotlinx.android.synthetic.main.fragment_bookmark.*
import kotlinx.coroutines.*

/**
 * A simple [Fragment] subclass.
 * Use the [TopStoriesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TopStoriesFragment : Fragment() {
    lateinit var rootView: View
    lateinit var adapter: TopStoriesAdapter
    var resultsList = ArrayList<Results>()
    lateinit var recyclerView: RecyclerView
    lateinit var noDataLayout:LinearLayout
    val TAG = this.javaClass.simpleName
    lateinit var mySwipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_top_stories, container, false)
        initView()
        if (isOnline(requireActivity())) {
            recyclerView.visibility = View.VISIBLE
            noDataLayout.visibility = View.GONE
            getData()
        } else {
            recyclerView.visibility = View.GONE
            noDataLayout.visibility = View.VISIBLE
        }

        return rootView
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun initView() {
        adapter = TopStoriesAdapter(mContext = requireActivity(), resultsList = resultsList)
        recyclerView = rootView.findViewById<View>(R.id.top_recycleView) as RecyclerView
        noDataLayout = rootView.findViewById<View>(R.id.topNoData) as LinearLayout
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.adapter = adapter
        mySwipeRefreshLayout = rootView.findViewById<View>(R.id.top_swipe) as SwipeRefreshLayout

        mySwipeRefreshLayout.setOnRefreshListener {
            Log.i(TAG, "onRefresh called from SwipeRefreshLayout")

            // This method performs the actual data-refresh operation.
            // The method calls setRefreshing(false) when it's finished.
            if (isOnline(requireActivity())) {
                recyclerView.visibility = View.VISIBLE
                noDataLayout.visibility = View.GONE
                getData()
            } else {
                recyclerView.visibility = View.GONE
                noDataLayout.visibility = View.VISIBLE

            }


        }
    }

    private fun getData() {
        val api_key: String = "o3bPtHrknIpKsQtAFGDgeGftxuKYV7HV"
        val apiService = MyRetrofitBuilder.apiService
        CoroutineScope(Dispatchers.IO).launch {
            resultsList.clear()
            val response = apiService.getTopStories(api_key)
            MainScope().launch {
                withContext(Dispatchers.Default) {
                    resultsList.addAll(response.execute().body()!!.results)
                }
                adapter.notifyDataSetChanged()
            }
        }
        if (mySwipeRefreshLayout.isRefreshing) {
            mySwipeRefreshLayout.setRefreshing(false)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }


}