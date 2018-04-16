package com.vital.thread;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private Set<Integer> mPlayer1;
    private Set<Integer> mPlayer2;
    private LinearLayout mLeftPane;
    private LinearLayout mMiddlePane;
    private LinearLayout mRightPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLeftPane = findViewById(R.id.left_pane);
        mMiddlePane = findViewById(R.id.middle_pane);
        mRightPane = findViewById(R.id.right_pane);
        refreshView();
    }

    private void refreshView() {
        for (int i = 0; i < 50; i++) {
            if (mPlayer1.contains(i)) {
                mMiddlePane.addView(getCircle(R.color.red));
            } else if (mPlayer2.contains(i)) {
                mMiddlePane.addView(getCircle(R.color.green));
            } else {
                mMiddlePane.addView(getCircle(R.color.black));
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
}
