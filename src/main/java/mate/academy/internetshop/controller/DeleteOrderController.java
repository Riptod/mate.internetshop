package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.service.OrderService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class DeleteOrderController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(DeleteOrderController.class);

    @Inject
    private static OrderService orderService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String orderId = req.getParameter("order_id");
        try {
            orderService.delete(Long.valueOf(orderId));
        } catch (DataProcessingException e) {
            LOGGER.error("Can't delete order", e);
            req.setAttribute("errorMsg", "Can't delete order");
            req.getRequestDispatcher("/WEB-INF/views/errorDb.jsp").forward(req, resp);
        }
        resp.sendRedirect(req.getContextPath() + "/servlet/getAllOrders");
    }
}
