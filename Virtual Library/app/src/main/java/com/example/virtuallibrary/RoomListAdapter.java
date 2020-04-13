package com.example.virtuallibrary;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.ViewHolder> {

    private List<RoomInfo> roomList;
    private OnRecyclerViewClickListener listener;


    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView roomName;
        TextView number;
        TextView subject;
        TextView task;
        View itemView;

        public ViewHolder(View view){
            super(view);
            itemView = view;
            roomName = view.findViewById(R.id.room_list_name);
            number = view.findViewById(R.id.room_list_number);
            subject = view.findViewById(R.id.room_subject);
            task = view.findViewById(R.id.room_task);
        }

    }

    public void setItemClickListener(OnRecyclerViewClickListener itemClickListener) {
        listener = itemClickListener;
    }

    public RoomListAdapter(List<RoomInfo> roomList){
        this.roomList = roomList;
    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.room_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                Toast.makeText(v.getContext(), "number " + Integer.toString(position), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(v.getContext(), RoomspaceActivity.class);
//                parent.getContext().startActivities(new Intent[]{intent});
//            }
//        });
        if (listener != null){
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    listener.onItemClickListener(v);
                }
            });
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RoomInfo room = roomList.get(position);
        holder.roomName.setText("Room: " + room.getRoomName());
        holder.number.setText(room.getNumberOfUsers() + " people in room");
        holder.subject.setText(room.getSubject());
        holder.task.setText(room.getTask());
    }


    @Override
    public int getItemCount() {
        return roomList.size();
    }
}
