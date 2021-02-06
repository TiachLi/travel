package travel.web.servlet;

import travel.domain.PageBean;
import travel.domain.Route;
import travel.domain.User;
import travel.service.FavoriteService;
import travel.service.impl.FavoriteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/favorite/*")
public class FavoriteServlet extends BaseServlet {
private FavoriteService favoriteService =new FavoriteServiceImpl();

    public void showAll(HttpServletRequest request, HttpServletResponse response) throws IOException{
        User user= (User)request.getSession().getAttribute("user");
        String currentPageStr = request.getParameter("currentPage");
        int currentPage=1;
        if (currentPageStr!=null&&currentPageStr.length()>0&&!currentPageStr.equals("null")){
            currentPage=Integer.parseInt(currentPageStr);
        }

        int uid=1;
        if (user==null){
            return;
        }
        else {
            uid=user.getUid();
        }
        PageBean<Route> pageBean =new PageBean<Route>();
        pageBean=favoriteService.FavoritePageQuery(uid,pageBean,currentPage);
        writeValue(pageBean,response);
    }

    public void rank(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String currentPageStr = request.getParameter("currentPage");
        int currentPage=1;
        if (currentPageStr!=null&&currentPageStr.length()>0&&!currentPageStr.equals("null")){
            currentPage=Integer.parseInt(currentPageStr);
        }
        PageBean<Route> pageBean =new PageBean<Route>();
        pageBean=favoriteService.RankPageQuery(pageBean,currentPage);
        writeValue(pageBean,response);
    }

}
