package com.example.virtuallibrary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RoomSpaceFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private ArrayList<User> userArrayList = new ArrayList<User>();
    private String users;

    @Override
    public View onCreateView(
            LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_roomspace, container, false);

        TextView subject_text = view.findViewById(R.id.subject_description);

        TextView task_text = view.findViewById(R.id.task_description);



        recyclerView = (RecyclerView) view.findViewById(R.id.user_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new UserAdapter(getContext(), this.userArrayList);

        //receive data
        Bundle bundle = getArguments();
        String subject = bundle.getString("subject");
        String task = bundle.getString("task");
        users = bundle.getString("users");
        String currentUser = bundle.getString("currentUser");
        User newUser = new User(currentUser);
        this.userArrayList.add(newUser);
        subject_text.setText(subject);
        task_text.setText(task);
        createListData();

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        return view;
    }

    private void createListData() {

        try{
            JSONArray user_list = new JSONArray(users);
            if (user_list.length() > 0){
                for (int i=0; i < user_list.length(); i++){
                    String user_name = user_list.get(i).toString();
                    Log.d("user", user_name);
                    User user = new User(user_name);
                    this.userArrayList.add(user);
                }
            }
        }catch (Exception e){
            User user = new User("No users");
            this.userArrayList.add(user);
        }

//        User user = new User("Evelyn");
//        this.userArrayList.add(user);
//        this.userArrayList.add(user);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(RoomSpaceFragment.this, ChatActivity.class);
//                NavHostFragment.findNavController(RoomSpaceFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                startActivity(new Intent(getActivity(), ChatActivity.class));
            }
        });
    }
}
