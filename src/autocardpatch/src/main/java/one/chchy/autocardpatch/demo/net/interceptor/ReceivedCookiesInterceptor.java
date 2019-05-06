package one.chchy.autocardpatch.demo.net.interceptor;

import okhttp3.Cookie;
import okhttp3.Interceptor;
import okhttp3.Response;
import one.chchy.autocardpatch.util.CookieUtil;

import java.io.IOException;
import java.util.HashSet;

public class ReceivedCookiesInterceptor  implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();

            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }

//            SharedPreferences.Editor config = MyApplication.getContext().getSharedPreferences("config", MyApplication.getContext().MODE_PRIVATE)
//                    .edit();
//            config.putStringSet("cookie", cookies);
//            config.commit();

//            if (CookieUtil.cookies.containsKey("cookie")){
//                CookieUtil.cookies.get("cookie").addAll(cookies);
//            }
//            else {
                CookieUtil.setCookies("cookie", cookies);
//            }
        }

        return originalResponse;
    }
}
