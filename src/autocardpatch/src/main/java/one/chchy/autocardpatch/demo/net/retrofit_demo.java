package one.chchy.autocardpatch.demo.net;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import one.chchy.autocardpatch.demo.net.ihttp.INewhr;
import one.chchy.autocardpatch.demo.net.interceptor.AddCookiesInterceptor;
import one.chchy.autocardpatch.demo.net.interceptor.ReceivedCookiesInterceptor;
import one.chchy.autocardpatch.security.RSAEncrypt;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;

public class retrofit_demo {

    public static void main(String[] args){
        System.out.println("hello retrofit");

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new AddCookiesInterceptor()) //这部分
                .addInterceptor(new ReceivedCookiesInterceptor()) //这部分
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://newhr.itep.com.cn").client(client).build();

        INewhr iNewhr = retrofit.create(INewhr.class);

        Call call = iNewhr.get();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String loginSource = "";
                try {
                    loginSource = response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.print(loginSource);

                Document document = Jsoup.parse(loginSource);

                for (Element element : document.getElementsByTag("script")){
                    if (element.toString().contains("doLogin")){
                        String doLogin = element.data().toString();
                        int startIndex = doLogin.indexOf("doLogin") + 8;
                        int endIndex = doLogin.indexOf(");", startIndex) - 1;

                        String[] params = doLogin.substring(startIndex, endIndex).split(",");

                        String rsaE = params[0].replace("\"", "").trim();
                        String rsaM = params[1].replace("\"", "").trim();
                        String randSeed = params[2].replace("\"", "").trim();
                        String path = params[3].replace("\"", "").trim();

                        RSAEncrypt.rsaE = rsaE;
                        RSAEncrypt.rsaM = rsaM;
                        RSAEncrypt.randSeed = randSeed;

                        System.out.println(rsaE);
                        System.out.println(rsaM);
                        System.out.println(randSeed);

                        try {
                            Call loginCall = iNewhr.get(RSAEncrypt.encrypt(RSAEncrypt.encode64Str("chenghui", "0147258369qQ_", randSeed, "1")));

                            Response loginResponse = loginCall.execute();

                            System.out.println(((ResponseBody)loginResponse.body()).string());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            Call loginCall = iNewhr.getDefault("ESS");

                            Response loginResponse = loginCall.execute();

                            System.out.println(((ResponseBody)loginResponse.body()).string());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        break;
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                System.out.println(throwable.toString());
            }
        });
    }
}
