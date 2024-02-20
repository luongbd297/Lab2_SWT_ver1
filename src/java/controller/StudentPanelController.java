/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.ExamScheduleDAO;
import dao.NotificationDAO;
import dao.RegistrationDAO;
import dao.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.ExamSchedule;
import model.Notification;
import model.Registration;
import model.User;
import utils.SessionManager;

/**
 *
 * @author 84354
 */
public class StudentPanelController extends HttpServlet {

    private ExamScheduleDAO examDAO;
    private RegistrationDAO registerDAO;
    private NotificationDAO noteDAO;
    private UserDAO userDAO;

    public void init() {
        examDAO = new ExamScheduleDAO();
        registerDAO = new RegistrationDAO();
        noteDAO = new NotificationDAO();
        userDAO = new UserDAO();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StudentPanelController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StudentPanelController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String mod = request.getParameter("mod");
        String action = request.getParameter("action");
        User user = SessionManager.getSessionUser(request);
        if (mod != null && mod.equals("1")) {
            if (action != null && action.equals("0")) {
                String id = request.getParameter("id");
                ExamSchedule e = examDAO.getExamScheduleById(id);
                Registration r = new Registration("", user.getAccount(), e.getId(), "Processing");
                registerDAO.addRegistration(r);
                r = registerDAO.getRegistrationByAccountAndExamId(user.getAccount(), e.getId());
                Notification n = new Notification("", "admin", "New registration request from " + user.getAccount(), r.getId(), "0");
                noteDAO.addNotification(n);
                ArrayList<Registration> regis = registerDAO.getRegistrationByUser(user.getAccount());
                request.setAttribute("regis", regis);
                request.getRequestDispatcher("RegistedExam.jsp").forward(request, response);
            }
            ArrayList<ExamSchedule> exams = examDAO.getAllExamSchedules();
            ArrayList<ExamSchedule> filteredExams = new ArrayList<>();
            for (ExamSchedule exam : exams) {
                Registration reg = registerDAO.getRegistrationByAccountAndExamId(user.getAccount(), exam.getId());
                if (reg == null || (!reg.getStatus().equals("Processing") && !reg.getStatus().equals("Accepted"))) {
                    filteredExams.add(exam);
                }
            }
            request.setAttribute("exams", filteredExams);
            request.getRequestDispatcher("ListExam.jsp").forward(request, response);

        } else if (mod != null && mod.equals("2")) {
            if (action != null && action.equals("0")) {
                String id = request.getParameter("id");
                Registration regis = registerDAO.getRegistrationByAccountAndExamId(user.getAccount(), id);
                regis.setStatus("Rejected");    
            }
            ArrayList<Registration> regis = registerDAO.getRegistrationByUser(user.getAccount());
            request.setAttribute("regis", regis);
            request.getRequestDispatcher("RegistedExam.jsp").forward(request, response);
        } else if (mod != null && mod.equals("0")) {
            request.getRequestDispatcher("StudentProfile.jsp").forward(request, response);

        } else if (mod != null && mod.equals("3")) {
            if (action != null && action.equals("0")) {
                String id = request.getParameter("id");
                noteDAO.deleteNotification(id);
            }
            ArrayList<Notification> notes = noteDAO.getNotificationsByAccount(user.getAccount());
            request.setAttribute("note", notes);
            request.getRequestDispatcher("StudentNotification.jsp").forward(request, response);
        } else {
            response.sendRedirect("Student.jsp");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = SessionManager.getSessionUser(request);
        if (request.getParameter("updateProfile") != null) {
            String name = request.getParameter("name");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            String address = request.getParameter("address");
            User u = new User(user.getAccount(), user.getPassword(), name, email, phone, address, user.getRole());
            userDAO.updateUser(u);
            SessionManager.createSession(request, u);
            request.setAttribute("user", u);
            request.getRequestDispatcher("StudentProfile.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
