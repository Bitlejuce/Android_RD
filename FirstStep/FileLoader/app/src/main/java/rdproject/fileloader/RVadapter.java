package rdproject.fileloader;

import android.content.ComponentName;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import rdproject.fileloader.pojo.Link;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;


public class RVadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Link> items;
    private MainActivity activity;

    public RVadapter(MainActivity activity) {
        this.activity = activity;
        items = activity.getLinkList();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        Link item = items.get(position);
        itemViewHolder.link.setText(item.getLink());
        itemViewHolder.timeAndDate.setText(getDate((item.getDateMills())));

        //checking status to set background colour
        switch (item.getStatus()){
            case Link.STATUS_LOADED: itemViewHolder.listItem.setBackgroundColor(GREEN);
                return;
            case Link.STATUS_ERROR: itemViewHolder.listItem.setBackgroundColor(RED);
        }
    }

    private String getDate(long dateMills) {
        Date currentTime = new Date(dateMills);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.US);
        return sdf.format(currentTime);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        TextView link;
        TextView timeAndDate;
        LinearLayout listItem;

        public ItemViewHolder(View itemView) {
            super(itemView);
            link = itemView.findViewById(R.id.link_textview);
            timeAndDate = itemView.findViewById(R.id.time_date_textview);
            listItem = itemView.findViewById(R.id.list_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName("btest.bapplication", "btest.bapplication.BMainActivity"));
                    intent.putExtra("linkToPic", items.get(getAdapterPosition()).getLink());
                    intent.putExtra("from","list");
                    intent.putExtra("status",items.get(getAdapterPosition()).getStatus());
                    try {
                        activity.startActivity(intent);
                    } catch (Exception e) {

                    }
                }
            });

        }
    }
}
