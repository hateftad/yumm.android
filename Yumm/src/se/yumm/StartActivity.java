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
import se.yumm.handlers.TaskHandler;
import se.yumm.handlers.WebServiceHandler;
import se.yumm.items.Restaurants;
import se.yumm.listeners.IActionListener;
import se.yumm.listeners.IEventListener;
import se.yumm.listeners.ITaskEventListener;
import se.yumm.utils.PropertiesManager;
import se.yumm.utils.URLS;
import se.yumm.views.BottomButtonBar;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
	public ProgressDialog m_dialog;

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
		if(!m_webHandler.isLoggedIn()){
			m_dialog = ProgressDialog.show(this, "Logging In..", "Please Wait", true, false);	
		}
		//m_webHandler = new WebServices(this);
		//m_webHandler.LoginClient();
		
		final TaskHandler taskHandler = m_webHandler.GetTaskHandler();
		final StartPageAdapter sPageAdp = new StartPageAdapter(this);
		
		ListView listView = (ListView) findViewById(R.id.startPageListView);
		listView.setAdapter(sPageAdp);
		listView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

				if (scrollState == 0) {
					m_bottomBar.Animate(R.anim.bottom_button_fade_in, getApplicationContext());
				}
				else if(scrollState == 1){
					m_bottomBar.Animate(R.anim.bottom_button_fade_out, getApplicationContext());
				}
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				
			}
		});

		taskHandler.SetEventListener(new ITaskEventListener() {

			@Override
			public void onRestaurantComplete() {
				sPageAdp.updateRestaurants(taskHandler.getRestaurants());
				
			}

			@Override
			public void onUserComplete() {
				m_dialog.dismiss();
				m_webHandler.RetrieveData(m_webHandler.UrlBuilder(null, URLS.RETRIEVE));
			}
		});

		((StartPageAdapter) sPageAdp).SetEventListener(new IActionListener() {
			//OnClick
			@Override
			public void OnComplete(int itemId) {

				Restaurants restaurant = m_webHandler.GetTaskHandler().getRestaurant(itemId);
				Intent intent = new Intent(getApplicationContext(), RestaurantMenuActivity.class);
				intent.putExtra("Restaurant", restaurant);
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
		
		m_actionBar.addActionIcon(R.drawable.map_button, new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(getApplicationContext(), ListMapActivity.class);
				intent.putParcelableArrayListExtra("Restaurants", m_webHandler.GetTaskHandler().getRestaurants());
				intent.putExtra("loggedIn", m_webHandler.isLoggedIn());
				startActivity(intent);
				
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
