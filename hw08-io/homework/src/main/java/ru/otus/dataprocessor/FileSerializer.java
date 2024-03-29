package ru.otus.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FileSerializer implements Serializer {
    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        //формирует результирующий json и сохраняет его в файл
        var gson = new Gson();
        final JsonElement jsonElement = gson.toJsonTree(data);
        try(JsonWriter jsonWriter = gson.newJsonWriter(new FileWriter(fileName))) {
            gson.toJson(jsonElement, jsonWriter);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }

}
