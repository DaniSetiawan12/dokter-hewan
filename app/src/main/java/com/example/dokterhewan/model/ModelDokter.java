package com.example.dokterhewan.model;

public class ModelDokter {

    String namaDokter, nomorTelp, noIzin, jadwalLayanan, _id;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNamaDokter() {
        return namaDokter;
    }

    public void setNamaDokter(String namaDokter) {
        this.namaDokter = namaDokter;
    }

    public String getNomorTelp() {
        return nomorTelp;
    }

    public void setNomorTelp(String nomorTelp) {
        this.nomorTelp = nomorTelp;
    }

    public String getNoIzin() {
        return noIzin;
    }

    public void setNoIzin(String noIzin) {
        this.noIzin = noIzin;
    }

    public String getJadwalLayanan() {
        return jadwalLayanan;
    }

    public void setJadwalLayanan(String jadwalLayanan) {
        this.jadwalLayanan = jadwalLayanan;
    }
}
