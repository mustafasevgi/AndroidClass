package com.example.todolist;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

// Sometimes none of the built-in controls meet your needs.
public class ToDoListItemView extends TextView {
    private Paint mMarginPaint;
    private Paint mLinePaint;
    private int mPaperColor;
    private int mMargin;

    public ToDoListItemView(Context context, AttributeSet ats, int ds) {
        super(context, ats, ds);
        init();
    }

    public ToDoListItemView(Context context) {
        super(context);
        init();
    }

    public ToDoListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // Get a reference to resources table.
        final Resources resources = getResources();

        // Anti-aliasing is important when drawing text as anti-aliased
        // text is significantly easier to read.
        mMarginPaint = new Paint(Paint.ANTI_ALIAS_FLAG); // Margin paint brush
        mMarginPaint.setColor(resources.getColor(R.color.margin_color));

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG); // Line paint brush
        mLinePaint.setColor(resources.getColor(R.color.line_color));

        // Get the paper background color
        mPaperColor = resources.getColor(R.color.paper_color);

        // Get the margin width.
        mMargin = (int) resources.getDimension(R.dimen.margin);
    }

    // We override onDraw method to modify the View's appearance
    @Override
    public void onDraw(Canvas canvas) {
        // Fill  entire canvas bitmap with paper color.
        canvas.drawColor(mPaperColor);

        // Draw ruled line
        final int lineX1 = 0;
        final int lineY1 = getMeasuredHeight();
        final int lineX2 = getMeasuredWidth();
        final int lineY2 = getMeasuredHeight();
        canvas.drawLine(lineX1, lineY1, lineX2, lineY2, mLinePaint);

        // Draw margin
        final int marginX1 = mMargin;
        final int marginY1 = 0;
        final int marginX2 = mMargin;
        final int marginY2 = getMeasuredHeight();
        canvas.drawLine(marginX1, marginY1, marginX2, marginY2, mMarginPaint);

        // Move the text across from the margin
        canvas.translate(mMargin, 0);

        // Render the text as usual using the TextView base class.
        super.onDraw(canvas);
    }
}
