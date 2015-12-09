package com.lc.animation;

import android.view.View;

import com.nineoldandroids.animation.ObjectAnimator;

public class FlipY extends BaseEffects {

	@Override
	protected void setupAnimation(View view) {
		getAnimatorSet().playTogether(
				ObjectAnimator.ofFloat(view, "scrollY", -150, 0).setDuration(
						mDuration)

		);
	}
}
