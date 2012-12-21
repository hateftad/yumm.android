package se.yumm;

//*****************************
/* @author Hatef Tadayon
/*
 * 
 */
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import se.yumm.asyncTasks.GetPlaces;
import se.yumm.handlers.LinearLayoutHandler;
import se.yumm.handlers.WebServiceHandler;
import se.yumm.handlers.YummWebClient;
import se.yumm.listeners.FlingGestureListener;
import se.yumm.poi.Restaurants;
import se.yumm.utils.PropertiesManager;
import se.yumm.utils.TAGS;
import se.yumm.views.CustomHorizontalScrollView;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Point;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class StartActivity extends Activity implements OnClickListener
{
		
	private ArrayList<Restaurants> m_restaurants;
	private FlingGestureListener m_gLstr;
	private LinearLayoutHandler m_layoutHandler;
	private ListView m_listView;
	private CustomHorizontalScrollView m_horizView;
	private ProgressDialog m_dialog;
	

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		
		WebServiceHandler webHandler = new WebServiceHandler(this);
		Point point  = new Point();
		
		Display disp = getWindowManager().getDefaultDisplay();
		disp.getSize(point);
		int width = PropertiesManager.GetInstance().m_windowWidth = point.x;
		int height = PropertiesManager.GetInstance().m_windowHeight = point.y;
		
		m_horizView = (CustomHorizontalScrollView) findViewById(R.id.horizScrollView);
		m_horizView.SetItemWidth(width);
		m_horizView.SetMaxItem(2);//setting maximum of 3 items for horscrollview
		
		//m_dialog = ProgressDialog.show(this, "Logging In..", "Please Wait", true, false);
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
