package se.yumm.items;

import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

public class MenuCategory
{

	private String[] m_categories;

	@SerializedName("category")
	private ArrayList<MenuItems> m_items;

	public ArrayList<MenuItems> getItems()
	{
		return m_items;
	}

	public void setItems(ArrayList<MenuItems> m_items)
	{
		this.m_items = m_items;
	}

	public String[] getCategories()
	{
		return m_categories;
	}

	public void setCategories(String[] m_categories)
	{
		this.m_categories = m_categories;
	}
}
