package se.yumm;

//*****************************
/* @author Hatef Tadayon
/*
 * 
 */

import se.yumm.handlers.WebServiceHandler;
import se.yumm.utils.PropertiesManager;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Point;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;


public class StartActivity extends Activity implements OnClickListener
{
	private WebServiceHandler m_webHandler;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
	
		Point point  = new Point();
		Display disp = getWindowManager().getDefaultDisplay();
		disp.getSize(point);
		
		//settings for CustomHorizontalScrollView, minor Hack :)
		//on TODO list
		PropertiesManager.GetInstance().m_windowWidth = point.x;
		PropertiesManager.GetInstance().m_windowHeight = point.y;
		PropertiesManager.GetInstance().m_maxItems = 2;
		
		//logs in and retrieves initial data and fills listview all in one go,
		//most probably going to change this and write my own webhandler
		m_webHandler = new WebServiceHandler(this);
		m_webHandler.LoginClient();
		
		Button btn = (Button) findViewById(R.id.alphaSortBtn);
		btn.bringToFront();
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				if(m_webHandler.isLoggedIn())
					System.out.println("A-Z");
				
			}
		});
		
		Button btn2 = (Button) findViewById(R.id.ratingSortBtn);
		btn2.bringToFront();
		btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(m_webHandler.isLoggedIn())
					System.out.println("rating");
				
			}
		});
		
		Button btn3 = (Button) findViewById(R.id.closestSortBtn);
		btn3.bringToFront();
		btn3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(m_webHandler.isLoggedIn())
					System.out.println("Closest");
				
			}
		});
		
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
	
}
