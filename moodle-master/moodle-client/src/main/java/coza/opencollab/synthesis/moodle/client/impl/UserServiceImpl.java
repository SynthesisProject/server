package coza.opencollab.synthesis.moodle.client.impl;

import com.google.gson.reflect.TypeToken;
import coza.opencollab.synthesis.moodle.client.dao.User;
import coza.opencollab.synthesis.moodle.client.UserService;
import java.text.MessageFormat;
import java.util.List;

/**
 *
 * @author OpenCollab
 */
public class UserServiceImpl extends AbstractServiceImpl implements UserService {
    
    private static final String USER_DETAILS_FUNCTION = "core_user_get_users_by_id";
    private final String queryProperties = "userids[0]={0}";
    
    @Override
    public String getUserDisplayName(String token, int userId) {
        User user = getUser(callService(token, USER_DETAILS_FUNCTION, MessageFormat.format(queryProperties, userId)));
        return user.getFullname(); 
    }
    
    private User getUser(String json){
        List<User> userList =  gson.fromJson(json, new TypeToken<List<User>>() {
            }.getType());
        for(User user: userList){
            return user;
        }
        return null;
    }
}
