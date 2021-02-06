package travel.web.servlet;

import travel.domain.PageBean;
import travel.domain.Route;
import travel.domain.User;
import travel.service.FavoriteService;
import travel.service.RouteService;
import travel.service.impl.FavoriteServiceImpl;
import travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService routeService =new RouteServiceImpl();
    private FavoriteService favoriteService=new FavoriteServiceImpl();
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*
            private int totalCount;//总记录数
            private int totalPage;//总页数
            private int currentPage;//当前页码
            private int pageSize;//每页显示的条数
            private List<T> list;//每页显示的数据集合
         */

        //获取参数
        String cidStr = request.getParameter("cid");
        String currentPageStr = request.getParameter("currentPage");
        String rname = request.getParameter("rname");
        int pageSize =5;

        int cid=0;
        int currentPage=0;

        //处理数据
        if (cidStr==null||cidStr.length()<=0||cidStr.equals("null")){
            cid=0;
        }
        else {
            cid=Integer.parseInt(cidStr);
        }

        if (currentPageStr==null||currentPageStr.length()<=0){
            currentPage=1;
        }
        else {
            currentPage=Integer.parseInt(currentPageStr);
        }
        if (rname.equals("null")){
            rname=null;
        }
        //封装对象
        PageBean<Route> pb =routeService.pageQuery(cid, currentPage, pageSize,rname);

        //返回数据
        writeValue(pb,response);
    }

    public void findOne(HttpServletRequest request, HttpServletResponse response) throws IOException{
        //获取数据
        String rid = request.getParameter("rid");

        Route route=routeService.findOne(rid);
        writeValue(route,response);
    }
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user");
        int uid=0;
        if (user==null){
            return;
        }else{
            uid=user.getUid();
        }

        boolean flag= routeService.isFavorite(rid,uid);
        writeValue(flag,response);
    }
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException{
        User user = (User) request.getSession().getAttribute("user");
        int uid=0;
        if (user==null){
            return;
        }else{
            uid=user.getUid();
        }
        String rid = request.getParameter("rid");
        favoriteService.add(rid,uid);
    }

}
