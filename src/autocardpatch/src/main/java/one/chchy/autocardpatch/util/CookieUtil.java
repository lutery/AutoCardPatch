package one.chchy.autocardpatch.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class CookieUtil {

    private static Map<String, HashSet<String>> cookies = new HashMap<>();

    public static void setCookies(String key, HashSet<String> values){
        if (!cookies.containsKey("cookie")){
            cookies.put("cookie", values);
        }
        else{
//            HashSet<String> cookieValue = cookies.get("cookie");
//
//            for (String value : values){
//                for (String cookie : cookieValue){
//                    if (value.equals(cookie)){
//                        cookieValue.remove(cookie);
//                        break;
//                    }
//                }
//            }

            cookies.get("cookie").addAll(values);
        }
    }

    public static HashSet<String> getCookies(String key){
        return cookies.get(key);
    }

    public static boolean contains(String key){
        return cookies.containsKey(key);
    }
}
