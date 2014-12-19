/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Service;

import Database.*;

import com.firebase.client.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;


import java.util.Date;
import java.util.Calendar;
import java.util.logging.Level;

/**
 *
 * @author Sakurai
 */
@WebService(serviceName = "Service")
public class Service {
    private static String postId;
    
    private Firebase ref = new Firebase("https://simpleblog5.firebaseio.com/");
    private Firebase refpost = new Firebase("https://simpleblog5.firebaseio.com/post/");
    private String var = "asdfasdfasdf";
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
    public List<Post> listPost(@WebParam(name = "status") String status) throws Exception {
        String jsonString = readUrl("https://simpleblog5.firebaseio.com/post.json");
        List<Post> posts = new ArrayList<>();
        
        HashMap<String, Map<String, String>> result = new ObjectMapper().readValue(jsonString, HashMap.class);

        for(String key: result.keySet()){
            if(result.get(key).get("status").equals(status)){
                Post post = new Post();
                post.setId(key);
                post.setJudul(result.get(key).get("judul"));
                post.setContent(result.get(key).get("konten"));
                post.setTanggal(result.get(key).get("tanggal"));
                post.setAuthor(result.get(key).get("author"));
                post.setStatus(result.get(key).get("status"));
                posts.add(post);
            }
        }
        
        return posts;
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
    public boolean addUser(@WebParam(name = "username") String username, @WebParam(name = "password") String password, @WebParam(name = "nama") String nama, @WebParam(name = "email") String email, @WebParam(name = "role") String role) throws Exception {
        Firebase userReference = ref.child("user");
        String jsonString = readUrl("https://simpleblog5.firebaseio.com/user.json");
        boolean Success = true;
        HashMap<String, Map<String, String>> result = new ObjectMapper().readValue(jsonString, HashMap.class);
 
        for(String key: result.keySet()){
            if(result.get(key).get("username").equals(username)){
                // Username telah ada di database
                Success = false;
                break;
            }  
        }
        if(Success){
            Map<String, String> newUser = new HashMap<String, String>();
            newUser.put("username", username);
            newUser.put("password", password);
            newUser.put("nama", nama);
            newUser.put("email", email);
            newUser.put("role", role);
            userReference.push().setValue(newUser);
        }
        return Success;
    }
    
    /**
     * listUser web service operation
     */
    @WebMethod(operationName = "listUser")
    public List<User> listUser() throws Exception {
        String jsonString = readUrl("https://simpleblog5.firebaseio.com/user.json");
        List<User> users = new ArrayList<>();
        
        HashMap<String, Map<String, String>> result = new ObjectMapper().readValue(jsonString, HashMap.class);

        for(String key: result.keySet()){
            User user = new User();
            user.setUsername(result.get(key).get("username"));
            user.setPassword(result.get(key).get("password"));
            user.setName(result.get(key).get("nama"));
            user.setEmail(result.get(key).get("email"));
            user.setRole(result.get(key).get("role"));
            users.add(user);
        }
        
        return users;
    }
    
    /**
     * editUser web service operation
     */
    @WebMethod(operationName = "editUser")
    public boolean editUser(@WebParam(name = "username") String username, @WebParam(name = "password") String password, @WebParam(name = "nama") String nama, @WebParam(name = "email") String email, @WebParam(name = "role") String role) throws Exception {
        String jsonString = readUrl("https://simpleblog5.firebaseio.com/user.json");
        HashMap<String, Map<String, String>> result = new ObjectMapper().readValue(jsonString, HashMap.class);
        String userid = "";
        for(String key: result.keySet()){
            if(result.get(key).get("username").equals(username)){
                userid = key;
                break; // tidak perlu menjalankan search lagi karena username bersifat unik
            }
        }
        
        Firebase userReference = ref.child("user/" + userid);
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
    public boolean deleteUser(@WebParam(name = "username") String username) throws Exception {
        // delete user based on username
        String userJsonString = readUrl("https://simpleblog5.firebaseio.com/user.json");
        
        HashMap<String, Map<String, String>> userMap = new ObjectMapper().readValue(userJsonString, HashMap.class);
        
        for(String key: userMap.keySet()){
            if(userMap.get(key).get("username").equals(username)){
                Firebase userReference = ref.child("user/" + key);
                
                userReference.removeValue();
            }
        }
        
        // detele corresponding user's posts
        String postJsonString = readUrl("https://simpleblog5.firebaseio.com/post.json");
               
        HashMap<String, Map<String, String>> postMap = new ObjectMapper().readValue(postJsonString, HashMap.class);

        for(String key: postMap.keySet()){
            if(postMap.get(key).get("author").equals(username)){
                Firebase postReference = ref.child("post/" + key);
                postReference.removeValue();
            }
        }
        
        return true;
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
    public List<Comment> listComment(@WebParam(name = "postId") String postId) throws Exception {
        String jsonString = readUrl("https://simpleblog5.firebaseio.com/post/" + postId + "/komentar.json");
        List<Comment> comments = new ArrayList<>();
        
        if(!jsonString.equals("null")){
            HashMap<String, Map<String, String>> result = new ObjectMapper().readValue(jsonString, HashMap.class);
        
            for(String key: result.keySet()){
                Comment comment = new Comment();
                comment.setName(result.get(key).get("nama"));
                comment.setEmail(result.get(key).get("email"));
                comment.setDate(result.get(key).get("tanggal"));
                comment.setComment(result.get(key).get("komentar"));
                comments.add(comment);
            }
        }
        
        return comments;
    }
    
    /**
     * search web service operation
     */
    @WebMethod(operationName = "search")
    public List<Post> search(@WebParam(name = "katakunci") String katakunci) throws Exception {
        String jsonString = readUrl("https://simpleblog5.firebaseio.com/post.json");
        List<Post> posts = new ArrayList<>();
        
        HashMap<String, Map<String, String>> result = new ObjectMapper().readValue(jsonString, HashMap.class);

        for(String key: result.keySet()){
//            if(result.get(key).get("judul").equalsIgnoreCase(katakunci) || result.get(key).get("konten").equalsIgnoreCase(katakunci)){
                Post post = new Post();
                post.setId(key);
                post.setJudul(result.get(key).get("judul"));
                post.setContent(result.get(key).get("konten"));
                post.setTanggal(result.get(key).get("tanggal"));
                post.setAuthor(result.get(key).get("tanggal"));
                post.setStatus(result.get(key).get("status"));
                String keyword = katakunci.toLowerCase();
                String judul = post.getJudul().toLowerCase();
                String content = post.getContent().toLowerCase();
                if(judul.contains(keyword)||content.contains(keyword))
                {
                    posts.add(post);
                }
//            }
        }
        
        return posts;
    }
    
    public String postResults(String data){
        return data;
    }
    
    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read); 

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }
    
    /**
     * login web service operation
     */
      @WebMethod(operationName = "login")
      public boolean login(@WebParam(name = "username") String username,@WebParam(name = "password") String password) throws Exception {
        String jsonString = readUrl("https://simpleblog5.firebaseio.com/user.json");
        boolean isfound = false;
        HashMap<String, Map<String, String>> result = new ObjectMapper().readValue(jsonString, HashMap.class);

        for(String key: result.keySet()){
            if(result.get(key).get("username").equals(username) && result.get(key).get("password").equals(password)){
                isfound = true;
                break;
            }
        }
        
        return isfound;
    }
      
	/**
     * getAuthorPost web service operation
     */
    @WebMethod(operationName = "getAuthorPost")
    public List<Post> getAuthorPost(@WebParam(name = "author") String author) throws Exception {
        String jsonString = readUrl("https://simpleblog5.firebaseio.com/post.json");
        List<Post> posts = new ArrayList<>();
        HashMap<String, Map<String, String>> result = new ObjectMapper().readValue(jsonString, HashMap.class);

        for(String key: result.keySet()){
            if(result.get(key).get("author").equals(author) && result.get(key).get("status").equals("published")){
                Post post = new Post();
                post.setId(key);
                post.setJudul(result.get(key).get("judul"));
                post.setContent(result.get(key).get("konten"));
                post.setTanggal(result.get(key).get("tanggal"));
                post.setAuthor(result.get(key).get("author"));
                post.setStatus(result.get(key).get("status"));
                posts.add(post);
            }
        }
        
        return posts;
    }
	/**
     * getUserRole web service operation
     */
    @WebMethod(operationName = "getUserRole")
    public String getUserRole(@WebParam(name = "username") String username) throws Exception {
        String jsonString = readUrl("https://simpleblog5.firebaseio.com/user.json");
        HashMap<String, Map<String, String>> result = new ObjectMapper().readValue(jsonString, HashMap.class);
        String role = "Role not found";
        for(String key: result.keySet()){
            if(result.get(key).get("username").equals(username)){
                role=result.get(key).get("role");
                break;
            }
        }
        return role;
    }
	/**
     * ChangeStatusPost web service operation
     */
    @WebMethod(operationName = "ChangeStatusPost")
    public boolean ChangeStatusPost(@WebParam(name = "postId") String postId, @WebParam(name = "status") String status) {
        Firebase postReference = ref.child("post/" + postId);
        Map<String, Object> updatedPost = new HashMap<String, Object>();
        
        updatedPost.put("status", status);
        
        postReference.updateChildren(updatedPost);
        
        return true;
    }
	
    /**
     * deleteRealPost web service operation
     */
    @WebMethod(operationName = "deleteRealPost")
    public boolean deleteRealPost(@WebParam(name = "postId") String postId) {
        Firebase postReference = ref.child("post/" + postId);       
        postReference.removeValue();
        return true;
    }
    
    /**
     * checkuserexist web service operation
     */
    @WebMethod(operationName = "checkuserexist")
    public int checkuserexist(@WebParam(name = "username") String username, @WebParam(name = "password") String password) throws Exception {
        String jsonString = readUrl("https://simpleblog5.firebaseio.com/user.json");
        HashMap<String, Map<String, String>> result = new ObjectMapper().readValue(jsonString, HashMap.class);
        for(String key: result.keySet()){
            if(result.get(key).get("username").equals(username) && result.get(key).get("password").equals(password)){
                return 1;// tidak perlu menjalankan search lagi karena username bersifat unik
            }
        }
        
        return 0;
    }
    
    /**
     * getnama web service operation
     */
    @WebMethod(operationName = "getnama")
    public String getnama(@WebParam(name = "username") String username) throws Exception {
        String jsonString = readUrl("https://simpleblog5.firebaseio.com/user.json");
        HashMap<String, Map<String, String>> result = new ObjectMapper().readValue(jsonString, HashMap.class);
        String nama = "Failed to get Nama";
        for(String key: result.keySet()){
            if(result.get(key).get("username").equals(username)){
                nama = result.get(key).get("nama");
                break; // tidak perlu menjalankan search lagi karena username bersifat unik
            }
        }
        
        return nama;
    }
    
    /**
     * getemail web service operation
     */
    @WebMethod(operationName = "getemail")
    public String getemail(@WebParam(name = "username") String username) throws Exception {
        String jsonString = readUrl("https://simpleblog5.firebaseio.com/user.json");
        HashMap<String, Map<String, String>> result = new ObjectMapper().readValue(jsonString, HashMap.class);
        String email = "Failed to get email";
        for(String key: result.keySet()){
            if(result.get(key).get("username").equals(username)){
                email = result.get(key).get("email");
                break; // tidak perlu menjalankan search lagi karena username bersifat unik
            }
        }
        
        return email;
    }
    
        /**
     * getPost web service operation
     */
    public Post getPost(@WebParam(name = "postid") String postid) throws Exception {
        String jsonString = readUrl("https://simpleblog5.firebaseio.com/post.json");
        HashMap<String, Map<String, String>> result = new ObjectMapper().readValue(jsonString, HashMap.class);
        Post post = new Post();
        
        for(String key: result.keySet()){
            System.out.println("The key is : "+key);
            if(key.equals(postid)){
                post.setId(key);
                post.setJudul(result.get(key).get("judul"));
                post.setContent(result.get(key).get("konten"));
                post.setTanggal(result.get(key).get("tanggal"));
                post.setAuthor(result.get(key).get("author"));
                post.setStatus(result.get(key).get("status"));
                System.out.println("key : "+key);
                System.out.println("konten : "+post.getContent());
                break; // key bersifat unik yang merupakan hasil generate dari firebase sendiri.
            }
            
        }
        
        return post;
    }


}
