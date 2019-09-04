/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gt.edu.academik.fileservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author diego
 */
@WebServlet(name = "FileLoadServlet", urlPatterns = {"/archivo-carga"})
@MultipartConfig (location="/tmp")
public class FileLoadServlet extends HttpServlet {
  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            String pattern = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            
            for ( Part part: request.getParts()) {
                String nameFile = pattern + "-" + part.getSubmittedFileName();
                part.write(nameFile);
            }
            
            this.writeResponse(response, "Cargando satisfactoriamente.");
            
        } catch (IOException | ServletException ex) {
            this.writeResponse(response, "Error al subir el archivo:" + ex.getMessage());
        }
       
    }
    
    private void writeResponse (HttpServletResponse response, String result)throws IOException {
        StringBuilder responseHtml = new StringBuilder();
        
        responseHtml.append("<!DOCTYPE html>");
        responseHtml.append("<html>");
        responseHtml.append("<head>");
        responseHtml.append("<title>Carga de Archivo</title>");
        responseHtml.append("</head>");
        responseHtml.append("<body>");
        responseHtml.append("<h1>Resultado de Carga</h1>");
        responseHtml.append("<strong>").append (result).append("</strong>");
        responseHtml.append("</body>");
        responseHtml.append("</html>");
        
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            out.println(responseHtml.toString());
        }
    }
    

}
