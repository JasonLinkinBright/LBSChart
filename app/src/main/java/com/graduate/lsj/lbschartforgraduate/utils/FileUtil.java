package com.graduate.lsj.lbschartforgraduate.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.graduate.lsj.lbschartforgraduate.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lsj on 2016/6/1.
 */
public class FileUtil {
    /**
     * 反斜杠 “/”
     */
    private static final String BACKSLASH = "/";

    /**
     * sd卡根目录
     */
    public static final String sdcard_root_directory = getSdcardRootDirectory();

    /**
     * 判断是否有sd卡
     * @return
     */
    public static boolean isHasSdcard() {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 新建一个文件夹,如果没有
     *
     * @param context    folderRoot 为空时，必填。
     * @param folderRoot 文件夹路径，如果为空，则以当前app名称在sd根目录新建文件夹，再在里面新建当前文件夹。
     * @param folderName 文件夹名称
     * @return 当前新建文件夹路径
     */
    public static String newFileFolder(Context context, String folderRoot, String folderName) {
        if (isHasSdcard()) {
            if (TextUtils.isEmpty(folderRoot)) {
                folderRoot = getCurrentAppPath(context);
            }
        } else {
            if (TextUtils.isEmpty(folderRoot)) {
                folderRoot = getRootDirectory(context);
            }
        }
        File file = new File(folderRoot + BACKSLASH + folderName);
        if (!file.exists()) {
            file.mkdirs();
//        } else {
//            deleteFile(file.getPath());
//            file.mkdirs();
        }
        return file.getPath();
    }

    /**
     * 获取应用名称
     *
     * @param context
     * @return
     */
    public static String getCurrentAppPath(Context context) {
        String appName = context.getResources().getString(R.string.app_name);
        return getSdcardRootDirectory() + BACKSLASH + appName;
    }

    /**
     * 给定一个路径和文件名，创建并返回一个完整的图片文件地址
     *
     * @param fileRoot
     * @param fileName nullable，为空则获取当前时间
     * @return
     */
    public static final String newImageFileDir(String fileRoot, String fileName) {
        fileName = TextUtils.isEmpty(fileName) ? getTime() : fileName;
        return fileRoot + BACKSLASH + fileName + ".jpg";
    }

    public static String newFilePath(String fileRoot,String fileName){
        fileName = TextUtils.isEmpty(fileName) ? getTime() : fileName;
        return fileRoot + BACKSLASH + fileName;
    }

    public static String getSdcardRootDirectory() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 获取系统根目录
     * @param context
     * @return
     */
    public static String getRootDirectory(Context context) {
        return context.getApplicationInfo().dataDir;
    }

    /**
     * 删除文件或者文件夹
     *
     * @param fileName
     */
    public static void deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            if (file.isDirectory()) {
                if (file.listFiles().length == 0) {
                    file.delete();
                } else {
                    for (File f : file.listFiles()) {
                        if (f.isDirectory()) {
                            deleteFile(f.getPath());
                        } else {
                            f.delete();
                        }
                    }
                    file.delete();
                }
            } else {
                file.delete();
            }
        }
    }

    public static List<String> getFolderContent(String folderDir) {
        File file = new File(folderDir);
        List<String> filesPath = new ArrayList<>();
        if (!file.exists()) {

        } else {
            for (File f : file.listFiles()) {
                if (f.isFile()) {
                    filesPath.add(f.getPath());
                }
            }
        }
        return filesPath;
    }

    public static List<String> getCatgFolderContent(String folderDir, String catg) {
        List<String> allFiles = new ArrayList<>();
        List<String> catgFiles = new ArrayList<>();
        allFiles = getFolderContent(folderDir);
        for (String str : allFiles) {
            if (!TextUtils.isEmpty(str) && str.contains(catg))
                catgFiles.add(str);
        }
        return catgFiles;
    }

    private static String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return formatter.format(curDate);
    }

    public static boolean checkFileExists(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    public static String newImageFileFolder(Context context, String folderRoot, String folderName) {
        if (isHasSdcard()) {
            if (TextUtils.isEmpty(folderRoot)) {
                folderRoot = getCurrentAppPath(context);
            }
        } else {
            if (TextUtils.isEmpty(folderRoot)) {
                folderRoot = getRootDirectory(context);
            }
        }
        File file = new File(folderRoot + BACKSLASH + folderName);
        if (!file.exists()) {
            file.mkdirs();
        } else {
            return folderRoot+BACKSLASH+folderName;
        }
        return file.getPath();
    }
}