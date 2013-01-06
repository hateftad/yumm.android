package se.yumm.items;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class RestaurantList implements Parcelable
{

	private ArrayList<Restaurants> m_restaurantList = new ArrayList<Restaurants>();
	private int m_size;

	public RestaurantList()
	{
		m_size = 0;
	}
	private RestaurantList(Parcel in)
	{

		for (int i = 0; i < m_size; i++) {
			String name = in.readString();
			String b = in.readString();
		}
	}
	public ArrayList<Restaurants> getRestaurantList()
	{
		return m_restaurantList;
	}
	
	public void setRestaurantList(ArrayList<Restaurants> m_restaurantList)
	{
		m_size = m_restaurantList.size();
		this.m_restaurantList = m_restaurantList;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		
		m_size = m_restaurantList.size();
		
		for (int i = 0; i < m_restaurantList.size(); i++) {
			
			out.writeString(m_restaurantList.get(i).getName());
			out.writeString(m_restaurantList.get(i).getAddress());
			out.writeString(m_restaurantList.get(i).getPhoneNr());
			out.writeString(m_restaurantList.get(i).getDescription());
			out.writeString(m_restaurantList.get(i).getPriceRng());
			out.writeString(m_restaurantList.get(i).getPublicUrl());
			out.writeString(m_restaurantList.get(i).getWebpage());
			out.writeInt(m_restaurantList.get(i).getFavouriteCount());
			out.writeDouble(m_restaurantList.get(i).getLocation().getLatitude());
			out.writeDouble(m_restaurantList.get(i).getLocation().getLongitude());
			out.writeStringList(m_restaurantList.get(i).getMenuHeaders());
			ArrayList<MenuItems> m = m_restaurantList.get(i).getMenuItems();
			for (int j = 0; j < m.size(); j++) {
				out.writeString(m.get(j).getDishName());
				out.writeString(m.get(j).getDescription());
				out.writeInt(m.get(j).getPrice());
			}
		
		}
	}
	
	public static Parcelable.Creator<RestaurantList> CREATOR =  new Parcelable.Creator<RestaurantList>() {

		@Override
		public RestaurantList createFromParcel(Parcel in) {
			// TODO Auto-generated method stub
			return new RestaurantList(in);
		}

		@Override
		public RestaurantList[] newArray(int size) {
			// TODO Auto-generated method stub
			return null;
		}
	};

}
