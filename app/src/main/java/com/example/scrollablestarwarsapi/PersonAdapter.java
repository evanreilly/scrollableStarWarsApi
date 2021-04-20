package com.example.scrollablestarwarsapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.scrollablestarwarsapi.model.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<com.example.scrollablestarwarsapi.PersonAdapter.PersonHolder> {
    private List<Person> persons;
    private ItemClickListener mClickListener;
    private LayoutInflater mInflater;

    PersonAdapter(Context context, List<Person> data) {
        this.mInflater = LayoutInflater.from(context);
        this.persons = data;
    }



    //populate viewHolder methods
    @NonNull
    @Override
    public PersonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.person, parent, false);

        return new PersonHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull PersonHolder holder, int position) {
        Person currentPerson = persons.get(position);
        holder.textViewName.setText(currentPerson.getName());
        holder.textViewHeight.setText(String.valueOf(currentPerson.getHeight()));
        holder.textViewMass.setText(String.valueOf(currentPerson.getMass()));
        holder.textViewBirthYear.setText(currentPerson.getBirthYear());
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    public void setPersons(List<Person> persons){
        this.persons = persons;
        notifyDataSetChanged(); //Not the best practice but works for demonstration
        //Although this method isn't used I've kept it to demonstrate how you might update the view
    }
    class PersonHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewName;
        private TextView textViewHeight;
        private TextView textViewMass;
        private TextView textViewBirthYear;


        public PersonHolder(@NonNull View itemView) {
            super(itemView);
            //Bind Data to the view
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewHeight = itemView.findViewById(R.id.text_view_height);
            textViewMass = itemView.findViewById(R.id.text_view_mass);
            textViewBirthYear = itemView.findViewById(R.id.text_view_birth_year);
            //connect click listener
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

    }

    Person getItem(int id) {
        return persons.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
