package com.hangw.service;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.hangw.model.Location;

@Service
public class GeocodingService {

    private final String apiKey = "AIzaSyDQOAHhoen5B1R7h2pOaXCJQDPPyYTgHc0";
    private final OkHttpClient client = new OkHttpClient();

    public Location getCoordinates(String address) throws Exception {
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" 
                      + address.replace(" ", "%20") + "&key=" + apiKey;

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new Exception("Failed to get response from Google API");
            }

            String jsonResponse = response.body().string();
            JSONObject json = new JSONObject(jsonResponse);
            JSONArray results = json.getJSONArray("results");

            if (results.length() > 0) {
                JSONObject location = results.getJSONObject(0)
                                            .getJSONObject("geometry")
                                            .getJSONObject("location");

                double latitude = location.getDouble("lat");
                double longitude = location.getDouble("lng");
                return new Location(address, latitude, longitude);
            } else {
                throw new Exception("No results found for the given address");
            }
        }
    }
}
