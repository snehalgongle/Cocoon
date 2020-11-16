package com.snake.cocoon.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.snake.cocoon.R
import com.snake.cocoon.adapter.BookMarkAdapter
import com.snake.cocoon.data.local.AppDatabase
import com.snake.cocoon.data.local.TopStoriesTable


/**
 * A simple [Fragment] subclass.
 * Use the [BookmarkFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookmarkFragment : Fragment() {

    lateinit var rootView: View
    lateinit var adapter: BookMarkAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var noDataLayout: LinearLayout
    var topStoriesList = ArrayList<TopStoriesTable>()
    val TAG = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_bookmark, container, false)
        initView()
        getLocalData()
        return rootView
    }

    private fun initView() {
        recyclerView = rootView.findViewById<View>(R.id.recyclerView) as RecyclerView
        noDataLayout = rootView.findViewById<View>(R.id.noDataLayout) as LinearLayout
        adapter = BookMarkAdapter(requireActivity(), topStoriesList)
        recyclerView.layoutManager = GridLayoutManager(requireActivity(), 2)
        recyclerView.adapter = adapter
    }

    private fun getLocalData() {
        topStoriesList.clear()
        val dao = AppDatabase.getInstance(requireActivity()).topStoriesDao()
        topStoriesList.addAll(dao.getAll())
        if (topStoriesList.size == 0) {
            recyclerView.visibility = View.GONE
            noDataLayout.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            noDataLayout.visibility = View.GONE
        }
        adapter.notifyDataSetChanged()
//        recyclerView.isGone = recyclerView.isVisible
//        noDataLayout.isVisible = noDataLayout.isGone
    }
}