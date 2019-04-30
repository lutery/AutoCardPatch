package one.chchy.autocardpatch.demo.net.ihttp;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface INewhr {

    @GET("/HR/login.aspx")
    Call<ResponseBody> get();

    @GET("/HR/login.aspx")
    Call<ResponseBody> get(@Query("ls")String passport);

    @GET("/HR/default.aspx")
    Call<ResponseBody> getDefault(@Query("Mode")String mode);
}
