package se.yumm.items;



import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MenuItems implements Parcelable
{

	@SerializedName("dish")
	private String m_dishName;

	@SerializedName("price")
	private int m_price;

	@SerializedName("desc")
	private String m_description;
	
	private boolean m_isHeader;

	// private List<String> m_ingredients;
	// private int m_rating;

	public MenuItems()
	{
		m_dishName = null;
		m_description = null;
		// m_ingredients = null;
		m_price = 0;
		// m_rating = 0;
	}
	
	public MenuItems(String name, int price, String description, boolean header)
	{
		m_dishName = name;
		m_price = price;
		m_description = description;
		m_isHeader = header;
	}

	public String getDishName()
	{
		return m_dishName;
	}

	public void setDishName(String m_dishName)
	{
		this.m_dishName = m_dishName;
	}

	/*
	 * public List<String> getIngredients() { return m_ingredients; } public
	 * void setIngredients(List<String> m_ingredients) { this.m_ingredients =
	 * m_ingredients; }
	 */
	public int getPrice()
	{
		return m_price;
	}

	public void setPrice(int m_price)
	{
		this.m_price = m_price;
	}

	/*
	 * public int getRating() { return m_rating; } public void setRating(int
	 * m_rating) { this.m_rating = m_rating; }
	 */
	public String getDescription()
	{
		return m_description;
	}

	public void setDescription(String m_description)
	{
		this.m_description = m_description;
	}

	public boolean isHeader() {
		return m_isHeader;
	}

	public void setIsHeader(boolean m_isHeader) {
		this.m_isHeader = m_isHeader;
	}
	
	private MenuItems(Parcel in)
	{
		m_dishName = in.readString();
		m_description = in.readString();
		m_price = in.readInt();
		m_isHeader = in.readByte() == 1;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		
		out.writeString(m_dishName);
		out.writeString(m_description);
		out.writeInt(m_price);
		out.writeByte((byte)(m_isHeader ? 1 : 0));
	}
	
	public static final Parcelable.Creator<MenuItems> CREATOR = new Parcelable.Creator<MenuItems>() {
        public MenuItems createFromParcel(Parcel in) {
            return new MenuItems(in);
        }

        public MenuItems[] newArray(int size) {
            return new MenuItems[size];
        }
    };

}