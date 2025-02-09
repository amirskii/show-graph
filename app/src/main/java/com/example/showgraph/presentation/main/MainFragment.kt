package com.example.showgraph.presentation.main

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.showgraph.databinding.FragmentMainBinding
import com.example.showgraph.presentation.base.BaseFragment
import com.example.showgraph.presentation.main.adapter.PointsAdapter
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<FragmentMainBinding>(
    FragmentMainBinding::inflate
) {
    private val viewModel by viewModel<MainViewModelImpl>()

    private val adapter by lazy {
        PointsAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        observe()
    }

    private fun setupUi() {
        with(binding) {
            recyclerView.adapter = adapter
            button.setOnClickListener {
                val count = editText.text.toString().toIntOrNull() ?: 0
                if (count > 0) {
                    viewModel.getPoints(count)
                } else {
                    showError("Введите корректное число точек")
                }
            }
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
//                    binding.swipeRefreshLayout.isRefreshing = state.loading

                    state.points?.let {
                        adapter.submitList(it)
                        val entries = it.map {
                            Entry(it.x, it.y)
                        }
                        setupChart(entries)
                    }

                    if (!state.error.isNullOrBlank()) {
                        showError(state.error)
                    }
                }
            }
        }
    }

    private fun setupChart(entries: List<Entry>) {
        val dataSet = LineDataSet(entries, "График координат").apply {
            color = Color.BLUE
            setDrawCircles(true)
            setDrawValues(false)
            setDrawFilled(true)
            lineWidth = 2f
            valueTextColor = Color.BLACK
//            mode = LineDataSet.Mode.CUBIC_BEZIER // Enables smooth curve
        }
        val lineData = LineData(dataSet)
        with(binding) {
            chart.data = lineData
            chart.description = Description().apply { text = "Координаты точек" }
            // Enable zooming
            chart.isDragEnabled = true
            chart.setScaleEnabled(true)
            chart.setPinchZoom(true)

            // Customize X and Y axis
//            val xAxis: XAxis = chart.xAxis
//            xAxis.position = XAxis.XAxisPosition.BOTTOM
//
//            val leftAxis: YAxis = chart.axisLeft
//            leftAxis.setDrawGridLines(false)
//            chart.axisRight.isEnabled = false

            chart.invalidate()
        }
    }

//    private fun saveChartAsImage() {
//        with(binding) {
//            val bitmap = chart.chartBitmap
//                val file = File(Environment.getExternalStorageDirectory(Environment.DIRECTORY_PICTURES), "chart.png")
//            try {
//                val outputStream = FileOutputStream(file)
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
//                outputStream.flush()
//                outputStream.close()
//                showMessage("График сохранён: ${file.absolutePath}")
//            } catch (e: IOException) {
//                e.printStackTrace()
//                showError("Ошибка сохранения графика")
//            }
//        }
//    }
}