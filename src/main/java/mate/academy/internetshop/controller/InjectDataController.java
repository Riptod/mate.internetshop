package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.models.Role;
import mate.academy.internetshop.models.User;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class InjectDataController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(InjectDataController.class);

    @Inject
    private static UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = new User();
        user.setName("User");
        user.setSurname("User");
        user.addRole(Role.of("USER"));
        user.setLogin("1");
        user.setPassword("1");
        try {
            userService.create(user);
        } catch (DataProcessingException e) {
            LOGGER.error("Can't create User:", e);
            req.setAttribute("errorMsg", "Can't create User");
            req.getRequestDispatcher("/WEB-INF/views/errorDb.jsp").forward(req, resp);
        }

        User admin = new User();
        admin.setName("admin");
        admin.setSurname("admin");
        admin.addRole(Role.of("ADMIN"));
        admin.setLogin("2");
        admin.setPassword("2");
        try {
            userService.create(admin);
        } catch (DataProcessingException e) {
            LOGGER.error("Can't create Admin:", e);
            req.getRequestDispatcher("/WEB-INF/views/errorDb.jsp").forward(req, resp);
        }
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
