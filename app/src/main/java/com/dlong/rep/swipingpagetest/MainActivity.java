package com.dlong.rep.swipingpagetest;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.dlong.rep.swipingpagetest.view.PageEnabledSlidingPaneLayout;

public class MainActivity extends AppCompatActivity {

    /** 侧划控件 */
    private PageEnabledSlidingPaneLayout slidingPaneLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slidingPaneLayout = (PageEnabledSlidingPaneLayout) findViewById(R.id.SlidingPaneLayout);
        initSlidingPaneLayout();
    }

    /**
     * 初始化左划侧边菜单栏效果
     */
    private void initSlidingPaneLayout(){
        // 设置侧划后首页上透明，不带颜色，不会变灰色
        slidingPaneLayout.setSliderFadeColor(Color.TRANSPARENT);
        // 拿到左侧菜单栏view
        final View leftView = slidingPaneLayout.getChildAt(0);
        slidingPaneLayout.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(@NonNull View panel, float slideOffset) {
                // 划动过程中
                // 左侧边栏的变化
                leftView.setPivotX(-leftView.getWidth() / 5.0f);
                leftView.setPivotY(leftView.getHeight() / 2.0f);
                leftView.setScaleX(0.8f + 0.2f * slideOffset);//0.8~1
                leftView.setScaleY(0.8f + 0.2f * slideOffset);//0.8~1
                leftView.setAlpha(0.8f + 0.2f * slideOffset);//0.8~1
                // 首页的变化
                panel.findViewById(R.id.ll_main).setScaleX(1f - 0.2f * slideOffset);
                panel.findViewById(R.id.ll_main).setScaleY(1f - 0.2f * slideOffset);
                // 背景阴影图片的变化
                panel.findViewById(R.id.img_shadow).setScaleX(1f - 0.1f * slideOffset);
                panel.findViewById(R.id.img_shadow).setScaleY(1f - 0.13f * slideOffset);
                panel.findViewById(R.id.img_shadow).setAlpha(1f - 0.3f * slideOffset);
            }

            @Override
            public void onPanelOpened(@NonNull View view) {

            }

            @Override
            public void onPanelClosed(@NonNull View panel) {
            }
        });
    }
}
