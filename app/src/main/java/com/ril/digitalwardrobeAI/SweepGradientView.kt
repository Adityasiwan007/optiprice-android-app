package com.ril.digitalwardrobeAI

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.ril.digitalwardrobeAI.Constants.LOGTAG
import com.ril.digitalwardrobeAI.View.Fragment.ColorFragment

public class SweepGradientView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): View(context, attrs, defStyleAttr) {

    lateinit var fragment: ColorFragment;
    @kotlin.jvm.JvmField
    var cx: Double=0.0.toDouble()

    @kotlin.jvm.JvmField
    public var cy:Double=0.0.toDouble();

    @kotlin.jvm.JvmField
    public var r = 0.0.toDouble();
    private val archPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 13 * context.resources.displayMetrics.density
    }

    private val archBounds = RectF()
    private val archInset = 72 * context.resources.displayMetrics.density

    private lateinit var gradientColors: IntArray;
    private lateinit var gradientPositions: FloatArray
    //= floatArrayOf(0/360f,60/360f, 120/360f)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        val size = Math.min(width, height)
        cx = width / 2f .toDouble()
        cy = height / 2f .toDouble()
        r = (width.toFloat() - (archInset * 2))/2.toDouble()
        setMeasuredDimension(size, size)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
      //gradientColors= intArrayOf(R.color.white,R.color.white);
       //gradientPositions= floatArrayOf(0f,1f);

    }
var position=0;
    var length=0;
    fun setColors(colors: IntArray,uploadedColor: Int) {
        gradientColors = colors;
        length=colors.size
        val inc = ((120f / 360).toFloat() / (colors.size - 1).toFloat());
        var f = FloatArray(colors.size);
        var matchColorCount = 0
        for (i in 0..colors.size - 1) {
            if (i == 0)
                f[i] = 0.0f;
            else if (i == colors.size - 1)
                f[i] = 120f / 360;
            else
                f[i] = (f[i - 1] + inc).toFloat();

            if (uploadedColor == colors[i]) {
                //Log.d(LOGTAG, " uploaded color " + uploadedColor + " colors in wardrobe " + colors[i] + ",loc in arc " + f[i]);
                position = i;
                matchColorCount++;
            }
        }
         position = position - (matchColorCount/2)
        gradientPositions = f;

    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.d(LOGTAG,"View  WIDTH " + width+", height "+height)

        val cx = width / 2f
        val cy = height / 2f
        Log.d(LOGTAG,"SweepGradient center x "+cx+", center y "+cy+", archInset"+archInset)
        archPaint.shader = SweepGradient(width / 2f, height / 2f, gradientColors, gradientPositions)
        archBounds.set(archInset, archInset, width.toFloat() - archInset, height.toFloat() - archInset)
        canvas.save()
        canvas.rotate(-90f, cx, cy)
        canvas.drawArc(archBounds, 0f, 120f, false, archPaint)
        // canvas.drawCircle(cx, cy, width / 2.2f, archPaint)
        // calculate marker postion
        var angle=((120.0f/length)*position)

        if(fragment!=null){
            fragment.setMarkerPosition(angle);
        }
        Log.d(VIEW_LOG_TAG,"angle $angle")
        canvas.restore()
    }

}