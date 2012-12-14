package se.yumm.adapters;

import java.util.ArrayList;
import java.util.List;

import se.yumm.R;
import se.yumm.poi.Restaurants;
import se.yumm.utils.TAGS;
import se.yumm.views.CustomHorizontalScrollView;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import se.yumm.handlers.*;

public class HorizontalScrollAdapter extends ArrayAdapter<Restaurants>
{

	private final Activity m_context;
	private List<Restaurants> m_restaurants;

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
		this.setRestaurants(values);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		LayoutInflater inflater = (LayoutInflater) m_context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View horView = inflater.inflate(R.layout.activity_start, parent, false);
		
		Restaurants list = m_restaurants.get(position);
		
		TextView t = (TextView) horView.findViewById(R.id.horizRestText);
		TextView t2 = (TextView) horView.findViewById(R.id.horizRestText2);
		// TextView t3 = (TextView)
		// horView.findViewById(R.id.horizRestImageText);
		ImageView iv = (ImageView) horView.findViewById(R.id.horizRestImage);
		t.setText("Namn : " + list.getName() + "\n" + "Rating : "
				+ list.getFavouriteCount() + "/10 \n");
		t2.setText("Typ av kök: Japanskt");
		// t3.setText("Deliciouis");

		iv.setImageResource(R.drawable.hotspicy);
		// m_restaurants.get(position);
		 
		//LinearLayout layout = (LinearLayout) m_context.findViewById(R.id.topLayer);
		//CHorizontalScrollView hsv =  (CHorizontalScrollView) layout.findViewWithTag(TAGS.HSV);
		
		//ImageView iv = (ImageView) hsv.findViewWithTag(TAGS.IMAGE);
		//iv.setImageResource(R.drawable.redfish);
		
		
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
