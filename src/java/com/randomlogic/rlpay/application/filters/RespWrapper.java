/*
 * Copyright 2019 Random Logic Consulting Services and Paul G. Allen. All rights reserved.
 */
package com.randomlogic.rlpay.application.filters;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 *
 * @author Paul.Allen
 */
public class RespWrapper extends HttpServletResponseWrapper
{
    private static final boolean debug = false;

    private PrintWriter pw = null;

    private Object streamUsed = null;

    MyServletOutputStream stream = null;

    public RespWrapper (HttpServletResponse httpServletResponse)
    {
        super (httpServletResponse);
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException
    {
        if (debug)
        {
            System.out.println ("RespWrapper.getOutputStream()");
        }

        if ((streamUsed != null) && (streamUsed == pw))
        {
            throw new IllegalStateException();
        }

        stream = new MyServletOutputStream();
        streamUsed = stream;

        return stream;
    }

    @Override
    public PrintWriter getWriter() throws IOException
    {
        if (debug)
        {
            System.out.println ("RespWrapper.getWriter()");
        }

        if ((streamUsed != null) && (streamUsed == stream))
        {
            throw new IllegalStateException();
        }

        if (pw == null)
        {
            if (stream == null)
            {
                stream = new MyServletOutputStream();
            }

            pw = new PrintWriter (stream);
            streamUsed = pw;
        }

        return pw;
    }

    public byte[] getWrapperBytes()
    {
        if (debug)
        {
            System.out.println ("RespWrapper.getWrapperBytes()");
        }

        if (stream == null)
        {
            stream = new MyServletOutputStream();
            streamUsed = stream;
        }

        return stream.getBytes();
    }

    private final class MyServletOutputStream extends ServletOutputStream
    {
        private ByteArrayOutputStream out = new ByteArrayOutputStream();

        @Override
        public void write (int b) throws IOException
        {
            out.write (b);
        }

        public byte[] getBytes()
        {
            return out.toByteArray();
        }

        @Override
        public boolean isReady()
        {
            return false;
        }

        @Override
        public void setWriteListener (WriteListener wl)
        {

        }
    }
}
