package travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import travel.domain.Category;
import travel.service.CategoryService;
import travel.service.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
        CategoryService categoryService =new CategoryServiceImpl();

    public void   findAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Category> all = categoryService.findAll();
        writeValueString(all,response);

      //  writeValue(all,response);
    }
}
