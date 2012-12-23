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
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;


public class StartActivity extends Activity implements OnClickListener
{
	

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
		
		//logs in and retrieves initial data, most probably going to change this
		//and use a different way
		WebServiceHandler webHandler = new WebServiceHandler(this);
		webHandler.LoginClient();
		
		
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
