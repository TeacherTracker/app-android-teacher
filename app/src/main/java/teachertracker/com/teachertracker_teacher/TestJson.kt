package teachertracker.com.teachertracker_teacher

import org.json.JSONObject

/**
 * Created by eka on 2017. 12. 16..
 */
object TestJson {
    var json = JSONObject("{\n" +
            "    \"weather\": {\n" +
            "        \"minutely\": [\n" +
            "            {\n" +
            "                \"station\": {\n" +
            "                    \"longitude\": \"126.96579\",\n" +
            "                    \"latitude\": \"37.57141\",\n" +
            "                    \"name\": \"서울\",\n" +
            "                    \"id\": \"108\",\n" +
            "                    \"type\": \"KMA\"\n" +
            "                },\n" +
            "                \"wind\": {\n" +
            "                    \"wdir\": \"289.90\",\n" +
            "                    \"wspd\": \"4.70\"\n" +
            "                },\n" +
            "                \"precipitation\": {\n" +
            "                    \"sinceOntime\": \"0.00\",\n" +
            "                    \"type\": \"0\"\n" +
            "                },\n" +
            "                \"sky\": {\n" +
            "                    \"code\": \"SKY_A01\",\n" +
            "                    \"name\": \"맑음\"\n" +
            "                },\n" +
            "                \"rain\": {\n" +
            "                    \"sinceOntime\": \"0.00\",\n" +
            "                    \"sinceMidnight\": \"0.00\",\n" +
            "                    \"last10min\": \"0.00\",\n" +
            "                    \"last15min\": \"0.00\",\n" +
            "                    \"last30min\": \"0.00\",\n" +
            "                    \"last1hour\": \"0.00\",\n" +
            "                    \"last6hour\": \"0.00\",\n" +
            "                    \"last12hour\": \"0.00\",\n" +
            "                    \"last24hour\": \"0.00\"\n" +
            "                },\n" +
            "                \"temperature\": {\n" +
            "                    \"tc\": \"-3.30\",\n" +
            "                    \"tmax\": \"-2.00\",\n" +
            "                    \"tmin\": \"-7.00\"\n" +
            "                },\n" +
            "                \"humidity\": \"23.50\",\n" +
            "                \"pressure\": {\n" +
            "                    \"surface\": \"1015.10\",\n" +
            "                    \"seaLevel\": \"1026.10\"\n" +
            "                },\n" +
            "                \"lightning\": \"0\",\n" +
            "                \"timeObservation\": \"2017-12-16 15:55:00\"\n" +
            "            }\n" +
            "        ]\n" +
            "    },\n" +
            "    \"common\": {\n" +
            "        \"alertYn\": \"Y\",\n" +
            "        \"stormYn\": \"N\"\n" +
            "    },\n" +
            "    \"result\": {\n" +
            "        \"code\": 9200,\n" +
            "        \"requestUrl\": \"/weather/current/minutely?stnid=108&version=1\",\n" +
            "        \"message\": \"성공\"\n" +
            "    }\n" +
            "}")
}