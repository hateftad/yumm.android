package se.yumm;

//*****************************
/* @author Hatef Tadayon
/*
 * 
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import se.yumm.handlers.LocationHandler;
import se.yumm.handlers.RestaurantHandler;
import se.yumm.handlers.WebServiceHandler;
import se.yumm.listeners.ActionListener;
import se.yumm.poi.Restaurants;
import se.yumm.utils.PropertiesManager;
import se.yumm.utils.URLS;
import se.yumm.views.CustomHorizontalScrollView;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Point;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;


public class StartActivity extends Activity implements OnClickListener
{
	private WebServiceHandler m_webHandler;
	private LocationHandler m_locationhdlr;
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
		//m_webHandler = new WebServices(this);
		//m_webHandler.LoginClient();
		
		m_locationhdlr = new LocationHandler(this);
		m_webHandler.GetRestaurantHandler().GetAdapter().SetEventListener(new ActionListener() {
			
			@Override
			public void OnClicked(View v) {
				System.out.println("Fek");
				
			}
		});
		
		Button btn = (Button) findViewById(R.id.alphaSortBtn);
		btn.bringToFront();
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				Sort(RestaurantHandler.NameComparator);
			}
		});
		
		Button btn2 = (Button) findViewById(R.id.ratingSortBtn);
		btn2.bringToFront();
		btn2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Sort(RestaurantHandler.RatingComparator);
			}
		});
		
		Button btn3 = (Button) findViewById(R.id.closestSortBtn);
		btn3.bringToFront();
		btn3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(m_webHandler.isLoggedIn())
				{
					String location = Double.toString(m_locationhdlr.getLocation().getLongitude()) 
							+ "," + Double.toString(m_locationhdlr.getLocation().getLatitude());
					m_webHandler.RetrieveData(m_webHandler.UrlBuilder(location, URLS.CLOSEST));
				}
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
	protected void onResume()
	{
		super.onResume();
		m_locationhdlr.update();
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		m_locationhdlr.pause();
	}
	public void Sort(Comparator<Restaurants> method)
	{
		RestaurantHandler rh = m_webHandler.GetRestaurantHandler();
		ArrayList<Restaurants> list = rh.getRestaurants();
		if (rh.isAscending()) {
			Collections.sort(list, method);
		}
		else
		{
			Collections.sort(list, Collections.reverseOrder(method));
		}
		rh.setAscending(!rh.isAscending());
		rh.UpdateData(list);
	}

	@Override
	public void onClick(View v) {
		
		System.out.println("Clicked item nr "+v.getId());
		
	}
}
