package com.graduate.lsj.lbschartforgraduate.listener;


import android.view.MotionEvent;

import com.graduate.lsj.lbschartforgraduate.ui.view.LoopView;

// Referenced classes of package com.qingchifan.view:
//            LoopView
/**
 * Created by lsj on 2016/4/12.
 */
public final class LoopViewGestureListener extends android.view.GestureDetector.SimpleOnGestureListener {

    final LoopView loopView;

    public LoopViewGestureListener(LoopView loopview) {
        loopView = loopview;
    }

    @Override
    public final boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        loopView.scrollBy(velocityY);
        return true;
    }
}
