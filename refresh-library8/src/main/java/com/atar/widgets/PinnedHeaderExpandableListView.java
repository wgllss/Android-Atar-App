package com.atar.widgets;//package com.atar.widgets;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.graphics.Canvas;
//import android.graphics.Rect;
//import android.util.AttributeSet;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AbsListView;
//import android.widget.ExpandableListView;
//import android.widget.AbsListView.OnScrollListener;
//
///**
// * 
// *****************************************************************************************************************************************************************************
// * �����������̶������� ExpandableListView
// * @author :Atar
// * @createTime:2015-6-5�????5:35:59
// * @version:1.0.0
// * @modifyTime:
// * @modifyAuthor:
// * @description:
// *****************************************************************************************************************************************************************************
// */
//public class PinnedHeaderExpandableListView extends ExpandableListView implements OnScrollListener {
//	public interface OnHeaderUpdateListener {
//		public View getPinnedHeader();
//
//		public void updatePinnedHeader(View headerView, int groupPosition);
//	}
//
//	private View mHeaderView;
//	private int mHeaderWidth;
//	private int mHeaderHeight;
//	private View mTouchTarget;
//	private OnScrollListener mScrollListener;
//	private OnHeaderUpdateListener mHeaderUpdateListener;
//	private boolean mActionDownHappened = false;
//	protected boolean mIsHeaderGroupClickable = true;
//
//	public PinnedHeaderExpandableListView(Context context) {
//		super(context);
//		initView();
//	}
//
//	public PinnedHeaderExpandableListView(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		initView();
//	}
//
//	public PinnedHeaderExpandableListView(Context context, AttributeSet attrs, int defStyle) {
//		super(context, attrs, defStyle);
//		initView();
//	}
//
//	private void initView() {
//		setFadingEdgeLength(0);
//		setOnScrollListener(this);
//	}
//
//	@Override
//	public void setOnScrollListener(OnScrollListener l) {
//		if (l != this) {
//			mScrollListener = l;
//		} else {
//			mScrollListener = null;
//		}
//		super.setOnScrollListener(this);
//	}
//
//	/**
//	 *  �?group添�????��?��??件�?????
//	 * @author :Atar
//	 * @createTime:2015-6-5�????6:06:21
//	 * @version:1.0.0
//	 * @modifyTime:
//	 * @modifyAuthor:
//	 * @param onGroupClickListener
//	 * @param isHeaderGroupClickable
//	 * @description:�?�????group?????��?��????��??�????�????OnGroupClickListener#onGroupClick�?�????true�?
//	 * 并�??isHeaderGroupClickable设为false??��??
//	 */
//	public void setOnGroupClickListener(OnGroupClickListener onGroupClickListener, boolean isHeaderGroupClickable) {
//		mIsHeaderGroupClickable = isHeaderGroupClickable;
//		super.setOnGroupClickListener(onGroupClickListener);
//	}
//
//	public void setOnHeaderUpdateListener(OnHeaderUpdateListener listener) {
//		mHeaderUpdateListener = listener;
//		if (listener == null) {
//			mHeaderView = null;
//			mHeaderWidth = mHeaderHeight = 0;
//			return;
//		}
//		mHeaderView = listener.getPinnedHeader();
//		mHeaderView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//		int firstVisiblePos = getFirstVisiblePosition();
//		int groupPosition = getPackedPositionGroup(getExpandableListPosition(firstVisiblePos));
//		listener.updatePinnedHeader(mHeaderView, groupPosition);
//		requestLayout();
//		postInvalidate();
//	}
//
//	@Override
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//		if (mHeaderView == null) {
//			return;
//		}
//		measureChild(mHeaderView, widthMeasureSpec, heightMeasureSpec);
//		mHeaderWidth = mHeaderView.getMeasuredWidth();
//		mHeaderHeight = mHeaderView.getMeasuredHeight();
//	}
//
//	@Override
//	protected void onLayout(boolean changed, int l, int t, int r, int b) {
//		super.onLayout(changed, l, t, r, b);
//		if (mHeaderView == null) {
//			return;
//		}
//		int delta = mHeaderView.getTop();
//		mHeaderView.layout(0, delta, mHeaderWidth, mHeaderHeight + delta);
//	}
//
//	@Override
//	protected void dispatchDraw(Canvas canvas) {
//		super.dispatchDraw(canvas);
//		if (mHeaderView != null) {
//			drawChild(canvas, mHeaderView, getDrawingTime());
//		}
//	}
//
//	@Override
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//		int x = (int) ev.getX();
//		int y = (int) ev.getY();
//		int pos = pointToPosition(x, y);
//		if (mHeaderView != null && y >= mHeaderView.getTop() && y <= mHeaderView.getBottom()) {
//			if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//				mTouchTarget = getTouchTarget(mHeaderView, x, y);
//				mActionDownHappened = true;
//			} else if (ev.getAction() == MotionEvent.ACTION_UP) {
//				View touchTarget = getTouchTarget(mHeaderView, x, y);
//				if (touchTarget == mTouchTarget && mTouchTarget.isClickable()) {
//					mTouchTarget.performClick();
//					invalidate(new Rect(0, 0, mHeaderWidth, mHeaderHeight));
//				} else if (mIsHeaderGroupClickable) {
//					int groupPosition = getPackedPositionGroup(getExpandableListPosition(pos));
//					if (groupPosition != INVALID_POSITION && mActionDownHappened) {
//						if (isGroupExpanded(groupPosition)) {
//							collapseGroup(groupPosition);
//						} else {
//							expandGroup(groupPosition);
//						}
//					}
//				}
//				mActionDownHappened = false;
//			}
//			return true;
//		}
//
//		return super.dispatchTouchEvent(ev);
//	}
//
//	@SuppressLint("NewApi") private View getTouchTarget(View view, int x, int y) {
//		if (!(view instanceof ViewGroup)) {
//			return view;
//		}
//
//		ViewGroup parent = (ViewGroup) view;
//		int childrenCount = parent.getChildCount();
//		final boolean customOrder = isChildrenDrawingOrderEnabled();
//		View target = null;
//		for (int i = childrenCount - 1; i >= 0; i--) {
//			final int childIndex = customOrder ? getChildDrawingOrder(childrenCount, i) : i;
//			final View child = parent.getChildAt(childIndex);
//			if (isTouchPointInView(child, x, y)) {
//				target = child;
//				break;
//			}
//		}
//		if (target == null) {
//			target = parent;
//		}
//
//		return target;
//	}
//
//	private boolean isTouchPointInView(View view, int x, int y) {
//		if (view.isClickable() && y >= view.getTop() && y <= view.getBottom() && x >= view.getLeft() && x <= view.getRight()) {
//			return true;
//		}
//		return false;
//	}
//
//	public void requestRefreshHeader() {
//		refreshHeader();
//		invalidate(new Rect(0, 0, mHeaderWidth, mHeaderHeight));
//	}
//
//	protected void refreshHeader() {
//		if (mHeaderView == null) {
//			return;
//		}
//		int firstVisiblePos = getFirstVisiblePosition();
//		int pos = firstVisiblePos + 1;
//		int firstVisibleGroupPos = getPackedPositionGroup(getExpandableListPosition(firstVisiblePos));
//		int group = getPackedPositionGroup(getExpandableListPosition(pos));
//		if (group == firstVisibleGroupPos + 1) {
//			View view = getChildAt(1);
//			if (view == null) {
//				return;
//			}
//			if (view.getTop() <= mHeaderHeight) {
//				int delta = mHeaderHeight - view.getTop();
//				mHeaderView.layout(0, -delta, mHeaderWidth, mHeaderHeight - delta);
//			} else {
//				mHeaderView.layout(0, 0, mHeaderWidth, mHeaderHeight);
//			}
//		} else {
//			mHeaderView.layout(0, 0, mHeaderWidth, mHeaderHeight);
//		}
//		if (mHeaderUpdateListener != null) {
//			mHeaderUpdateListener.updatePinnedHeader(mHeaderView, firstVisibleGroupPos);
//		}
//	}
//
//	@Override
//	public void onScrollStateChanged(AbsListView view, int scrollState) {
//		if (mScrollListener != null) {
//			mScrollListener.onScrollStateChanged(view, scrollState);
//		}
//	}
//
//	@Override
//	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//		if (totalItemCount > 0) {
//			refreshHeader();
//		}
//		if (mScrollListener != null) {
//			mScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
//		}
//	}
//
// }