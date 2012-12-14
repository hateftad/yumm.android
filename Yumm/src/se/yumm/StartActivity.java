package se.yumm;

import java.util.ArrayList;
import se.yumm.asyncTasks.GetPlaces;
import se.yumm.handlers.LinearLayoutHandler;
import se.yumm.listeners.FlingGestureListener;
import se.yumm.poi.Restaurants;
import se.yumm.utils.TAGS;
import se.yumm.views.CustomHorizontalScrollView;
import android.os.Bundle;
import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Point;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class StartActivity extends Activity implements OnClickListener
{
		
	private ArrayList<Restaurants> m_restaurants;
	private FlingGestureListener m_gLstr;
	private LinearLayoutHandler m_layoutHandler;
	private ListView m_listView;
	private CustomHorizontalScrollView m_horizView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		Display disp = getWindowManager().getDefaultDisplay();
		Point point  = new Point();
		disp.getSize(point);
		int width = point.x;
		int height = point.y;
		
		
		//m_horizView = new CHorizontalScrollView(this, 3, width);
		//m_horizView.setTag(TAGS.HSV);
		
		setContentView(R.layout.activity_start);
		
		m_horizView = (CustomHorizontalScrollView) findViewById(R.id.horizScrollView);
		m_horizView.SetItemWidth(width);
		m_horizView.SetMaxItem(3);//setting maximum of 3 items for horscrollview
		
		GetPlaces g = new GetPlaces(this);
		g.execute("http://yummapp.appspot.com/places/?q=&json=true");
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_start, menu);
		return true;
	}

	@Override
	public void onClick(View v)
	{
		

	}

	public void animateSwipe(boolean right)
	{
		TranslateAnimation anim = null;

		LinearLayout layout = (LinearLayout) this
				.findViewById(R.id.horizRestLayout);

		if (right)
		{
			anim = new TranslateAnimation(0.0f, layout.getWidth(), 0.0f, 0.0f);
		} else
		{
			anim = new TranslateAnimation(0.0f, -layout.getWidth(), 0.0f, 0.0f);
		}

		anim.setDuration(250);
		anim.setInterpolator(new AccelerateInterpolator(1.0f));

		layout.startAnimation(anim);
	}

}
