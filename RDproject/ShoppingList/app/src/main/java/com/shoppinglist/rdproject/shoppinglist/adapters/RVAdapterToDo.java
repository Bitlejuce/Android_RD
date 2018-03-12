package com.shoppinglist.rdproject.shoppinglist.adapters;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shoppinglist.rdproject.shoppinglist.Product;
import com.shoppinglist.rdproject.shoppinglist.R;

import java.util.List;


public class RVAdapterToDo extends RecyclerView.Adapter<RVAdapterToDo.ProductViewHolder>{

    private List<Product> product;
      int layoutId;
    public List<Product> getProductList() {
        return product;
    }

    public RVAdapterToDo(List<Product> product, int layoutId){
        this.product = product;
        this.layoutId = layoutId;
    }
    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        switch (layoutId) {
            case R.id.lis_to_do: {
                 v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
                 return new ProductViewHolder(v);
            }
            case R.id.list_done: {
                 v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_done, parent, false);
            }
        }
            return new ProductViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ProductViewHolder holder, int i) {
        switch (layoutId) {
            case R.id.lis_to_do: {
                holder.productName.setText(product.get(i).getName());
                holder.productQty.setText(product.get(i).getQuantity());
                holder.productPhoto.setImageResource(product.get(i).getPhotoId());
                return;
            }
            case R.id.list_done: {
                holder.productName.setText(product.get(i).getName());
                holder.productQty.setText(product.get(i).getQuantity());
                holder.productPhoto.setImageResource(product.get(i).getPhotoId());
            }
        }
    }

    @Override
    public int getItemCount() {
        return product.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        CardView cv;
        TextView productName;
        TextView productQty;
        ImageView productPhoto;
        ProductViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView; //(CardView)itemView.findViewById(R.id.item);
            productName = (TextView)itemView.findViewById(R.id.product_name);
            productQty = (TextView)itemView.findViewById(R.id.quantity);
            productPhoto = (ImageView)itemView.findViewById(R.id.pic_of_the_product);
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

                RVAdapterToDo adapterFrom = (RVAdapterToDo) listFrom.getAdapter();
                RVAdapterToDo adapterTo = (RVAdapterToDo) listTo.getAdapter();

                product = adapterFrom.getProductList().get(position);
                List<Product> listWhereAddProduct = adapterTo.getProductList();
                List<Product> listWhereRemoveProduct = adapterFrom.getProductList();

                    listWhereAddProduct.add(0, product);
                    adapterTo.notifyDataSetChanged();

                    listWhereRemoveProduct.remove(position);
                    adapterFrom.notifyDataSetChanged();

                if (layoutId == R.id.lis_to_do) {
                    Snackbar.make(view, "You just deleted " + (position + 1), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            } catch (Exception e) {
                // ignore right now
            }
        }

    }
}