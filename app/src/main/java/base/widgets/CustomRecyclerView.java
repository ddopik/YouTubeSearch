package base.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.example.youtubesearcher.R;

public class CustomRecyclerView extends RecyclerView {

    private static final int HORIZONTAL_RECYCLER_VIEW = 0;
    private static final int VERTICAL_RECYCLER_VIEW = 1;
    private static final int GRID_RECYCLER_VIEW = 2;

    public CustomRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        styleRecyclerView(context, attrs);
    }

    private void styleRecyclerView(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.CustomRecyclerView, 0, 0);
        int recyclerOrientation = typedArray.getInteger(R.styleable.CustomRecyclerView_orientation, 0);

        if (recyclerOrientation == HORIZONTAL_RECYCLER_VIEW) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            setLayoutManager(linearLayoutManager);
        } else if (recyclerOrientation == VERTICAL_RECYCLER_VIEW) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
//            linearLayoutManager.setAutoMeasureEnabled(false);
            setLayoutManager(linearLayoutManager);
        } else if (recyclerOrientation == GRID_RECYCLER_VIEW) {
            int spanSize = typedArray.getInteger(R.styleable.CustomRecyclerView_span_size, 2);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, spanSize);
            setLayoutManager(gridLayoutManager);
        }
        typedArray.recycle();
    }


}