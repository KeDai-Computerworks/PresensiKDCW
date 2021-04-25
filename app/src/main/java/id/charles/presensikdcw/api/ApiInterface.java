package id.charles.presensikdcw.api;


import java.util.List;

import id.charles.presensikdcw.model.ModeLoglAbsensi;
import id.charles.presensikdcw.model.ModelAbsensi;
import id.charles.presensikdcw.model.ModelKegiatan;
import id.charles.presensikdcw.model.ModelLogin;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("agendas")
    Call<List<ModelKegiatan>> kegiatan();

    @GET("ceknoreg/{noreg}")
    Call<ModeLoglAbsensi> logAbsensi(@Path("noreg") String noReg);

    @FormUrlEncoded
    @POST("presensi")
    Call<ModelAbsensi> absensi(@Field("agenda") int agenda,
                               @Field("catatan") String catatan,
                               @Field("created_by") int created);

    @FormUrlEncoded
    @POST("logpresensi")
    Call<ModeLoglAbsensi> logAbsensi(@Field("id_anggota") int idAnggota,
                                  @Field("id_absensi") int idAbsensi);
    @FormUrlEncoded
    @POST("login")
    Call<ModelLogin> logAbsensi(@Field("email") String email,
                                @Field("password") String pass);

    @GET("presensis")
    Call<List<ModelKegiatan>> presensi();


}
