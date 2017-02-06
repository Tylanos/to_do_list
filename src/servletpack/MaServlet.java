package servletpack;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Message;

/**
 * Servlet implementation class MaServlet
 */
@WebServlet("/MaServlet")
public class MaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		chargerDonnees();
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.getOutputStream().println("okgo");
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Message newMess = new Message();
		newMess.setMessage(request.getParameter("message"));

	}
	
	public List<Message> chargerDonnees() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ToDoList");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Message> maListe = em.createQuery("SELECT m FROM Message m").getResultList();
        em.getTransaction().commit();
        em.close();
        emf.close();
        
        //affichage liste
        if (maListe.isEmpty()) {
        	System.out.println("pas de message enregistré");
        }
        else {
        	System.out.println("Liste des messages");
        	for (Message m : maListe) {
        		System.out.println(m.getMessage());
        	}
        }
        
        return maListe;
	}
	
	

}
