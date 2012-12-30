package se.yumm.utils;

import java.util.Comparator;

import se.yumm.poi.Restaurants;

public class NameComparator implements Comparator<Restaurants> {

	@Override
	public int compare(Restaurants lhs, Restaurants rhs) {
		
		String name1 = lhs.getName().toUpperCase();
		String name2 = rhs.getName().toUpperCase();
		
		return name1.compareTo(name2);
	}

}
