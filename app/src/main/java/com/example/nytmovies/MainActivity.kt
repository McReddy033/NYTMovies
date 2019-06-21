package com.example.nytmovies

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.*
import android.widget.EditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import com.example.nytmovies.data.*


class MainActivity : AppCompatActivity() {

    private val nytApiService by lazy { NytApiService.create() }
    private val nytStartApiService by lazy { NytStartApiService.create() }
    private var offset = 0
    private val api_key = "DdBxG1tz1GaWa8WztcorKNYMPVcFkyI3"
    private var movieList = arrayListOf<Model.Movie>()
    private lateinit var rvMovieList: RecyclerView
    private lateinit var temp: Model.Movie
    private lateinit var mActionBarToolbar: Toolbar
    private lateinit var editTextSearch: EditText
    //private lateinit var btnSearch: Button
    private lateinit var btnAcSearch: MenuItem
    private lateinit var mLayoutManager: LinearLayoutManager
    //private lateinit var pgScrollListener : PaginationScrollListener

    val PAGE_START = 1
    private val currentPage = PAGE_START
    private var isLastPage = false
    private val totalPage = 20
    private var isLoading = false
    private var hasMore = true
    var itemCount = 0
    private var isStartSearh : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMovieList = findViewById(R.id.rvMovieList)
        mLayoutManager = LinearLayoutManager(this)
        rvMovieList.layoutManager = mLayoutManager

        var pgScrollListener = object : PaginationScrollListener(mLayoutManager) {

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadingIsComing() {
                isLoading = true

                //you have to call loadmore items to get more data
                getMoreItems()
            }
        }
        rvMovieList.addOnScrollListener(pgScrollListener)

        editTextSearch = findViewById(R.id.editText_search)
        mActionBarToolbar = findViewById(R.id.toolbarActionbar)
        mActionBarToolbar.title = ""
        setSupportActionBar(mActionBarToolbar)

        beginSearch(" ", offset)
    }

    private fun getMoreItems() {
        isLoading = false
        offset += 20
        beginSearch(editTextSearch.text.toString(), offset)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this).inflate(R.menu.menu_activity_main, menu)

        btnAcSearch = menu!!.findItem(R.id.actionSearch)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.actionSearch -> {
                if (editTextSearch.text.isNotEmpty()) {
                    isStartSearh = false
                    movieList.clear()
                    offset = 0
                    beginSearch(editTextSearch.text.toString(), offset)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun beginSearch(searching_article: String, startPosition: Int) {
        if (hasMore) {
            if (isStartSearh){
                Log.d("Search", " -------------------------------Begin start from " + startPosition)
                nytStartApiService.searchMovies(startPosition.toString(), api_key)
                    .enqueue(object : Callback<Model.Result> {
                        override fun onFailure(call: Call<Model.Result>, t: Throwable) {
                            //tv_count_articles.text = "Произошла ошибка"
                        }

                        override fun onResponse(call: Call<Model.Result>, response: Response<Model.Result>) {
                            hasMore = response.body()!!.hasMore.toString().toBoolean()
                            for (i in 0 until response.body()!!.results.size) {
                                temp = response.body()!!.results[i]
                                movieList.add(
                                    Model.Movie(
                                        temp.displayTitle,
                                        temp.byline,
                                        temp.headline,
                                        temp.summaryShort,
                                        temp.publicationDate,
                                        temp.link,
                                        temp.multimedia
                                    )
                                )
                            }
                            rvMovieList.adapter = null
                            rvMovieList.adapter = MovieListAdapter(movieList, { link -> naviationDetailScreen(link) })
                            rvMovieList.adapter!!.notifyDataSetChanged()
                        }
                    })

            }
            else {
                Log.d("Search", " -------------------------------Begin search from " + startPosition)
                nytApiService.searchMovies(searching_article, startPosition.toString(), api_key)
                    .enqueue(object : Callback<Model.Result> {
                        override fun onFailure(call: Call<Model.Result>, t: Throwable) {
                            //tv_count_articles.text = "Произошла ошибка"
                        }

                        override fun onResponse(call: Call<Model.Result>, response: Response<Model.Result>) {
                            hasMore = response.body()!!.hasMore.toString().toBoolean()
                            for (i in 0 until response.body()!!.results.size) {
                                temp = response.body()!!.results[i]
                                movieList.add(
                                    Model.Movie(
                                        temp.displayTitle,
                                        temp.byline,
                                        temp.headline,
                                        temp.summaryShort,
                                        temp.publicationDate,
                                        temp.link,
                                        temp.multimedia
                                    )
                                )
                            }
                            rvMovieList.adapter = null
                            rvMovieList.adapter = MovieListAdapter(movieList, { link -> naviationDetailScreen(link) })
                            rvMovieList.adapter!!.notifyDataSetChanged()
                        }
                    })
            }

        } else {
            Log.d("Search", " -----------------------------------Has more = false")
        }
    }

    private fun naviationDetailScreen(link: String) {
        val i = Intent(this@MainActivity, MovieWebActivity::class.java)
        i.putExtra("link", link)
        startActivity(i)
    }
}

