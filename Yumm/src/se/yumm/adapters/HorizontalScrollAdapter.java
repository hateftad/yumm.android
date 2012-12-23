package se.yumm.adapters;

import java.util.ArrayList;
import java.util.List;

import se.yumm.R;
import se.yumm.poi.Restaurants;
import se.yumm.utils.PropertiesManager;
import se.yumm.views.CustomHorizontalScrollView;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HorizontalScrollAdapter extends ArrayAdapter<Restaurants> implements Callback
{

	private final Activity m_context;
	private List<Restaurants> m_restaurants;
	private Handler m_handler;

	static class ViewHolder
	{
		public TextView contactDetails;
		public TextView rating;
		public ImageView rstImg;
	}

	public HorizontalScrollAdapter(Activity context, int resourceId,
			ArrayList<Restaurants> values)
	{ // List<Restaurants> rest) {
		super(context, resourceId, values);

		this.m_context = context;
		m_handler = new Handler(this);
		this.setRestaurants(values);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) m_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			//View horView = inflater.inflate(R.layout.activity_start, parent, false);
			convertView =  inflater.inflate(R.layout.activity_start, parent, false);
		}
		int width = PropertiesManager.GetInstance().m_windowWidth;
		int maxItem = PropertiesManager.GetInstance().m_maxItems;
		
		CustomHorizontalScrollView hsv =  (CustomHorizontalScrollView) m_context.findViewById(R.id.horizScrollView);
		hsv.SetItemWidth(width);
		hsv.SetMaxItem(maxItem);
		
		Restaurants list = m_restaurants.get(position);
		
		
		TextView t = (TextView) convertView.findViewById(R.id.horizRestText);
		TextView t2 = (TextView) convertView.findViewById(R.id.horizRestText2);
		ImageView iv = (ImageView) convertView.findViewById(R.id.horizRestImage);
		LayoutParams layoutParams = iv.getLayoutParams();
		layoutParams.height = m_context.getResources().getDimensionPixelSize(R.dimen.placeholder_height);
		
		t.setText(list.getName() + "\n" 
				+ "Japanskt "+ list.getFavouriteCount());
		t.setWidth(width);
		
		t2.setText("Telefon : " + list.getPhoneNr() + "\n" +
				   "Address : " + list.getAddress() + "\n" +
				   "Leveransvillkor : Hemkörning");
		t2.setWidth(width);
		
		if((position % 2)==0)
			iv.setImageResource(R.drawable.hotspicy);
		else
			iv.setImageResource(R.drawable.redfish);
		
		return convertView;
	}

	public List<Restaurants> getRestaurants()
	{
		return m_restaurants;
	}

	public void setRestaurants(List<Restaurants> m_restaurants)
	{
		this.m_restaurants = m_restaurants;
	}

	@Override
	public boolean handleMessage(Message msg) {
		
		
		//getItem()
		
		
		return true;
	}

}
