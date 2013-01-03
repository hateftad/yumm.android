package se.yumm;

import se.yumm.adapters.ListMapAdapter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class ListMapActivity extends MapActivity
{

	private MapView m_mapView;
	private MapController m_mapCtrl;
	private ListView m_listView;

	@Override
	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);
		setContentView(R.layout.list_map_activity);
		m_mapView = (MapView) findViewById(R.id.mapView);
		m_mapView.setBuiltInZoomControls(true);
		m_mapCtrl = m_mapView.getController();
		m_listView = (ListView) findViewById(R.id.listMapView);

		ListMapAdapter pAdapter = new ListMapAdapter(this);
		//pAdapter.updateRestaurants(restaurants);
		//m_listView.setAdapter(pAdapter);

		m_listView.setOnKeyListener(new OnKeyListener()
		{
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event)
			{
				// TODO Auto-generated method stub
				TextView t = (TextView) v.findViewById(R.id.restaurantName);
				Toast.makeText(getApplicationContext(), t.getText().toString(),
						Toast.LENGTH_SHORT).show();

				return false;
			}

		});

	}

	/*
	 * @Override protected void onListItemClick(ListView l, View v, int
	 * position, long id) {
	 * 
	 * //get selected items String selectedValue = (String)
	 * getListAdapter().getItem(position); Toast.makeText(this, selectedValue,
	 * Toast.LENGTH_SHORT).show();
	 * 
	 * }
	 */
	@Override
	protected boolean isRouteDisplayed()
	{
		// TODO Auto-generated method stub
		return false;
	}

}
