package se.yumm.views;

import se.yumm.R;
import se.yumm.utils.AnimationTags;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class BottomButtonBar extends RelativeLayout {


	private ImageView m_nameSortButton;
	private ImageView m_ratingSortButton;
	private ImageView m_locationSortButton;
	private Animation[] m_animations;
	

	
	public BottomButtonBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		load();
	}
	
	private void load() {
		if (isInEditMode()) {
			return;
		}
		initView();
	}
	
	private void initView()
	{
		LayoutInflater.from(getContext()).inflate(R.layout.bottombar, this, true);
		
		m_nameSortButton = (ImageView) findViewById(R.id.name_sort_button);
		m_ratingSortButton = (ImageView) findViewById(R.id.rating_sort_button);
		m_locationSortButton = (ImageView) findViewById(R.id.location_sort_button);
		
	}
	
	public BottomButtonBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	public void SetUpButton(int id, int resId, View.OnClickListener listener)
	{
		switch (id) {
		case 1:
			m_nameSortButton.setImageResource(resId);
			m_nameSortButton.setVisibility(View.VISIBLE);
			m_nameSortButton.setOnClickListener(listener);
			break;
		case 2:
			m_ratingSortButton.setImageResource(resId);
			m_ratingSortButton.setVisibility(View.VISIBLE);
			m_ratingSortButton.setOnClickListener(listener);
			break;
		case 3:
			m_locationSortButton.setImageResource(resId);
			m_locationSortButton.setVisibility(View.VISIBLE);
			m_locationSortButton.setOnClickListener(listener);
			break;
		default:
			break;
		}
	}
	
	public void Animate(final int animationId, Context context)
	{
		Animation fadeAnim = AnimationUtils.loadAnimation(context, animationId);
		//fadeAnim.setFillEnabled(true);
		fadeAnim.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {}
			@Override
			public void onAnimationRepeat(Animation animation) {}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				
				
				if (animationId == R.anim.bottom_button_fade_in) {
					m_nameSortButton.setVisibility(View.VISIBLE);
					m_ratingSortButton.setVisibility(View.VISIBLE);
					m_locationSortButton.setVisibility(View.VISIBLE);
				}
				else
				{
					m_nameSortButton.setVisibility(View.GONE);
					m_ratingSortButton.setVisibility(View.GONE);
					m_locationSortButton.setVisibility(View.GONE);
				}
				
			}
		});
		
		m_nameSortButton.startAnimation(fadeAnim);
		m_ratingSortButton.startAnimation(fadeAnim);
		m_locationSortButton.startAnimation(fadeAnim);
	}
	
}
