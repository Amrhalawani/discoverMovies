package amrhal.example.com.discovermovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amr hal on 3/03/2018.
 */

public class RecyclerAdaptor extends RecyclerView.Adapter<RecyclerAdaptor.myViewholder> {
    private static final String TAG ="TAG" ;
    List<MovieModel> list  = new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;


    public RecyclerAdaptor( Context context ) {

        this.context = context;
        Log.e(TAG, "RecyclerAdaptor: " + "context is "+ context.getClass().toString() + " / list size is "+ list.size());
      //  layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public myViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e(TAG, "onCreateViewHolder: ");

         layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list, parent, false);

        myViewholder myViewholder = new myViewholder(view);

        return myViewholder;
    }

    @Override
    public void onBindViewHolder(myViewholder holder, int position) {
        Log.e(TAG, "onBindViewHolder: ");
//"http://i.imgur.com/DvpvklR.png"
        MovieModel movieModel = list.get(position);

        Picasso.get()
                .load(movieModel.getPosterUrl())
                .placeholder(R.drawable.user_placeholder)
                .into(holder.imageView);

        Log.e("tag", "on bind ");
//todo onbind


    }


    @Override
    public int getItemCount() {
        Log.e(TAG, "getItemCount: list.size = "+ list.size());
        return list.size();
    }

    public void updateData(List newList) {
        this.list = newList;
        notifyDataSetChanged();
    }

    //------------------------------------------------------------------------------------------------------------------------------------------
    class myViewholder extends RecyclerView.ViewHolder {
        ImageView imageView;



        public myViewholder(View itemView) {
            super(itemView);
            Log.e(TAG, "myViewholder: ");
            imageView = itemView.findViewById(R.id.movieposterIV_ID);


        }

    }
}
