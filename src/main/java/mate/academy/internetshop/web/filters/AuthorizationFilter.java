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

import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.models.User;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class AuthorizationFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(AuthorizationFilter.class);

    public static final String EMPTY_STRING = "";

    @Inject
    private static UserService userService;

    private Map<String, String> protectedUrls = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectedUrls.put("/servlet/getAllUsers", "ADMIN");
        protectedUrls.put("/servlet/deleteUser", "ADMIN");
        protectedUrls.put("/servlet/createItem", "ADMIN");
        protectedUrls.put("/servlet/deleteOrder", "ADMIN");
        protectedUrls.put("/servlet/addItemToBucket", "USER");
        protectedUrls.put("/servlet/bucket", "USER");
        protectedUrls.put("/servlet/deleteItemFromBucket", "USER");
        protectedUrls.put("/servlet/completeOrder", "USER");
        protectedUrls.put("/servlet/getAllOrders", "USER");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String requestedUrl = req.getRequestURI().replace(req.getContextPath(), EMPTY_STRING);
        String roleName = protectedUrls.get(requestedUrl);
        if (roleName == null) {
            processAuthentication(chain, req, resp);
            return;
        }
        Long userId = (Long) req.getSession().getAttribute("userId");
        User user = null;
        try {
            user = userService.get(userId);
        } catch (DataProcessingException e) {
            LOGGER.error("Can't get user:", e);
            req.getRequestDispatcher("/WEB-INF/views/errorDb.jsp").forward(req, resp);
        }
        if (verifyRole(user, roleName)) {
            processAuthentication(chain, req, resp);
        } else {
            processDenied(req, resp);
        }
    }

    private void processDenied(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp").forward(req, resp);
    }

    private boolean verifyRole(User user, String roleName) {
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
