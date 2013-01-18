package se.yumm.asyncTasks;

import java.util.ArrayList;

import se.yumm.handlers.JsonHandler;
import se.yumm.items.Restaurants;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

public class GetPlaces extends AsyncTask<String, Void, ArrayList<Restaurants>>
{

	private final Activity m_context;
	private ArrayList<Restaurants> m_rList;
	//private ListView m_listView;

	public GetPlaces(Activity context)
	{
		m_context = context;
		m_rList = null;
	}

	@Override
	protected ArrayList<Restaurants> doInBackground(String... params)
	{

		ArrayList<Restaurants> list = null;
		//String result = null;
		JsonHandler json = new JsonHandler(m_context);
		json.RestaurantsJsonSorter(params[0]);
		/*
		try
		{
			
			result = json.JsonFromRawFile();
			list = json.GetListFromGson(result);

		} catch (IOException e)
		{
			
		}
		*/
		return list;
	}

	@Override
	protected void onPostExecute(ArrayList<Restaurants> result)
	{
		this.setRList(result);

		//LinearLayout layout = (LinearLayout) m_context.findViewById(R.id.topLayer);
		//m_listView = (ListView) layout.findViewWithTag(TAGS.LISTVIEW);
		//m_listView = (ListView) m_context.findViewById(R.id.startPageListView);
		//HorizontalScrollAdapter pAdapter = new HorizontalScrollAdapter(
		//		m_context, R.layout.activity_start, m_rList);
		//m_listView.setAdapter(pAdapter);

	}

	@Override
	protected void onProgressUpdate(Void... values)
	{
		ProgressDialog pd = new ProgressDialog(m_context);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("Working...");
		pd.setIndeterminate(true);
		pd.setCancelable(false);
	}

	public ArrayList<Restaurants> getRList()
	{
		return m_rList;
	}

	public void setRList(ArrayList<Restaurants> result)
	{
		this.m_rList = result;
	}

}
