package se.yumm.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.HorizontalScrollView;

public class CustomHorizontalScrollView extends HorizontalScrollView implements
		OnTouchListener, OnGestureListener
{

	private static final int SWIPE_MIN_DISTANCE = 300;
	private static final int SWIPE_THRESHOLD_VELOCITY = 300;
	private static final int SWIPE_PAGE_ON_FACTOR = 10;

	private Context m_context;
	private GestureDetector gestureDetector;
	private int m_scrollTo = 0;
	private int m_maxItem;
	private int m_activeItem = 0;
	private float m_prevScrollX = 0;
	private boolean m_start = true;
	private int m_itemWidth;
	private float m_currentScrollX;
	private boolean flingDisable = true;

	

	public CustomHorizontalScrollView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}
	
	public void init()
	{
		gestureDetector = new GestureDetector(m_context, this);
		this.setOnTouchListener(this);
	}
	public CustomHorizontalScrollView(Context context, int maxItem, int itemWidth)
	{
		super(context);
		this.m_maxItem = maxItem;
		this.m_itemWidth = itemWidth;
		init();
	}
	//these are for snapping features
	public void SetMaxItem(int max)
	{
		m_maxItem = max;
	}
	//so is this one
	public void SetItemWidth(int width)
	{
		m_itemWidth = width;
	}
	@Override
	public boolean onDown(MotionEvent e)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY)
	{
		
		if (flingDisable)
		{
			return false;
		}
		
		boolean returnValue = false;
		float ptx1 = 0, ptx2 = 0;
		
		if (e1 == null || e2 == null)
		{
			return false;
		}
		ptx1 = e1.getX();
		ptx2 = e2.getX();
		// right to left

		if (ptx1 - ptx2 > SWIPE_MIN_DISTANCE
				&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) 
		{
			if (m_activeItem < m_maxItem - 1)
				m_activeItem = m_activeItem + 1;

			returnValue = true;

		} 
		else if (ptx2 - ptx1 > SWIPE_MIN_DISTANCE
				&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) 
		{
			if (m_activeItem > 0)
				m_activeItem = m_activeItem - 1;

			returnValue = true;
		}
		m_scrollTo = m_activeItem * m_itemWidth;
		this.smoothScrollTo(0, m_scrollTo);
		return returnValue;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		if (gestureDetector.onTouchEvent(event))
		{
			return true;
		}
		
		Boolean returnValue = gestureDetector.onTouchEvent(event);

		int x = (int) event.getRawX();

		switch (event.getAction())
		{
			case MotionEvent.ACTION_MOVE:
				if (m_start)
				{
					this.m_prevScrollX = x;
					m_start = false;
				}
			break;
			
			case MotionEvent.ACTION_UP:
				m_start = true;
				this.m_currentScrollX = x;
				int minFactor = m_itemWidth / SWIPE_PAGE_ON_FACTOR;

				if ((this.m_prevScrollX - this.m_currentScrollX) > minFactor)
				{
					if (m_activeItem < m_maxItem - 1)
						m_activeItem = m_activeItem + 1;

				} else if ((this.m_currentScrollX - this.m_prevScrollX) > minFactor)
				{
					if (m_activeItem > 0)
						m_activeItem = m_activeItem - 1;
				}
				System.out.println("horizontal : " + m_activeItem);
				m_scrollTo = m_activeItem * m_itemWidth;
				this.smoothScrollTo(m_scrollTo, 0);
				returnValue = true;
			break;
		}
		
		return returnValue;
	}

}
