package umn.ac.id.a33081_teofilusyohaneswowiling;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static umn.ac.id.a33081_teofilusyohaneswowiling.ListLaguActivity.songFiles;

public class AllSongFragment extends Fragment {

    RecyclerView recyclerView;
    MusicAdapter musicAdapter;


    public AllSongFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_song, container, false);
        recyclerView = view.findViewById(R.id.rvAllSong);
        recyclerView.setHasFixedSize(true);

        if (songFiles.size() >= 0)
        {
            musicAdapter = new MusicAdapter(getContext(), songFiles);
            recyclerView.setAdapter(musicAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        }
        return view;
    }
}