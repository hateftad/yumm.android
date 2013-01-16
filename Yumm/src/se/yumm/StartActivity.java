package se.yumm;

/*
 * @author Hatef Tadayon
 *
 * 
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.loopj.android.http.PersistentCookieStore;

import se.yumm.adapters.StartPageAdapter;
import se.yumm.handlers.LocationHandler;
import se.yumm.handlers.RestaurantHandler;
import se.yumm.handlers.WebServiceHandler;
import se.yumm.items.Restaurants;
import se.yumm.listeners.IActionListener;
import se.yumm.listeners.IEventListener;
import se.yumm.listeners.ISideNavigationListener;
import se.yumm.utils.PropertiesManager;
import se.yumm.utils.URLS;
import se.yumm.views.ActionBar;
import se.yumm.views.BottomButtonBar;
import se.yumm.views.SideNavigationView;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;



public class StartActivity extends BaseActivity 
{
	
	private BottomButtonBar m_bottomBar;
	private LinearLayout m_customListView;

	// TODO fix library so it can support old API's
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		//causing a newApi warning
		Point point  = new Point();
		Display disp = getWindowManager().getDefaultDisplay();
		disp.getSize(point);

		//settings for CustomHorizontalScrollView, minor Hack :)
		//on TODO list
		PropertiesManager.GetInstance().m_windowWidth = point.x;
		PropertiesManager.GetInstance().m_windowHeight = point.y;
	
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_start);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.cutom_title);
		
		
		//sorting buttons
		SetUpBottomButtons();
		//home and list button
		SetUpActionBar();
		//slide in menu view
		SetupNavMenu();

		//logs in and retrieves initial data 
		m_webHandler = new WebServiceHandler(this);
		m_webHandler.SetCookieStore(new PersistentCookieStore(this));
		m_webHandler.LoginClient();
		//m_webHandler = new WebServices(this);
		//m_webHandler.LoginClient();
		
		final RestaurantHandler rh = m_webHandler.GetRestaurantHandler();
		final StartPageAdapter sPageAdp = new StartPageAdapter(this);
		
		ListView listView = (ListView) findViewById(R.id.startPageListView);
		listView.setAdapter(sPageAdp);
		listView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

				if (scrollState == 0) {
					//0
					m_bottomBar.Animate(R.anim.bottom_button_fade_in, getApplicationContext());
				}
				else if(scrollState == 1)
				{
					//1
					m_bottomBar.Animate(R.anim.bottom_button_fade_out, getApplicationContext());
					
				}
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				
			}
		});

		rh.SetEventListener(new IActionListener() {

			@Override
			public void OnComplete(View v) {
				sPageAdp.updateRestaurants(rh.getRestaurants());

			}
		});

		((StartPageAdapter) sPageAdp).SetEventListener(new IActionListener() {

			@Override
			public void OnComplete(View v) {

				System.out.println("KIR");
				Intent intent = new Intent(getApplicationContext(), ListMapActivity.class);
				intent.putParcelableArrayListExtra("Restaurants", m_webHandler.GetRestaurantHandler().getRestaurants());
				intent.putExtra("loggedIn", m_webHandler.isLoggedIn());
				startActivity(intent);

			}
		});
		
		m_customListView = (LinearLayout) findViewById(R.id.linearLayoutWithHorizView); 
		
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
	}

	@Override
	protected void onPause()
	{
		super.onPause();
	}
	
	private void Sort(Comparator<Restaurants> method)
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
		BaseAdapter sp = rh.GetAdapter();
		((StartPageAdapter) sp).updateRestaurants(list);

	}
	
	private void SetupNavMenu()
	{
		super.SetupNavMenu(new IEventListener() {
			
			@Override
			public void onCloseClick() {
				AnimateView(false);
			}
		});
		
	}

	private void SetUpActionBar()
	{
		
		super.SetupActionBar(new IEventListener() {
			
			@Override
			public void onCloseClick() {
				AnimateView(m_sideNavigation.isShown());
				
			}
		});
		
		m_actionBar.addActionIcon(R.drawable.ic_launcher, new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("new button");
				
			}
		});
		
	}
	
	protected void AnimateView(boolean sideBarShowing)
	{
		super.AnimateView(sideBarShowing);
		Animation anim = null;
		
		if (sideBarShowing) {
			//2
			anim = AnimationUtils.loadAnimation(this, R.anim.underlying_view_out_to_right);
			anim.setFillAfter(true);
			m_bottomBar.Animate(R.anim.bottom_button_fade_out, getApplicationContext());
			m_customListView.startAnimation(anim);
			
		}
		else
		{
			m_bottomBar.Animate(R.anim.bottom_button_fade_in, getApplicationContext());
			m_customListView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.underlying_view_in_from_right));
		}
		
	}
	
	
	private void SetUpBottomButtons() {

		m_bottomBar = (BottomButtonBar) findViewById(R.id.bottom_button_bar);
		m_bottomBar.SetUpButton(1, R.drawable.ic_launcher, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Sort(RestaurantHandler.NameComparator);
				
			}
		});
		m_bottomBar.SetUpButton(2, R.drawable.ic_launcher, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Sort(RestaurantHandler.RatingComparator);
				
			}
		});
		m_bottomBar.SetUpButton(3, R.drawable.ic_launcher, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(m_webHandler.isLoggedIn())
				{
					String location = Double.toString(m_locationHndlr.getLocation().getLongitude()) 
							+ "," + Double.toString(m_locationHndlr.getLocation().getLatitude());
					m_webHandler.RetrieveData(m_webHandler.UrlBuilder(location, URLS.CLOSEST));
				}
				
			}
		});
		
		

	}

}
