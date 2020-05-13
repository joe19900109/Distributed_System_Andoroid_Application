/**
 * @author Derek Lin
 *
 * This is the main class to activate the android function. Moreover, it will initiate the
 * other working class and pass the reference. After the backed thread finished the whole task,
 * it will call ready funtion to finish the android part.
 */
package cmu.edu.yunanl;

import android.os.AsyncTask;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class FetchingNBA {
    MainActivity ma = null;
    String[] standard = new String[]{"OKC", "ATL", "BKN", "BOS", "CHA", "CHI", "CLE", "DAL", "DEN", "DET",
    "GSW", "HOU", "IND", "LAC", "LAL", "MEM", "MIA", "MIL", "MIN", "NOP", "NYK", "ORL", "PHI", "PHX", "POR"
    , "SAC", "SAS", "TOR", "UTA", "WAS"};
    List<String> standardList = Arrays.asList(standard);

    public void search(String name, MainActivity ma) {
        this.ma = ma;
        new AsyncNBASearch().execute(name);
    }

    private class AsyncNBASearch extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urls) {
            return getPlayerData(urls[0]);
        }
        protected void onPostExecute(String data) {
            ma.searchReady(data);
        }
    }

    public JsonArray getData4Player(String name) throws IOException{
        //Task2 URL
        String urlString = "https://evening-brushlands-07577.herokuapp.com/FetchNBA?name=" + name;
        //Task1 URL
        //String urlString = "https://agile-river-55233.herokuapp.com/FetchNBA?name=" + name;
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        InputStreamReader is = new InputStreamReader(connection.getInputStream(), "UTF-8");
        Gson gson = new Gson();
        JsonArray allData =  gson.fromJson(is, JsonArray.class).getAsJsonArray();
        is.close();
        return  allData;
    }

    public String getPlayerData(String name){
        name = name.toUpperCase();
        JsonArray players = null;
        try {
            if(!standardList.contains(name))return "Please key in correct abbreviation team name!";
            players = getData4Player(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Player Name: ");
        sb.append("\n");
        sb.append("\n");
        String firstName = "";
        String lastName = "";
        for(JsonElement each: players){
            firstName = each.getAsJsonObject().get("FirstName").getAsString();
            lastName = each.getAsJsonObject().get("LastName").getAsString();
            sb.append(firstName + ", " + lastName);
            sb.append("\n");
        }
        return sb.toString();
    }
}
