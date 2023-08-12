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
import com.example.tugasakb.model.Note;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EditNoteFragment extends Fragment {

    //    Nim   : 10120155
    //    Nama  : Kautsar Teguh Dwi Putra
    //    Kelas : IF-4

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

        // button instance
        backButton = view.findViewById(R.id.backButton);
        simpanButton = view.findViewById(R.id.simpanButton);

        // when backButton is clicked
        backButton.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(EditNoteFragment.this);
            navController.popBackStack();
        });

        if (getArguments() != null) {
            String noteId = getArguments().getString("noteId");

            DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://tugas-akb-378e0-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("notes").child(noteId);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Note note = snapshot.getValue(Note.class);

                    judulEditText = view.findViewById(R.id.judul);
                    judulEditText.setText(note.getJudul());

                    kategoriEditText = view.findViewById(R.id.kategori);
                    kategoriEditText.setText(note.getKategori());

                    isiEditText = view.findViewById(R.id.isi);
                    isiEditText.setText(note.getIsi());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("err", error.toString());
                    Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                }
            });

            // when simpanButton is clicked
            simpanButton.setOnClickListener(view1 -> {
                String judul = judulEditText.getText().toString();
                String kategori = kategoriEditText.getText().toString();
                String isi = isiEditText.getText().toString();

                Note note = new Note(noteId, judul, kategori, isi);
                databaseReference.setValue(note);

                // navigate to the notes page
                NavController navController = NavHostFragment.findNavController(EditNoteFragment.this);
                navController.popBackStack();
            });
        }
    }


}