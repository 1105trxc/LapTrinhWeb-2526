package k8Edu.controller;

import jakarta.servlet.ServletException;
import k8Edu.model.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet implementation class WaitingController
 */
@SuppressWarnings("serial")
@WebServlet(urlPatterns="/waiting")
public class WaitingController extends HttpServlet {
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws
	ServletException, IOException {
	HttpSession session= req.getSession();
	if(session != null && session.getAttribute("account") != null) {
	UserModel u=(UserModel) session.getAttribute("account");
	req.setAttribute("username", u.getUserName());
	resp.sendRedirect("/home.jsp");
	}
	}
}
