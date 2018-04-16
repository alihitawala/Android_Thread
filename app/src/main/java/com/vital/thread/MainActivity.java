package com.vital.thread;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    public static final int MSG_REFRESH = 0;
    public static final int PLAYER_ONE = 1;
    public static final int PLAYER_TWO = 2;

    private Set<Integer> mPlayer1;
    private Set<Integer> mPlayer2;
    private LinearLayout mLeftPane;
    private LinearLayout mMiddlePane;
    private LinearLayout mRightPane;
    private HandlerThread mHandlerThread;
    private Handler mPlayerHandler;
    private Handler mUIHandler;
    private TextView mScoreboard1;
    private TextView mScoreboard2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLeftPane = findViewById(R.id.left_pane);
        mMiddlePane = findViewById(R.id.middle_pane);
        mRightPane = findViewById(R.id.right_pane);
        mScoreboard1 = findViewById(R.id.scoreboard_1);
        mScoreboard2 = findViewById(R.id.scoreboard_2);
        mHandlerThread = new HandlerThread("Microgolf");
        mPlayer1 = new HashSet<>();
        mPlayer2 = new HashSet<>();
        mHandlerThread.start();
        mPlayerHandler = new Handler(mHandlerThread.getLooper()){
            public void handleMessage (Message msg){
                if (msg.what == PLAYER_ONE){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mPlayer1.add(getUnfilledCircle().iterator().next());
                }
                if (msg.what == PLAYER_TWO){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mPlayer2.add(getUnfilledCircle().iterator().next());
                }
                mUIHandler.sendEmptyMessage(MSG_REFRESH);
            }
        };
        mUIHandler = new Handler(getMainLooper()){
            public void handleMessage(Message msg){
                if (msg.what == MSG_REFRESH){
                    refreshView();
                }
            }
        };
        refreshView();
    }

    private void refreshView() {
        StringBuilder scoreboard1 = new StringBuilder("");
        StringBuilder scoreboard2 = new StringBuilder("");
        mMiddlePane.removeAllViews();
        for (int i = 0; i < 50; i++) {
            if (mPlayer1.contains(i)) {
                mMiddlePane.addView(getCircle(R.color.red));
                scoreboard1.append(i).append("\n");
            } else if (mPlayer2.contains(i)) {
                mMiddlePane.addView(getCircle(R.color.green));
                scoreboard2.append(i).append("\n");
            } else {
                mMiddlePane.addView(getCircle(R.color.black));
            }
        }
        mScoreboard1.setText(scoreboard1.toString());
        mScoreboard2.setText(scoreboard2.toString());

        if (mPlayer1.size() + mPlayer2.size() == 50) {
            mHandlerThread.quit();
        }
        for (int i = 0; i < 50; i++) {
            if (i % 2 == 0) {
                mPlayerHandler.sendEmptyMessage(PLAYER_ONE);
            } else {
                mPlayerHandler.sendEmptyMessage(PLAYER_TWO);
            }

        }
    }

    private ImageView getCircle(int color) {
        ShapeDrawable oval = new ShapeDrawable (new OvalShape());
        oval.setIntrinsicHeight (50);
        oval.setIntrinsicWidth (50);
        oval.getPaint().setColor(getResources().getColor(color));
        ImageView imageView = new ImageView(this);
        imageView.setImageDrawable(oval);
        return imageView;
    }

    private Set<Integer> getUnfilledCircle() {
        Set<Integer> result = new HashSet<>();
        for (int i = 0; i < 50; i++) {
            if (!mPlayer1.contains(i) && !mPlayer2.contains(i)) {
                result.add(i);
            }
        }

        return result;
    }
}
