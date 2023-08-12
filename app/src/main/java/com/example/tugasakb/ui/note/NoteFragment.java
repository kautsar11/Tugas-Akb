package com.example.tugasakb.ui.note;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.tugasakb.R;
import com.example.tugasakb.adapter.NoteAdapter;
import com.example.tugasakb.databinding.FragmentNoteBinding;
import com.example.tugasakb.helper.Helper;
import com.example.tugasakb.model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class NoteFragment extends Fragment {

    //    Nim   : 10120155
    //    Nama  : Kautsar Teguh Dwi Putra
    //    Kelas : IF-4

    private FragmentNoteBinding binding;
    FloatingActionButton create_note;
    ListView listView;
    Helper dbHelper;

    public NoteFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        NoteViewModel dashboardViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        binding = FragmentNoteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listView = binding.notesList;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://tugas-akb-378e0-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("notes");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<HashMap<String, String>> notes = new ArrayList<>();
                for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {
                    Note note = noteSnapshot.getValue(Note.class);
                    HashMap<String, String> noteMap = new HashMap<>();
                    noteMap.put("id", note.getId());
                    noteMap.put("judul", note.getJudul());
                    noteMap.put("tanggal", note.getTanggal().toString());
                    noteMap.put("kategori", note.getKategori());
                    noteMap.put("isi", note.getIsi());
                    notes.add(noteMap);
                }

                NoteAdapter adapter = new NoteAdapter(getContext(), notes);
                registerForContextMenu(listView);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Terjadi error pada database silahkan buka ulang aplikasi", Toast.LENGTH_SHORT).show();
            }
        });

        // jika tombol tambah di klik
        create_note = binding.tambahNote;
        create_note.setOnClickListener(view -> {
            NavController navController = NavHostFragment.findNavController(NoteFragment.this);
            navController.navigate(R.id.action_noteFragment_to_createNoteFragment);
        });

        return root;
    }


    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (v.getId() == R.id.notes_list) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle("Pilih Aksi");
            menu.add(Menu.NONE, 1, 1, "Edit");
            menu.add(Menu.NONE, 2, 2, "Delete");
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;

        HashMap<String, String> note = (HashMap<String, String>) listView.getItemAtPosition(position);
        Log.d("id", "notes: " + note);
        String noteId = note.get("id");
        DatabaseReference noteRef = FirebaseDatabase.getInstance("https://tugas-akb-378e0-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("notes").child(noteId);


        switch (item.getItemId()) {
            case 1:
                // jika yang diklik adalah edit
                Bundle bundle = new Bundle();
                bundle.putString("noteId", noteId);

                NavController navController = NavHostFragment.findNavController(NoteFragment.this);
                navController.navigate(R.id.action_noteFragment_to_editNoteFragment, bundle);
                break;
            case 2:
                // jika yang diklik adalah delete
                noteRef.removeValue();
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}