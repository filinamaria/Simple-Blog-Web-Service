/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Service;

import com.firebase.client.*;
import java.util.HashMap;
import java.util.Map;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.Date;
import java.util.Calendar;
/**
 *
 * @author Sakurai
 */
@WebService(serviceName = "Service")
public class Service {
    
    private Firebase ref = new Firebase("https://simpleblog5.firebaseio.com/");
    private Firebase refpost = new Firebase("https://simpleblog5.firebaseio.com/post/");
    /**
     * addPost web service operation
     */
    @WebMethod(operationName = "addPost")
    public boolean addPost(@WebParam(name = "judul") String judul, @WebParam(name = "konten") String konten, @WebParam(name = "tanggal") String tanggal, @WebParam(name = "author") String author) {
        Firebase postReference = ref.child("post");
        Map<String, String> post = new HashMap<String, String>();
        
        post.put("judul", judul);
        post.put("konten", konten);
        post.put("tanggal", tanggal);
        post.put("author", author);
        post.put("status", "unpublished");
        
        postReference.push().setValue(post);
        return true;
    }
    
    /**
     * listPost web service operation
     */
    @WebMethod(operationName = "listPost")
    public String listPost(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
    
    /**
     * editPost web service operation
     */
    @WebMethod(operationName = "editPost")
    public boolean editPost(@WebParam(name = "id") String id, @WebParam(name = "judul") String judul, @WebParam(name = "konten") String konten) {
        Firebase postReference = ref.child("post/" + id);
        Map<String, Object> updatedPost = new HashMap<String, Object>();
        
        updatedPost.put("judul", judul);
        updatedPost.put("konten", konten);
        
        postReference.updateChildren(updatedPost);
        return true;
    }
    
    /**
     * deletePost web service operation
     */
    @WebMethod(operationName = "deletePost")
    public boolean deletePost(@WebParam(name = "id") String id) {
        Firebase postReference = ref.child("post/" + id);
        postReference.removeValue();
        return true;
    }
    
    /**
     * publishPost web service operation
     */
    @WebMethod(operationName = "publishPost")
    public String publishPost(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
    
    /**
     * addUser web service operation
     */
    @WebMethod(operationName = "addUser")
    public String addUser(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
    
    /**
     * listUser web service operation
     */
    @WebMethod(operationName = "listUser")
    public String listUser(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
    
    /**
     * editUser web service operation
     */
    @WebMethod(operationName = "editUser")
    public String editUser(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
    
    /**
     * deleteUser web service operation
     */
    @WebMethod(operationName = "deleteUser")
    public String deleteUser(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
    
    /**
     * addComment web service operation
     */
    @WebMethod(operationName = "addComment")
    public String addComment(@WebParam(name = "id") String id,@WebParam(name="nama") String nama,@WebParam(name="email") String email,@WebParam(name="komentar") String komentar) {
        Firebase postReference = refpost.child("/"+id+"/komentar");
        Map<String, String> komenkomentar = new HashMap<String, String>();
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        String tanggal = day+"-"+month+"-"+year;
        komenkomentar.put("nama", nama);
        komenkomentar.put("email", email);
        komenkomentar.put("tanggal",tanggal);
        komenkomentar.put("komentar", komentar);
        
        postReference.push().setValue(komenkomentar);
        return "Hello Success!";
    }
    
    /**
     * listComment web service operation
     */
    @WebMethod(operationName = "listComment")
    public String listComment(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
    
    /**
     * deleteComment web service operation
     */
    @WebMethod(operationName = "deleteComment")
    public String deleteComment(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
    
    /**
     * search web service operation
     */
    @WebMethod(operationName = "search")
    public String search(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
}
