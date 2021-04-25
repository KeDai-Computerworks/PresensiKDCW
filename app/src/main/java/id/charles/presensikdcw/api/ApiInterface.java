package id.charles.presensikdcw.api;

import java.util.List;

import id.charles.presensikdcw.model.ModeLogPresensi;
import id.charles.presensikdcw.model.ModelAgenda;
import id.charles.presensikdcw.model.ModelLogin;
import id.charles.presensikdcw.model.ModelPresensi;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("login")
    Call<ModelLogin> login(@Field("email") String email,
                           @Field("password") String pass);

    @GET("agendas")
    Call<List<ModelAgenda>> kegiatan();

    @FormUrlEncoded
    @POST("presensi")
    Call<ModelPresensi> absensi(@Field("agenda") int agenda,
                                @Field("catatan") String catatan,
                                @Field("created_by") int created);

    @GET("presensis")
    Call<List<ModelPresensi>> presensi();

    @GET("ceknoreg/{noreg}")
    Call<ModeLogPresensi> logAbsensi(@Path("noreg") String noReg);

    @FormUrlEncoded
    @POST("logpresensi")
    Call<ModeLogPresensi> logAbsensi(@Field("id_anggota") int idAnggota,
                                     @Field("id_presensi") int idAbsensi);

}
