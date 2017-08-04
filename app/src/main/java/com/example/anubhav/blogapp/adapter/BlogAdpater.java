package com.example.anubhav.blogapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anubhav.blogapp.R;
import com.example.anubhav.blogapp.model.Blog;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by anubhav on 31/07/17.
 */

public class BlogAdpater extends RecyclerView.Adapter<BlogAdpater.ViewHolder> {

    private Context context;
    private List<Blog> blogList;

    public BlogAdpater(Context context, List<Blog> blogList) {
        this.context = context;
        this.blogList = blogList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txtTitle, txtDescription, txtTimeStamp;
        public ImageView imgImage;
        public String userId;

        public ViewHolder(View itemView, Context ctx) {
            super(itemView);
            context = ctx;
            txtTitle = (TextView) itemView.findViewById(R.id.txtPostTitle);
            txtDescription = (TextView) itemView.findViewById(R.id.txtPostDescp);
            txtTimeStamp = (TextView) itemView.findViewById(R.id.txtTimeStamp);
            imgImage = (ImageView) itemView.findViewById(R.id.imgPost);
            userId = null;
        }

        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_post, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Blog currentBlog = blogList.get(position);
        String imageUrl = null;

        holder.txtTitle.setText(currentBlog.getTitle());
        holder.txtDescription.setText(currentBlog.getDescription());

        DateFormat dateFormat = DateFormat.getDateInstance();
        String formattedDate = dateFormat.format(new Date(Long.valueOf(currentBlog.getTimeStamp())));
        holder.txtTimeStamp.setText(formattedDate);

        imageUrl = currentBlog.getImage();

        //TODO: use Picasso to load image
        Picasso.with(context).load(imageUrl).into(holder.imgImage);

    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }
}
