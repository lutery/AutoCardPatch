package one.chchy.autocardpatch.demo.net;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import one.chchy.autocardpatch.demo.net.ihttp.INewhr;
import one.chchy.autocardpatch.demo.net.interceptor.AddCookiesInterceptor;
import one.chchy.autocardpatch.demo.net.interceptor.ReceivedCookiesInterceptor;
import one.chchy.autocardpatch.security.RSAEncrypt;
import one.chchy.autocardpatch.vo.DayResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.lang.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                            Call loginCall = iNewhr.get(RSAEncrypt.encrypt(RSAEncrypt.encode64Str("chenghui", "0147258369qQ+", randSeed, "1")));

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

                        try {
                            Call getSelfDayResult = iNewhr.getDayResult();

                            Response selfDayResult = getSelfDayResult.execute();

                            String selfDayResultSource = ((ResponseBody)selfDayResult.body()).string();
                            System.out.println(selfDayResultSource);

                            Document selfDayDocument = Jsoup.parse(selfDayResultSource);

                            Elements selfDayElement = selfDayDocument.select("input");
                            Optional<Element> eventarget = Optional.ofNullable(selfDayDocument.getElementById("__EVENTTARGET"));
                            Optional<Element> eventargument = Optional.ofNullable(selfDayDocument.getElementById("__EVENTARGUMENT"));
                            Optional<Element> viewstate = Optional.ofNullable(selfDayDocument.getElementById("__VIEWSTATE"));
                            Optional<Element> stategenerator = Optional.ofNullable(selfDayDocument.getElementById("__VIEWSTATEGENERATOR"));
                            Optional<Element> dateQuery = Optional.ofNullable(selfDayDocument.getElementById("DateQuery_rb1"));
                            Optional<Element> txtYearMonth = Optional.ofNullable(selfDayDocument.getElementById("DateQuery_txtYearMonth"));
                            Optional<Element> txtDate1 = Optional.ofNullable(selfDayDocument.getElementById("DateQuery_txtDate1"));
                            Optional<Element> btnLoadData = Optional.ofNullable(selfDayDocument.getElementById("btnLoadData"));
                            Optional<Element> txtDate2 = Optional.ofNullable(selfDayDocument.getElementById("DateQuery_txtDate2"));
                            Optional<Element> rad2 = Optional.ofNullable(selfDayDocument.getElementById("rad2"));
                            Optional<Element> txtRecord = Optional.ofNullable(selfDayDocument.getElementById("grdData_tcChangePage_txtRecord"));
                            Optional<Element> hid = Optional.ofNullable(selfDayDocument.getElementById("hid"));

                            Call postSelfDayResult = iNewhr.postDayResult(
                                    eventarget.map(t -> t.attr("value")).orElse(""),
                                    eventargument.map(t -> t.attr("value")).orElse(""),
                                    viewstate.map(t -> t.attr("value")).orElse(""),
                                    stategenerator.map(t -> t.attr("value")).orElse(""),
                                    dateQuery.map(t -> t.attr("value")).orElse(""),
                                    txtYearMonth.map(t -> t.attr("value")).orElse(""),
                                    txtDate1.map(t -> t.attr("value")).orElse(""),
                                    txtDate2.map(t -> t.attr("value")).orElse(""),
                                    /*btnLoadData.map(t -> t.attr("value")).orElse("")*/"33",
                                    /*btnLoadData.map(t -> t.attr("value")).orElse("")*/"8",
                                    txtRecord.map(t -> t.attr("value")).orElse(""),
                                    rad2.map(t -> t.attr("value")).orElse(""),
                                    hid.map(t -> t.attr("value")).orElse("")
                            );

                            Response postResponse  = postSelfDayResult.execute();

                            String selfDayResultStr = ((ResponseBody)postResponse.body()).string();
                            System.out.println(selfDayResultStr);

//                            File tmpFile = new File("F:/Test/selfDayResult.html");
//                            if (tmpFile.exists()){
//                                tmpFile.delete();
//                            }
//
//                            tmpFile.createNewFile();
//
//                            OutputStream fileOutpu = new FileOutputStream(tmpFile);
//
//                            fileOutpu.write(selfDayResultStr.getBytes("utf-8"));
//                            fileOutpu.flush();
//                            fileOutpu.close();

                            Document dayResultDoc = Jsoup.parse(selfDayResultStr);
                            Elements tdItems = dayResultDoc.getElementsByClass("TdItem");

                            List<DayResult> dayResults = new ArrayList<>();
                            for (Element tdItem : tdItems){
                                Element onWorkTime = tdItem.child(4);
                                Element offWorkTime = tdItem.child(6);
                                Element result = tdItem.child(11);
                                Element date = tdItem.child(1);

                                if (result.data().equals("正常")){
                                    continue;
                                }

                                DayResult dayResult = new DayResult();
                                if (onWorkTime.data() == null || onWorkTime.data().equals("")){
                                    dayResult.setOnWork(false);
                                }

                                if (offWorkTime.data() == null || offWorkTime.data().equals("")){
                                    dayResult.setOffWork(false);
                                }

                                try {
                                    dayResult.setWorkDate(new SimpleDateFormat("yyyy-MM-dd").parse(date.data()));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }

                            dayResults.forEach(dayResult -> {
                                System.out.println(dayResult.toString());
                            });

                        } catch (IOException e) {
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
