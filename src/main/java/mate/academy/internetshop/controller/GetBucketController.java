package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.service.BucketService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class GetBucketController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(GetBucketController.class);

    @Inject
    private static BucketService bucketService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long userId = (Long) req.getSession().getAttribute("userId");
        try {
            req.setAttribute("items", bucketService.getAllItems(bucketService.getByUser(userId)));
        } catch (DataProcessingException e) {
            LOGGER.error("Can't get all items from bucket:", e);
            req.setAttribute("errorMsg", "Can't get all items from bucket");
            req.getRequestDispatcher("/WEB-INF/views/errorDb.jsp").forward(req, resp);
        }
        req.getRequestDispatcher("/WEB-INF/views/bucket.jsp").forward(req, resp);
    }
}
