package se.yumm.adapters;

/*
* @author Hatef Tadayon
*
* 
*/

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;

import se.yumm.R;
import se.yumm.items.Restaurants;
import se.yumm.utils.ThreadPreconditions;
import se.yumm.utils.ViewHolder;
import android.content.Context;
import android.location.Location;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class ListMapAdapter extends BaseAdapter
{
	
	private List<Restaurants> m_restarauntList = Collections.emptyList();
	private final Context m_context;
	private Location m_location;
	DecimalFormat df = new DecimalFormat("###.##");
	
	public ListMapAdapter(Context context) {
		
		this.m_context = context;
	}
	
	public void updateRestaurants(List<Restaurants> restaurants)
	{
		ThreadPreconditions.checkOnMainThread();
		this.m_restarauntList = restaurants;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {

		return m_restarauntList.size();
	}

	@Override
	public Restaurants getItem(int position) {

		return m_restarauntList.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) 
		{
			convertView = View.inflate(m_context, R.layout.restaurant_row_view, null);
		}
		
		Restaurants rest = getItem(position);
		TextView restName =  ViewHolder.get(convertView, R.id.restaurantName);
		TextView addrText =  ViewHolder.get(convertView, R.id.addressText);
		TextView distText = ViewHolder.get(convertView, R.id.distanceText);
		
		
		restName.setText(rest.getName());
		addrText.setText(rest.getAddress());
		float distKM = m_location.distanceTo(rest.getLocation()) / 1000;
		String distance = df.format((double) distKM);
		distText.setText(distance  + " KM");
		
		return convertView;
	}


	public List<Restaurants> getRestarauntList() {
		return m_restarauntList;
	}

	public Location getLocation() {
		return m_location;
	}

	public void setLocation(Location m_location) {
		this.m_location = m_location;
	}
	
}
