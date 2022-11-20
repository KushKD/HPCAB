

package com.kushkumardhawan.filepicker.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class SquareImage extends AppCompatImageView {
    public SquareImage(Context context) {
        this(context, null);
    }

    public SquareImage(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec);
    }
}
