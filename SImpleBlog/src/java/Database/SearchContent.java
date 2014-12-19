/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import Login.Login;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceRef;
import service.Exception_Exception;
import service.Post;
import service.Service_Service;

/**
 *
 * @author Rikysamuel
 */
@ManagedBean(name="SearchCont", eager = true)
@SessionScoped
public class SearchContent {
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/intense-shore-8980.herokuapp.com/HelloService.wsdl")
    private Service_Service service;
    List<service.Post> listpost = new ArrayList<>();
    
    public SearchContent(){
        
    }

    public List<service.Post> getListpost() {
        return listpost;
    }

    public void setListpost(List<service.Post> listpost) {
        this.listpost = listpost;
    }
    
    public void getPost() throws Exception_Exception, IOException{
        HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        listpost = search(request.getParameter("search"));
//        System.out.println(getSearchContent().get(0).getAuthor());
//        System.out.println(getSearchContent().get(0).getJudul());
        ExternalContext extCont = FacesContext.getCurrentInstance().getExternalContext();
        extCont.redirect("/SImpleBlog/Search.xhtml");
    }

    private java.util.List<service.Post> search(java.lang.String katakunci) throws Exception_Exception {
        // Note that the injected javax.xml.ws.Service reference as well as port objects are not thread safe.
        // If the calling of port operations may lead to race condition some synchronization is required.
        service.Service port = service.getServicePort();
        return port.search(katakunci);
    }
    
}
