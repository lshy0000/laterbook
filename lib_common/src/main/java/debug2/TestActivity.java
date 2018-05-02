package debug2;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chuangfeigu.tools.app.BaseActivity;

/**
 * Created by lshy on 2018-1-23.
 */
@Route(path = "/lib_common/testactivity")
public class TestActivity extends BaseActivity {

//    @BindView(com.guiying.module.common.R2.id.list)
//    RecyclerView list;
//    @BindView(R2.id.empty)
//    FrameLayout empty;
//    @BindView(R2.id.progress)
//    FrameLayout progress;
//    @BindView(R2.id.error)
//    FrameLayout error;
//    @BindView(R2.id.ptr_layout)
//    SwipeRefreshLayout ptrLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.layout_view_empty);
//        findViewById(R.id.error)
//        ButterKnife.bind(this);
    }
}
