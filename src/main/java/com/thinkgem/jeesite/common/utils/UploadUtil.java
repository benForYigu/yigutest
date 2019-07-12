package com.thinkgem.jeesite.common.utils;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author FANQIBU
 * @Title: FileUtils
 * @Description: 文件（文件流转换、存储、拷贝、清空、写入）
 * @Company: 卜凡起的博客
 * @date 2018年2月2日
 */
public class UploadUtil {
    // 通过get请求得到读取器响应数据的数据流
    public static InputStream getInputStreamByGet(String url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url)
                    .openConnection();
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = conn.getInputStream();
                return inputStream;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //inputStream转outputStream
    public static ByteArrayOutputStream parse(InputStream in) {
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            int ch;
            while ((ch = in.read()) != -1) {
                swapStream.write(ch);
            }
            return swapStream;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    //outputStream转inputStream
    public static ByteArrayInputStream parse(OutputStream out) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos = (ByteArrayOutputStream) out;
            ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray());
            return swapStream;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        InputStream is = getInputStreamByGet("http://mmbiz.qpic.cn/mmbiz_png/KYOtWWibVu2UiaFNlhrOrmD5OFRMXyDJMAPwwQSr5FZe7O99eoppFPxsvDEcwKBibicgKkDK7SUYT2ewUOGSheGGRg/0?wx_fmt=png");
        saveData(is, new File("d://ceshi.jpg"));
    }

    // 将服务器响应的数据流存到本地文件
    public static void saveData(InputStream is, File file) {
        try (BufferedInputStream bis = new BufferedInputStream(is);
             BufferedOutputStream bos = new BufferedOutputStream(
                     new FileOutputStream(file));) {
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
                bos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>Title: copyFileUsingFileChannels</p>
     * <p>Description: 文件拷贝</p>
     *
     * @param source
     * @param dest
     * @throws IOException
     */
    @SuppressWarnings({"resource"})
    public synchronized static void copyFileUsingFileChannels(File source, File dest) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }

    /**
     * <p>Title: clearInfoForFile</p>
     * <p>Description: 文件内容清空</p>
     *
     * @param fileName
     */
    public static void clearInfoForFile(String fileName) {
        if (StringUtils.isEmpty(fileName)) {
            return;
        }
        File file = new File(fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <p>Title: writeFileByLine</p>
     * <p>Description: 按行向文件写入内容</p>
     *
     * @throws IOException
     */
    public static void writeFileByLine(String fileName, List<String> parameter) {
        if (StringUtils.isEmpty(fileName) || parameter == null || parameter.size() == 0) {
            return;
        }
        FileWriter fw = null;
        try {
            fw = new FileWriter(fileName);
            for (int i = 0; i < parameter.size(); i++) {
                fw.write(parameter.get(i) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fw.close();
            } catch (IOException e) {
            }
        }
    }

}

