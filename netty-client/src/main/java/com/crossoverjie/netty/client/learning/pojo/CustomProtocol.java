package com.crossoverjie.netty.client.learning.pojo;

/**
 * Function:
 *
 * @author crossoverJie
 *         Date: 17/05/2018 17:50
 * @since JDK 1.8
 */
public class CustomProtocol {

    private int header ;
    private String content ;

    public int getHeader() {
        return header;
    }

    public void setHeader(int header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CustomProtocol{" +
                "header=" + header +
                ", content='" + content + '\'' +
                '}';
    }
}
