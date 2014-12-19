<%-- 
    Document   : newcomment
    Created on : Nov 25, 2014, 11:30:16 AM
    Author     : Sakurai
--%>

<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>

<% 
    String postID = request.getParameter("ID");
    if ( postID != null) {
        try {
            service.Service_Service service = new service.Service_Service();
            service.Service port = service.getServicePort();

            String Name = request.getParameter("Name");
            String Email = request.getParameter("Email");
            String Comment = request.getParameter("Comment");
            
            port.addComment(postID, Name, Email, Comment);
        }catch(Exception ex){
            System.err.println(ex);
        }
    }
%>