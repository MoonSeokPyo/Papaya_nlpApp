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

    private final String apiKey = "1aecc8adda8186c6f0963b2994520212";
    private final OkHttpClient client = new OkHttpClient();

    public Location getCoordinates(String address) throws Exception {			//api를 통한 주소를 통해 좌표값 반환
        String url = "https://dapi.kakao.com/v2/local/search/address.json?query=" 
                      + address.replace(" ", "%20");

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "KakaoAK " + apiKey) 
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new Exception("Failed to get response from Kakao API");
            }

            String jsonResponse = response.body().string();
            JSONObject json = new JSONObject(jsonResponse);
            JSONArray documents = json.getJSONArray("documents");

            if (documents.length() > 0) {
                JSONObject location = documents.getJSONObject(0).getJSONObject("address");

                double latitude = location.getDouble("y"); 
                double longitude = location.getDouble("x"); 
                return new Location(address, latitude, longitude);
            } else {
                throw new Exception("No results found for the given address");
            }
        }
    }
}
