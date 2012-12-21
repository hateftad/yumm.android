package se.yumm.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import se.yumm.R;
import se.yumm.poi.MenuItems;
import se.yumm.poi.Restaurants;

import android.app.Activity;
import android.util.Log;

public class JsonHandler
{

	private InputStream m_inStream = null;
	private final Activity m_context;
	
	// TAGS
	private static final String WEBSITE = "www";
	private static final String DESCRIPTION = "description";
	private static final String MENU = "menu";
	private static final String NAME = "name";
	private static final String PHONENR = "phone";
	private static final String PRICERNG = "price_range";
	private static final String PRICE = "price";
	private static final String ADDRESS = "address";
	private static final String LOCATION = "location";
	private static final String MENUHDR = "menu_headers";
	private static final String FAVCOUNT = "favorite_count";
	private static final String PURL = "public_url";
	private static final String ISHEADER = "is_header";


	
	public JsonHandler(Activity context)
	{
		this.m_context = context;
	}

	public String JsonFromRawFile() throws IOException
	{
		m_inStream = m_context.getResources().openRawResource(R.raw.json_file);
		Writer writer = new StringWriter();
		char[] buffer = new char[1024];

		try
		{
			Reader reader = new BufferedReader(new InputStreamReader(
					m_inStream, "UTF-8"));
			int n;
			while ((n = reader.read(buffer)) != -1)
			{
				writer.write(buffer, 0, n);
			}

		} finally
		{
			m_inStream.close();
		}

		String result = writer.toString();

		return result;

	}

	public ArrayList<Restaurants> GetListFromGson(String jsonString)
	{

		Type listType = new TypeToken<Collection<Restaurants>>()
		{
		}.getType();

		Gson gson = new Gson();

		ArrayList<Restaurants> list = gson.fromJson(jsonString, listType);

		return list;
	}
	
	@SuppressWarnings("unused")
	public List<Restaurants> JsonSorter(String jsonString)
	{
		Restaurants restaurant = null;
		MenuItems menuitems = null;
		ArrayList<Restaurants> restList = new ArrayList<Restaurants>();
		try
		{
			//List<Restaurants> restList = null;
			JSONArray restArray = new JSONArray(jsonString);

			for (int i = 0; i < restArray.length(); i++)
			{
				restaurant = new Restaurants();
				JSONObject rest = restArray.getJSONObject(i);
				restaurant.setName(rest.getString(NAME));
				restaurant.setDescription(rest.getString(DESCRIPTION));
				restaurant.setPhoneNr(rest.getString(PHONENR));
				restaurant.setWebpage(rest.getString(WEBSITE));
				restaurant.setFavouriteCount(rest.getInt(FAVCOUNT));
				restaurant.setPriceRng(rest.getString(PRICERNG));
				restaurant.setAddress(rest.getString(ADDRESS));
				restaurant.setLocation(rest.getString(LOCATION));
				restaurant.setPublicUrl(rest.getString(PURL));
				
				JSONArray menuHdr = rest.getJSONArray(MENUHDR);
				for (int j = 0; j < menuHdr.length(); j++) {
					String m = menuHdr.get(j).toString();
					restaurant.setMenuHeaders(m);
				}
				
				JSONArray menu = rest.getJSONArray(MENU);
				for (int x = 0; x < menu.length(); x++)
				{
					JSONObject menuItem = menu.getJSONObject(x);
					if(!menuItem.getBoolean(ISHEADER))
					{
						String name = menuItem.getString(NAME);
						String desc = menuItem.getString(DESCRIPTION);
						int price = menuItem.getInt(PRICE);
						restaurant.setMenuItems(new MenuItems(name, price, desc, menuItem.getBoolean(ISHEADER)));
					}
				}
				restList.add(restaurant);

			}
		} catch (JSONException e)
		{
			Log.v("YUMM", e.toString());
		}
		return restList;
	}

	
	
}
