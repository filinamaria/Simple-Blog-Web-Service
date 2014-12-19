<%-- 
    Document   : loadcomments
    Created on : Nov 24, 2014, 9:39:46 PM
    Author     : Sakurai
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>


<%
    String postID = request.getParameter("ID");
    if ( postID != null) {
        try {
            service.Service_Service service = new service.Service_Service();
            service.Service port = service.getServicePort();

            List<service.Comment> comments = new ArrayList<service.Comment>();
            comments = port.listComment(postID);

            for(service.Comment s : comments){
                String name = s.getName();
                String comment = s.getComment();
                String date = s.getDate();
                out.println("<li class=\"art-list-item\">");
                out.println("<div class=\"art-list-item-title-and-time\">");
                out.println("<h2 class=\"art-list-title\">" + name +"</h2>");
                out.println("<div class=\"art-list-time\">" + date + "</div>");
                out.println("</div>");
                out.println("<p>");
                out.println(comment);
                out.println("</p>");
                out.println("</li>");
            }
        }catch(Exception ex){
            System.err.println(ex);
        }
    }
  
%>