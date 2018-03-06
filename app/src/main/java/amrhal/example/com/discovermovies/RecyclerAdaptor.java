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

import java.util.List;

/**
 * Created by Amr hal on 28/02/2018.
 */

public class RecyclerAdaptor extends RecyclerView.Adapter<RecyclerAdaptor.myViewholder> {
    List<MovieModel> list;
    Context context;
    LayoutInflater layoutInflater;


    public RecyclerAdaptor(List<MovieModel> list, Context context) {
        this.list = list;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public myViewholder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = layoutInflater.inflate(R.layout.item_list, parent,false);
        myViewholder myViewholder = new myViewholder(v);

        return myViewholder;
    }

    @Override
    public void onBindViewHolder(myViewholder holder, int position) {

        MovieModel movieModel = list.get(position);

       // Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(holder.imageView);
        holder.textView.setText(movieModel.getTitle());
        Log.e("tag", "on bind ");
//todo onbind


    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    //------------------------------------------------------------------------------------------------------------------------------------------
    class myViewholder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;


        public myViewholder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.movieposterIV_ID);
            textView = itemView.findViewById(R.id.movieName_ID);

        }

    }
}
