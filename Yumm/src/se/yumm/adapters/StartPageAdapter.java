package se.yumm.adapters;

import java.util.Collections;
import java.util.List;

import se.yumm.R;
import se.yumm.poi.Restaurants;
import se.yumm.utils.PropertiesManager;
import se.yumm.utils.ThreadPreconditions;
import se.yumm.utils.ViewHolder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class StartPageAdapter extends BaseAdapter {

	private List<Restaurants> m_restarauntList = Collections.emptyList();
	private final Context m_context;
	
	
	
	public StartPageAdapter(Context context)
	{
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
		// TODO Auto-generated method stub
		return m_restarauntList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return m_restarauntList.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) 
		{
			convertView = View.inflate(m_context, R.layout.activity_start, null);
		}
		int width = PropertiesManager.GetInstance().m_windowWidth;
		ImageView imgView = ViewHolder.get(convertView, R.id.horizRestImage);
		TextView subText = ViewHolder.get(convertView, R.id.horizRestText);
		TextView contactText = ViewHolder.get(convertView, R.id.horizRestText2);
		LayoutParams layoutParams = imgView.getLayoutParams();
		layoutParams.height = m_context.getResources().getDimensionPixelSize(R.dimen.placeholder_height);
		Restaurants rest = (Restaurants) getItem(position);
		
		subText.setText(rest.getName() + "\n" 
				+ "Japanskt "+ rest.getFavouriteCount());
		//t.setWidth(iv.getWidth());
		
		contactText.setText("Telefon : " + rest.getPhoneNr() + "\n" +
				   "Address : " + rest.getAddress() + "\n" +
				   "Leveransvillkor : Hemkörning");
		contactText.setWidth(width);
		
		if((position % 2) == 0)
			imgView.setImageResource(R.drawable.hotspicy);
		else
			imgView.setImageResource(R.drawable.redfish);
		
		return convertView;
	}

}
