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
import se.yumm.listeners.ISideNavigationListener;
import se.yumm.utils.PropertiesManager;
import se.yumm.utils.URLS;
import se.yumm.views.ActionBar;
import se.yumm.views.SideNavigationView;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;



public class StartActivity extends Activity 
{
	private WebServiceHandler m_webHandler;
	private LocationHandler m_locationhdlr;
	private ActionBar m_actionBar;
	private SideNavigationView m_sideNavigation;
	private Button m_alphaBtn, m_ratingBtn, m_rangeBtn;

	// TODO fix library so it can support old API's
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_start);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.cutom_title);

		SetUpActionBar();
		SetUpNavMenu();

		m_locationhdlr = new LocationHandler(this);

		//causing a newApi warning
		Point point  = new Point();
		Display disp = getWindowManager().getDefaultDisplay();
		disp.getSize(point);

		//settings for CustomHorizontalScrollView, minor Hack :)
		//on TODO list
		PropertiesManager.GetInstance().m_windowWidth = point.x;
		PropertiesManager.GetInstance().m_windowHeight = point.y;

		//logs in and retrieves initial data and fills listview all in one go,
		//most probably going to change this and use my own webhandler but not using it
		m_webHandler = new WebServiceHandler(this);
		m_webHandler.SetCookieStore(new PersistentCookieStore(this));
		m_webHandler.LoginClient();
		//m_webHandler = new WebServices(this);
		//m_webHandler.LoginClient();
		LinearLayout l = (LinearLayout)findViewById(R.id.linearLayout1);
		l.bringToFront();
		final RestaurantHandler rh = m_webHandler.GetRestaurantHandler();
		final BaseAdapter sPageAdp = rh.GetAdapter();

		ListView listView = (ListView) findViewById(R.id.startPageListView);
		listView.setAdapter(sPageAdp);
		listView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

				if (scrollState == 0) {

				}
				else
				{

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
				((StartPageAdapter)sPageAdp).updateRestaurants(rh.getRestaurants());

			}
		});

		((StartPageAdapter) sPageAdp).SetEventListener(new IActionListener() {

			@Override
			public void OnComplete(View v) {

				System.out.println("KIR");
				Intent intent = new Intent(getApplicationContext(), ListMapActivity.class);
				intent.putParcelableArrayListExtra("Restaurants", m_webHandler.GetRestaurantHandler().getRestaurants());
				startActivity(intent);

			}
		});

		SetUpBottomButtons();



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
		BaseAdapter sp = rh.GetAdapter();
		((StartPageAdapter) sp).updateRestaurants(list);

	}

	private void SetUpNavMenu()
	{

		m_sideNavigation = (SideNavigationView) findViewById(R.id.side_navigation_view);
		m_sideNavigation.setMenuItems(R.menu.side_navigation_menu);
		m_sideNavigation.setMenuClickCallback(new ISideNavigationListener() {

			@Override
			public void onSideNavigationItemClick(int itemId) {
				System.out.println("prep");

			}
		});

	}

	private void SetUpActionBar()
	{
		m_actionBar = (ActionBar) findViewById(R.id.actionbar);
		m_actionBar.setTitle(R.string.app_name);
		m_actionBar.setHomeLogo(R.drawable.ic_launcher, new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("SideMenu");
				m_sideNavigation.toggleMenu();

			}
		});

		m_actionBar.addActionIcon(R.drawable.ic_launcher, new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("new button");


			}
		});

	}
	private void SetUpBottomButtons() {
		
		
		m_alphaBtn = (Button) findViewById(R.id.alphaSortBtn);
		m_alphaBtn.bringToFront();
		m_alphaBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) 
			{
				Sort(RestaurantHandler.NameComparator);
				
			}
		});

		m_ratingBtn = (Button) findViewById(R.id.ratingSortBtn);
		m_ratingBtn.bringToFront();
		m_ratingBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Sort(RestaurantHandler.RatingComparator);
			}
		});

		m_rangeBtn = (Button) findViewById(R.id.closestSortBtn);
		m_rangeBtn.bringToFront();
		m_rangeBtn.setOnClickListener(new OnClickListener() {

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

}
