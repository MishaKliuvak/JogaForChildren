package com.example.jogaforchildren.Poses;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jogaforchildren.Const;
import com.example.jogaforchildren.R;
import com.squareup.picasso.Picasso;


import java.net.URI;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    List<Pose>  mData;

    public RecyclerViewAdapter(Context mContext, List<Pose> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }



    boolean state, finish;
    @SuppressLint("SetJavaScriptEnabled")
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_pose, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(v);

        final Dialog myDialog = new Dialog(mContext);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        myDialog.setContentView(R.layout.open_pose);


        final ImageView imageView = (ImageView) myDialog.findViewById(R.id.open_img);
        final TextView timer_txt = (TextView) myDialog.findViewById(R.id.timer_in_open);

        final WebView webView = myDialog.findViewById(R.id.video_vv);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        final Button complete = (Button) myDialog.findViewById(R.id.but_complete);
        final Button pause = (Button) myDialog.findViewById(R.id.but_pause);



        state = false;
        finish = false;
        viewHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  CountDownTimer c = new CountDownTimer(Integer.valueOf(mData.get(viewHolder.getAdapterPosition()).getTime())* 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        timer_txt.setText("" + millisUntilFinished/1000);
                    }

                    @Override
                    public void onFinish() {
                        timer_txt.setText("Завершено!");
                        finish = true;
                    }
                };
                myDialog.setOnCancelListener(new DialogInterface.OnCancelListener(){
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        c.cancel();
                        webView.loadUrl("about:blank");
                    }
                });

                webView.loadData(Const.getStringURL("300", "169", mData.get(viewHolder.getAdapterPosition()).getVideo()), "text/html", "utf-8");

                pause.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!state) { c.start(); state = true;}
                        else { c.cancel(); c.start();}
                        finish = false;
                    }
                });

                complete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!finish) {
                            Toast.makeText(mContext, "Ви ще не пройшли", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(mContext, "Збережено", Toast.LENGTH_SHORT).show();
                            finish = false;
                            myDialog.cancel();
                        }
                    }
                });


                Picasso.get().load(mData.get(viewHolder.getAdapterPosition()).getIcon()).into(imageView);
                timer_txt.setText(mData.get(viewHolder.getAdapterPosition()).getTime());
                myDialog.show();

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nameTV.setText(mData.get(position).getName());
        holder.levelTV.setText(mData.get(position).getLevel());
        Picasso.get().load(mData.get(position).getIcon()).into(holder.img_pose);
        holder.timerTV.setText(mData.get(position).getTime() + " c");
        holder.videoTV.setText(mData.get(position).getVideo());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTV, levelTV, timerTV, videoTV;
        private ImageView img_pose;
        private RelativeLayout item;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            item = (RelativeLayout) itemView.findViewById(R.id.pose_item);
            nameTV = (TextView) itemView.findViewById(R.id.name_pose);
            levelTV = (TextView) itemView.findViewById(R.id.level_pose);
            img_pose = (ImageView) itemView.findViewById(R.id.img_pose);
            timerTV = (TextView) itemView.findViewById(R.id.tTV);
            videoTV = (TextView) itemView.findViewById(R.id.video);
        }
    }


}
