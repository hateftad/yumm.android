package se.yumm;

/*
 * @author Hatef Tadayon
 *
 * 
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import se.yumm.adapters.ListMapAdapter;
import se.yumm.handlers.LocationHandler;
import se.yumm.handlers.TaskHandler;
import se.yumm.handlers.WebServiceHandler;
import se.yumm.items.Restaurants;
import se.yumm.listeners.ISideNavigationListener;
import se.yumm.utils.URLS;
import se.yumm.views.ActionBar;
import se.yumm.views.BottomButtonBar;
import se.yumm.views.SideNavigationView;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class ListMapActivity extends MapActivity
{

	private MapView m_mapView;
	private MapController m_mapCtrl;
	private ActionBar m_actionBar;
	private BottomButtonBar m_bottomBar;
	private SideNavigationView m_sideNavigation;
	private LocationHandler m_locationHndlr;
	private WebServiceHandler m_webHandler;

	@Override
	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);


		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.list_map_activity);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.cutom_title);

		m_mapView = (MapView) findViewById(R.id.mapView);
		m_mapView.setBuiltInZoomControls(true);
		m_mapCtrl = m_mapView.getController();


		m_locationHndlr = new LocationHandler(this);


		ArrayList<Restaurants> restaurants = getIntent().getParcelableArrayListExtra("Restaurants");
		boolean loggedIn = getIntent().getBooleanExtra("loggedIn", false);


		//Location l = m_locationHndlr.getLocation();
		AnimateToPoint(m_locationHndlr.getLocation());

		//m_mapCtrl.zoomIn();

		SetUpBottomButtons();

		SetUpActionBar();

		SetUpNavMenu();

		m_webHandler = new WebServiceHandler(getParent());
		m_webHandler.setLoggedIn(loggedIn);


		Location location = m_locationHndlr.getLocation();
		final TaskHandler taskHandler = m_webHandler.GetTaskHandler();
		taskHandler.setRestaurants(restaurants);

		ListMapAdapter listMapAdp = new ListMapAdapter(getBaseContext());
		listMapAdp.updateRestaurants(restaurants);
		listMapAdp.setLocation(location);
		taskHandler.SetAdapter(listMapAdp);
		ListView listView = (ListView) findViewById(R.id.listMapView);
		listView.setAdapter(listMapAdp);
		listView.setOnItemClickListener(new OnItemClickListener() {

			public int lastPos = -1;
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Restaurants r = taskHandler.getRestaurants().get(position);
				if (lastPos == position) {
					Intent intent = new Intent(getApplicationContext(), RestaurantMenuActivity.class);
					intent.putExtra("Restaurant", r);
					startActivity(intent);
					lastPos = -1;
				}
				else{
					AnimateToPoint(r.getLocation());
					lastPos = position;
				}
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

	}

	@Override
	protected boolean isRouteDisplayed()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		m_locationHndlr.update();
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		m_locationHndlr.pause();
	}

	private void AnimateToPoint(Location location)
	{
		int lat = ((int)(location.getLatitude() * 1E6));
		int lon = ((int)(location.getLongitude() * 1E6));
		GeoPoint point = new GeoPoint(lat, lon);
		m_mapCtrl.animateTo(point);
		m_mapCtrl.setZoom(14);
	}

	public void Sort(Comparator<Restaurants> method)
	{
		TaskHandler rh = m_webHandler.GetTaskHandler();
		ArrayList<Restaurants> list = rh.getRestaurants();
		if (rh.isAscending()) {
			Collections.sort(list, method);
		}
		else
		{
			Collections.sort(list, Collections.reverseOrder(method));
		}
		rh.setAscending(!rh.isAscending());
		BaseAdapter la = rh.GetAdapter();
		((ListMapAdapter) la).updateRestaurants(list);

	}

	private void SetUpNavMenu()
	{

		m_sideNavigation = (SideNavigationView) findViewById(R.id.side_navigation_view);
		m_sideNavigation.setMenuItems(R.menu.side_navigation_menu);
		m_sideNavigation.setMenuClickCallback(new ISideNavigationListener() {

			@Override
			public void onSideNavigationItemClick(int itemId) {
				AnimateView(false);

			}

			@Override
			public void onOutSideNavigationClick() {
				AnimateView(false);
			}
		});
	}

	private void SetUpActionBar()
	{

		View.OnClickListener homeButton = new OnClickListener() {
			@Override
			public void onClick(View v) {

				m_sideNavigation.toggleMenu();
				AnimateView(m_sideNavigation.isShown());


			}
		};

		m_actionBar = (ActionBar) findViewById(R.id.actionbar);
		m_actionBar.setTitle(R.string.app_name);
		m_actionBar.setHomeLogo(R.drawable.menu_button, homeButton);

		m_actionBar.addActionIcon(R.drawable.list_view_button, new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), RestaurantListActivity.class);
				intent.putParcelableArrayListExtra("Restaurants", m_webHandler.GetTaskHandler().getRestaurants());
				intent.putExtra("loggedIn", m_webHandler.isLoggedIn());
				startActivity(intent);

			}
		});

	}

	private void AnimateView(boolean sideBarShowing)
	{
		Animation anim = null;

		if (sideBarShowing) {
			//2
			anim = AnimationUtils.loadAnimation(this, R.anim.underlying_view_out_to_right);
			anim.setFillAfter(true);

			m_actionBar.Animate(R.anim.action_bar_out_to_right, getApplicationContext());
			m_bottomBar.Animate(R.anim.bottom_button_fade_out, getApplicationContext());
			//m_customListView.startAnimation(anim);

		}
		else
		{
			//3
			m_actionBar.Animate(R.anim.action_bar_in_from_left, getApplicationContext());
			m_bottomBar.Animate(R.anim.bottom_button_fade_in, getApplicationContext());
			//m_customListView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.underlying_view_in_from_right));
		}

	}


	private void SetUpBottomButtons() {

		m_bottomBar = (BottomButtonBar) findViewById(R.id.bottom_button_bar);
		m_bottomBar.SetUpButton(1, R.drawable.ic_launcher, new OnClickListener() {

			@Override
			public void onClick(View v) {
				Sort(TaskHandler.NameComparator);

			}
		});
		m_bottomBar.SetUpButton(2, R.drawable.ic_launcher, new OnClickListener() {

			@Override
			public void onClick(View v) {
				Sort(TaskHandler.RatingComparator);

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
