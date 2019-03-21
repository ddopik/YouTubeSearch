package base.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;




/**
 * Created by abdalla_maged on 10/18/2018.
 */

public class CustomTextView extends AppCompatTextView {

    public CustomTextView(Context context)
    { super(context); setFont(context); }

    public CustomTextView(Context context,AttributeSet set)
    { super(context,set); setFont(context); }

    public CustomTextView(Context context,AttributeSet set,int defaultStyle)
    { super(context,set,defaultStyle); setFont(context); }

    private void setFont(Context context) {

//        Typeface typeface=Typeface.createFromAsset(getContext().getAssets(),"font/arial_rounded_font.ttf");
//        Typeface typeface= ResourcesCompat.getFont(context, R.font.segoe_sb);
//        setTypeface(typeface); //function used to set font

    }
}

