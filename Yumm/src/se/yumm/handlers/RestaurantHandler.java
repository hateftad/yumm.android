package se.yumm.handlers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;

import se.yumm.R;
import se.yumm.adapters.StartPageAdapter;
import se.yumm.poi.Restaurants;
import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ListView;

public class RestaurantHandler {
	
	private Activity m_context;
	private JsonHandler m_jsonHndlr;
	private ArrayList<Restaurants> m_restaurants;
	private StartPageAdapter m_pageAdapter;
	@SuppressWarnings("unused")
	private ListView m_listView;
	private boolean m_ascending = true;
	
	
	public RestaurantHandler(Activity context)
	{
		m_context = context;
		m_jsonHndlr = new JsonHandler(m_context);
		m_pageAdapter = new StartPageAdapter(m_context);
		
		
		ListView listView = (ListView) m_context.findViewById(R.id.startPageListView);
		listView.setAdapter(m_pageAdapter);
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

	public void setRestaurants(ArrayList<Restaurants> m_restaurants) 
	{
		this.m_restaurants = m_restaurants;
	}
	

	public boolean isAscending() {
		return m_ascending;
	}

	public void setAscending(boolean m_ascending) {
		this.m_ascending = m_ascending;
	}

	public StartPageAdapter GetAdapter()
	{
		return m_pageAdapter;
	}
	
	public void SetAdapter(StartPageAdapter adapter)
	{
		this.m_pageAdapter = adapter;
	}
	
	public void UpdateData(ArrayList<Restaurants> newRestaurants)
	{
		m_pageAdapter.updateRestaurants(newRestaurants);
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
			m_pageAdapter.updateRestaurants(result);
		}
	}
	
	public static Comparator<Restaurants> NameComparator = new Comparator<Restaurants>() {
			
		
			@Override
			public int compare(Restaurants lhs, Restaurants rhs) {
				
				String name1 = lhs.getName().toUpperCase(Locale.getDefault());
				String name2 = rhs.getName().toUpperCase(Locale.getDefault());
				
				return name1.compareTo(name2);
			}

	};
	
	public static Comparator<Restaurants> RatingComparator = new Comparator<Restaurants>() {

		@Override
		public int compare(Restaurants lhs, Restaurants rhs) {
			int rtg1 = lhs.getFavouriteCount();
			int rtg2 = rhs.getFavouriteCount();;
			
			if (rtg1 == rtg2 ) {
				return 0;
			}
			else if (rtg1 > rtg2)
			{
				return 1;
			}
			return -1;
		}
	};

	
	
}
