package dev.colibri.githubclienttest.network;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dev.colibri.githubclienttest.entity.Owner;
import dev.colibri.githubclienttest.entity.Repository;

public class JsonParser {

    public ArrayList<Repository> getRepositories(String jsonString) throws JSONException {
        JSONObject json = new JSONObject(jsonString);

        JSONArray items = json.getJSONArray("items");
        ArrayList<Repository> result = new ArrayList<>();

        for (int i = 0; i < items.length(); i++) {
            JSONObject repositoryJson = items.getJSONObject(i);

            Repository repository = getRepository(repositoryJson);

            result.add(repository);
        }

        return result;
    }

    public Repository getRepository(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);

        return getRepository(jsonObject);
    }

    private Repository getRepository(JSONObject repositoryJson) throws JSONException {
        int id = repositoryJson.getInt("id");
        String name = repositoryJson.getString("name");
        String description = repositoryJson.getString("description");
        String createdAt = repositoryJson.getString("created_at");
        String updatedAt = repositoryJson.getString("updated_at");
        int starsCount = repositoryJson.getInt("stargazers_count");
        int forksCount = repositoryJson.getInt("forks_count");
        String language = repositoryJson.getString("language");

        JSONObject ownerJson = repositoryJson.getJSONObject("owner");
        String login = ownerJson.getString("login");
        String avatarUrl = ownerJson.getString("avatar_url");
        int ownerId = ownerJson.getInt("id");

        Owner owner = new Owner(login, ownerId, avatarUrl);
        return new Repository(id, name, description,
                              createdAt, updatedAt,starsCount,
                              language, forksCount, owner);
    }
}