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
import Database.*;

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
    public boolean editPost(@WebParam(name = "postId") String postId, @WebParam(name = "judul") String judul, @WebParam(name = "konten") String konten) {
        Firebase postReference = ref.child("post/" + postId);
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
    public boolean deletePost(@WebParam(name = "postId") String postId) {
        Firebase postReference = ref.child("post/" + postId);
        postReference.removeValue();
        return true;
    }
    
    /**
     * publishPost web service operation
     */
    @WebMethod(operationName = "publishPost")
    public boolean publishPost(@WebParam(name = "postId") String postId) {
        Firebase postReference = ref.child("post/" + postId);
        Map<String, Object> updatedPost = new HashMap<String, Object>();
        
        updatedPost.put("status", "published");
        
        postReference.updateChildren(updatedPost);
        return true;
    }
    
    /**
     * addUser web service operation
     */
    @WebMethod(operationName = "addUser")
    public boolean addUser(@WebParam(name = "username") String username, @WebParam(name = "password") String password, @WebParam(name = "nama") String nama, @WebParam(name = "email") String email, @WebParam(name = "role") String role) {
        Firebase userReference = ref.child("user");
        Map<String, String> newUser = new HashMap<String, String>();
        
        newUser.put("username", username);
        newUser.put("password", password);
        newUser.put("nama", nama);
        newUser.put("email", email);
        newUser.put("role", role);
        
        userReference.push().setValue(newUser);
        return true;
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
    public boolean editUser(@WebParam(name = "userId") String userId, @WebParam(name = "password") String password, @WebParam(name = "nama") String nama, @WebParam(name = "email") String email, @WebParam(name = "role") String role) {
        Firebase userReference = ref.child("user/" + userId);
        Map<String, Object> updatedUser = new HashMap<String, Object>();
        
        if(password != null){
            updatedUser.put("password", password);
        }
        if(nama != null){
            updatedUser.put("nama", nama);
        }
        if(email != null){
            updatedUser.put("email", email);
        }
        if(role != null){
            updatedUser.put("role", role);
        }
        
        userReference.updateChildren(updatedUser);
        return true;
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
     * search web service operation
     */
    @WebMethod(operationName = "search")
    public String search(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
}
