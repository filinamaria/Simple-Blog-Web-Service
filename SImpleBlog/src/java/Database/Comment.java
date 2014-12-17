/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Database;

import java.util.List;
import service.Exception_Exception;
import service.Post;
import service.*;
/**
 *
 * @author Sakurai
 */
public class Comment {
    private String name;
    private String email;
    private String date;
    private String comment;
    
    public Comment(){
        
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) throws Exception_Exception {
        this.comment = comment;
        List<Post> listPost = listPost("aaa");
    }

    private static java.util.List<service.Post> listPost(java.lang.String status) throws Exception_Exception {
        service.Service_Service service = new service.Service_Service();
        service.Service port = service.getServicePort();
        return port.listPost(status);
    }
    
    
    
}
