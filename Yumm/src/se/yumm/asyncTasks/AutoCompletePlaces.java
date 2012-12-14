package se.yumm.asyncTasks;

import java.util.ArrayList;

import se.yumm.R;
import se.yumm.handlers.WebServiceHandler;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class AutoCompletePlaces extends
		AsyncTask<String, Void, ArrayList<String>>
{

	private Context m_context;
	private AutoCompleteTextView m_locationText;

	public AutoCompletePlaces(Context context, AutoCompleteTextView textView)
	{
		m_context = context;
		m_locationText = textView;
	}

	@Override
	protected ArrayList<String> doInBackground(String... params)
	{
		// TODO Auto-generated method stub
		return WebServiceHandler.AutoCompletePlaces(params[0], m_context);
	}

	@Override
	protected void onPostExecute(ArrayList<String> result)
	{
		Log.v("Yumm", "onPostExecute : " + result.size());

		ArrayAdapter<String> tempAdapter = new ArrayAdapter<String>(m_context,
				R.layout.auto_comp_list);
		tempAdapter.setNotifyOnChange(true);
		m_locationText.setAdapter(tempAdapter);

		for (String string : result)
		{
			tempAdapter.add(string);
			tempAdapter.notifyDataSetChanged();
		}
	}

}
