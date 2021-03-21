package umn.ac.id.a33081_teofilusyohaneswowiling;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyVieHolder> {

    private Context mContext;
    private ArrayList<SongFiles> mFiles = new ArrayList<>();


    MusicAdapter(Context mContext, ArrayList<SongFiles> mFiles){
        this.mFiles = mFiles;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public MyVieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.music_items, parent, false);
        return new MyVieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVieHolder holder, int position) {
        holder.tvMusicFileName.setText(mFiles.get(position).getTitle());
        byte[] image = getAlbumArt(mFiles.get(position).getPath());

        if (image != null)
        {
            Glide.with(mContext).asBitmap().load(image).into(holder.ivMusicImage);
        } else{
            Glide.with(mContext).load(R.drawable.ic_launcher_background).into(holder.ivMusicImage);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PlayerActivity.class);
                intent.putExtra("position", position);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFiles.size();
    }


    public class MyVieHolder extends RecyclerView.ViewHolder
    {
        TextView tvMusicFileName;
        ImageView ivMusicImage;

        public MyVieHolder(@NonNull View itemView) {
            super(itemView);
            tvMusicFileName = itemView.findViewById(R.id.tvMusicFileName);
            ivMusicImage = itemView.findViewById(R.id.ivMusicImage);
        }
    }

    private byte[] getAlbumArt(String uri)
    {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }
}
