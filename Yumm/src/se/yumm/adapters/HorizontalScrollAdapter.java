package se.yumm.adapters;

import java.util.ArrayList;
import java.util.List;

import se.yumm.R;
import se.yumm.items.Restaurants;
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

public class HorizontalScrollAdapter extends ArrayAdapter<Restaurants>
{

	private final Activity m_context;
	private List<Restaurants> m_restaurants;

	static class ViewHolder
	{
		public TextView contactDetails;
		public TextView imageSubText;
		public ImageView restaurantImg;
	}

	public HorizontalScrollAdapter(Activity context, int resourceId,
			ArrayList<Restaurants> values)
	{
		super(context, resourceId, values);
		this.m_context = context;
		this.setRestaurants(values);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{

		LayoutInflater inflater = (LayoutInflater) m_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View horView = inflater.inflate(R.layout.row_view, parent, false);

		Restaurants list = m_restaurants.get(position);

		return horView;
	}

	public List<Restaurants> getRestaurants()
	{
		return m_restaurants;
	}

	public void setRestaurants(List<Restaurants> m_restaurants)
	{
		this.m_restaurants = m_restaurants;
	}


}
