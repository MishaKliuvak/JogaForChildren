package com.example.jogaforchildren.BottomContainers;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.jogaforchildren.Const;
import com.example.jogaforchildren.Poses.Pose;
import com.example.jogaforchildren.R;

import java.util.ArrayList;
import java.util.List;

public class TimerFragment extends Fragment {
    private View v;
    private Button easy, middle, high;
    private RelativeLayout first, second;
    private ViewPagerAdapter vpa;
    private static TimerFragment timerFragment = null;
    private TimerFragment() {
    }
    public static TimerFragment getInstance() {
        if (timerFragment == null) TimerFragment.timerFragment = new TimerFragment();
        return TimerFragment.timerFragment;
    }

    public CountDownTimer c;
    public boolean started = false;
    private WebView webView;
    private TextView timer;
    private Button startBtn, next;
    private TextView finishTV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_timer, container, false);
        easy = v.findViewById(R.id.easyLvl);
        middle = v.findViewById(R.id.middleLvl);
        high = v.findViewById(R.id.highLvl);
        next = v.findViewById(R.id.next);

        first = v.findViewById(R.id.first);
        second = v.findViewById(R.id.second);
        finishTV = v.findViewById(R.id.finish);

        finishTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish = false;
                count =0;
                c.cancel();
                first.setVisibility(View.VISIBLE);
                second.setVisibility(View.INVISIBLE);
            }
        });

        webView = v.findViewById(R.id.video_timer);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        timer = v.findViewById(R.id.timer_timer);
        startBtn = v.findViewById(R.id.but_start);

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(Const.getListEasy());
            }
        });

        middle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(Const.getListMedium());
            }
        });

        high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(Const.getListHigh());
            }
        });
        return v;
    }

    int count = 0;
    boolean finish = false;
    List<Pose> cur = new ArrayList<>();
    private void start(List<Pose> list){
        cur = list;
        started = true;
        first.setVisibility(View.INVISIBLE);
        second.setVisibility(View.VISIBLE);

        webView.loadData(Const.getStringURL(String.valueOf(Const.getWidth()), String.valueOf(Const.getWidth() * 1080 / 1920),
                list.get(count).getVideo()), "text/html", "utf-8");
        timer.setText(list.get(count).getTime());

        c = new CountDownTimer(Integer.valueOf(list.get(count).getTime()) * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                timer.setText("0");
                finish = true;
            }
        };
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish = false;
                c.start();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!finish) Toast.makeText(getContext(), "Ви ще не пройшли", Toast.LENGTH_SHORT).show();
                else {
                    if(list.size() - count >= 1) {
                        count++;
                        webView.loadData(Const.getStringURL(String.valueOf(Const.getWidth()), String.valueOf(Const.getWidth() * 1080 / 1920),
                                list.get(count).getVideo()), "text/html", "utf-8");
                        finish = false;
                        clear();
                    }
                    else {
                        Toast.makeText(getContext(), "Рівень успішно пройдений", Toast.LENGTH_SHORT).show();
                        finish = false;
                        count =0;
                        c.cancel();
                        first.setVisibility(View.VISIBLE);
                        second.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
    }

    public void clear(){
        c.cancel();
        timer.setText(cur.get(count).getTime());
    }
}
