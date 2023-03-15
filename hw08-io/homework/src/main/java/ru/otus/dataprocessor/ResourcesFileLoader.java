package ru.otus.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import ru.otus.model.Measurement;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ResourcesFileLoader implements Loader {
    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        List<Measurement> resultList;
        var gson = new Gson();
        try(var reader = ResourcesFileLoader.class.getClassLoader().getResourceAsStream(fileName);
            var jsonReader = gson.newJsonReader(new InputStreamReader(reader))) {
            Type listType = new TypeToken<ArrayList<Measurement>>(){}.getType();
            resultList = gson.fromJson(jsonReader, listType);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
        return resultList;
    }

}
