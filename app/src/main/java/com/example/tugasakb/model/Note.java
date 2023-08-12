package com.example.tugasakb.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Note {

    //    Nim   : 10120155
    //    Nama  : Kautsar Teguh Dwi Putra
    //    Kelas : IF-4

    private String id;
    private String judul;
    private String tanggal;
    private String kategori;
    private String isi;

    public Note() {
    }
    public Note(String id,String judul, String kategori, String isi) {
        this.id = id;
        this.judul = judul;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        this.tanggal = dateFormat.format(new Date());
        this.kategori = kategori;
        this.isi = isi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public Object getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

}