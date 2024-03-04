package src.Conexiones;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
public class mongodb{
public mongodb(String databaseName){
String connectionString = "mongodb://localhost:27017";

        MongoClient mongoClient = new MongoClient(new MongoClientURI(connectionString));
        MongoDatabase database = mongoClient.getDatabase(databaseName);
}
}
