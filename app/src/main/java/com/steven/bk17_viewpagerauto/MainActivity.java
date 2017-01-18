package com.steven.bk17_viewpagerauto;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager_main;
    private List<ImageView> totalList = new ArrayList<ImageView>();
    private RadioGroup radioGroup_main;
    private RadioButton[] arrRadioButton = null;
    private TextView textView_main_title;
    private TypedArray array = null;
    private String[] arrTitles = null;
    private List<Map<String, Object>> listItems = new ArrayList<>();
    private String[] list = new String[4];

    private static final int STATE0 = 0;
    private int index = 0;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case STATE0:
                    viewPager_main.setCurrentItem(index++);
                    if (index > totalList.size()) {
                        index = 0;
                    }
                    sendEmptyMessageDelayed(STATE0, 3000);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        initView();

        initDots();
    }

    private void initData() {
        list = getResources().getStringArray(R.array.arrTitles);
        int[] img = {R.drawable.icon_wddb_dbsp, R.drawable.icon_wddb_ddxyb, R.drawable.icon_wdtj_lcz,
                R.drawable.icon_ydsp_wdtj_jksqd};
        arrTitles = getResources().getStringArray(R.array.arrTitles);
        array = getResources().obtainTypedArray(R.array.arrImages);
        for (int i = 0; i < array.length(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageDrawable(array.getDrawable(i));
            totalList.add(imageView);
        }

        for (int i = 0; i < list.length - 1; i++) {
            Map<String, Object> map = new HashMap<>();//创建哈希表
            map.put("title", list[i]);
            map.put("img", img[i]);
            listItems.add(map);
        }
    }

    private void initView() {
        radioGroup_main = (RadioGroup) findViewById(R.id.radioGroup_main);
        textView_main_title = (TextView) findViewById(R.id.textView_main_title);
        viewPager_main = (ViewPager) findViewById(R.id.viewPager_main);
        viewPager_main.setAdapter(new MyPagerAdapter(totalList));

        viewPager_main.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                arrRadioButton[position].setChecked(true);
                textView_main_title.setText(arrTitles[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initDots() {
        arrRadioButton = new RadioButton[array.length()];
        for (int i = 0; i < arrRadioButton.length; i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setButtonDrawable(R.drawable.dots);
            radioGroup_main.addView(radioButton);
            arrRadioButton[i] = radioButton;
        }
        arrRadioButton[0].setChecked(true);

        radioGroup_main
                .setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        for (int i = 0; i < arrRadioButton.length; i++) {
                            if (arrRadioButton[i].getId() == checkedId) {
                                viewPager_main.setCurrentItem(i);
                            }
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.sendEmptyMessage(STATE0);
    }
}
