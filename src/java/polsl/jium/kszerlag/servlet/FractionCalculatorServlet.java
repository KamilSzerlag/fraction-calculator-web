package polsl.jium.kszerlag.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import polsl.jium.kszerlag.model.arithmetic.fraction.Fraction;
import polsl.jium.kszerlag.model.arithmetic.fraction.FractionArithmeticException;
import polsl.jium.kszerlag.model.evaluator.EvaluationExpressionException;
import polsl.jium.kszerlag.model.evaluator.SimpleFractionExpressionEvaluator;

/**
 * Servlet class supporting http request handling for web
 * and fraction calculator functionalities
 * 
 * @version 1.0
 * @author Kamil Szerląg
 */
public class FractionCalculatorServlet extends HttpServlet {
    
    /**
     * Member used to calculate expression
     */
    private SimpleFractionExpressionEvaluator evaluator;
    
    /**
     * Initializing member variables 
     * @throws ServletException if Servlet error occurs
     */
    @Override
    public void init() throws ServletException {
        evaluator = new SimpleFractionExpressionEvaluator();
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
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Kalkulator ułamków</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<div style=\"position: absolute; left: 40%;\">");
            out.println("<h1>Kalkulator ułamkowy</h1>");
            out.println("<div>");
            out.println("<label>Wyrażenie:</label>");
            out.println("<form name=\"calculate\" action=\"FractionCalculatorServlet\">");
            out.println("<input type=\"text\" name=\"expression\" value=\"" + getInitParameter("defaultExpression") + "\" minlenght=\"3\" size=\"10\" required />");
            out.println("<input type=\"submit\" value=\"Oblicz\" />");
            out.println("</form>");
            
            String lastCalculation = fetchCookieElseDefault(request.getCookies(), "last_calculation", getInitParameter("noCalculations"));
            String expression = request.getParameter("expression");
            String result = null;
            String exception = null;
            try {
                if (expression != null) {
                    expression = expression.trim();
                    Fraction fractionResult = evaluator.eval(expression);
                    if (fractionResult == null) {
                        out.println("<h1 style=\"color: red\">Wprowadzone wyrażenie jest niepoprawne!</h1>");
                    } else {
                        result = fractionResult.toString();
                    }
                }
                if (result == null) {
                    result = getInitParameter("defaultResult");
                }
                out.println("<h1>Wynik obliczeń:</br>" + (expression != null && !expression.isEmpty() ? expression + " = " + result : result) + "</h1>");
            } catch (EvaluationExpressionException | FractionArithmeticException e) {
                out.println("<h1 style=\"color: red\"> Wprowadzone wyrażenie jest niepoprawne! </h1>");
                e.printStackTrace();
                exception = e.toString();
            }
            
            if (result != null && expression != null && !expression.isEmpty()) {
                Cookie cookie = new Cookie("last_calculation", expression + " = " + result);
                response.addCookie(cookie);
            }
            out.println("<h3>Poprzednio obliczone:</br>" + lastCalculation + "</h3>");
            out.println("<form action=" + request.getContextPath() +">");
            out.println("<input type=\"submit\" value=\"Wyczyść wynik\"/>");
            out.println("</form>");
            out.println("</div>");
            out.println("</div>");
            if (exception != null) {
                out.println("<h4 style=\"color: red\">Exception: " + exception + "</p>");
            }
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * 
     * @param cookies array of Cookie objects
     * @param cookieName name of cookie which should be retrived
     * @param defaultValue default String value when cookie is not present
     * @return cookie value if is present, else return default value
     */
    private String fetchCookieElseDefault(Cookie[] cookies, String cookieName,  String defaultValue) {
        if (cookies != null) {
            for (Cookie cooky : cookies) {
                if (cooky.getName().equals(cookieName)) {
                    return cooky.getValue();
                }
            }
        }
        return defaultValue;
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
        processRequest(request, response);
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
        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet supporting http request handling for web and fraction calculator functionalities";
    }// </editor-fold>

}
