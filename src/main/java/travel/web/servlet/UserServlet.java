package travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import travel.domain.ResultInfo;
import travel.domain.User;
import travel.service.UserService;
import travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService service = new UserServiceImpl();

    public void register(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //验证码校验
        String check = request.getParameter("check");
        String check_server= (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        request.getSession().removeAttribute("CHECKCODE_SERVER");
        ResultInfo info =new ResultInfo();

        if (check_server!=null&&check.equalsIgnoreCase(check_server)){
            //获取参数
            Map<String, String[]> map = request.getParameterMap();
            //封装对象
            User user =new User();
            try {
                BeanUtils.populate(user,map);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //注册
            String flag = service.register(user);
            System.out.println(flag);
            if(flag.equals("success")){
                //注册成功
                info.setFlag(true);
            }
            if (flag.equals("name_error")){
                //注册失败
                info.setFlag(false);
                info.setErrorMsg("真实姓名重复");
            }
            if (flag.equals("tel_error")){
                info.setFlag(false);
                info.setErrorMsg("手机号重复");
            }
        }else {
            info.setErrorMsg("验证码错误");
        }

       /* ObjectMapper mapper =new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);*/

        writeValue(info,response);
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String check = request.getParameter("check");

        String check_server= (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        request.getSession().removeAttribute("CHECKCODE_SERVER");

        if (check_server==null||!check.equalsIgnoreCase(check_server)){
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            ObjectMapper mapper = new ObjectMapper();
            response.setContentType("application/json;charset=utf-8");
            mapper.writeValue(response.getOutputStream(),info);
            return;
        }

        //1.获取用户名和密码数据
        Map<String, String[]> map = request.getParameterMap();
        //2.封装User对象
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //3.调用Service查询
        User u  = service.login(user);

        ResultInfo info = new ResultInfo();

        //4.判断用户对象是否为null
        if(u == null){
            //用户名密码或错误
            info.setFlag(false);
            info.setErrorMsg("用户名密码或错误");
        }

        //5.判断登录成功
        if(u != null){
            request.getSession().setAttribute("user",u);//登录成功标记
            //登录成功
            info.setFlag(true);
        }
        //响应数据
        writeValue(info,response);
    }

    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //从session中获取登录用户
        Object user = request.getSession().getAttribute("user");
        //将user写回客户端

       /* ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),user);*/
        writeValue(user,response);
    }
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.销毁session
        request.getSession().invalidate();

        //2.跳转登录页面
        response.sendRedirect(request.getContextPath()+"/login.html");
    }

    public String  error() throws Exception {
        throw new Exception();
    }
}
