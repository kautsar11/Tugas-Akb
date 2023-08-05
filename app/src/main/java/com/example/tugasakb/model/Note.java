package com.example.tugasakb.model;

public class Note {
    private String id;
    private String judul;
    private String tanggal;
    private String kategori;
    private String isi;

    public Note() {
    }

    public Note(String id, String judul, String tanggal, String kategori, String isi) {
        this.id = id;
        this.judul = judul;
        this.tanggal = tanggal;
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

    public String getTanggal() {
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

    // untuk debugging
    @Override
    public String toString() {
        return "ID: " + id + "\n"
                + "Judul: " + judul + "\n"
                + "Tanggal: " + tanggal + "\n"
                + "Kategori: " + kategori + "\n"
                + "Isi: " + isi;
    }
}
