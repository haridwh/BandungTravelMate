package com.example.skday.bandungtravelmate.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.example.skday.bandungtravelmate.Adapter.ReviewAdapter
import com.example.skday.bandungtravelmate.POJO.DetailPlace
import com.example.skday.bandungtravelmate.R
import com.example.skday.bandungtravelmate.Service.MapClient
import com.example.skday.bandungtravelmate.Service.ServiceGenerator
import com.example.skday.bandungtravelmate.Utils.EndPoints
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_place.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPlaceActivity : AppCompatActivity(), OnMapReadyCallback {

    private var collapsedMenu: Menu? = null
    private var appBarExpanded: Boolean = true
    private var result: DetailPlace.ResultBean? = null
    var mapFragment: SupportMapFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_place)

        setSupportActionBar(animToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        collapsing_toolbar.title = "Detail"

        appbar.addOnOffsetChangedListener { _, verticalOffset ->
            if (Math.abs(verticalOffset) > 200) {
                appBarExpanded = false
                invalidateOptionsMenu()
            } else {
                appBarExpanded = true
                invalidateOptionsMenu()
            }
        }

        val intentData = intent
        collapsing_toolbar.title = intentData.getStringExtra("placeName")
        getDetailPlace(intentData.getStringExtra("placeId"))

        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as (SupportMapFragment)
        transparentImage.setOnTouchListener({ _, event ->
            val action = event.action
            when (action) {
                MotionEvent.ACTION_DOWN -> {
                    // Disallow ScrollView to intercept touch events.
                    nestedScrollView.requestDisallowInterceptTouchEvent(true)
                    // Disable touch on transparent view
                    false
                }

                MotionEvent.ACTION_UP -> {
                    // Allow ScrollView to intercept touch events.
                    nestedScrollView.requestDisallowInterceptTouchEvent(false)
                    true
                }

                MotionEvent.ACTION_MOVE -> {
                    nestedScrollView.requestDisallowInterceptTouchEvent(true)
                    false
                }

                else -> true
            }
        })

        fab_favorite.setOnClickListener {
            Toast.makeText(this@DetailPlaceActivity, "Coming Soon", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getDetailPlace(placeId: String) {
        var client = ServiceGenerator.createService(MapClient::class.java)
        var api = client.requestDetail(placeId, EndPoints.API_KEY)
        api.enqueue(object : Callback<DetailPlace> {
            override fun onResponse(call: Call<DetailPlace>?, response: Response<DetailPlace>?) {
                if (response != null) {
                    if (response.isSuccessful) {
                        if (response.body()?.status.equals("OK")){

                            result = response.body()?.result
                            Log.d("json", Gson().toJson(response.body()))
                            initView(result)
                            mapFragment?.getMapAsync(this@DetailPlaceActivity)
                        }else{
                            Toast.makeText(this@DetailPlaceActivity, "Something went wrong, Try again later ...", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<DetailPlace>?, t: Throwable?) {
                Log.d("Failure", t?.message)
                Toast.makeText(this@DetailPlaceActivity, "Something went wrong, Try again later ...", Toast.LENGTH_LONG).show()

            }
        })
    }

    private fun initView(data: DetailPlace.ResultBean?) {
        if (data != null) {
            Picasso.with(this)
                    .load(EndPoints.IMAGE_URL + data.photos[0].photo_reference + "&key=${EndPoints.API_KEY}")
                    .placeholder(R.drawable.placeholder)
                    .fit()
                    .into(header)
            tvTitle.text = data.name
            tvRating.text = data.rating.toString()
            rbRating.rating = data.rating.toFloat()
            tvAlamat.text = data.formatted_address
            if (data.formatted_phone_number != null) {
                tvPhoneNumber.text = data.formatted_phone_number
            } else {
                tvPhoneNumber.visibility = View.GONE
            }
            if (data.opening_hours != null) {
                if (data.opening_hours.isOpen_now) {
                    tvAccessTime.text = "Open now : ${data.opening_hours.weekday_text[1].substringAfter(':')}"
                }
            } else {
                tvAccessTime.visibility = View.GONE
            }

            val adapter = ReviewAdapter(this, data.reviews)
            rvReview.adapter = adapter
            rvReview.layoutManager = LinearLayoutManager(this)
        }

    }

    override fun onMapReady(map: GoogleMap?) {
        val lat = result?.geometry?.location?.lat
        val lng = result?.geometry?.location?.lng
        if (lat != null && lng != null) {
            map?.addMarker(MarkerOptions()
                    .position(LatLng(lat, lng))
                    .title(result?.name))
            map?.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lng), 16.0f))
        }
        Log.d("LatLng", "$lat $lng")
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        if (collapsedMenu != null && !appBarExpanded) {
            collapsedMenu!!.add("Favorite")
                    .setIcon(R.drawable.ic_favorite)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        }
        return super.onPrepareOptionsMenu(collapsedMenu)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        collapsedMenu = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        } else if (item?.title == "Favorite") {
            Toast.makeText(this@DetailPlaceActivity, "Coming Soon", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
}
