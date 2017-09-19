package com.ahuo.myapp2.ui.activity;

import android.content.Intent;
import android.support.v4.app.FragmentTabHost;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TabHost;

import com.ahuo.myapp2.R;
import com.ahuo.myapp2.base.BaseActivity;
import com.ahuo.myapp2.core.service.AliveService;
import com.ahuo.myapp2.ui.fragment.DiscoveryFragment;
import com.ahuo.myapp2.ui.fragment.MainFragment;
import com.ahuo.myapp2.ui.fragment.PersonFragment;
import com.ahuo.myapp2.ui.widget.MyTabView;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tabContent)
    FrameLayout mTabContent;
    @BindView(R.id.tabHost)
    FragmentTabHost mTabHost;
    private Class[] mClassFragments={MainFragment.class, DiscoveryFragment.class, PersonFragment.class};
    private String[] mStrTab;
    private int[] mIRTab= {R.drawable.bg_tab_selector_main, R.drawable.bg_tab_selector_discover, R.drawable.bg_tab_selector_person};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        mStrTab=getResources().getStringArray(R.array.main_tab_tag);
        mTabHost.setup(this,getSupportFragmentManager(),R.id.tabContent);
        for (int i = 0; i < mClassFragments.length; i++) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mStrTab[i]).setIndicator(getTabView(i));
            mTabHost.addTab(tabSpec, mClassFragments[i], null);
        }
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);

        startAliveService();

    }

    private void startAliveService() {

        Intent intent = new Intent(this, AliveService.class);
        startService(intent);

    }

    private MyTabView getTabView(int position) {
        MyTabView tabView = new MyTabView(this);
        tabView.setImageResource(mIRTab[position]).setTextContent(mStrTab[position]);
        return tabView;
    }

    @Override
    public void refresh() {

    }

    @Override
    public void setPresenter() {

    }

}
