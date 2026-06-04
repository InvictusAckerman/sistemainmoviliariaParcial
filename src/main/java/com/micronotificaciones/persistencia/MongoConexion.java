package com.micronotificaciones.persistencia;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoConexion {

    private static MongoConexion instancia;
    private MongoClient mongoClient;
    private MongoDatabase database;

    private static final String MONGO_URI = "mongodb://localhost:27017";
    private static final String DB_NAME   = "ms-notificaciones";
    private static final String COLECCION = "notificaciones";

    private MongoConexion() {
        mongoClient = MongoClients.create(MONGO_URI);
        database    = mongoClient.getDatabase(DB_NAME);
    }

    public static synchronized MongoConexion getInstance() {
        if (instancia == null) instancia = new MongoConexion();
        return instancia;
    }

    public MongoCollection<Document> getColeccion() {
        return database.getCollection(COLECCION);
    }
}