package one.chchy.autocardpatch.demo.net.interceptor;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import one.chchy.autocardpatch.util.CookieUtil;

import java.io.IOException;
import java.util.HashSet;

public class AddCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
//        HashSet<String> preferences = (HashSet) MyApplication.getContext().getSharedPreferences("config",
//                MyApplication.getContext().MODE_PRIVATE).getStringSet("cookie", null);
//        if (preferences != null) {
//            for (String cookie : preferences) {
//                builder.addHeader("Cookie", cookie);
//                Log.v("OkHttp", "Adding Header: " + cookie); // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
//            }
//        }

        if (CookieUtil.cookies.containsKey("cookie")) {
            for (String cookie : CookieUtil.cookies.get("cookie")) {
                builder.addHeader("Cookie", cookie);
            }
        }

        return chain.proceed(builder.build());
    }
}
