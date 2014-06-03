package view;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * <code>AdministrationFilter</code> is an filter class which filters all the
 * request sent for administration user interface.
 * 
 * @author Rahman Firouzi
 */
public class AssistanceFilter implements Filter {

	@Override
	public void destroy() {
	}
/**
 * method filters every new request
 * @param request
 * @param response
 * @param chain
 * @throws IOException
 * @throws ServletException 
 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		UserManager manager = (UserManager) req.getSession().getAttribute("userManager");

		if (manager != null && manager.isLoggedIn() && manager.isAssistance()) {
			chain.doFilter(request, response);
		} else {
			// User is not logged in, so redirect to index.
			HttpServletResponse res = (HttpServletResponse) response;
			res.sendRedirect(req.getContextPath() + "/index");
		}
                        
                        
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
}
