package com.example.mogakko.global.dbInit;

import com.example.mogakko.domain.values.domain.Location;
import com.example.mogakko.domain.values.dto.LocationDTO;
import com.example.mogakko.domain.values.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

@Component
@RequiredArgsConstructor
public class LocationDbInit {

    @Value("${seoulApiKey}")
    private String seoulApiKey;
    private final LocationService locationService;

//    @PostConstruct
    private void insertSubwayStationsIntoDb() {
        int cnt = 767;  // 나중에 api에 지하철역이 추가/삭제되면 고쳐줘야됨.
        try {
            URL url = new URL("http://openapi.seoul.go.kr:8088/" + seoulApiKey + "/json/SearchInfoBySubwayNameService/1/" + cnt);
            BufferedReader bf;
            bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String resString = bf.readLine();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(resString);

            JSONObject jsonObject1 = (JSONObject) jsonObject.get("SearchInfoBySubwayNameService");
            JSONArray stationInfoArray = (JSONArray) jsonObject1.get("row");

            for (int i = 0; i < stationInfoArray.size(); i++) {
                JSONObject bookObject = (JSONObject) stationInfoArray.get(i);

                String stationName = (String) bookObject.get("STATION_NM");
                String lineNumber = (String) bookObject.get("LINE_NUM");

                Location location = new Location();
                location.setStationName(stationName);
                location.setLineNumber(lineNumber);

                locationService.saveLocation(new LocationDTO(location));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
