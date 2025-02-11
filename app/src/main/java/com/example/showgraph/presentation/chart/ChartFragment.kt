package com.example.showgraph.presentation.chart

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.showgraph.databinding.FragmentChartBinding
import com.example.showgraph.framework.BitmapSaver
import com.example.showgraph.presentation.base.BaseFragment
import com.example.showgraph.presentation.chart.adapter.PointsAdapter
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

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
            coordinatesRecyclerView.adapter = adapter

            saveChart.setOnClickListener {
                if (checkStoragePermission()) {
                    saveChartAsImage()
                } else {
                    requestStoragePermission()
                }
            }
        }
    }

    private fun observe() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    state.chartData?.let {
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
        }
    }

    private fun saveChartAsImage() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                with(binding) {
                    val bitmap = chart.chartBitmap
                    val fileName = "saved_image.png"
                    BitmapSaver.saveBitmap(
                        context = requireContext(),
                        bitmap = bitmap,
                        format = Bitmap.CompressFormat.PNG,
                        quality = 100,
                        fileName = fileName
                    ).getOrNull()?.let {
                        showMessage("График сохранён")
                    }
                }
            }
        }
    }

    private val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    }

    private fun checkStoragePermission(): Boolean {
        return ContextCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestStoragePermission() {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission), REQUEST_STORAGE_PERMISSION)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_STORAGE_PERMISSION) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                saveChartAsImage()
            } else {
                showError("Разрешение на запись не предоставлено")
            }
        }
    }

    private companion object {
        const val REQUEST_STORAGE_PERMISSION = 1001
    }
}