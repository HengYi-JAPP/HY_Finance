package com.hengyi.cores.filter;

import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: liuyuan
 * @Description:跨域过滤器,解决前后端交互跨域问题
 * @Date: 2018/03/22 09:12
 */
public class AccessFilter implements Filter {

    public FilterConfig config;

    @Override
    public void doFilter(ServletRequest request, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) resp;
        response.setHeader("Access-Control-Allow-Origin", "*"); //解决跨域访问报错
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600"); //设置过期时间
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, client_id, uuid, Authorization");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // 支持HTTP 1.1.
        response.setHeader("Pragma", "no-cache"); // 支持HTTP 1.0. response.setHeader("Expires", "0");
//        if (request.getMethod().equals("OPTIONS")) {
//            HttpUtil.setResponse(response, HttpStatus.OK.value(), null);
//            return;
//        }
        //chain.doFilter(request, resp);
        ServletRequest requestWrapper = null;
        if (request instanceof HttpServletRequest) {
            requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);
        }
        if (requestWrapper == null) {
            chain.doFilter(request,response);
        } else {
            chain.doFilter(requestWrapper,response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        config = filterConfig;
    }


    @Override
    public void destroy() {
        this.config = null;
    }
}
