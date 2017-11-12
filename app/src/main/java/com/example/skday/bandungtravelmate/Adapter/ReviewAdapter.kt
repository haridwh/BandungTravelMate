package com.example.skday.bandungtravelmate.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import com.example.skday.bandungtravelmate.POJO.DetailPlace
import com.example.skday.bandungtravelmate.R

/**
 * Created by skday on 11/12/17.
 */
class ReviewAdapter(var context: Context, var listReview: List<DetailPlace.ResultBean.ReviewsBean>) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_review, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val review = listReview[position]

        holder?.tvNama?.text = review.author_name
        holder?.tvRating?.text = review.rating.toString()
        holder?.rbRating?.rating = review.rating.toFloat()
        holder?.tvTime?.text = review.relative_time_description
        holder?.tvReview?.text = review.text
    }

    override fun getItemCount(): Int = listReview.size

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var tvNama = itemView?.findViewById<View>(R.id.tv_nama) as TextView
        var tvRating = itemView?.findViewById<View>(R.id.tv_rating) as TextView
        var rbRating = itemView?.findViewById<View>(R.id.rb_rating) as RatingBar
        var tvTime = itemView?.findViewById<View>(R.id.tv_time) as TextView
        var tvReview = itemView?.findViewById<View>(R.id.tv_review) as TextView
    }
}