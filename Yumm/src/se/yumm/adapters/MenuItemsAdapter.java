package se.yumm.adapters;

import java.util.ArrayList;
import se.yumm.R;
import se.yumm.utils.ViewHolder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MenuItemsAdapter extends BaseAdapter{

	private final Context m_context;
	private ArrayList<String> m_menuHeaders;
	
	public MenuItemsAdapter(Context context, ArrayList<String> headers)
	{
		this.m_context = context;
		m_menuHeaders = new ArrayList<String>();
		m_menuHeaders.add("Visa Alla");
		m_menuHeaders.addAll(headers);
	}
	
	@Override
	public int getCount() {
		return m_menuHeaders.size();
	}

	@Override
	public String getItem(int position) {
		return m_menuHeaders.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (convertView == null) 
		{
			convertView = View.inflate(m_context, R.layout.menu_items_row_view, null);
		}
		
		String header = getItem(position);
		TextView headerView =  ViewHolder.get(convertView, R.id.menu_header);
		headerView.setText(header);
		
		return convertView;
	}

}
