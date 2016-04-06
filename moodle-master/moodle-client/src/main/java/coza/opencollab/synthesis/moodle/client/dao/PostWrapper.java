package coza.opencollab.synthesis.moodle.client.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

/**
 *
 * @author OpenCollab
 */
public class PostWrapper {

    private List<Post> posts = new ArrayList<Post>();
    private List<Object> warnings = new ArrayList<Object>();

    /**
     *
     * @return The posts
     */
    public List<Post> getPosts() {
        return posts;
    }

    /**
     *
     * @param posts The posts
     */
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    /**
     *
     * @return The warnings
     */
    public List<Object> getWarnings() {
        return warnings;
    }

    /**
     *
     * @param warnings The warnings
     */
    public void setWarnings(List<Object> warnings) {
        this.warnings = warnings;
    }

    @Override
    public String toString() {
        return "Post{" + "posts=" + posts.size() + ", warnings=" + warnings.size() + '}';
    }

}
