package se.yumm.poi;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;
import android.location.Address;
import android.location.Location;

public class Restaurants
{

	@SerializedName("name")
	private String m_name;

	@SerializedName("address")
	private String m_address;

	// public String m_kitchenType;

	@SerializedName("phone")
	private String m_phoneNr;

	@SerializedName("description")
	private String m_description;

	@SerializedName("www")
	private String m_webpage;

	@SerializedName("location")
	private String m_location;

	@SerializedName("menu")
	private ArrayList<MenuCategory> m_menu;

	@SerializedName("menu_headers")
	private ArrayList<String> m_menuHeaders;

	@SerializedName("price_range")
	private String m_priceRng;

	@SerializedName("favorite_count")
	private int m_favouriteCount;

	public Restaurants()
	{
		m_name = null;
		m_address = null;
		// m_kitchenType = null;
		m_phoneNr = null;
		m_description = null;
		m_webpage = null;
		m_location = null;
	}

	public String getName()
	{
		return m_name;
	}

	public void setName(String m_name)
	{
		this.m_name = m_name;
	}

	public String getAddress()
	{
		return m_address;
	}

	public void setAddress(String m_address)
	{
		this.m_address = m_address;
	}

	/*
	 * public String getKitchen() { return m_kitchenType; } public void
	 * setKitchen(String m_kitchen) { this.m_kitchenType = m_kitchen; } /*
	 * public List<MenuItems> getMenu() { return m_menu; } public void
	 * setMenu(List<MenuItems> m_menu) { this.m_menu = m_menu; }
	 */
	public String getPhoneNr()
	{
		return m_phoneNr;
	}

	public void setPhoneNr(String m_phoneNr)
	{
		this.m_phoneNr = m_phoneNr;
	}

	public String getLocation()
	{
		return m_location;
	}

	public void setLocation(double latitude, double longitude)
	{
		// this.m_location.setLatitude(latitude);
		// this.m_location.setLongitude(longitude);
		m_location = latitude + "," + longitude;
	}

	public String getDescription()
	{
		return m_description;
	}

	public void setDescription(String m_description)
	{
		this.m_description = m_description;
	}

	public String getWebpage()
	{
		return m_webpage;
	}

	public void setWebpage(String m_webpage)
	{
		this.m_webpage = m_webpage;
	}

	public List<String> getMenuHeaders()
	{
		return m_menuHeaders;
	}

	public void setMenuHeaders(ArrayList<String> m_menuHeaders)
	{
		this.m_menuHeaders = m_menuHeaders;
	}

	public String getPriceRng()
	{
		return m_priceRng;
	}

	public void setPriceRng(String m_priceRng)
	{
		this.m_priceRng = m_priceRng;
	}

	public int getFavouriteCount()
	{
		return m_favouriteCount;
	}

	public void setFavouriteCount(int m_favouriteCount)
	{
		this.m_favouriteCount = m_favouriteCount;
	}
}
