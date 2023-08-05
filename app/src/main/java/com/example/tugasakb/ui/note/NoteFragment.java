package com.example.tugasakb.ui.note;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;


public class NoteFragment extends Fragment {

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
        dbHelper = new Helper(getContext());


        ArrayList<HashMap<String, String>> notes = dbHelper.getAll();

        if (notes.isEmpty()) {
            TextView noDataTextView = binding.noDataTextview;
            noDataTextView.setVisibility(View.VISIBLE);
        } else {
            NoteAdapter adapter = new NoteAdapter(getContext(), notes);
            registerForContextMenu(listView);
            listView.setAdapter(adapter);
        }

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
        int id;
        int position = info.position;

        HashMap<String, String> note = (HashMap<String, String>) listView.getItemAtPosition(position);
        switch (item.getItemId()) {
            case 1:
                // jika yang diklik adalah edit
                id = Integer.parseInt(note.get("id"));

                Bundle bundle = new Bundle();
                bundle.putInt("noteId", id);

                NavController navController = NavHostFragment.findNavController(NoteFragment.this);
                navController.navigate(R.id.action_noteFragment_to_editNoteFragment, bundle);
                break;
            case 2:
                // jika yang diklik adalah delete
                // dapatkan id note dan hapus note
                id = Integer.parseInt(note.get("id"));
                dbHelper.delete(id);

                // ambil ulang seluruh data dari db
                ArrayList<HashMap<String, String>> notesList = dbHelper.getAll();

                // update data ke listview
                NoteAdapter adapter = new NoteAdapter(getContext(), notesList);
                listView.setAdapter(adapter);
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