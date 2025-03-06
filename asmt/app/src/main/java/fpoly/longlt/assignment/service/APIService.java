package fpoly.longlt.assignment.service;

import java.util.List;

import fpoly.longlt.assignment.model.Cart;
import fpoly.longlt.assignment.model.Order;
import fpoly.longlt.assignment.model.ResponData;
import fpoly.longlt.assignment.model.Fruits;
import fpoly.longlt.assignment.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {
    public static String BASE_URL = "http://10.24.4.169:3000/";
    @POST("asmtapi/add_fruit")
    Call<ResponData<Fruits>> addFruit(@Body Fruits fruits);
    @POST("asmtapi/add_user")
    Call<ResponData<User>> createUser(@Body User user);
    @POST("asmtapi/add-to-cart")
    Call<ResponData<Cart>> addToCart(@Body Cart cart);
    @POST("order/createOrder")
    Call<ResponData<Order>> createOrder(@Body Order order);
    @PUT("asmtapi/updateuser/{id}")
    Call<ResponData<User>> updateUser(@Path("id") String id, @Body User user);
    @PUT("updatestatus/update-status/{id}")
    Call<ResponData<Fruits>> updateStatus(@Path("id") String id, @Body Fruits fruits);
    @GET("order/getAllOrder")
    Call<ResponData<List<Order>>> getAllOrder();
    @GET("asmtapi/cart/{id}")
    Call<ResponData<List<Cart>>> getCart(@Path("id") String id);
    @GET("asmtapi/getAllFruit")
    Call<ResponData<List<Fruits>>> getAllFruit();
    @GET("asmtapi/getAllUser")
    Call<ResponData<List<User>>> getAllUser();
    @GET("asmtapi/geIidUserByEmailAndPass")
    Call<ResponData<String>> getIdUserByEmailAndPass(@Query("email") String email, @Query("password") String password);
    @GET("asmtapi/getFruitDetail/{id}")
    Call<ResponData<Fruits>> getFruitDetail(@Path("id") String id);
    @GET("asmtapi/getInfomationUser/{id}")
    Call<ResponData<User>> getInfomationUser(@Path("id") String id);
    @GET("asmtapi/getIdFruitBuyName")
    Call<ResponData<Fruits>> getIdFruit(@Query("namefruit") String namefruit);
    @GET("order/getAllOrderByUser/{userId}")
    Call<ResponData<List<Order>>> getAllOrderByUser(@Path("userId") String userId);
}
