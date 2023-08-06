package com.example.tugasakb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tugasakb.R;

import java.util.ArrayList;
import java.util.HashMap;

public class NoteAdapter extends BaseAdapter {

    //    Nim   : 10120155
    //    Nama  : Kautsar Teguh Dwi Putra
    //    Kelas : IF-4

    private final Context context;
    private ArrayList<HashMap<String, String>> data;

    public NoteAdapter(Context context, ArrayList<HashMap<String, String>> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_note, parent, false);
        }

        HashMap<String, String> note = data.get(position);

        TextView judulTextView = convertView.findViewById(R.id.judul);
        TextView tanggalTextView = convertView.findViewById(R.id.tanggal);
        TextView kategoriTextView = convertView.findViewById(R.id.kategori);
        TextView isiTextView = convertView.findViewById(R.id.isi);

        judulTextView.setText(note.get("judul"));
        tanggalTextView.setText(note.get("tanggal"));
        kategoriTextView.setText(note.get("kategori"));
        isiTextView.setText(note.get("isi"));

        return convertView;
    }
}

