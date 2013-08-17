package com.example.webviewscroll;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AbsoluteLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AddressBar extends RelativeLayout {
	
	AddressBarImpl mAddressBarImpl = null;
	AbsoluteLayout.LayoutParams mAddressBarImplLayoutParams = null;
	boolean mAnimating = false;
	boolean mIsShown = false;

	public AddressBar(Context context) {
		super(context);
		init();
	}

	public AddressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public AddressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	
	private void init() {
		
		if (mAddressBarImpl != null) {
			return;
		}

		final int w = 500;
		final int h = 60;
		
		setMinimumHeight(h);
		setMinimumWidth(w);
		setBackgroundColor(0x00000000);
		
		AbsoluteLayout abs = new AbsoluteLayout(getContext()) {
			@Override
			protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
				heightMeasureSpec = MeasureSpec.getMode(heightMeasureSpec) | (h * 2);
				super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			}
		};
		abs.setLayoutParams(new RelativeLayout.LayoutParams(w, h * 2));
		((RelativeLayout.LayoutParams) abs.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		
		addView(abs);
		
		mAddressBarImpl = new AddressBarImpl(getContext());
		mAddressBarImplLayoutParams = new AbsoluteLayout.LayoutParams(w, h, 0, h);
		mAddressBarImpl.setBackgroundColor(0xffff0000);
		abs.addView(mAddressBarImpl, mAddressBarImplLayoutParams);
	}
	
	private static class AddressBarImpl extends TextView {
		public AddressBarImpl(Context context) {
			super(context);
			init();
		}

		public AddressBarImpl(Context context, AttributeSet attrs) {
			super(context, attrs);
			init();
		}

		public AddressBarImpl(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			init();
		}
		
		private void init() {
			setMinimumHeight(60);
			setMinimumWidth(500);
			setText("di zhi lan!");
		}
	}

	public int getContentTop() {
		return mAddressBarImplLayoutParams.y - mAddressBarImplLayoutParams.height;
	}

	public int getContentBottom() {
		return mAddressBarImplLayoutParams.y + mAddressBarImplLayoutParams.height - mAddressBarImplLayoutParams.height;
	}

	public int getContentHeight() {
		return mAddressBarImplLayoutParams.height;
	}
	
	public void moveContent(int dx, int dy) {
		
		mAddressBarImplLayoutParams.x += dx;
		mAddressBarImplLayoutParams.y += dy;
		
		if (getContentBottom() <= 0) {
			mIsShown = false;
		} else {
			mIsShown = true;
		}
		
		requestLayout();
	}

	public void showWithAnimation(long durationMS) {
		if (mAnimating || mIsShown) {
			return;
		}
		
		mAnimating = true;
		mIsShown = true;

		int dy = -getContentTop();
		mAddressBarImplLayoutParams.y += dy;
		requestLayout();
		TranslateAnimation anim = new TranslateAnimation(0, 0, -dy, 0);
		anim.setInterpolator(new DecelerateInterpolator(1.5f));;
		anim.setDuration(durationMS);
		anim.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {}
			@Override
			public void onAnimationRepeat(Animation animation) {}
			@Override
			public void onAnimationEnd(Animation animation) {
				mAnimating = false;
			}
		});
		mAddressBarImpl.startAnimation(anim);
	}
	
	public void hideWithAnimation(long durationMS) {
		if (mAnimating || !mIsShown) {
			return;
		}
		
		mAnimating = true;
		mIsShown = false;

		int dy = -getContentBottom();
		mAddressBarImplLayoutParams.y += dy;
		requestLayout();
		TranslateAnimation anim = new TranslateAnimation(0, 0, -dy, 0);
		anim.setInterpolator(new DecelerateInterpolator(1.5f));
		anim.setDuration(durationMS);
		anim.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {}
			@Override
			public void onAnimationRepeat(Animation animation) {}
			@Override
			public void onAnimationEnd(Animation animation) {
				mAnimating = false;
			}
		});
		mAddressBarImpl.startAnimation(anim);
	}
}
