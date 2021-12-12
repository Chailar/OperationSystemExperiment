package OperationSystem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Download {
    private int threadNumber;
    private String url;
    private String path;

    public Download(int number, String url, String path) {
        this.threadNumber = number;
        this.url = url;
        this.path = path;
    }

    public String getUrl() {
        return this.url;
    }

    private void start() {
        long size = -1;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");
            connection.connect();
            size = connection.getContentLengthLong();
        } catch (MalformedURLException e) {
            throw new RuntimeException("URL错误");
        } catch (IOException e) {
            System.err.println("x 连接服务器失败[" + e.getMessage() + "]");
        }
        for (int i = 0; i < threadNumber; i++) {
            long begin = i * size / threadNumber;
            long end = (i + 1) * size / threadNumber - 1;
            if (i == threadNumber - 1) {
                end = size;
            }
            new downloadThread(url, begin, end, i, path).start();
        }
    }

    class downloadThread extends Thread {
        private long begin, end;
        private String url, path;
        public int i;

        public downloadThread(String url, long begin, long end, int i, String path) {
            this.url = url;
            this.begin = begin;
            this.end = end;
            this.path = path;
            this.i = i + 1;
        }

        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            System.out.println("线程" + i + "开始下载");
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Range", "bytes=" + begin + "-" + end);
                connection.connect();
                File file = new File(path);
                RandomAccessFile out = null;
                if (file != null) {
                    out = new RandomAccessFile(file, "rw");
                }
                out.seek(begin);
                InputStream in = connection.getInputStream();
                byte[] b = new byte[1024];
                int len = 0;
                while ((len = in.read(b)) >= 0) {
                    out.write(b, 0, len);
                }
                in.close();
                out.close();
            } catch (MalformedURLException e) {
                throw new RuntimeException("URL错误");
            } catch (IOException e) {
                System.err.println("x 连接服务器失败[" + e.getMessage() + "]");
            }
            System.out.println("线程" + i + "结束下载,耗时" + (System.currentTimeMillis() - startTime) + "ms");
        }
    }

    public static void main(String[] args) {
        new Download(4, "http://121.41.224.216/register.html", "E:\\programming\\java\\school\\src\\OperationSystem\\test.html").start();
    }
}
