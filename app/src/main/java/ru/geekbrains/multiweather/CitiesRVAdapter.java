package ru.geekbrains.multiweather;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CitiesRVAdapter extends RecyclerView.Adapter<CitiesRVAdapter.VievHolder> {

    public interface OnItemClickListener{
        void onItemClick(ListItem item, int pos);
    }

    List<ListItem> items;
    private int layoutResID;
    OnItemClickListener itemClickListener;

    public CitiesRVAdapter(List<ListItem> items, int layoutResID,OnItemClickListener onItemClickListener){
        this.items = items;
        this.layoutResID = layoutResID;
        this.itemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public VievHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int vievtype) {
       View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cities_rv_adapter,viewGroup,false);
       VievHolder vievHolder = new VievHolder(view);
       return vievHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VievHolder vievHolder, int i) {
        vievHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class VievHolder extends RecyclerView.ViewHolder {

        TextView cities_tv;

        public VievHolder(@NonNull View itemView) {
            super(itemView);
            cities_tv = itemView.findViewById(R.id.tv_cities);
        }

        public void bind (final int position){
            final ListItem item = items.get(position);
            cities_tv.setText(item.getCity());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(item, position);
                }
            });
        }
    }
}
