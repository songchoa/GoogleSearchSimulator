package com.gses;

import javax.servlet.*;

public class TimerFilter implements javax.servlet.Filter
{
    private FilterConfig filterConfig;
 
    public void doFilter(ServletRequest request, ServletResponse response,
           FilterChain chain) 
           throws java.io.IOException, javax.servlet.ServletException
    {
        long start = System.currentTimeMillis();
        System.out.println("Milliseconds in: " + start);
        chain.doFilter(request, response);
        long end = System.currentTimeMillis();
        System.out.println("Milliseconds out: " + end);
    }
 
    public void init(final FilterConfig filterConfig)
    {
        this.filterConfig = filterConfig;
    }
 
    public void destroy()
    {
        filterConfig = null;
    }
}