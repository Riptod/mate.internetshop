package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class DeleteUserController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(DeleteUserController.class);

    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String userId = req.getParameter("user_id");
        try {
            userService.delete(Long.valueOf(userId));
        } catch (DataProcessingException e) {
            LOGGER.error("Can't delete user", e);
            req.setAttribute("errorMsg", "Can't delete user");
            req.getRequestDispatcher("/WEB-INF/views/errorDb.jsp").forward(req, resp);
        }
        resp.sendRedirect(req.getContextPath() + "/servlet/getAllUsers");
    }
}
