package com.example.showgraph.presentation.chart

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.showgraph.databinding.FragmentChartBinding
import com.example.showgraph.presentation.base.BaseFragment
import com.example.showgraph.presentation.chart.adapter.PointsAdapter
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ChartFragment : BaseFragment<FragmentChartBinding>(
    FragmentChartBinding::inflate
) {
    private val args: ChartFragmentArgs by navArgs()
    private val viewModel by viewModel<ChartViewModelImpl>{ parametersOf(args.count) }

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
            // Enable zooming
            chart.isDragEnabled = true
            chart.setScaleEnabled(true)
            chart.setPinchZoom(true)
            chart.description = Description().apply { text = "Координаты точек" }
            recyclerView.adapter = adapter
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    state.points?.let {
                        adapter.submitList(it.points)
                        setupChart(it.chartEntries)
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
            mode = LineDataSet.Mode.CUBIC_BEZIER // Enables smooth curve
        }
        val lineData = LineData(dataSet)
        with(binding) {
            chart.data = lineData
            chart.invalidate()
            // Customize X and Y axis
//            val xAxis: XAxis = chart.xAxis
//            xAxis.position = XAxis.XAxisPosition.BOTTOM
//            val leftAxis: YAxis = chart.axisLeft
//            leftAxis.setDrawGridLines(false)

            chart.axisRight.isEnabled = false        }
    }

    private fun saveChartAsImage() {
        with(binding) {
            val bitmap = chart.chartBitmap
                val file = File(Environment.getExternalStorageDirectory(), "chart.png")
            try {
                val outputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.flush()
                outputStream.close()
                showMessage("График сохранён: ${file.absolutePath}")
            } catch (e: IOException) {
                e.printStackTrace()
                showError("Ошибка сохранения графика")
            }
        }
    }
}