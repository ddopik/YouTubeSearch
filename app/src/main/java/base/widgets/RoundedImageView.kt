package base.widgets

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View

class RoundedImageView : AppCompatImageView {
    private var mPaint: Paint? = null
    private var mPath: Path? = null
    private var mBitmap: Bitmap? = null
    private var mMatrix: Matrix? = null
    private val mRadius = convertDpToPixel(10)
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var mDrawable: Drawable? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        mPaint = Paint()
        mPaint!!.color = Color.WHITE

        mPath = Path()
    }

    fun convertDpToPixel(dp: Int): Int {
        val displayMetrics = Resources.getSystem().displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), displayMetrics).toInt()
    }

   override fun setImageDrawable(drawable: Drawable?) {
        mDrawable = drawable
        if (drawable == null) {
            return
        }
        mBitmap = drawableToBitmap(drawable)
        mBitmap = roundCorner(mBitmap!!, 15f)
        val bDIWidth = mBitmap!!.width
        val bDIHeight = mBitmap!!.height
        //Fit to screen.
        val scale: Float
        if (mHeight / bDIHeight.toFloat() >= mWidth / bDIWidth.toFloat()) {
            scale = mHeight / bDIHeight.toFloat()
        } else {
            scale = mWidth / bDIWidth.toFloat()
        }
        val borderLeft = (mWidth - bDIWidth * scale) / 2
        val borderTop = (mHeight - bDIHeight * scale) / 2
        mMatrix = getImageMatrix()
        val drawableRect = RectF(0f, 0f, bDIWidth.toFloat(), bDIHeight.toFloat())
        val viewRect = RectF(borderLeft, borderTop, bDIWidth * scale + borderLeft, bDIHeight * scale + borderTop)
        mMatrix!!.setRectToRect(drawableRect, viewRect, Matrix.ScaleToFit.CENTER)
        invalidate()
    }


    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        val bitmap: Bitmap
        if (drawable is BitmapDrawable) {
            if (drawable.bitmap != null) {
                return drawable.bitmap
            }
        }
        if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        }
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    private fun roundCorner(src: Bitmap, round: Float): Bitmap {
        // image size
        val width = src.width
        val height = src.height

        // create bitmap output
        val result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        // set canvas for painting
        val canvas = Canvas(result)
        canvas.drawARGB(0, 0, 0, 0)

        // config paint
        val paint = Paint()
        paint.isAntiAlias = true
        paint.color = Color.BLACK

        // config rectangle for embedding
        val rect = Rect(0, 0, width, height)
        val rectF = RectF(rect)

        // draw rect to canvas
        canvas.drawRoundRect(rectF, round, round, paint)

        // create Xfer mode
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        // draw source image to canvas
        canvas.drawBitmap(src, rect, rect, paint)

        // return final image
        return result
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = View.MeasureSpec.getSize(widthMeasureSpec)
        mHeight = View.MeasureSpec.getSize(heightMeasureSpec)
        if (mDrawable != null && mHeight > 0 && mWidth > 0) {
            setImageDrawable(mDrawable)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (mBitmap == null) {
            return
        }
        canvas.drawColor(Color.TRANSPARENT)
        mPath!!.reset()
        mPath!!.moveTo(0f, mRadius.toFloat())
        mPath!!.lineTo(0f, canvas.height.toFloat())
        mPath!!.lineTo(canvas.width.toFloat(), canvas.height.toFloat())
        mPath!!.lineTo(canvas.width.toFloat(), mRadius.toFloat())
        mPath!!.quadTo(canvas.width.toFloat(), 0f, (canvas.width - mRadius).toFloat(), 0f)
        mPath!!.lineTo(mRadius.toFloat(), 0f)
        mPath!!.quadTo(0f, 0f, 0f, mRadius.toFloat())
        canvas.drawPath(mPath!!, mPaint!!)
        canvas.clipPath(mPath!!)
        canvas.drawBitmap(mBitmap!!, mMatrix!!, mPaint)
    }
}