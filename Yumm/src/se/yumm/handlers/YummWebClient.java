package se.yumm.handlers;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class YummWebClient {

	static String LOCAL_BASE_URL = "http://10.0.2.2:8080";
	static String BASE_URL = "http://yummapp.appspot.com";
	
	private static AsyncHttpClient m_client = new AsyncHttpClient();
	
	public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		m_client.get(getAbsoluteUrl(url), params, responseHandler);
	}

	public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		m_client.post(getAbsoluteUrl(url), params, responseHandler);
	}

	private static String getAbsoluteUrl(String relativeUrl) {
		return LOCAL_BASE_URL + relativeUrl;
	}

	public static AsyncHttpClient GetClient() {
		return m_client;
	}

	public static void SetClient(AsyncHttpClient m_client) {
		YummWebClient.m_client = m_client;
	}
	public static void AddHeader(String header, String value)
	{
		YummWebClient.m_client.addHeader(header, value);
	    
	}


}
