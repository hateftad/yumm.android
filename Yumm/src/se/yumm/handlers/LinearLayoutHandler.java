package se.yumm.handlers;

import se.yumm.R;
import se.yumm.utils.TAGS;
import android.R.integer;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LinearLayoutHandler extends LinearLayout
{
	private Context m_context;
	
	public LinearLayoutHandler(Context context)
	{
		super(context);
		m_context = context;
	}
	
	public LinearLayoutHandler(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		m_context = context;
	}
	
	public LinearLayoutHandler(Context context, AttributeSet attrs,int defStyle) 
	{
		super(context, attrs, defStyle);
		m_context = context;
	}
	public void AddTextView(int width, int height, int grav, String text, TAGS text1)
	{
		TextView v = new TextView(m_context);
		v.setTag(text1);
		v.setWidth(width);
		v.setHeight(height);
		v.setGravity(grav);
		v.setText(text);
		addView(v);
	}
	public void AddImageView(TAGS iden)
	{
		ImageView imageView = new ImageView(m_context);
		imageView.setTag(iden);
		imageView.setImageResource(R.drawable.hotspicy);
		addView(imageView);
	}
	LinearLayout GetLayout()
	{
		return this;
	}
}
