package com.nikasov.waterbalance.utils

import android.content.Context
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import kotlinx.android.synthetic.main.chart_marker_view.view.*

class CustomMarkerView(
    context: Context,
    private val layoutRes: Int
)
    : MarkerView(context, layoutRes) {

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        tvContent.text = "opa"
        super.refreshContent(e, highlight)
    }

    override fun getOffsetForDrawingAtPoint(posX: Float, posY: Float): MPPointF {
        return super.getOffsetForDrawingAtPoint((-width).toFloat(), height.toFloat())
    }
}