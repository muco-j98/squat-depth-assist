package com.muco.squatdepthassist.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View


class SkeletonOverlay constructor(context: Context?, attributeSet: AttributeSet?) :
    View(context, attributeSet) {

    private var bitmap: Bitmap? = null

    fun setBitmap(bitmap: Bitmap) {
        this.bitmap = bitmap
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (bitmap != null) {
            canvas.drawBitmap(bitmap!!, 0f, 0f, null)
        }
    }
}