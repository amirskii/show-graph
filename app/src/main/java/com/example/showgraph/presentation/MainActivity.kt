package com.example.showgraph.presentation

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.showgraph.R
import com.example.showgraph.databinding.ActivityMainBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }

    private fun fetchData(count: Int) {
        val url = "https://hr-challenge.dev.tapyou.com/api/test/points?count=$count"
        val requestQueue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            Log.d("1111", "fetchData: $response")
            parseResponse(response)
        }, { error ->
            Log.d("1111", "fetchData error: $error")
            Toast.makeText(this, "Ошибка загрузки данных", Toast.LENGTH_SHORT).show()
        })
        requestQueue.add(request)
    }

    private fun parseResponse(response: JSONObject) {
        val pointsArray = response.getJSONArray("points")
        val points = mutableListOf<Pair<Float, Float>>()
        val entries = mutableListOf<Entry>()

        for (i in 0 until pointsArray.length()) {
            val point = pointsArray.getJSONObject(i)
            val x = point.getDouble("x").toFloat()
            val y = point.getDouble("y").toFloat()
            points.add(Pair(x, y))
            entries.add(Entry(x, y))
        }

    }

    private fun setupRecyclerView(points: List<Pair<Float, Float>>) {
//        with(binding) {
//            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
//            recyclerView.adapter = PointsAdapter(points)
//        }
    }

}