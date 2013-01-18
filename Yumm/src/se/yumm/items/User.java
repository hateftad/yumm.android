package se.yumm.items;

import java.util.ArrayList;

public class User {
	
	private String m_sessionId;
	private boolean m_loggedIn;
	private ArrayList<Restaurants> m_favourites;
	private ArrayList<MenuItems> m_menuItemFavs;
	private String m_birthday;
	private String m_phoneNr;
	private String m_userType;
	private String m_address;
	private String m_email;
	private String m_area;
	private String m_postalCode;
	private String m_sex;
	
	public User()
	{
		m_favourites = new ArrayList<Restaurants>();
		m_menuItemFavs = new ArrayList<MenuItems>();
	}

	public String getSessionId() {
		return m_sessionId;
	}

	public void setSessionId(String m_sessionId) {
		this.m_sessionId = m_sessionId;
	}

	public ArrayList<Restaurants> getFavourites() {
		return m_favourites;
	}

	public void addFavourites(Restaurants restaurant) {
		this.m_favourites.add(restaurant);
	}

	public ArrayList<MenuItems> getMenuItems() {
		return m_menuItemFavs;
	}

	public void addMenuItems(MenuItems menuItem) {
		this.m_menuItemFavs.add(menuItem);
	}

	public String getPhoneNr() {
		return m_phoneNr;
	}

	public void setPhoneNr(String m_phoneNr) {
		this.m_phoneNr = m_phoneNr;
	}

	public String getUserType() {
		return m_userType;
	}

	public void setUserType(String m_userType) {
		this.m_userType = m_userType;
	}

	public String getAddress() {
		return m_address;
	}

	public void setAddress(String m_address) {
		this.m_address = m_address;
	}

	public String getEmail() {
		return m_email;
	}

	public void setEmail(String m_email) {
		this.m_email = m_email;
	}

	public String getArea() {
		return m_area;
	}

	public void setArea(String m_area) {
		this.m_area = m_area;
	}

	public String getPostalCode() {
		return m_postalCode;
	}

	public void setPostalCode(String m_postalCode) {
		this.m_postalCode = m_postalCode;
	}

	public String getSex() {
		return m_sex;
	}

	public void setSex(String m_sex) {
		this.m_sex = m_sex;
	}

	public boolean isLoggedIn() {
		return m_loggedIn;
	}

	public void setLoggedIn(boolean m_logedIn) {
		this.m_loggedIn = m_logedIn;
	}

	public String getBirthday() {
		return m_birthday;
	}

	public void setBirthday(String m_birthday) {
		this.m_birthday = m_birthday;
	}

}
