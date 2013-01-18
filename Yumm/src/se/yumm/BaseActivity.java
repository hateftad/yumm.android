package se.yumm;

import se.yumm.handlers.LocationHandler;
import se.yumm.handlers.WebServiceHandler;
import se.yumm.listeners.IEventListener;
import se.yumm.listeners.ISideNavigationListener;
import se.yumm.views.ActionBar;
import se.yumm.views.BottomButtonBar;
import se.yumm.views.SideNavigationView;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class BaseActivity extends Activity {

	protected SideNavigationView m_sideNavigation;
	protected ActionBar m_actionBar;
	protected BottomButtonBar m_bottomBar;
	protected LocationHandler m_locationHndlr;
	protected WebServiceHandler m_webHandler;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	
		m_locationHndlr = new LocationHandler(this);
		m_webHandler = new WebServiceHandler(this);
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		
	}
	
	@Override
	protected void onPause() {
        super.onPause();
        m_locationHndlr.pause();
	}
	
	
	@Override
	protected void onResume()
	{
		super.onResume();
		m_locationHndlr.update();
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
	
	protected void SetupActionBar(final IEventListener listener)
	{
		m_actionBar = (ActionBar) findViewById(R.id.actionbar);
		m_actionBar.setTitle(R.string.app_name);
		m_actionBar.setHomeLogo(R.drawable.menu_button, new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				m_sideNavigation.toggleMenu();
				listener.onCloseClick();
			}
		});
	}
	
	protected void SetupNavMenu(final IEventListener listener)
	{
		m_sideNavigation = (SideNavigationView) findViewById(R.id.side_navigation_view);
		m_sideNavigation.setMenuItems(R.menu.side_navigation_menu);
		m_sideNavigation.setMenuClickCallback(new ISideNavigationListener() {

			@Override
			public void onSideNavigationItemClick(int itemId) {
				
				listener.onCloseClick();
			}

			@Override
			public void onOutSideNavigationClick() {
				
				listener.onCloseClick();
			}
		});
	}
	
	protected void AnimateView(boolean sideBarShowing)
	{
		if (sideBarShowing) {
			m_actionBar.Animate(R.anim.action_bar_out_to_right, getApplicationContext());
		}
		else
		{
			m_actionBar.Animate(R.anim.action_bar_in_from_left, getApplicationContext());
		}
	}
	
}
