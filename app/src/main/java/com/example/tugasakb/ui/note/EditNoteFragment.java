package com.example.tugasakb.ui.note;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import java.util.HashMap;

public class EditNoteFragment extends Fragment {
    EditText judulEditText, kategoriEditText, isiEditText;
    Button backButton, simpanButton;
    String judul, kategori, isi;

    public EditNoteFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try (Helper dbHelper = new Helper(getContext())) {
            // button instance
            backButton = view.findViewById(R.id.backButton);
            simpanButton = view.findViewById(R.id.simpanButton);

            // ketika backButton di klik
            backButton.setOnClickListener(v -> {
                NavController navController = NavHostFragment.findNavController(EditNoteFragment.this);
                navController.popBackStack();
            });

            if (getArguments() != null) {
                int noteId = getArguments().getInt("noteId");

                HashMap<String, String> note = dbHelper.getNoteById(noteId);

                judulEditText = view.findViewById(R.id.judul);
                judulEditText.setText(note.get("judul"));

                kategoriEditText = view.findViewById(R.id.kategori);
                kategoriEditText.setText(note.get("kategori"));

                isiEditText = view.findViewById(R.id.isi);
                isiEditText.setText(note.get("isi"));

                // ketika simpanButton di klik
                simpanButton.setOnClickListener(view1 -> {
                    judul = judulEditText.getText().toString();
                    kategori = kategoriEditText.getText().toString();
                    isi = isiEditText.getText().toString();

                    dbHelper.update(noteId, judul, kategori, isi);

                    // mengarahkan ke halaman notes
                    NavController navController = NavHostFragment.findNavController(EditNoteFragment.this);
                    navController.popBackStack();
                });
            }
        } catch (Exception e) {
            Log.d("err", e.toString());
            Toast.makeText(getContext(), "Terjadi error", Toast.LENGTH_SHORT).show();
        }
    }

}