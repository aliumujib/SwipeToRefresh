package com.aliumujib.swipetorefresh;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.AnimatorRes;
import android.support.v4.view.ViewCompat;
import android.view.animation.Animation;

import com.aliumujib.swipetorefresh.demo.library.R;

/**
 * Created by ABDUL-MUJEEB ALIU on 05/12/2017.
 */

public class ProgressImage extends android.support.v7.widget.AppCompatImageView {
    // Final variables
    private final int FAD = 0, SCALE = 1, ROTATION = 2, ROTATE_X = 3, ROTATE_Y = 4, BOUNCE = 5;

    // Main component references
    private Context mContext;
    private int animationType;
    private Animator mainAnimator;
    private boolean stopAnimation;

    private static final int KEY_SHADOW_COLOR = 0x1E000000;
    private static final int FILL_SHADOW_COLOR = 0x3D000000;

    // PX
    private static final float X_OFFSET = 0f;
    private static final float Y_OFFSET = 1.75f;
    private static final float SHADOW_RADIUS = 3.5f;
    private static final int SHADOW_ELEVATION = 4;

    private Animation.AnimationListener mListener;
    private int mShadowRadius;

    /**
     * Enums of the animations
     */
    public enum Animations {
        FAD, SCALE, ROTATION, ROTATE_X, ROTATE_Y, BOUNCE
    }

    public ProgressImage(Context context, Animations animations) {
        super(context);

        mContext = context;
        handelAnimation(animations);
    }


    public ProgressImage(Context context, int color, final float radius) {
        super(context);
        final float density = getContext().getResources().getDisplayMetrics().density;
        final int diameter = (int) (radius * density * 2);
        final int shadowYOffset = (int) (density * Y_OFFSET);
        final int shadowXOffset = (int) (density * X_OFFSET);

        mShadowRadius = (int) (density * SHADOW_RADIUS);

        ShapeDrawable circle;
        if (elevationSupported()) {
            circle = new ShapeDrawable(new OvalShape());
            ViewCompat.setElevation(this, SHADOW_ELEVATION * density);
        } else {
            OvalShape oval = new ProgressImage.OvalShadow(mShadowRadius, diameter);
            circle = new ShapeDrawable(oval);
            ViewCompat.setLayerType(this, ViewCompat.LAYER_TYPE_SOFTWARE, circle.getPaint());
            circle.getPaint().setShadowLayer(mShadowRadius, shadowXOffset, shadowYOffset,
                    KEY_SHADOW_COLOR);
            final int padding = (int) mShadowRadius;
            // set padding so the inner image sits correctly within the shadow.
            setPadding(padding, padding, padding, padding);
        }
        circle.getPaint().setColor(color);
        setBackgroundDrawable(circle);

        mContext = context;
        try {
            attachAnimation(animationType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean elevationSupported() {
        return android.os.Build.VERSION.SDK_INT >= 21;
    }

    public void setAnimationListener(Animation.AnimationListener listener) {
        mListener = listener;
    }

    @Override
    public void onAnimationStart() {
        super.onAnimationStart();
        if (mListener != null) {
            mListener.onAnimationStart(getAnimation());
        }
    }

    @Override
    public void onAnimationEnd() {
        super.onAnimationEnd();
        if (mListener != null) {
            mListener.onAnimationEnd(getAnimation());
        }
    }

    /**
     * Function to attach animation to View
     *
     * @param animationType Animation type id
     */
    public void attachAnimation(int animationType) {
        this.animationType = animationType;
        switch (animationType) {

            case FAD:
                startAnimation(R.animator.fade_in_out);
                break;

            case SCALE:
                startAnimation(R.animator.scale_small_larg);
                break;

            case ROTATION:
                startAnimation(R.animator.rotate);
                break;

            case ROTATE_X:
                startAnimation(R.animator.rotate_x);
                break;

            case ROTATE_Y:
                startAnimation(R.animator.rotate_y);
                break;

        }

    }


    /**
     * Function to handel animation
     *
     * @param animations Type of animation
     */
    private void handelAnimation(Animations animations) {

        if (animations == Animations.FAD) {
            startAnimation(R.animator.fade_in_out);
        } else if (animations == Animations.SCALE) {
            startAnimation(R.animator.scale_small_larg);
        } else if (animations == Animations.ROTATION) {
            startAnimation(R.animator.rotate);
        } else if (animations == Animations.ROTATE_X) {
            startAnimation(R.animator.rotate_x);
        } else if (animations == Animations.ROTATE_Y) {
            attachAnimation(R.animator.rotate_y);
        }

    }

    /**
     * Function to attach animation to view
     *
     * @param id Animation resource ID
     */
    private void startAnimation(@AnimatorRes int id) {

        mainAnimator = AnimatorInflater.loadAnimator(getContext(), id);
        mainAnimator.setTarget(this);
        mainAnimator.start();
        stopAnimation = true;
        mainAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (stopAnimation) {
                    mainAnimator.start();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * Function to start animate progress
     */
    public void startAnimation() {
        setVisibility(VISIBLE);
        stopAnimation = true;
        mainAnimator.start();
    }

    /**
     * Function to stop animating and hide progress
     */
    public void stopAnimationAndHide() {
        setVisibility(GONE);
        stopAnimation();
    }

    /**
     * Function to stop animating
     */
    public void stopAnimation() {
        stopAnimation = false;
    }

    private class OvalShadow extends OvalShape {
        private RadialGradient mRadialGradient;
        private int mShadowRadius;
        private Paint mShadowPaint;
        private int mCircleDiameter;

        public OvalShadow(int shadowRadius, int circleDiameter) {
            super();
            mShadowPaint = new Paint();
            mShadowRadius = shadowRadius;
            mCircleDiameter = circleDiameter;
            mRadialGradient = new RadialGradient(mCircleDiameter / 2, mCircleDiameter / 2,
                    mShadowRadius, new int[] {
                    FILL_SHADOW_COLOR, Color.TRANSPARENT
            }, null, Shader.TileMode.CLAMP);
            mShadowPaint.setShader(mRadialGradient);
        }

        @Override
        public void draw(Canvas canvas, Paint paint) {
            final int viewWidth = ProgressImage.this.getWidth();
            final int viewHeight = ProgressImage.this.getHeight();
            canvas.drawCircle(viewWidth / 2, viewHeight / 2, (mCircleDiameter / 2 + mShadowRadius),
                    mShadowPaint);
            canvas.drawCircle(viewWidth / 2, viewHeight / 2, (mCircleDiameter / 2), paint);
        }
    }

}