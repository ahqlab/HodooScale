package com.animal.scale.hodoo.activity.setting.user.group.list;

import com.animal.scale.hodoo.domain.InvitationUser;

import java.util.List;

public interface UserGroupList {
    interface View {
        void setAdapterData(List<InvitationUser> users);
    }
    interface Presenter {
        void getInvitationList();
        void setInvitationState( int state, int toUserIdx, int fromUseridx );
    }
}
