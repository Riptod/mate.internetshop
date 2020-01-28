package mate.academy.internetshop.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.service.ItemService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class GetAllItemsController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(GetAllItemsController.class);

    @Inject
    private static ItemService itemService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            req.setAttribute("items", itemService.getAll());
        } catch (DataProcessingException e) {
            LOGGER.error("Can't get all items", e);
            req.getRequestDispatcher("/WEB-INF/views/errorDb.jsp").forward(req, resp);
        }
        req.getRequestDispatcher("/WEB-INF/views/allItems.jsp").forward(req, resp);
    }
}
