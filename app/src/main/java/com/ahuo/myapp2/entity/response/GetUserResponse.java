package com.ahuo.myapp2.entity.response;


import com.ahuo.myapp2.base.BaseResponse;
import com.ahuo.myapp2.entity.other.User;

import java.util.List;

/**
 * Created on 17-5-27
 *
 * @author liuhuijie
 */

public class GetUserResponse extends BaseResponse {

    private List<User> users;

    public GetUserResponse() {
    }

    public List<User> getUsers() {
        return this.users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }



}
