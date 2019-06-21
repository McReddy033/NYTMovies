package com.example.nytmovies.data

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nytmovies.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_item.view.*

class MovieListAdapter(val movies: ArrayList<Model.Movie>, private val onItemClicked: ((link: String) -> Unit)) :
    RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MovieListAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.movie_item, p0, false))
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieListAdapter.ViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle = view.tvTitle
        val tvDate = view.tvDate
        val tvShortDescription = view.tvShortDescription
        //val tvAuthor = view.tvAuthor
        val ivMultimedia = view.ivMultimedia
        val tvHeadline = view.tvHeadline
        val tvReadMore = view.tvReadMore

        fun bind(item: Model.Movie) {
            tvTitle?.text = item.displayTitle
            tvDate?.text = item.publicationDate
            tvShortDescription?.text = item.summaryShort
            tvHeadline?.text =item.headline
            tvReadMore.setOnClickListener { onItemClicked.invoke(item.link.url) }
            Picasso.get().load(item.multimedia?.src).into(ivMultimedia)
        }

    }
}
