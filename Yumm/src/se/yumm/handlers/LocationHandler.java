package se.yumm.handlers;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class LocationHandler implements LocationListener
{

	private final Context m_context;
	private Address m_address;
	private Geocoder m_geocoder;
	private Location m_location;
	private LocationManager m_locationMgr;
	private String m_provider;
	private boolean m_geoEnabled = false;
	
	/*
	 * y = 59.332598;
     * x = 18.074781;
	 */

	public LocationHandler(Context context)
	{
		// keep a copy of context
		m_context = context;
		// check if geocoder is present, usually not on emulator
		if (Geocoder.isPresent())
		{
			Toast.makeText(m_context, " Geocoder Enabled ", Toast.LENGTH_SHORT).show();
			m_geocoder = new Geocoder(context, Locale.getDefault());
			// locationUpdate();
			m_geoEnabled = true;
		} else
		{
			Toast.makeText(m_context, "Geocoder disabled ", Toast.LENGTH_SHORT).show();
			m_geoEnabled = false;
		}

		m_locationMgr = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		// criteria for selecting a location provider
		Criteria criteria = new Criteria();
		m_provider = m_locationMgr.getBestProvider(criteria, false);
		m_location = m_locationMgr.getLastKnownLocation(m_provider);

	}

	public String getAddress(int lineNr)
	{
		if (m_geoEnabled)
		{
			locationUpdate();
			// 0 is address
			// 1 is city
			// 2 is country
			return m_address.getAddressLine(lineNr);
		}
		return "empty";
	}

	public Location getLocation()
	{
		return m_location;
	}

	public void setLocation(Location location)
	{
		this.m_location = location;
	}
	
	public float DistanceTo(Location dest)
	{
		return m_location.distanceTo(dest);
	}
	
	@Override
	public void onLocationChanged(Location location)
	{

		m_location = location;
		if (m_geoEnabled)
		{
			//locationUpdate();
		}
	}

	@Override
	public void onProviderDisabled(String provider)
	{
		Toast.makeText(m_context, "Gps Disabled", Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onProviderEnabled(String provider)
	{
		// TODO Auto-generated method stub
		Toast.makeText(m_context, "Gps Enabled", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras)
	{
		// TODO Auto-generated method stub

	}

	public boolean locationUpdate()
	{
		
		try
		{
			List<Address> address = m_geocoder.getFromLocation(
					m_location.getLatitude(), m_location.getLongitude(), 1);
			m_address = address.get(0);
		}
		
		catch (IOException e)
		{
			Log.d("Yumm", "GetLocationText", e);
			return false;
		}
		
		return true;
	}

	public void update()
	{
		m_locationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				2000, 2, this);
	}

	public void pause()
	{
		m_locationMgr.removeUpdates(this);
	}
}
