package se.yumm.listeners;

import se.yumm.StartActivity;
import android.R.integer;
import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnTouchListener;

public abstract class FlingGestureListener implements OnTouchListener
{

	private final GestureDetector m_gDetector;
	private MotionEvent m_LastOnDownEvent = null;
	
	public FlingGestureListener(Context context)
	{
		m_gDetector = new GestureDetector(context, new GestureListener());
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		m_LastOnDownEvent = event;
		boolean result = m_gDetector.onTouchEvent(event);
		if (!result)
		{
			if (event.getAction() == MotionEvent.ACTION_UP)
			{
				result = true;
			}
		}
		return true;
	}

	private class GestureListener extends GestureDetector.SimpleOnGestureListener
	{
		private static final int SWIPE_MIN_DISTANCE = 50;
		private static final int SWIPE_MIN_VELOCITY = 100;
		
		
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY)
		{
			//int changeInX = (int) (e2.getX() - e1.getX());
			
			if (e1==null)
			{
				//return false;
				e1 = m_LastOnDownEvent;
			}
			
			if (e1==null && e2 == null)
			{
				return false;
			} 
			
			 if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE 
					 && Math.abs(velocityX) > SWIPE_MIN_VELOCITY) {
		           onRightToLeft();
		           return true;
		        } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE 
		        		&& Math.abs(velocityX) > SWIPE_MIN_VELOCITY) {
		           onLeftToRight();
		           return true;
		        }
		        if(e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE 
		        		&& Math.abs(velocityY) > SWIPE_MIN_VELOCITY) {
		           onBottomToTop();
		           return true;
		        } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE 
		        		&& Math.abs(velocityY) > SWIPE_MIN_VELOCITY) {
		           onTopToBottom();
		           return true;
		        }
		        
			return false;

		}
		
		@Override
		public boolean onDown(MotionEvent evt)
		{
			m_LastOnDownEvent = evt;
			return true;
		}
		
		@Override
		public void onLongPress(MotionEvent e) 
		{
			System.out.println("kir");
        }
	}
	public abstract void onRightToLeft();
	public abstract void onLeftToRight();
	public abstract void onBottomToTop();
	public abstract void onTopToBottom();
	
	
}
