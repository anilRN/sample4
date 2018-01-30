package sample4;
import java.io.BufferedReader;


import sample3.*;
import api.*;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import com.google.gson.Gson;

public class search {
	
	public static int cuisine_id;

	public static void main(String[] args) throws Exception {
		
		String Name;
		Example a;
		Scanner in=new Scanner(System.in);
		Name=in.next();
		search s=new search();
		a = s.cuisine_http_Connection();
		
		System.out.println("Testing 1 - Send Http GET request");
		s.sendGet();
		s.match(a, Name);
		
		BufferedReader br = new BufferedReader(new FileReader("/home/anil/Desktop/search"));
		Gson obj = new Gson();
		Level1 level1=obj.fromJson(br, Level1.class);
		s.restaurants(level1);
		
		

	}
	private void sendGet() throws Exception {

		String url = "https://developers.zomato.com/api/v2.1/search?entity_id=4&cuisines="+cuisine_id+"&sort=rating&order=desc";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("user-key", "442861c540487705cee55f9b804af1cd");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
        String st=response.toString();
                
        BufferedWriter writer=new BufferedWriter(new FileWriter("/home/anil/Desktop/search"));
        writer.write(st);
        writer.close();
	}
	
	void match(Example c,String Name)
	{
		System.out.println("Entered");
		System.out.println(c.getCuisines() == null);
		
		HashMap<String,Integer> hashmap=new HashMap<String,Integer>();
		System.out.println(c.getCuisines().size());
		
		for(int i=0;i<c.getCuisines().size();i++)
		{
			System.out.println(c.getCuisines().get(i).getCuisine().getName() == null);
			hashmap.put(c.getCuisines().get(i).getCuisine().getName(),c.getCuisines().get(i).getCuisine().getId());
		}
		System.out.println(hashmap.keySet());
		cuisine_id = hashmap.get(Name);
	}
	
	public static Example cuisine_http_Connection() throws Exception {
		
		System.out.println("Testing 1 - Send Http GET request");
		String url = "https://developers.zomato.com/api/v2.1/cuisines?city_id=4";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("user-key", "442861c540487705cee55f9b804af1cd");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

	 	while ((inputLine = in.readLine()) != null) {
	 		response.append(inputLine);
	 	}
	 	in.close();
        String st=response.toString();
        BufferedWriter writer=new BufferedWriter(new FileWriter("/home/anil/Desktop/cuisine"));
        writer.write(st);
        writer.close();
		BufferedReader br = new BufferedReader(new FileReader("/home/anil/Desktop/cuisine"));
		Gson ob = new Gson();
		Example c=ob.fromJson(br, Example.class);
		return c;
	
    }

		void restaurants(Level1 level1) {
			Map<String, Float> hashMap = new HashMap<>();
			for (int i = 0; i < level1.restaurants.size(); i++) {
				hashMap.put(level1.getRestaurants().get(i).getRestaurant().getName(), Float
						.parseFloat(level1.getRestaurants().get(i).getRestaurant().getUser_rating().getAggregate_rating()));
			}

			Map<String, Float> tempMap = new HashMap<String, Float>();
			for (String wsState : hashMap.keySet()) {
				tempMap.put(wsState, hashMap.get(wsState));
			}
			int count = 0;
			Set<Map.Entry<String, Float>> mappingSet = tempMap.entrySet();
			for (Map.Entry<String, Float> set : mappingSet) {
				++count;
				System.out.println("name:" + set.getKey() + "     rating:" + set.getValue());
				if (count == 5)
					break;
			}
		}

}
