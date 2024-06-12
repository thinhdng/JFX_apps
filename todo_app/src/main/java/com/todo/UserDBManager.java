package com.todo;
import com.mongodb.MongoException;
import com.mongodb.MongoWriteConcernException;
import com.mongodb.MongoWriteException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.FindIterable;
import org.bson.Document;

public class UserDBManager {
    public static String uri = "mongodb+srv://thinhdnguyen:tn13ad41@cluster1.kfwsmpo.mongodb.net/?retryWrites=true&w=majority&appName=Cluster1";
    // Create a MongoDB client
    MongoClient mongoClient = MongoClients.create(UserDBManager.uri);
    // Connect to the database
    MongoDatabase database = mongoClient.getDatabase("userData");
    boolean userExisted = false;
    public boolean addUser(String usn, String pwd){
        MongoCollection<Document> collection = database.getCollection("User");
        Document userLogin = new Document("username", usn).append("password", pwd);
        //ensure unique usernames
        collection.createIndex(new Document("username", 1), new IndexOptions().unique(true));
        try {
            collection.insertOne(userLogin);
            System.out.println("Document inserted successfully");
        } catch (MongoWriteException e) {
            if (e.getError().getCategory().equals(com.mongodb.ErrorCategory.DUPLICATE_KEY)) {
                userExisted = true;
                return false;
            } else {
                // Other write error
                System.out.println("Write error: " + e.getMessage());
                return false;
            }
        } catch (MongoWriteConcernException e) {
            // Write concern error
            System.out.println("Write concern error: " + e.getMessage());
            return false;
        } catch (MongoException e) {
            // Other MongoDB exception
            System.out.println("MongoDB exception: " + e.getMessage());
            return false;
        }
            return true;
    }

    public boolean validateUser(String usn, String pwd){
        MongoCollection<Document> collection = database.getCollection("User");
        Document userLogin = new Document("username", usn);
        FindIterable<Document> result = collection.find(userLogin).limit(1);
        //find if username exists. if yes, check password. if not, return false.
        if(result.iterator().hasNext()){
            if(pwd.equals(result.first().getString("password")))
                return true;
            else
                return false;
        }else{
            return false;
        }
    }
}
