package com.example.webviewscroll;

import android.util.Log;
import android.view.animation.Interpolator;;

public class UniformDecelerationInterpolator implements Interpolator {

	float v0;
	float a;
	float maxDistance;
	
	public UniformDecelerationInterpolator(float v0, float a, float maxDistance) {
		this.v0 = Math.abs(v0);
		this.a = Math.abs(a);
		this.maxDistance = Math.abs(maxDistance);
	}


	@Override
	public float getInterpolation(float t) {
		t = t * 1000;
		float vt = v0 - a * t;
		float ret = 0;
		if (vt < 0) {
			Log.i("inter", "v < 0");
			ret = 0.5f * v0 * v0 / a / maxDistance;
		} else {
			Log.i("inter", "v >= 0");
			ret = (v0 * t - 0.5f * a * t * t) / maxDistance;
		}
		ret = Math.max(0f, ret);
		ret = Math.min(1f, ret);
		Log.i("inter", String.format("t = %f, v0 = %f, a = %f, ret = %f", t, v0, a, ret));
		return ret;
	}
	
	int getMaxDistance() {
		float dist = 0.5f * v0 * v0 / a;
		if (dist < maxDistance) {
			return (int)(0.5f + dist);
		} else {
			return (int)(0.5f + maxDistance);
		}
	}


	public int getTimeMS() {
		float dist = 0.5f * v0 * v0 / a;
		if (dist < maxDistance) {
			return (int) (v0 / a + 0.5f);
		} else {
			return (int) (v0 - Math.sqrt(v0 * v0 - 2 * a * maxDistance) + 0.5f);
		}
	}
}
