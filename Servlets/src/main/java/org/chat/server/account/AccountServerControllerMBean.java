package org.chat.server.account;

public interface AccountServerControllerMBean {
    int getUsers();

    int getUsersLimit();

    void setUsersLimit(int usersLimit);
}
