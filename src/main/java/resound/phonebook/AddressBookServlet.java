package resound.phonebook;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AddressBookServlet
 */
@WebServlet("/")
public class AddressBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String LOGIN = "user";
	private static final String PASSWORD = "pass";
	private AddressBook addressBook;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddressBookServlet() {
        super();
    }
    
    @Override
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	try {
        	addressBook = new AddressBook();
			addressBook.addContact("first", "1234", "comm");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		handle(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		handle(request, response);
	}

	private void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException{
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String target = request.getRequestURI().substring(request.getContextPath().length());

		try{
			switch (target) {
			case "/":
				outputPage("index.jsp", request, response);
				break;
			case "/auth":
				handleAuth(request, response);
				break;
			case "/logout":
				handleLogout(request,response);
				break;
			case "/view":
				handleView(request, response);
				break;
			case "/add":
				handleAdd(request, response);
				break;
			case "/edit":
				handleEdit(request, response);
				break;
			case "/remove":
				handleRemove(request, response);
				break;
			default:
				break;
			}		
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	private void handleView(HttpServletRequest request, HttpServletResponse response) throws ServletException, SQLException{
		Map<String, String> numbers = new HashMap<String, String>();
		Map<String, String> comments = new HashMap<String, String>();
		for(Map.Entry<String, Contact> entry : addressBook.getContacts().entrySet()){
			numbers.put(entry.getKey(), entry.getValue().getNumber());
			comments.put(entry.getKey(), entry.getValue().getComment());
		}
		request.setAttribute("numbers", numbers);
		request.setAttribute("comments", comments);
		outputPage("view.jsp", request, response);
	}
	
	private void handleAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, SQLException{
		if(request.getParameter("name")==null){
			request.setAttribute("name", "");
			request.setAttribute("number", "");
			request.setAttribute("comment", "");
			request.setAttribute("action", "add");
			outputPage("edit.jsp", request, response);
		}else{
			String name = request.getParameter("name");
			String number = request.getParameter("number");
			String comment = request.getParameter("comment");
			if( !(!name.equals("") & (!number.equals("") | !comment.equals(""))) ){
				request.setAttribute("message", "Input name and number or comment!");
				request.setAttribute("action", "add");
				outputPage("edit.jsp", request, response);
			}else{
				addressBook.addContact(name, number, comment);
				request.setAttribute("message", "Contact was added");
				outputPage("index.jsp", request, response);
			}
		}
	}
	
	private void handleEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, SQLException{
		String name = request.getParameter("name");
		if(request.getParameter("edited")!=null){
			String eNumber = (String) request.getParameter("number");
			String eComment = (String) request.getParameter("comment");
			addressBook.editContact(name, eNumber, eComment);
			handleView(request, response);
		}else if(name!=null){ 
			Contact contact = addressBook.getContacts().get(name);
			request.setAttribute("edit.name", name);
			request.setAttribute("edit.number", contact.getNumber());
			request.setAttribute("edit.comment", contact.getComment());
			request.setAttribute("action", "edit");
			outputPage("edit.jsp", request, response);
		}
		
		
	}
	
	private void handleRemove(HttpServletRequest request, HttpServletResponse response) throws ServletException, SQLException{
		String name = request.getParameter("name");
		Contact contact = null;
		contact = addressBook.getContact(name);
		addressBook.remove(name);
		request.setAttribute("message", "Removed: "+contact);
		handleView(request, response);
	}
	
	private void handleAuth(HttpServletRequest request, HttpServletResponse response) throws ServletException{
		String login = request.getParameter("login");
		String pass = request.getParameter("password");
		if(login!=null & pass!=null){
			if(login.equals(LOGIN) & pass.equals(PASSWORD)){
				HttpSession session = request.getSession(true);
				session.setAttribute("auth", login);
				//request.setAttribute("message, "");
				outputPage("index.jsp", request, response);
			}else{
				request.setAttribute("message", "Wrong Login or Password");
				outputPage("auth.jsp", request, response);
			}
		}else{
			request.setAttribute("message", "Input Login and Password!");
			outputPage("auth.jsp", request, response);
		}
	}
	
	private void handleLogout(HttpServletRequest request, HttpServletResponse response){
		request.getSession().invalidate();
		try {
			response.sendRedirect(request.getContextPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void outputPage(String JSPName, HttpServletRequest request, HttpServletResponse response) throws ServletException{
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsps/" + JSPName);
		try {
			dispatcher.forward(request, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
