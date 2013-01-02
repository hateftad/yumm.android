package se.yumm;

import se.yumm.asyncTasks.AutoCompletePlaces;
import se.yumm.handlers.JsonHandler;
import se.yumm.handlers.LocationHandler;
//import se.yumm.handlers.WebServiceHandler;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class MainActivity extends Activity implements TextWatcher
{

	private AutoCompleteTextView m_locationText;
	private AutoCompleteTextView m_kitchenText;
	private ArrayAdapter<String> m_listAdapter;
	private LocationHandler m_locationHdlr;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// m_locationBtn = (Button) findViewById(R.id.locationButton);

		m_listAdapter = new ArrayAdapter<String>(this, R.layout.auto_comp_list);
		m_listAdapter.setNotifyOnChange(true);

		m_kitchenText = (AutoCompleteTextView) findViewById(R.id.autoCompleteVarious);
		String[] kitchen = getResources().getStringArray(R.array.KitchenArray);
		m_kitchenText.setAdapter(new ArrayAdapter<String>(this,
				R.layout.auto_comp_list, kitchen));

		m_locationHdlr = new LocationHandler(this);

		m_locationText = (AutoCompleteTextView) findViewById(R.id.autoCompletePlaces);
		m_locationText.setSingleLine(true);
		m_locationText.setAdapter(m_listAdapter);
		m_locationText.addTextChangedListener(this);
		// m_locationText.setOnItemClickListener(this);
		// WebServiceHandler.GetRestaurantsList(getApplicationContext());

		// JsonHandler jsonhdlr = new JsonHandler();
		// jsonhdlr.GetJsonFromURL("http://yummapp.appspot.com/places/?q=&json");
		// http://yummapp.appspot.com/places/?q=&json=true
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		// set bool to resume or stop
		m_locationHdlr.update();

	}

	@Override
	protected void onPause()
	{
		super.onPause();
		m_locationHdlr.pause();
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		// location manager bool set to false
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void onLocationButtonClick(View view)
	{
		// get location and store address
		// set bool to start receiving updates from locationmgr
		String str = m_locationHdlr.getAddress(0);
		m_locationText.setText(str);

	}

	public void onSearchButtonClick(View view)
	{
		Intent intent = new Intent(getApplication(), ListMapActivity.class);

		// EditText text = (EditText) findViewById(R.id.autoCompletePlaces);
		// String address = text.getText().toString();
		// Toast.makeText(getApplicationContext(), address,
		// Toast.LENGTH_LONG).show();
		startActivity(intent);
	}

	@Override
	public void afterTextChanged(Editable s)
	{

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after)
	{

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count)
	{
		if (count % 3 == 1)
		{
			m_listAdapter.clear();
			AutoCompletePlaces task = new AutoCompletePlaces(this,
					m_locationText);
			task.execute(m_locationText.getText().toString());
		}
	}

}
