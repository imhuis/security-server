package com.imhuis.securityserver.common.web;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author: imhuis
 * @date: 2022/1/14
 * @description:
 */
public class ContentBodyCachingRequestWrapper extends HttpServletRequestWrapper {

    private byte[] cachedBody;

    private BufferedReader reader;

    private ServletInputStream inputStream;

    public ContentBodyCachingRequestWrapper(HttpServletRequest request) {
        super(request);
        loadBody(request);
    }

    // InputStream requestInputStream = request.getInputStream();
    //        this.cachedBody = StreamUtils.copyToByteArray(requestInputStream);
    private void loadBody(HttpServletRequest request) {
        try {
            cachedBody = IOUtils.toByteArray(request.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        inputStream = new RequestCachingInputStream(cachedBody);
    }

    public byte[] getBody() {
        return cachedBody;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (inputStream != null) {
            return inputStream;
        }
        return super.getInputStream();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        if (reader == null) {
            reader = new BufferedReader(new InputStreamReader(inputStream, getCharacterEncoding()));
        }
        return reader;
    }

    private static class RequestCachingInputStream extends ServletInputStream {

        private final ByteArrayInputStream inputStream;

        public RequestCachingInputStream(byte[] bytes) {
            inputStream = new ByteArrayInputStream(bytes);
        }

        @Override
        public int read() throws IOException {
            return inputStream.read();
        }

        @Override
        public boolean isFinished() {
            return inputStream.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readlistener) {
        }

    }
}
