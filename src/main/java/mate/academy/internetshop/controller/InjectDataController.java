package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.models.Role;
import mate.academy.internetshop.models.User;
import mate.academy.internetshop.service.UserService;

public class InjectDataController extends HttpServlet {
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
        userService.create(user);

        User admin = new User();
        admin.setName("admin");
        admin.setSurname("admin");
        admin.addRole(Role.of("ADMIN"));
        admin.setLogin("2");
        admin.setPassword("2");
        userService.create(admin);
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
