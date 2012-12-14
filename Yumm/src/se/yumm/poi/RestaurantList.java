package se.yumm.poi;

import java.util.ArrayList;
import java.util.List;

public class RestaurantList
{
	//make something here
	private ArrayList<Restaurants>[] m_restaurantList;

	public List<Restaurants>[] getRestaurantList()
	{
		return m_restaurantList;
	}

	public void setRestaurantList(ArrayList<Restaurants>[] m_restaurantList)
	{
		this.m_restaurantList = m_restaurantList;
	}

}
