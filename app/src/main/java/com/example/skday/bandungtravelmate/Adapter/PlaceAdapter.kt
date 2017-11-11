package com.example.skday.bandungtravelmate.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.example.skday.bandungtravelmate.POJO.ListPlace
import com.example.skday.bandungtravelmate.R
import com.example.skday.bandungtravelmate.Utils.EndPoints
import com.squareup.picasso.Picasso

/**
 * Created by skday on 11/11/17.
 */
class PlaceAdapter(var context: Context?, var listPlace: List<ListPlace.ResultsBean>?) : RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_place, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val place: ListPlace.ResultsBean = listPlace!![position]

        holder?.tvNama?.text = place.name
        holder?.tvAlamat?.text = place.formatted_address
        holder?.rbRating?.rating = place.rating.toFloat()
        Log.d("IMAGE_URL", EndPoints.IMAGE_URL + place.photos[0].photo_reference + "&key=${EndPoints.API_KEY}")
        Picasso.with(context)
                .load(EndPoints.IMAGE_URL + place.photos[0].photo_reference + "&key=${EndPoints.API_KEY}")
                .placeholder(R.drawable.placeholder)
                .fit()
                .into(holder?.ivPlace)
    }

    override fun getItemCount(): Int = listPlace!!.size

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var ivPlace = itemView?.findViewById<View>(R.id.iv_place) as ImageView
        var tvNama = itemView?.findViewById<View>(R.id.tv_nama) as TextView
        var rbRating = itemView?.findViewById<View>(R.id.rb_rating) as RatingBar
        var tvAlamat = itemView?.findViewById<View>(R.id.tv_alamat) as TextView
    }
}