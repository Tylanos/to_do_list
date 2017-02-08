package servletpack;

import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Message;

/**
 * Servlet implementation class AjoutDonnee
 */
@WebServlet("/AjoutDonnee")
public class AjoutDonnee extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjoutDonnee() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("ajoutMessage.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletInputStream sin = request.getInputStream();
		//lecture du flux en entree
		byte[] input = new byte[request.getContentLength()];
		sin.read(input, 0, input.length);
		sin.close();
		String messageRecu = new String(input);
		response.setStatus(HttpServletResponse.SC_OK);
		
		//ajout du message en base
		Message msg = new Message();
		msg.setMessage(messageRecu);
		this.nouveauMessage(msg);
		
		//ouverture du flux de sortie
		OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
		writer.write("message ajouté !");
	
	}
	
	public void nouveauMessage(Message message) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ToDoList");
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        em.persist(message);
        em.getTransaction().commit();
        
        em.close();
        emf.close();
	}

}
