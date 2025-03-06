package fpoly.longlt.assignment.service;

import static fpoly.longlt.assignment.service.APIService.BASE_URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HTTPRequest {
    APIService apiservice;
    public HTTPRequest(){
        apiservice = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(APIService.class);
    }
    public APIService getApiservice() {
        return apiservice;
    }
}
