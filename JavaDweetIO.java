package javadweetio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JavaDweetIO {
    public static void main(String[] args) throws IOException {        
        String myThing = "";
        String last_timestamp="";
       
     while(true) {   
        try {
            JSONObject json = ReadJSON("https://dweet.io//get/latest/dweet/for/" + myThing);
            JSONArray jsonArray_with = json.getJSONArray("with");
           
            for(int i=0; i<jsonArray_with.length(); i++){
                JSONObject jsonObject_with = (JSONObject) jsonArray_with.get(i);
                String created = jsonObject_with.getString("created");
                JSONObject jsonObject_with_content = jsonObject_with.getJSONObject("content");
                Double temperature = jsonObject_with_content.getDouble("temperature");
                Double humidity = jsonObject_with_content.getDouble("humidity");
                
                
                if (!last_timestamp.equalsIgnoreCase(created)) {
                	System.out.println("New data arrived.....");
                	String data1="\nTime="+created+",Temperature="+temperature+",Humidity="+humidity;
                	System.out.println(data1);               	
                	
                	
                last_timestamp=created;	
                }else {//System.out.println("Old data.");}
            }
            
        }
            }catch (IOException | JSONException e){
            System.out.println(e.toString());
        }
       try {
		Thread.sleep(10000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     }
     
    }
    
   public static JSONObject ReadJSON(String url) throws IOException, JSONException {        
        try (InputStream inputStream = new URL(url).openStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder jsonBody = new StringBuilder();
            int singleChar;
            while ((singleChar = bufferedReader.read()) != -1) {
                jsonBody.append((char)singleChar);
            }
            JSONObject json = new JSONObject(jsonBody.toString());
            return json;
        }
    }
}
