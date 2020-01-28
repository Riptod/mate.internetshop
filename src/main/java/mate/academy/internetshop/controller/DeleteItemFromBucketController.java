package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class DeleteItemFromBucketController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(DeleteItemFromBucketController.class);

    @Inject
    private static BucketService bucketService;
    @Inject
    private static ItemService itemService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String itemId = req.getParameter("item_id");
        Long userId = (Long) req.getSession().getAttribute("userId");
        try {
            bucketService.deleteItem(bucketService.getByUser(userId), itemService
                    .get(Long.valueOf(itemId)));
        } catch (DataProcessingException e) {
            LOGGER.error("Can't delete item", e);
            req.getRequestDispatcher("/WEB-INF/views/errorDb.jsp").forward(req, resp);
        }
        resp.sendRedirect(req.getContextPath() + "/servlet/bucket");
    }
}
