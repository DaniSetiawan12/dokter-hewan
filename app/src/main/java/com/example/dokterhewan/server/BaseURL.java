package com.example.dokterhewan.server;

public class BaseURL {

//    public static String baseUrl = "http://192.168.18.146:5000/";
//    public static String baseUrl = "http://172.31.0.98:5000/";
//    public static String baseUrl = "http://192.168.43.99:5000/";
    public static String baseUrl = "http://192.168.43.156:5000/";
//    public static String baseUrl   = "http://192.168.18.102:5000/";

    //users
    public static String registrasiUser     = baseUrl + "user/registrasi";
    public static String loginUser          = baseUrl + "user/login";
    public static String updateUser          = baseUrl + "user/ubahpetuser/";


    //dokwan
    public static String inputDokwan          = baseUrl + "dokwan/input";
    public static String getDataDokwan        = baseUrl + "dokwan/getdata/";
    public static String getDataDokwanFav        = baseUrl + "dokwan/getdatafav/";
    public static String udpdateData          = baseUrl + "dokwan/ubahpetdokwan/";
    public static String tambahGambar         = baseUrl + "dokwan/ubahgambar/";
    public static String updateDokwan         = baseUrl + "dokwan/ubah/";
    public static String getDataHistory         = baseUrl + "dokwan/getdatahistory/";
    public static String ubahJenisHewan         = baseUrl + "dokwan/ubahjenishewan/";
    public static String ubahPerawatanHewan     = baseUrl + "dokwan/ubahperawatanhewan/";
    public static String hapusJenisHewan        = baseUrl + "dokwan/hapusjenishewan/";
    public static String hapusDataPerawatan        = baseUrl + "dokwan/hapusdataperawatan/";
    public static String hapusData          = baseUrl + "dokwan/hapusdata/";

    //dokter
    public static String inputDokter          = baseUrl + "dokter/input";
    public static String getDataDokter        = baseUrl + "dokter/getdata/";
    public static String updateDokter         = baseUrl + "dokter/ubahpetdokter/";
    public static String hapusDokter          = baseUrl + "dokter/hapusdokter/";
    public static String updatePetDataShhop   = baseUrl + "dokter/ubahpetshop/";
}