package se.yumm.handlers;
/*
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.cookie.Cookie;
import org.apache.http.message.BasicNameValuePair;

import com.loopj.android.http.PersistentCookieStore;

import se.yumm.R;
import se.yumm.poi.Restaurants;
import android.app.Activity;
import android.os.AsyncTask;

public class WebServices {
	
	//URLS
	private static final String YUMM_JSON = "/places/?q=&json=true";
	private static final String LOGIN = "/login/";

	//Member Variables
	private Activity m_context;
	private Cookie m_cookie;
	private RestaurantHandler m_restHndlr;
	private PersistentCookieStore m_cookieStore;
	private boolean m_isLoggedIn = false;
	
	public WebServices(Activity activity) 
	{
		m_context = activity;
		m_restHndlr = new RestaurantHandler(activity);
		m_cookieStore = new PersistentCookieStore(activity);
		YummWebClient.GetClient().setCookieStore(m_cookieStore);
	}
	

	@SuppressWarnings("unchecked")
	public void LoginClient()
	{
		
		String username = m_context.getResources().getString(R.string.username);
		String password = m_context.getResources().getString(R.string.password);

		ArrayList<NameValuePair> form = new ArrayList<NameValuePair>();
		form.add(new BasicNameValuePair("email", username));
		form.add(new BasicNameValuePair("password", password));
		
		YummWebClient.AddHeader("Content", "application/x-www-form-urlencoded");
		
		LoginTask lt = new LoginTask();
		lt.execute(form);
		
	}
	
	public void RetrieveData()
	{
		YummWebClient.AddHeader("Cookie", "name="+m_cookie.getValue());
		
		RetrieveTask rt = new RetrieveTask();
		rt.execute("");
		
	}
	
	public boolean isLoggedIn() {
		return m_isLoggedIn;
	}


	public void setIsLoggedIn(boolean m_isLoggedIn) {
		this.m_isLoggedIn = m_isLoggedIn;
	}

	private class LoginTask extends AsyncTask<ArrayList<NameValuePair>, Void, Void>
	{

		@Override
		protected Void doInBackground(ArrayList<NameValuePair>... params) {
			
			YummWebClient.post(LOGIN, params[0]);
			return null;

		}
		@Override
		protected void onPostExecute(Void d)
		{
			for (Cookie cookie : m_cookieStore.getCookies()) {
				if(cookie.getName().equals("sid"))
					m_cookie = cookie;
			}
			setIsLoggedIn(true);
		}
	}
	
	private class RetrieveTask extends AsyncTask<String, Void, String>
	{

		@Override
		protected String doInBackground(String... params) {
			
			return YummWebClient.get(YUMM_JSON);
		}
		@Override
		protected void onPostExecute(String result)
		{
			String r = result;
		}
	}
	
	

}
*/