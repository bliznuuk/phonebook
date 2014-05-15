package resound.phonebook;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class FilterAuth
 */
@WebFilter({ "/add", "/edit", "/delete" })
public class FilterAuth implements Filter {

    /**
     * Default constructor. 
     */
    public FilterAuth() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hReq = (HttpServletRequest)request;
		String login = (String) hReq.getSession().getAttribute("auth");
//		request.setAttribute("message", "arg1");
		if(login==null){
			request.setAttribute("action", "outh");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/auth");
			dispatcher.forward(request, response);
		}else
			chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
