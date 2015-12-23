package e.aakriti.work.common;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class WrappingListView extends ListView {
	public WrappingListView(Context context) {
		super(context);
	}

	public WrappingListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public WrappingListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int heightSpec = heightMeasureSpec;
		if (getLayoutParams().height == LayoutParams.WRAP_CONTENT) {
			// The great Android "hackatlon", the love, the magic.
			// The two leftmost bits in the height measure spec have
			// a special meaning, hence we can't use them to describe height.
			heightSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		}
		super.onMeasure(widthMeasureSpec, heightSpec);
	}
}
