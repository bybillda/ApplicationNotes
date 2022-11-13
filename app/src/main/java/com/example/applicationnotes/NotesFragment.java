package com.example.applicationnotes;

import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotesFragment newInstance(String param1, String param2) {
        NotesFragment fragment = new NotesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_notes, container, false);

        TextView textView = rootView.findViewById(R.id.header);
        textView.setTypeface(Typeface.DEFAULT_BOLD);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(NOTE, currentPosition);
        super.onSaveInstanceState(outState);
    }

    private boolean isLandscape(){
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

    }

    private static final String NOTE = "City";
    private int currentPosition = 0;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (savedInstanceState != null){
            currentPosition = savedInstanceState.getInt(NOTE, 0);

        }

        initCitiesList(view);

        if (isLandscape()){
            showLandCoatOfArms(currentPosition);
        }
    }

    private void initCitiesList(View view) {
        LinearLayout linearLayout = (LinearLayout) view;
        String[] cities = getResources().getStringArray(R.array.notes);

        for (int i = 0; i < cities.length; i++){
            TextView textView = new TextView(getContext());
            textView.setText(cities[i]);
            textView.setTextSize(35);
            linearLayout.addView(textView);

            final int index = i;
            textView.setOnClickListener( v -> {
                showCoatOfArmsV2(index);
            });
        }

    }

    private void showCoatOfArmsV2(int index){
        if (isLandscape()) {
            showLandCoatOfArms(index);
        }
        else {
            showPortraitCoatOfArms(index);
        }
    }

    private void showPortraitCoatOfArms(int index){
        WhatToDoFragment coatOfArmsFragment = WhatToDoFragment.newInstance(index);

        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, coatOfArmsFragment)
                .addToBackStack("")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    private void showLandCoatOfArms(int index){
        WhatToDoFragment coatOfArmsFragment = WhatToDoFragment.newInstance(index);

        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.what_to_do_container, coatOfArmsFragment)
                .commit();
    }
}