package se.yumm.handlers;

import java.util.ArrayList;

import se.yumm.R;
import se.yumm.adapters.HorizontalScrollAdapter;
import se.yumm.poi.Restaurants;
import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ListView;

public class RestaurantHandler {
	
	private Activity m_context;
	private JsonHandler m_jsonHndlr;
	private ArrayList<Restaurants> m_restaurants;
	
	
	public RestaurantHandler(Activity context)
	{
		m_context = context;
		m_jsonHndlr = new JsonHandler(m_context);
	}
	
	public void RestaurantsFromJson(String jsonString)
	{
		//private class 
		GetRestaurantTask rt = new GetRestaurantTask();
		rt.execute(jsonString);
		
	}
	
	public ArrayList<Restaurants> getRestaurants() {
		return m_restaurants;
	}

	public void setRestaurants(ArrayList<Restaurants> m_restaurants) {
		this.m_restaurants = m_restaurants;
	}
	
	private class GetRestaurantTask extends AsyncTask<String, Void, ArrayList<Restaurants>> {

		@Override
		protected ArrayList<Restaurants> doInBackground(String... params) {
			
			return m_jsonHndlr.JsonSorter(params[0]);
		}
		
		@Override
		protected void onPostExecute(ArrayList<Restaurants> result)
		{
			setRestaurants(result);
			ListView listView = (ListView) m_context.findViewById(R.id.startPageListView);
			HorizontalScrollAdapter pAdapter = new HorizontalScrollAdapter(
					m_context, R.layout.activity_start, m_restaurants);
			listView.setAdapter(pAdapter);

		}
	}
	
}
