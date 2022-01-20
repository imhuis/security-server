//package com.imhui.security.common.web;
//
//import javax.servlet.ReadListener;
//import javax.servlet.ServletInputStream;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//
///**
// * @author: imhuis
// * @date: 2022/1/14
// * @description:
// */
//public class CachedBodyServletInputStream extends ServletInputStream {
//
//    private InputStream cachedBodyInputStream;
//
//    public CachedBodyServletInputStream(byte[] cachedBody) {
//        this.cachedBodyInputStream = new ByteArrayInputStream(cachedBody);
//    }
//
//    @Override
//    public boolean isFinished() {
//        return cachedBody.available() == 0;
//    }
//
//    @Override
//    public boolean isReady() {
//        return true;
//    }
//
//    @Override
//    public void setReadListener(ReadListener readListener) {
//
//    }
//
//    @Override
//    public int read() throws IOException {
//        return cachedBodyInputStream.read();
//    }
//}
