package com.graduate.lsj.lbschartforgraduate.dao.task;

/**
 * Created by lsj on 2016/3/22.
 */
public class TaskExecutor {

    private static TaskExecutor INSTANCE = new TaskExecutor();

    public static TaskExecutor get() {
        return INSTANCE;
    }

    public void execute(BaseTask task) {
        task.run();
    }

}
