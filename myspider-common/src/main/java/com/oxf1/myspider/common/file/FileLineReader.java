package com.oxf1.myspider.common.file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by cxu on 2015/11/14.
 */
public class FileLineReader {
    /**
     * 从offset起读取lines行
     * @param filePath
     * @param offset
     * @param lines
     * @return
     */
    public static FileLines readLines(String filePath, long offset, int lines) throws IOException {
        FileLines fileLines = new FileLines();

        File f = new File(filePath) ;    // 指定要操作的文件
        if (!f.exists()) {
            fileLines.setOffset(offset);
            return fileLines;
        }
        else if(f.length()<=offset){//游标大于文件
            fileLines.setOffset(offset);
            return fileLines;
        }
        RandomAccessFile rdf = null;// 声明RandomAccessFile类的对象
        rdf = new RandomAccessFile(f, "r");

        rdf.seek(offset);
        for (int i = 0; i < lines; i++) {
            String str = rdf.readLine();
            if (str != null) {
                fileLines.addLine(str);
                fileLines.setOffset(rdf.getFilePointer());
            }
            else{
                break;
            }
        }
        rdf.close();
        return fileLines;
    }
}
