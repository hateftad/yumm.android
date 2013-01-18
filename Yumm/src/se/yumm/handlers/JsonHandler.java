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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import se.yumm.R;
import se.yumm.items.MenuItems;
import se.yumm.items.Restaurants;
import se.yumm.items.User;

import android.content.Context;
import android.util.Log;

public class JsonHandler
{

	private InputStream m_inStream = null;
	private final Context m_context;

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
	private static final String USER = "user";
	private static final String SESSID = "session_id";
	private static final String SUCCESS = "success";
	private static final String FAV_REST = "favorite_restaurants";
	private static final String FAV_MENU = "favorite_menu_items";
	private static final String AREA = "area";
	private static final String POSTC = "postal_code";
	private static final String BIRTH = "birthday";
	private static final String SEX = "sex";
	private static final String EMAIL = "email";

	/*
	 * {"session_id": "832451523657818511", "success": true, 
	 * "user": {"favorite_restaurants": [], "phone": null, "birthday": null, "postal_code": null, 
	 * "name": "Normal user", "address": null, "favorite_menu_items": [], "area": null, 
	 * "email": "bob@bob.bob", "sex": null}}
	 */



	public JsonHandler(Context m_context2)
	{
		this.m_context = m_context2;
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

	public User HandleUserJson(String jsonString)
	{
		User user = new User();
		try
		{
			JSONObject userObj = new JSONObject(jsonString);
			user.setSessionId(userObj.getString(SESSID));
			user.setLoggedIn(userObj.getBoolean(SUCCESS));
			JSONObject userContent = userObj.getJSONObject(USER);
			JSONArray favRest = userContent.getJSONArray(FAV_REST);
			for (int i = 0; i < favRest.length(); i++) {
				if (favRest.get(i) != null) {
					user.addFavourites(HandleRestaurantJSON(favRest.getJSONObject(i)));
				}
			}
			user.setPhoneNr(userContent.getString(PHONENR));
			user.setBirthday(userContent.getString(BIRTH));
			user.setPostalCode(userContent.getString(POSTC));
			user.setUserType(userContent.getString(NAME));
			user.setAddress(userContent.getString(ADDRESS));

			JSONArray favMenu = userContent.getJSONArray(FAV_MENU);
			for (int x = 0; x < favMenu.length(); x++) {
				if (favMenu.get(x) != null) {
					user.addMenuItems(HandleMenuItem(favMenu.getJSONObject(x)));
				}
			}
			user.setAddress(userContent.getString(AREA));
			user.setEmail(userContent.getString(EMAIL));
			user.setSex(userContent.getString(SEX));




		} catch (JSONException e)
		{
			Log.v("YUMM", e.toString());
		}
		return user;
	}

	public ArrayList<Restaurants> RestaurantsJsonSorter(String jsonString)
	{
		ArrayList<Restaurants> restList = new ArrayList<Restaurants>();
		try
		{

			JSONArray restArray = new JSONArray(jsonString);

			for (int i = 0; i < restArray.length(); i++)
			{
				//Restaurants restaurant = new Restaurants();
				JSONObject rest = restArray.getJSONObject(i);
				Restaurants restaurant = HandleRestaurantJSON(rest);
				
				JSONArray menuHdr = rest.getJSONArray(MENUHDR);
				for (int j = 0; j < menuHdr.length(); j++) {
					String m = menuHdr.get(j).toString();
					restaurant.addMenuHeaders(m);
				}
				
				JSONArray menu = rest.getJSONArray(MENU);
				for (int x = 0; x < menu.length(); x++)
				{
					restaurant.addMenuItems(HandleMenuItem(menu.getJSONObject(x)));
				}
				restList.add(restaurant);

			}
		} 
		catch (JSONException e)
		{
			Log.v("YUMM", e.toString());
		}
		return restList;
	}
	
	private MenuItems HandleMenuItem(JSONObject menuItem)
	{
		MenuItems mItem = null;
		try
		{
			if(!menuItem.getBoolean(ISHEADER))
			{
				String name = menuItem.getString(NAME);
				String desc = menuItem.getString(DESCRIPTION);
				int price = menuItem.getInt(PRICE);
				mItem = new MenuItems(name, price, desc, menuItem.getBoolean(ISHEADER));
			}
		} 
		catch(JSONException j)
		{
			Log.v("YUMM", j.toString());
		}
		return mItem;
	}
	
	private Restaurants HandleRestaurantJSON(JSONObject rest)
	{
		Restaurants restaurant = null;
		try
		{
			restaurant = new Restaurants();
			restaurant.setName(rest.getString(NAME));
			restaurant.setDescription(rest.getString(DESCRIPTION));
			restaurant.setPhoneNr(rest.getString(PHONENR));
			restaurant.setWebpage(rest.getString(WEBSITE));
			restaurant.setFavouriteCount(rest.getInt(FAVCOUNT));
			restaurant.setPriceRng(rest.getString(PRICERNG));
			restaurant.setAddress(rest.getString(ADDRESS));
			restaurant.setLocation(rest.getString(LOCATION));
			restaurant.setPublicUrl(rest.getString(PURL));
			
		} 
		catch (JSONException e)
		{
			Log.v("YUMM", e.toString());
		}
		
		return restaurant;
	}


}
