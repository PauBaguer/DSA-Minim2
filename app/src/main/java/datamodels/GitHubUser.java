package datamodels;

import java.io.Serializable;
import java.util.HashMap;

public class GitHubUser implements Serializable {
    private String login;
    private String repos_url;
       private String name;
       private Integer followers;
       private Integer following;
       private HashMap<String, String> repoHashmap;




    public GitHubUser(String login, String repos_url, String name, Integer followers, Integer following) {
        this.login = login;
        this.repos_url = repos_url;
        this.name = name;
        this.followers = followers;
        this.following = following;
        this.repoHashmap = new HashMap<>();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRepos_url() {
        return repos_url;
    }

    public void setRepos_url(String repos_url) {
        this.repos_url = repos_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public Integer getFollowing() {
        return following;
    }

    public void setFollowing(Integer following) {
        this.following = following;
    }

    public HashMap<String, String> getRepoHashmap() {
        return repoHashmap;
    }

    public void setRepoHashmap(HashMap<String, String> repoHashmap) {
        this.repoHashmap = repoHashmap;
    }
}
