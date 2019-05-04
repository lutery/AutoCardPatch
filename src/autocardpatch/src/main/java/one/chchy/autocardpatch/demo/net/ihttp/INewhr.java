package one.chchy.autocardpatch.demo.net.ihttp;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface INewhr {

    @GET("/HR/login.aspx")
    Call<ResponseBody> get();

    @GET("/HR/login.aspx")
    Call<ResponseBody> get(@Query("ls")String passport);

    @GET("/HR/default.aspx")
    Call<ResponseBody> getDefault(@Query("Mode")String mode);

    @GET("/HR/Att/SelfDayResulft.aspx")
    Call<ResponseBody> getDayResult();

    @FormUrlEncoded
    @POST("/HR/Att/SelfDayResulft.aspx")
    Call<ResponseBody> postDayResult(@Field("__EVENTTARGET")String eventarget, @Field("__EVENTARGUMENT")String eventargument, @Field("__VIEWSTATE")String viewstate, @Field("__VIEWSTATEGENERATOR")String viewstategenerator, @Field("DateQuery$1")String dataQuery, @Field("DateQuery$txtYearMonth")String yearMonth, @Field("DateQuery$txtDate1")String date1, @Field("DateQuery$txtDate2")String txtDate2, @Field("btnLoadData.x")String btnLoadDataX, @Field("btnLoadData.y")String btnLoadDataY, @Field("2")String two, @Field("grdData$tcChangePage$txtRecord")String txtRecord, @Field("hid")String hid);
}
