//package com.imhui.security.common.web;
//
//import org.springframework.util.StreamUtils;
//
//import javax.servlet.ServletInputStream;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletRequestWrapper;
//import java.io.*;
//
///**
// * @author: imhuis
// * @date: 2022/1/14
// * @description:
// */
//public class ContentBodyCachingRequestWrapper extends HttpServletRequestWrapper {
//
//    private byte[] cachedBody;
//
//    public ContentBodyCachingRequestWrapper(HttpServletRequest request) throws IOException {
//        super(request);
//        InputStream requestInputStream = request.getInputStream();
//        this.cachedBody = StreamUtils.copyToByteArray(requestInputStream);
//    }
//
//    @Override
//    public ServletInputStream getInputStream() throws IOException {
//        return new CachedBodyServletInputStream(this.cachedBody);
//    }
//
//    @Override
//    public BufferedReader getReader() throws IOException {
//        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cachedBody);
//        return new BufferedReader(new InputStreamReader(byteArrayInputStream));
//    }
//}
