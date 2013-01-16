package se.yumm.handlers;

/*
* @author Hatef Tadayon
*
* 
*/

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;

import se.yumm.adapters.StartPageAdapter;
import se.yumm.items.Restaurants;
import se.yumm.listeners.IActionListener;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.BaseAdapter;

public class RestaurantHandler {
	
	private JsonHandler m_jsonHndlr;
	private ArrayList<Restaurants> m_restaurants;
	private BaseAdapter m_pageAdapter;
	private IActionListener m_eventListener = null;
	private boolean m_ascending = true;
	
	
	public RestaurantHandler(Context context)
	{
		m_jsonHndlr = new JsonHandler(context);
		m_restaurants = new ArrayList<Restaurants>();
		
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

	public BaseAdapter GetAdapter()
	{
		return m_pageAdapter;
	}
	
	public void SetAdapter(BaseAdapter adapter)
	{
		this.m_pageAdapter = adapter;
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
			m_eventListener.OnComplete(null);
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
			else
				return -1;
		}
	};

	public void SetEventListener(IActionListener listener)
	{
		this.m_eventListener = listener;
	}
	
}
