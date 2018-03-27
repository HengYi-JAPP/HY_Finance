package com.hengyi.cores.filter;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * @author liuyuan
 * @Description
 * @Date: 2018-03-22 09:23
 */
public class BodyReaderHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] body;

    public BodyReaderHttpServletRequestWrapper(HttpServletRequest request)throws IOException {
        super(request);
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (!commonsMultipartResolver.isMultipart(request)) {
            byte[] bytes = null;
            ByteArrayOutputStream baos = null;
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
                String str = "";
                baos = new ByteArrayOutputStream();
                while ((str = br.readLine()) != null) {
                     baos.write(str.getBytes());
                }
                baos.flush();
                bytes = baos.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                    if (baos != null) {
                        baos.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            body = bytes;
        } else {
            body = null;
        }
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (body != null) {
            final ByteArrayInputStream bais = new ByteArrayInputStream(body);
            return new ServletInputStream() {
                @Override
                public int read() throws IOException {
                    return bais.read();
                }
            };
        }
        return this.getRequest().getInputStream();
    }
}