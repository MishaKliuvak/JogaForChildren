package com.example.jogaforchildren.BottomContainers;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jogaforchildren.Const;
import com.example.jogaforchildren.Poses.Pose;
import com.example.jogaforchildren.Poses.RecyclerViewAdapter;
import com.example.jogaforchildren.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class ListFragment extends Fragment {

    private View v;
    private RecyclerView myRV;
    private List<Pose> poseList;
    FirebaseFirestore db;
    RecyclerViewAdapter recyclerViewAdapter;
    public ListFragment() {
    }
    Pose p;
    private static final String TAG = "ExpenseFragment";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_list, container, false);
        poseList = new ArrayList<>();
        myRV = v.findViewById(R.id.poses_rv);

        FirebaseFirestore docRef = FirebaseFirestore.getInstance();


        docRef.collection("poses").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot dc: queryDocumentSnapshots) {
                    p = dc.toObject(Pose.class);
                    Const.addItem(p);
                    poseList.add(p);
                }
                recyclerViewAdapter = new RecyclerViewAdapter(getContext(), poseList);
                myRV.setAdapter(recyclerViewAdapter);
            }
        });

        myRV.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
