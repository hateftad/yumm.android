package se.yumm.adapters;

/*
* @author Hatef Tadayon
*
* 
*/

import java.util.Collections;
import java.util.List;

import se.yumm.R;
import se.yumm.items.Restaurants;
import se.yumm.utils.ThreadPreconditions;
import se.yumm.utils.ViewHolder;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class ListMapAdapter extends BaseAdapter
{
	
	private List<Restaurants> m_restarauntList = Collections.emptyList();
	private final Context m_context;
	
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
			convertView = View.inflate(m_context, R.layout.row_view, null);
		}
		
		Restaurants rest = getItem(position);
		TextView restName =  ViewHolder.get(convertView, R.id.restaurantName);
		TextView addrText =  ViewHolder.get(convertView, R.id.addressText);
		
		restName.setText(rest.getName());
		addrText.setText(rest.getAddress());
		
		
		return convertView;
	}


	public List<Restaurants> getRestarauntList() {
		return m_restarauntList;
	}


	public void setRestarauntList(List<Restaurants> m_restarauntList) {
		this.m_restarauntList = m_restarauntList;
	}
	
}
