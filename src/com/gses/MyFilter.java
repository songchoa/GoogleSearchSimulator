package com.gses;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.util.Arrays;

public class MyFilter implements Filter
{
    private FilterConfig filterConfig = null;

    private static class ByteArrayServletStream extends ServletOutputStream
    {
        ByteArrayOutputStream baos;

        ByteArrayServletStream(ByteArrayOutputStream baos)
        {
            this.baos = baos;
        }

        public void write(int param) throws IOException
        {
            baos.write(param);
        }
    }

    private static class ByteArrayPrintWriter
    {

        private ByteArrayOutputStream baos = new ByteArrayOutputStream();

        private PrintWriter pw = new PrintWriter(baos);

        private ServletOutputStream sos = new ByteArrayServletStream(baos);

        public PrintWriter getWriter()
        {
            return pw;
        }

        public ServletOutputStream getStream()
        {
            return sos;
        }

        byte[] toByteArray()
        {
            return baos.toByteArray();
        }
    }

    public class CharResponseWrapper extends HttpServletResponseWrapper
    {
        private ByteArrayPrintWriter output;
        private boolean usingWriter;

        public CharResponseWrapper(HttpServletResponse response)
        {
            super(response);
            usingWriter = false;
            output = new ByteArrayPrintWriter();
        }

        public byte[] getByteArray()
        {
            return output.toByteArray();
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException
        {
            // will error out, if in use
            if (usingWriter) {
                super.getOutputStream();
            }
            usingWriter = true;
            return output.getStream();
        }

        @Override
        public PrintWriter getWriter() throws IOException
        {
            // will error out, if in use
            if (usingWriter) {
                super.getWriter();
            }
            usingWriter = true;
            return output.getWriter();
        }

        public String toString()
        {
            return output.toString();
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException
    {
        this.filterConfig = filterConfig;
    }

    public void destroy()
    {
        filterConfig = null;
    }

    public void doFilter(
            ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException
    {
    	if(response == null)
    	System.out.println("response is null");
    	
        CharResponseWrapper wrappedResponse = new CharResponseWrapper(
                (HttpServletResponse)response);
        
        if(request == null) System.out.println("it's null");
        
        chain.doFilter(request, wrappedResponse);
        byte[] bytes = wrappedResponse.getByteArray();
        
        
        System.out.println(Arrays.toString(bytes));
        
        if(wrappedResponse == null) { System.out.println("1 null");}
        
        if(wrappedResponse.getContentType() == null) {System.out.println("2 null");}
        if (wrappedResponse.getContentType().contains("text/html")) {
            String out = new String(bytes);
            System.out.println("out is :" + out);
            // DO YOUR REPLACEMENTS HERE
            out = out.replace("</HEAD>", "WTF</HEAD>");
            //response.getOutputStream().write(out.getBytes());
            response.getWriter().println("<h1>print this out</h1>");
        }
        else {
            response.getOutputStream().write(bytes);
        }
    }
}