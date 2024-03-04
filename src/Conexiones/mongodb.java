package src.Conexiones;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
public mongodb{

String connectionString = "mongodb://localhost:27017";

        MongoClient mongoClient = new MongoClient(new MongoClientURI(connectionString));
        MongoDatabase database = mongoClient.getDatabase("YourDatabaseName");
}
