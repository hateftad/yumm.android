package se.yumm.asyncTasks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import se.yumm.R;
import se.yumm.adapters.HorizontalScrollAdapter;
import se.yumm.handlers.JsonHandler;
import se.yumm.handlers.LinearLayoutHandler;
import se.yumm.poi.Restaurants;
import se.yumm.utils.TAGS;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.LinearLayout;
import android.widget.ListView;

public class GetPlaces extends AsyncTask<String, Void, ArrayList<Restaurants>>
{

	private final Activity m_context;
	private JsonHandler m_jsonHdlr;
	private ArrayList<Restaurants> m_rList;
	private ListView m_listView;

	public GetPlaces(Activity context)
	{
		m_context = context;
		m_jsonHdlr = new JsonHandler(context);
		m_rList = null;
	}

	@Override
	protected ArrayList<Restaurants> doInBackground(String... params)
	{

		ArrayList<Restaurants> list = null;
		String result = null;

		try
		{
			result = m_jsonHdlr.JsonFromRawFile();
			list = m_jsonHdlr.GetListFromGson(result);

		} catch (IOException e)
		{

		}
		// json.WebCall(params[0]);
		// return json.GetJsonFromURL(params[0]);
		return list;
	}

	@Override
	protected void onPostExecute(ArrayList<Restaurants> result)
	{
		this.setRList(result);

		//LinearLayout layout = (LinearLayout) m_context.findViewById(R.id.topLayer);
		//m_listView = (ListView) layout.findViewWithTag(TAGS.LISTVIEW);
		m_listView = (ListView) m_context.findViewById(R.id.startPageListView);
		HorizontalScrollAdapter pAdapter = new HorizontalScrollAdapter(
				m_context, R.layout.activity_start, m_rList);
		m_listView.setAdapter(pAdapter);

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
