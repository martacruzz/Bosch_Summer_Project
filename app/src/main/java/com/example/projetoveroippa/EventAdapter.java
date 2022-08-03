package com.example.projetoveroippa;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetoveroippa.databinding.EventItemBinding;
import com.example.projetoveroippa.object.Event;
import com.example.projetoveroippa.object.Praesensa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.List;

    public class EventAdapter extends RecyclerView.Adapter<EventAdapter.myViewHolder>{

        Context mContext;
        Activity activity;
        List<Event> mData;
        Dialog mDialog;
        public EventAdapter(Context mContext, List<Event> mData , Activity activity){

            this.mContext = mContext;
            this.mData = mData;
            this.activity = activity;

        }

        @NonNull
        @Override
        public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(mContext);
            View v = inflater.inflate(R.layout.event_item,parent,false);
            return new myViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
            holder.textViewName.setText(mData.get(position).name);
            holder.textViewDate.setText(mData.get(position).date);
            holder.textViewMessage.setText(Praesensa.arrayMensagens[mData.get(position).message]);
            holder.textViewTime.setText(mData.get(position).hour);
            holder.event = mData.get(position);

        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public class myViewHolder extends RecyclerView.ViewHolder{
            TextView textViewName;
            TextView textViewDate;
            TextView textViewTime;
            TextView textViewMessage;
            Event event;
            public myViewHolder(View itemView){
                super (itemView);
                textViewName = itemView.findViewById(R.id.textViewName);
                textViewDate = itemView.findViewById(R.id.textViewDate);
                textViewTime = itemView.findViewById(R.id.textViewTime);
                textViewMessage = itemView.findViewById(R.id.textViewMessageDisplayEvent);
                itemView.findViewById(R.id.imageView5).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialog = new Dialog(view.getContext());
                        DataBase.activeEvent = event;
                        mDialog.setContentView(R.layout.pop_up_event);
                        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        ((TextView)mDialog.findViewById(R.id.textViewNamePopUp)).setText(textViewName.getText());
                        ((TextView)mDialog.findViewById(R.id.textViewDatePopoUp)).setText(textViewDate.getText());
                        ((TextView)mDialog.findViewById(R.id.textViewTimePopUP)).setText(textViewTime.getText());
                        ((TextView)mDialog.findViewById(R.id.textView12)).setText(event.description);
                        ((TextView)mDialog.findViewById(R.id.textViewEditEventPopUp)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Navigation.findNavController(activity, R.id.nav_host_fragment_content_main).navigate(R.id.action_mainMenuEvents_to_editEvent);
                                mDialog.dismiss();
                            }
                        });
                        ((FloatingActionButton)mDialog.findViewById(R.id.makeCall4)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Navigation.findNavController(activity,R.id.nav_host_fragment_content_main).navigate(R.id.action_mainMenuEvents_to_callCenter);
                               mDialog.dismiss();
                            }
                        });
                        mDialog.show();
                    }
                });
            }
        }
    }

