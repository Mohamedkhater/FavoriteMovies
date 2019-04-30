package Utils;

import android.net.Network;
import android.net.Uri;
import android.util.Log;

import com.example.lifecycle.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public  class NetworkUtils {
    public static final String TAG = NetworkUtils.class.getSimpleName();
    private static String sortby = "sort_by";
    private static String sortby_value = "popularity.desc";
    private static String api_key = "api_key";
    private static final String BASEURL = "api.themoviedb.org/3/discover/movie?";
    private static final String api_key_value = "51d850fe504b9b9ebd6df40d48d30cf4";

    public String  fetchData(String url) throws MalformedURLException {
        URL movies_url = new URL(url);
        HttpURLConnection connection;

        try {
            connection = (HttpURLConnection) movies_url.openConnection();
            InputStream inputSteam = connection.getInputStream();
            Scanner s = new Scanner(inputSteam);
            s.useDelimiter("//A");
            if(s.hasNext()){

                return s.next();
            }
           // String jsondata=s.toString();

            return null;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
    public ArrayList<Movie> parseJSON(String json){
        JSONObject mainObject;
        try {
            mainObject= new JSONObject(json);

            ArrayList<Movie>movies= new ArrayList<Movie>();
            JSONArray results= mainObject.getJSONArray("results");

            for(int i=0;i<results.length();i++){
                Movie movie= new Movie();

                JSONObject movie_item=results.getJSONObject(i);

                movie.setId(movie_item.getString("id"));

                movie.setAdult(movie_item.getBoolean("adult"));
                movie.setVideo(movie_item.getBoolean("video"));


                movie.setOriginal_title(movie_item.getString("original_title"));

                movie.setVote_average(movie_item.getDouble("vote_average"));
                movie.setPopularity(movie_item.getDouble("popularity"));
                //Log.d(NetworkUtils.class.getSimpleName(),""+movie.getPopularity());

                movie.setTitle(movie_item.getString("title"));

                movie.setBackdrop_path(movie_item.getString("backdrop_path"));
                movie.setPoster_path(movie_item.getString("poster_path"));


                movies.add(movie);




            }
            return movies;



        }
        catch(Exception e){
            e.printStackTrace();
            Log.d(TAG,"can't return movie arraylist");
            return null;
        }


    }


    URL makeURLFromString(String url_string) {
        Uri uri = Uri.parse(url_string).buildUpon().appendQueryParameter(sortby, sortby_value).appendQueryParameter(api_key, api_key_value).build();
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;

    }
}