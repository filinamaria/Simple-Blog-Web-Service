/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Service;

import Database.*;

import com.firebase.client.*;
import com.firebase.client.snapshot.Node;
import com.shaded.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Date;
import java.util.Calendar;
import Database.*;
import static java.lang.System.console;

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
                post.setAuthor(result.get(key).get("tanggal"));
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
    public String search(@WebParam(name = "id") String id) {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //System.out.println(snapshot.getValue());
                var = snapshot.getValue().toString();
                var = postResults(var);
                System.out.println("finish");
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });
        System.out.println("EXIT");
        return var+" PING";
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
        
}
