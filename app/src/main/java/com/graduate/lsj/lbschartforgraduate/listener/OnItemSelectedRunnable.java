package com.graduate.lsj.lbschartforgraduate.listener;

import com.graduate.lsj.lbschartforgraduate.ui.view.LoopView;

/**
 * Created by lsjon 2016/4/12.
 */
public final class OnItemSelectedRunnable implements Runnable {
    final LoopView loopView;

    public OnItemSelectedRunnable(LoopView loopview) {
        loopView = loopview;
    }

    @Override
    public final void run() {
        loopView.onItemSelectedListener.onItemSelected(loopView.getSelectedItem());
    }
}
