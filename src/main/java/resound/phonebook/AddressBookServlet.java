package resound.phonebook;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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
    	addressBook = new AddressBook();
    	try {
			addressBook.addContact("first", "1234", "comm");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handle(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		handle(request, response);
	}

	private void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");
		String target = request.getRequestURI().substring(request.getContextPath().length());
		
		String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
		String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try (Connection connection = DriverManager.getConnection(
				"jdbc:mysql://"+host+":3306/phonebook3", "adminXU9chNa", "pyd5AUHSEa7H")){
			Statement statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO addresses VALUES('nm1','phn1','cmnt1')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(host+" "+port);
			e.printStackTrace();
		}

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
	}
	
	private void handleView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
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
	
	private void handleAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
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
	
	private void handleEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String name = request.getParameter("name");
		if(request.getParameter("edited")!=null){
			String eName = (String) request.getParameter("name");
			String eNumber = (String) request.getParameter("number");
			String eComment = (String) request.getParameter("comment");
			addressBook.editContact(name, eName, eNumber, eComment);
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
	
	private void handleRemove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String name = request.getParameter("name");
		Contact contact = addressBook.getContact(name);
		addressBook.remove(name);
		request.setAttribute("message", "Removed: "+contact);
		handleView(request, response);
	}
	
	private void handleAuth(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
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
	
	private void handleLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().invalidate();
		response.sendRedirect(request.getContextPath());
	}

	private void outputPage(String JSPName, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsps/" + JSPName);
		dispatcher.forward(request, response);
	}
}
