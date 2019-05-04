package in.imagineer.nwrq;

import org.json.JSONObject;

public class Utils {
    public static String prettifyJson(String str) {
        String json;
        try {
            JSONObject jsonObject = new JSONObject(str);
            json = jsonObject.toString(4);
        } catch (Exception e) {
            json = str;
        }
        return json;
    }
}
