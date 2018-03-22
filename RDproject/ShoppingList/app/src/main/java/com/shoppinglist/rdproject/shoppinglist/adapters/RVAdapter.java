package com.shoppinglist.rdproject.shoppinglist.adapters;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shoppinglist.rdproject.shoppinglist.DBHelper;
import com.shoppinglist.rdproject.shoppinglist.DataListHolder;
import com.shoppinglist.rdproject.shoppinglist.Product;
import com.shoppinglist.rdproject.shoppinglist.R;

import java.util.List;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ProductViewHolder>{

    private List<Product> product;
    private int layoutId;
    private Context context;
    private DataListHolder dataListHolder;
    public List<Product> getProductList() {
        return product;
    }

    public RVAdapter(Context context, List<Product> product, int layoutId, String listName){
        this.product = product;
        this.layoutId = layoutId;
        this.context = context;
        dataListHolder = new DataListHolder(context, listName);
    }
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView)LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        switch (layoutId) {
            case R.id.lis_to_do: {
                 v.setCardBackgroundColor(context.getResources().getColor(R.color.cardBackground));
                 return new ProductViewHolder(v);
            }
            case R.id.list_done: {
                v.setCardBackgroundColor(context.getResources().getColor(R.color.cardview_dark_background));
            }
        }
            return new ProductViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ProductViewHolder holder, int i) {
                String name = product.get(i).getName();
                holder.productName.setText(name);
                holder.productQty.setText(product.get(i).getQuantity());
                holder.productPhoto.setText(name.substring(0,1).toUpperCase());
        }

    @Override
    public int getItemCount() {
        return product.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView cv;
        TextView productName;
        TextView productQty;
        TextView productPhoto;
        ProductViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView; //(CardView)itemView.findViewById(R.id.item);
            productName = (TextView)itemView.findViewById(R.id.product_name);
            productQty = (TextView)itemView.findViewById(R.id.quantity);
            productPhoto = (TextView)itemView.findViewById(R.id.pic_of_the_product);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            try {
                RecyclerView listFrom;
                RecyclerView listTo;
                int position = getAdapterPosition();
                Product product;

                if (layoutId == R.id.lis_to_do) {
                    listFrom = (RecyclerView) view.getRootView().findViewById(R.id.lis_to_do);   // testing
                    listTo = (RecyclerView) view.getRootView().findViewById(R.id.list_done);   // testing
                } else {
                    listTo = (RecyclerView) view.getRootView().findViewById(R.id.lis_to_do);   // testing
                    listFrom = (RecyclerView) view.getRootView().findViewById(R.id.list_done);   // testing
                }

                RVAdapter adapterFrom = (RVAdapter) listFrom.getAdapter();
                RVAdapter adapterTo = (RVAdapter) listTo.getAdapter();

                product = adapterFrom.getProductList().get(position);
                if (product.getStatus() == DBHelper.STATUS_TODO) {
                    product.setStatus(DBHelper.STATUS_DONE);
                }else {
                    product.setStatus(DBHelper.STATUS_TODO);
                }
                List<Product> listWhereAddProduct = adapterTo.getProductList();
                List<Product> listWhereRemoveProduct = adapterFrom.getProductList();

                    listWhereAddProduct.add(0, product);
                    adapterTo.notifyDataSetChanged();

                    listWhereRemoveProduct.remove(position);
                    adapterFrom.notifyDataSetChanged();


                dataListHolder.update(product);


                if (layoutId == R.id.lis_to_do) {
                    Snackbar.make(view, "Well done, you just got  " + (product.getName() + "!"), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            } catch (Exception e) {
                // ignore right now
            }
        }

    }
}