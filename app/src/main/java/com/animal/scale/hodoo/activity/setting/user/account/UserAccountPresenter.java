package com.animal.scale.hodoo.activity.setting.user.account;

import android.content.Context;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.common.CommonModel;
import com.animal.scale.hodoo.domain.User;

import java.util.Iterator;
import java.util.List;

public class UserAccountPresenter implements UserAccountIn.Presenter {

    UserAccountIn.View view;

    UserAccountModel model;

    public UserAccountPresenter(UserAccountIn.View view) {
        this.view = view;
        this.model = new UserAccountModel();
    }

    @Override
    public void initUserData(Context context) {
        model.initUserData(context);
    }

    @Override
    public void getData() {
        model.getUserData(new UserAccountModel.asyncTaskListner() {
            @Override
            public void doPostExecute(List<User> data) {

                User master = null;
                int index = 0;
                int idx = model.getUserIdx();
                for (int i = 0; i < data.size(); i++) {
                    if ( data.get(i).getAccessType() == 1 ) {
                        master = data.get(i);
                        index = i;
                    }
                }
                if ( master != null ) {
                    if ( master.getUserIdx() != idx && index != 0 ) {
                        Iterator<User> iterator = data.iterator();
                        while (iterator.hasNext()) {
                            if (iterator.next() == master)
                                iterator.remove();
                        }
                        data.add(0, master);
                    }
                }
                view.setAdapter(idx, data);
            }
            @Override
            public void doPreExecute() {

            }
        });
    }

    @Override
    public void withdrawGroup(final Context context, final User user) {
        model.withdrawGroup(user.getUserIdx(), new CommonModel.DomainCallBackListner<Integer>() {
            @Override
            public void doPostExecute(Integer result) {
                if ( result > 0 ) {
                    view.showSinglePopup(context.getString(R.string.user_account__group_withdraw_title_fin), user.getNickname() + context.getString(R.string.user_account__group_withdraw_title_fin));
                    getData();
                }
            }

            @Override
            public void doPreExecute() {

            }
        });
    }

    @Override
    public void getAccessType() {
        if ( model.getAccessType() > 0 ) {
            view.setEditBtn();
        }
    }
}
