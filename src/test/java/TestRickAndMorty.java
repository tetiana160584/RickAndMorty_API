import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import rest.ApiRequest;


public class TestRickAndMorty {
    static final String URL = "https://rickandmortyapi.com/api";
    static JSONObject episodes;

    @BeforeClass
    public static void bef() {
        JSONObject mainApi = new JSONObject(ApiRequest.getRest(URL));
        episodes = new JSONObject(ApiRequest.getRest(mainApi.getString("episodes")));
    }

    @Test
    public void test1() {
        System.out.println(episodes.toString());
    }

    @Test
    public void test2() {
        JSONObject info = episodes.getJSONObject("info");
        String nextUrl = info.optString("next"); //  получить значение строки "next" или " " (пустую строку), если оно равно null.
        while (!nextUrl.isEmpty()) { // проверка, что значение "nextUrl" не пустое
            JSONObject nextEpisodes = new JSONObject(ApiRequest.getRest(nextUrl));
            JSONArray results = nextEpisodes.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject episode = results.getJSONObject(i);
                String name = episode.getString("name");
                String airDate = episode.getString("air_date");
                System.out.println("\"name\": \"" + name + "\",");
                System.out.println("\"air_date\": \"" + airDate + "\",");
            }
            nextUrl = nextEpisodes.getJSONObject("info").optString("next"); // получаю значения "next" из объукта info.
        }
    }
}


