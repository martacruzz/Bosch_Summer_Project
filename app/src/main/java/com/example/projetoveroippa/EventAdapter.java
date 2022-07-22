package com.example.projetoveroippa;

import android.content.Context;
import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetoveroippa.databinding.EventItemBinding;
import com.example.projetoveroippa.object.Event;

import org.w3c.dom.Text;

import java.util.List;

    public class EventAdapter extends RecyclerView.Adapter<EventAdapter.myViewHolder>{

        Context mContext;
        List<Event> mData;

        public EventAdapter(Context mContext, List<Event> mData){

            this.mContext = mContext;
            this.mData = mData;

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
            holder.textViewName = mData.get(position).name;
            holder.textViewDate = mData.get(position).date;
            holder.textViewTime = mData.get(position).hour;
            holder.textViewDescription = mData.get(position).description;

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        public class myViewHolder extends RecyclerView.ViewHolder{
            String textViewName;
            String textViewDate;
            String textViewTime;
            String textViewDescription;
            public myViewHolder(View itemView){
                super (itemView);
                textViewName = itemView.findViewById(R.id.textViewName).toString();
                textViewDate = itemView.findViewById(R.id.textViewDate).toString();
                textViewTime = itemView.findViewById(R.id.textViewTime).toString();
                textViewDescription = itemView.findViewById(R.id.textViewDescription).toString();
            }
        }
    }

