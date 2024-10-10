package sg.edu.nus.ophone.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

//code by Team3.Chen Sirui
@Component
public class LoginInterceptor implements HandlerInterceptor {
    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(LoginInterceptor.class);
    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, Object handler) throws Exception {
        Logger.info("LoginInterceptor request: {}", request.getRequestURI());
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        //NOTICE! username may be very important, I recommend to add this attribute to Class User
        if (username == null) {
            //this redirection url may need modification
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
