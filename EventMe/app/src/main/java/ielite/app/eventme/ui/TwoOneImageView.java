package ielite.app.eventme.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by Suraj on 22-03-2016.
 */
public class TwoOneImageView extends ImageView {

    public TwoOneImageView(Context context) {
        super(context);
    }

    public TwoOneImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TwoOneImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int twoOneHeight = MeasureSpec.getSize(widthMeasureSpec) / 2;
        int twoOneHeightSpec = MeasureSpec
                .makeMeasureSpec(twoOneHeight, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, twoOneHeightSpec);
    }
}

