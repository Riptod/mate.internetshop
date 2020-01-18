package mate.academy.internetshop.web.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.models.Role;
import mate.academy.internetshop.models.User;
import mate.academy.internetshop.service.UserService;

import static mate.academy.internetshop.models.Role.RoleName.ADMIN;
import static mate.academy.internetshop.models.Role.RoleName.USER;

public class AuthorizationFilter implements Filter {
    public static final String EMPTY_STRING = "";
    @Inject
    private static UserService userService;
    private Map<String, Role.RoleName> protectedUrlsAdmin = new HashMap<>();
    private Map<String, Role.RoleName> protectedUrlsUser = new HashMap<>();

    private Map<String, Role.RoleName> protectedUrls = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectedUrlsAdmin.put("/servlet/getAllUsers", ADMIN);
        protectedUrlsAdmin.put("/servlet/deleteUser", ADMIN);
        protectedUrlsAdmin.put("/servlet/createItem", ADMIN);
        protectedUrlsAdmin.put("/servlet/deleteOrder", ADMIN);
        protectedUrlsUser.put("/servlet/addItemToBucket", USER);
        protectedUrlsUser.put("/servlet/bucket", USER);
        protectedUrlsUser.put("/servlet/deleteItemFromBucket", USER);
        protectedUrlsUser.put("/servlet/compliteOrder", USER);
        protectedUrlsUser.put("/servlet/getAllOrders", USER);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String requestedUrl = req.getRequestURI().replace(req.getContextPath(), EMPTY_STRING);
        Role.RoleName roleNameAdmin = protectedUrlsAdmin.get(requestedUrl);
        Role.RoleName roleNameUser = protectedUrlsUser.get(requestedUrl);
        if (roleNameUser == null && roleNameAdmin == null) {
            processAuthentication(chain, req, resp);
        }
        Long userId = (Long) req.getSession().getAttribute("userId");
        User user = userService.get(userId);
        if (verifyRole(user, roleNameAdmin) || verifyRole(user, roleNameUser)) {
            processAuthentication(chain, req, resp);
        } else {
            processDenied(req, resp);
        }
    }

    private void processDenied(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp").forward(req, resp);
    }

    private boolean verifyRole(User user, Role.RoleName roleName) {
        return user.getRoles().stream().anyMatch(r -> r.getRoleName().equals(roleName));
    }

    private void processAuthentication(FilterChain chain,
                                       HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
