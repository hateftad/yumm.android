package se.yumm.adapters;

import java.util.List;

import se.yumm.R;
import se.yumm.R.drawable;
import se.yumm.R.id;
import se.yumm.R.layout;
import se.yumm.poi.Restaurants;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PlacesAdapter extends ArrayAdapter<String>
{

	private final Activity m_context;
	private List<Restaurants> m_restaurants;
	private final String[] m_values;
	private LinearLayout m_horizontalLayout;

	static class ViewHolder
	{
		public TextView rstText;
		public TextView adrsText;
	}

	public PlacesAdapter(Activity context, int layout, String[] values)
	{
		super(context, layout, values);
		this.m_context = context;
		this.m_values = values;
		m_horizontalLayout = (LinearLayout) m_context
				.findViewById(R.id.horizRestLayout);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		/*
		 * LayoutInflater inflater = (LayoutInflater)
		 * m_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); View
		 * rowView = inflater.inflate(R.layout.row_view, parent, false);
		 * TextView restName = (TextView)
		 * rowView.findViewById(R.id.restaurantName); TextView addText =
		 * (TextView) rowView.findViewById(R.id.addressText);
		 * addText.setText("kir"); restName.setText(m_values[position]);
		 */
		LayoutInflater inflater = (LayoutInflater) m_context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View horView = inflater.inflate(R.layout.activity_start, parent, false);
		//TextView t = (TextView) horView.findViewById(R.id.horizRestText);
		//TextView t2 = (TextView) horView.findViewById(R.id.horizRestText2);
		//ImageView iv = (ImageView) horView.findViewById(R.id.horizRestImage);
		//t.setText("kirkajhdkjashdkjashdkjashdkjahkjdhaskjdhaksjhdkajshdkjashdkjhasdjkashd");
		//t2.setText("kos");
		//iv.setImageResource(R.drawable.hotspicy);

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
