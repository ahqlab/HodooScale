package com.animal.scale.hodoo.service;

import com.animal.scale.hodoo.domain.ResultMessageGroup;
import com.animal.scale.hodoo.domain.User;

import retrofit2.Call;

public class UserServiceImpl implements UserService{
    @Override
    public Call<ResultMessageGroup> registUser(User user) {
        return null;
    }

    @Override
    public Call<User> login(User user) {
        return null;
    }

}
