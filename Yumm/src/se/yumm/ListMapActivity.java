package se.yumm;

/*
* @author Hatef Tadayon
*
* 
*/

import java.util.ArrayList;
import se.yumm.adapters.ListMapAdapter;
import se.yumm.handlers.LocationHandler;
import se.yumm.handlers.RestaurantHandler;
import se.yumm.items.Restaurants;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
	private ListView m_listView;
	private LocationHandler m_locationHndlr;
	private RestaurantHandler m_restaurantHndlr;

	@Override
	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);
		setContentView(R.layout.list_map_activity);
		
		ArrayList<Restaurants> restaurants = getIntent().getParcelableArrayListExtra("Restaurants");
		
		m_mapView = (MapView) findViewById(R.id.mapView);
		m_mapView.setBuiltInZoomControls(true);
		m_mapCtrl = m_mapView.getController();
		m_listView = (ListView) findViewById(R.id.listMapView);
		m_locationHndlr = new LocationHandler(this);
		
		//Location l = m_locationHndlr.getLocation();
		Location l = restaurants.get(0).getLocation();
		int lat = ((int)(l.getLatitude() * 1E6));
		int lon = ((int)(l.getLongitude() * 1E6));
		GeoPoint point = new GeoPoint(lat, lon);
		m_mapCtrl.animateTo(point);
		m_mapCtrl.setZoom(14);
		//m_mapCtrl.zoomIn();
		
		ListMapAdapter pAdapter = new ListMapAdapter(this);
		pAdapter.updateRestaurants(restaurants);
		m_restaurantHndlr = new RestaurantHandler(getBaseContext());
		m_restaurantHndlr.SetAdapter(pAdapter);

		m_listView.setAdapter(pAdapter);
		
		m_listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Restaurants r = m_restaurantHndlr.getRestaurants().get(position);
				System.out.println(r.getName());
				
			}
		});
		
		

	}

	@Override
	protected boolean isRouteDisplayed()
	{
		// TODO Auto-generated method stub
		return false;
	}

}
