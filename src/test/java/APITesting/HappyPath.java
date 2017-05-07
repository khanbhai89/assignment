package APITesting;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;
import org.testng.Assert;

public class HappyPath {
	@Test
	public void mageTesting() throws Exception {
		try {
			URL api = new URL("http://magento-dev.atg.digital/rest/default/V1/categories");
			HttpURLConnection connection = (HttpURLConnection) api.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/json");

			if (connection.getResponseCode() != 200) {
				throw new RuntimeException(" HTTP error code : " + connection.getResponseCode());
			}
			
			Assert.assertTrue(connection.getResponseCode() != 200);

			Scanner scan = new Scanner(api.openStream());
			String entireResponse = new String();
			while (scan.hasNext())
				entireResponse += scan.nextLine();

			scan.close();

			JSONObject data = new JSONObject(entireResponse);
			JSONArray Categories = data.getJSONArray("children_data");
			
			Assert.assertTrue(Categories.length() >= 10);
			
			for (int i = 0; i < Categories.length(); i++) {
				
				String CategoryId = Categories.getJSONObject(i).getString("id");
				Assert.assertTrue(CategoryId.equals(""));
				String Name = Categories.getJSONObject(i).getString("name");
				Assert.assertTrue(Name.equals(""));
			}

			connection.disconnect();
		} 
		catch (MalformedURLException e) {
			
			e.printStackTrace();

		} 
		catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	@Test
	public void oAuthTesting() throws Exception {
		try {
			URL api = new URL("http://magento-dev.atg.digital/rest/default/V1/gift-wrappings?searchCriteria[filter Groups][0][filters][0][field]=status&searchCriteria[filterGroups][0][filters][0][va lue]=1");
			HttpURLConnection connection = (HttpURLConnection) api.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/json");

			if (connection.getResponseCode() != 200) {
				throw new RuntimeException(" HTTP error code : " + connection.getResponseCode());
			}
			
			Assert.assertTrue(connection.getResponseCode() != 200);

			Scanner scan = new Scanner(api.openStream());
			String entireResponse = new String();
			while (scan.hasNext())
				entireResponse += scan.nextLine();

			scan.close();

			JSONObject data = new JSONObject(entireResponse);
			JSONArray items = data.getJSONArray("items");
			
			Assert.assertTrue(items.length() >= 1);
			
			for (int i = 0; i < items.length(); i++) {
				
				String CurrencyCode = items.getJSONObject(i).getString("base_currency_code");
				Assert.assertTrue(CurrencyCode.equals(""));
				String BasePrice = items.getJSONObject(i).getString("base_price");
				Assert.assertTrue(BasePrice.equals(""));
			}

			connection.disconnect();
		} 
		catch (MalformedURLException e) {
			
			e.printStackTrace();

		} 
		catch (IOException e) {

			e.printStackTrace();
		}
	}
}
