package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import mate.academy.internetshop.exceptions.AuthentificationException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.models.User;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.UserService;

public class LoginController extends HttpServlet {
    @Inject
    private static UserService userService;
    @Inject
    private static ItemService itemService;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        itemService.get(1L);
        itemService.delete(2L);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("psw");
        try {
            User user = userService.login(login, password);
            HttpSession session = req.getSession(true);
            session.setAttribute("userId", user.getId());
            Cookie cookie = new Cookie("MATE", user.getToken());
            resp.addCookie(cookie);
            resp.sendRedirect(req.getContextPath() + "/servlet/home");
        } catch (AuthentificationException e) {
            req.setAttribute("errorMsg", "Incorrect login or password");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }

    }
}
