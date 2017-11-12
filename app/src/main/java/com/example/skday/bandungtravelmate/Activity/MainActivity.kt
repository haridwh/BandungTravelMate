package com.example.skday.bandungtravelmate.Activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import com.example.skday.bandungtravelmate.Adapter.PlaceAdapter
import com.example.skday.bandungtravelmate.POJO.ListPlace
import com.example.skday.bandungtravelmate.R
import com.example.skday.bandungtravelmate.Service.MapClient
import com.example.skday.bandungtravelmate.Service.ServiceGenerator
import com.example.skday.bandungtravelmate.Utils.EndPoints
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolBar)
        supportActionBar?.title = "Bandung Travel Mate"

        getListPlace()
    }

    private fun getListPlace() {
        var client = ServiceGenerator.createService(MapClient::class.java)
        var api = client.requestListPlace()
        api.enqueue(object : Callback<ListPlace> {
            override fun onResponse(call: Call<ListPlace>?, response: Response<ListPlace>?) {
                if (response != null) {
                    if (response.isSuccessful) {
                        var data = response.body()?.results
                        val adapter = PlaceAdapter(this@MainActivity, data!!)
                        rvPlace.adapter = adapter
                        rvPlace.layoutManager = LinearLayoutManager(this@MainActivity)
                    }
                }
            }

            override fun onFailure(call: Call<ListPlace>?, t: Throwable?) {
                var data: List<ListPlace.ResultsBean> = Gson().fromJson(EndPoints.DUMMY_RESPONSE, object : TypeToken<List<ListPlace.ResultsBean>>() {}.type)
                val adapter = PlaceAdapter(this@MainActivity, data!!)
                rvPlace.adapter = adapter
                rvPlace.layoutManager = LinearLayoutManager(this@MainActivity)
                Log.d("Failure", t?.message)
                Toast.makeText(this@MainActivity, "Terjadi Kesalah", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == R.id.menu_profile) {
            startActivity(Intent(this, ProfileActivity::class.java))
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}
