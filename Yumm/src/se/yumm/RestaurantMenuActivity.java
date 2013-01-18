package se.yumm;

import java.util.ArrayList;
import java.util.List;

import se.yumm.adapters.MenuItemsAdapter;
import se.yumm.items.MenuItems;
import se.yumm.items.Restaurants;
import se.yumm.listeners.IEventListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RestaurantMenuActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.menu_headers_activity);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.cutom_title);
		
		
		SetUpActionBar();
		
		SetupNavMenu();
		
		Restaurants restaurant = getIntent().getParcelableExtra("Restaurant");
		ArrayList<String> menuHeaders = restaurant.getMenuHeaders();
		
		MenuItemsAdapter adp = new MenuItemsAdapter(getApplicationContext(), menuHeaders);
		
		ListView list = (ListView) findViewById(R.id.menu_headers_list_view);
		list.setAdapter(adp);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				ArrayList<MenuItems> menuItems = m_webHandler.GetTaskHandler().getRestaurant(position).getMenuItems();
				Intent intent = new Intent(getApplicationContext(), RestaurantMenuActivity.class);
				intent.putParcelableArrayListExtra("menuItems", menuItems);
				startActivity(intent);
			}
		});
		
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
		
		m_actionBar.addActionIcon(R.drawable.bag_button, new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				
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
			//m_customListView.startAnimation(anim);
			
		}
		else
		{
			//m_customListView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.underlying_view_in_from_right));
		}
		
	}
}
