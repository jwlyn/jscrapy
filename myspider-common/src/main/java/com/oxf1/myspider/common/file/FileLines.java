package com.oxf1.myspider.common.file;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cxu on 2015/11/14.
 */
public class FileLines {

    private List<String> lines = new ArrayList<String>();
    private long offset;//文件偏移位置

    public List<String> getLines() {
        return lines;
    }

    public void addLine(String line) {
        this.lines.add(line);
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public int getLinesCount() {
        return lines.size();
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();
        for (String s : lines) {
            buf.append(s).append("\n");
        }
        return buf.toString();
    }
}
