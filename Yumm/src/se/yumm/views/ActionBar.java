package se.yumm.views;

import se.yumm.R;
import se.yumm.utils.AnimationTags;
import se.yumm.utils.PropertiesManager;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ActionBar extends RelativeLayout {

	private LayoutInflater m_inflater;

	private ImageView m_homeBtn;
	private TextView m_title;
	private LinearLayout m_iconContainer;
	private Animation[] m_animations;
	private boolean m_pushedAside = false;


	public ActionBar(Context context, AttributeSet attrs) {
		super(context, attrs);

		m_inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		RelativeLayout barView = (RelativeLayout) m_inflater.inflate(R.layout.actionbar, null);
		addView(barView);

		m_homeBtn = (ImageView) barView.findViewById(R.id.leftActionBarBtn);
		m_title = (TextView) barView.findViewById(R.id.actionbar_title);
		m_iconContainer = (LinearLayout) barView.findViewById(R.id.actionbar_actionIcons);
		m_animations = new Animation[2];
		m_animations[AnimationTags.FADEOUT] = AnimationUtils.loadAnimation(getContext(), R.anim.action_bar_out_to_right);
		m_animations[AnimationTags.FADEIN] = AnimationUtils.loadAnimation(getContext(), R.anim.action_bar_in_from_left);
		
	}

	public void setHomeLogo(int resId) {
		setHomeLogo(resId, null);
	}

	public void setHomeLogo(int resId, OnClickListener onClickListener) {
		m_homeBtn.setImageResource(resId);
		m_homeBtn.setVisibility(View.VISIBLE);
		m_homeBtn.setOnClickListener(onClickListener);
		if (onClickListener != null) {

		}
	}

	public void setTitle(CharSequence title) {
		m_title.setText(title);
	}

	public void setTitle(int resid) {
		m_title.setText(resid);
	}

	public void addActionIcon(int iconResourceId, OnClickListener onClickListener) {
		// Inflate
		View view = m_inflater.inflate(R.layout.actionbar_icon, m_iconContainer, false);
		ImageButton imgButton = (ImageButton) view.findViewById(R.id.actionbar_item);
		imgButton.setImageResource(iconResourceId);
		imgButton.setOnClickListener(onClickListener);

		m_iconContainer.addView(view, m_iconContainer.getChildCount());
	}


	public boolean removeActionIconAt(int index) {
		int count = m_iconContainer.getChildCount();
		if (count > 0 && index >= 0 && index < count) {
			m_iconContainer.removeViewAt(index);
			return true;
		}
		return false;
	}

	public void Animate(final int animationId, Context context)
	{
		final float w = PropertiesManager.GetInstance().m_windowWidth;
		Animation TranslateAnim = AnimationUtils.loadAnimation(context, animationId);
		TranslateAnim.setFillEnabled(true);
		TranslateAnim.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {}
			@Override
			public void onAnimationRepeat(Animation animation) {}

			@Override
			public void onAnimationEnd(Animation animation) {

				animation = new TranslateAnimation(0.0f, 0.0f, 0.0f, 0.0f);
				animation.setDuration(1);
				m_homeBtn.startAnimation(animation);

				if (animationId == R.anim.action_bar_out_to_right) {
					int newHomeLeft = (int) (m_homeBtn.getLeft() + w * 0.85);
					m_homeBtn.layout(newHomeLeft, 
							m_homeBtn.getTop(), 
							newHomeLeft + m_homeBtn.getMeasuredWidth(), 
							m_homeBtn.getTop() + m_homeBtn.getMeasuredHeight());
					m_homeBtn.bringToFront();
					m_pushedAside = true;
				}
				else if(animationId == R.anim.action_bar_in_from_left)
				{
					int newHomeLeft = (int) (m_homeBtn.getLeft() - w * 0.85);
					m_homeBtn.layout(newHomeLeft, 
							m_homeBtn.getTop(), 
							newHomeLeft + m_homeBtn.getMeasuredWidth(), 
							m_homeBtn.getTop() + m_homeBtn.getMeasuredHeight());
					m_pushedAside = false;
				}

				if(m_iconContainer.getChildCount() > 0)
				{
					int newLeft = 0;
					for(int i=0; i < m_iconContainer.getChildCount(); i++)
					{
						View v = m_iconContainer.getChildAt(i);
						if(m_pushedAside)
						{
							newLeft = (int) (v.getLeft() + w);
							v.invalidate();
						}
						else
						{
							newLeft = (int) (v.getLeft() - w);
							v.bringToFront();
						}

						v.layout(newLeft, 
								v.getTop(), 
								newLeft + v.getMeasuredWidth(), 
								v.getTop() + v.getMeasuredHeight());
					}
				}

			}
		});

		m_homeBtn.startAnimation(TranslateAnim);

		Animation textAnim = AnimationUtils.loadAnimation(context, animationId);
		textAnim.setFillAfter(true);
		m_title.startAnimation(textAnim);

		for(int i=0; i < m_iconContainer.getChildCount(); i++)
		{
			m_iconContainer.getChildAt(i).startAnimation(TranslateAnim);

		}


	}

}
