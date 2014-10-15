package com.airensoft.skovp.sample.utils.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.airensoft.skovp.sample.utils.domain.GlobalObject;


/**
 * @author Hyun Jun Jang
 * 
 */
@SuppressWarnings("serial")
public class InitServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
{
	public void init(ServletConfig servletConfig) throws ServletException
	{
		super.init(servletConfig);

		GlobalObject globalObject;

		globalObject = GlobalObject.getInstance();

		globalObject.setCurrentPath(getServletContext().getRealPath(""));
		globalObject.setRootPath(getServletContext().getRealPath("/"));
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
	}
}
