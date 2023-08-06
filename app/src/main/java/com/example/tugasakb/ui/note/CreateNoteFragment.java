package com.example.tugasakb.ui.note;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tugasakb.R;
import com.example.tugasakb.helper.Helper;

public class CreateNoteFragment extends Fragment {

    //    Nim   : 10120155
    //    Nama  : Kautsar Teguh Dwi Putra
    //    Kelas : IF-4

    EditText judulEditText, kategoriEditText, isiEditText;
    Button backButton, simpanButton;

    public CreateNoteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_note, container, false);

        // edit text instance
        judulEditText = view.findViewById(R.id.judul);
        kategoriEditText = view.findViewById(R.id.kategori);
        isiEditText = view.findViewById(R.id.isi);

        // button instance
        backButton = view.findViewById(R.id.backButton);
        simpanButton = view.findViewById(R.id.simpanButton);

        // ketika backButton di klik
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = NavHostFragment.findNavController(CreateNoteFragment.this);
                navController.popBackStack();
            }
        });

        // ketika simpanButton di klik
        simpanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try (Helper dbHelper = new Helper(getContext())) {
                    String judul = judulEditText.getText().toString();
                    String kategori = kategoriEditText.getText().toString();
                    String isi = isiEditText.getText().toString();

                    dbHelper.insert(judul, kategori, isi);

                    // mengarahkan ke halaman notes
                    NavController navController = NavHostFragment.findNavController(CreateNoteFragment.this);
                    navController.popBackStack();
                } catch (Exception e) {
                    Log.d("err", e.toString());
                    Toast.makeText(getContext(), "Terjadi error", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }
}