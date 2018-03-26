package com.anrongtec.laucher.widget;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public class AutoRecyclerView extends RecyclerView {
	private int max_height = 400;

	public AutoRecyclerView(Context context) {
		super(context);
	}

	public AutoRecyclerView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		max_height = ViewUtils.dip2px(context, max_height);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (getChildCount() > 0) {
			int height;
			View child = getChildAt(0);
			LayoutParams params = (LayoutParams) child.getLayoutParams();
			child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
			int max = getAdapter().getItemCount() * (child.getMeasuredHeight() + getPaddingTop() + getPaddingBottom() + params.topMargin + params
					.bottomMargin);
			height = Math.min(max, max_height);
			setMeasuredDimension(widthMeasureSpec, height);
		} else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}
}
