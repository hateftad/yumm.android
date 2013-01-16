package se.yumm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import se.yumm.adapters.ListMapAdapter;
import se.yumm.handlers.LocationHandler;
import se.yumm.handlers.RestaurantHandler;
import se.yumm.handlers.WebServiceHandler;
import se.yumm.items.Restaurants;
import se.yumm.listeners.IEventListener;
import se.yumm.listeners.ISideNavigationListener;
import se.yumm.utils.URLS;
import se.yumm.views.ActionBar;
import se.yumm.views.BottomButtonBar;
import se.yumm.views.SideNavigationView;
import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class RestaurantListActivity extends BaseActivity{


	private BottomButtonBar m_bottomBar;
	private LinearLayout m_customListView;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.restaurant_list_activity);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.cutom_title);
		
		
		ArrayList<Restaurants> restaurants = getIntent().getParcelableArrayListExtra("Restaurants");
		boolean loggedIn = getIntent().getBooleanExtra("loggedIn", false);
		
		//sorting buttons
		SetUpBottomButtons();
		//home and list button
		SetUpActionBar();
		//slide in menu view
		SetUpNavMenu();
		
		m_webHandler = new WebServiceHandler(getParent());
		m_webHandler.setLoggedIn(loggedIn);
		
		
		Location location = m_locationHndlr.getLocation();
		final RestaurantHandler rh = m_webHandler.GetRestaurantHandler();
		rh.setRestaurants(restaurants);
		
		ListMapAdapter listMapAdp = new ListMapAdapter(getBaseContext());
		listMapAdp.updateRestaurants(restaurants);
		listMapAdp.setLocation(location);
		rh.SetAdapter(listMapAdp);
		ListView listView = (ListView) findViewById(R.id.restaurant_list_view);
		listView.setAdapter(listMapAdp);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Restaurants r = rh.getRestaurants().get(position);
				//Intent intent = new Intent(getApplicationContext());
				
				
			}
		});
		
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
		
		m_customListView = (LinearLayout) findViewById(R.id.RelativeLayoutRestList);
	}
	
	@Override
	protected void onStart()
	{
		super.onStart();
		
	}
	
	@Override
	protected void onPause() {
        super.onPause();

	}
	
	
	@Override
	protected void onResume()
	{
		super.onResume();

	}
	
	@Override
	protected void onRestart()
	{
		super.onRestart();
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
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
		BaseAdapter lp = rh.GetAdapter();
		((ListMapAdapter) lp).updateRestaurants(list);

	}
	
	private void SetUpNavMenu()
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
				AnimateView(false);
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
		LinearLayout l = (LinearLayout) findViewById(R.id.linearlayoutListView);
		Animation anim = null;
		
		if (sideBarShowing) {
			//2
			anim = AnimationUtils.loadAnimation(this, R.anim.underlying_view_out_to_right);
			anim.setFillAfter(true);
			
			m_bottomBar.Animate(R.anim.bottom_button_fade_out, getApplicationContext());
			m_customListView.startAnimation(anim);
			l.startAnimation(anim);
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
