age com.example.YunKeTong.http;


import android.support.annotation.NonNull;
import android.util.Log;

import com.example.YunKeTong.httpBean.CourseInfoBean;
import com.example.YunKeTong.httpBean.CoursesListBean;
import com.example.YunKeTong.httpBean.DefaultResultBean;
import com.example.YunKeTong.httpBean.DictInfoListBean;
import com.example.YunKeTong.httpBean.LoginBean;
import com.example.YunKeTong.httpBean.RegisterBean;
import com.example.YunKeTong.httpBean.SearchListBean;
import com.example.YunKeTong.httpBean.StudentsListBean;
import com.example.YunKeTong.httpBean.SystemBean;
import com.example.YunKeTong.httpBean.UploadAvatarBean;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class HttpUtil extends HttpBase {
    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
    private static Retrofit mRetrofit;

    /**
     * 初始化Retrofit
     *
     * @return
     */
    @NonNull
    private static Retrofit init() {
        if (mRetrofit != null) return mRetrofit;

        Retrofit retrofit = new Retrofit.Builder()
          .baseUrl("http://www.exm.com")
          .addConverterFactory(GsonConverterFactory.create())
          .client(new OkHttpClient())
          .build();

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20L, TimeUnit.SECONDS)
                .readTimeout(15L, TimeUnit.SECONDS)
                .writeTimeout(15L, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(HTTP_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return mRetrofit;
    }


	public interface RestApi {
    @GET("/search.json")
    Call<List<SearchResult>> search(
      @Query("key") String key
       );

      //可以请求
      @GET("/something.json")
      Call<SomeThing> dosomething(
          @Query("params") long params
          
          
       );

	}


    //发送请求并处理结果
	RestApi restApi = retrofit.create(RestApi.class);
 	Call<List<SearchResult>> searchResultsCall = resetApi.search("retrofit");
 	//Response<List<SearchResult> searchResults = searchResultsCall.execute();  同步方法
 	searchResultsCall.enqueue(new Callback<List<SearchResult>>() {
        @Override
        public void onResponse(Response<List<SearchResult>> response, Retrofit retrofit) {
          content.setText(response.body().toString());
        }

        @Override
        public void onFailure(Throwable t) {
          content.setText("error");
        }
      });
