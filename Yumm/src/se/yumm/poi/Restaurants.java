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
	private Location m_location;

	@SerializedName("menu")
	private ArrayList<MenuItems> m_menu;

	@SerializedName("menu_headers")
	private ArrayList<String> m_menuHeaders;

	@SerializedName("price_range")
	private String m_priceRng;

	@SerializedName("favorite_count")
	private int m_favouriteCount;
	
	private String m_publicUrl;

	public Restaurants()
	{
		m_name = null;
		m_address = null;
		// m_kitchenType = null;
		m_phoneNr = null;
		m_description = null;
		m_webpage = null;
		m_location = null;
		m_menuHeaders = new ArrayList<String>();
		m_menu = new ArrayList<MenuItems>();
		m_location = new Location("yumm");
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

	public Location getLocation()
	{
		return m_location;
	}

	public void setLocation(String location)
	{
		String coord[] = location.split(",");
		m_location.setLatitude(Double.parseDouble(coord[0]));
		m_location.setLongitude(Double.parseDouble(coord[1]));
		//m_location = location;
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

	public void setMenuHeaders(String menuHdr)
	{
		this.m_menuHeaders.add(menuHdr);
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

	public String getPublicUrl() {
		return m_publicUrl;
	}

	public void setPublicUrl(String m_publicUrl) {
		this.m_publicUrl = m_publicUrl;
	}

	public ArrayList<MenuItems> getMenuItems() {
		return m_menu;
	}

	public void setMenuItems(MenuItems menuItem) {
		this.m_menu.add(menuItem);
	}
}
