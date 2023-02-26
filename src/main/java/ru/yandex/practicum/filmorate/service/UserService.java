package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exceptions.ErrorResponse;
import ru.yandex.practicum.filmorate.exceptions.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public User create(User user) {
        return userStorage.create(user);
    }

    public User update(User user) {
        return userStorage.update(user);
    }

    public List<User> usersList() {
        return userStorage.usersList();
    }

    public User findUserById(Integer id) {
        if (userStorage.getUserMap().containsKey(id)) {
            return userStorage.getUserMap().get(id);
        } else {
            throw new UserNotFoundException("not found User by Id: "+id);
        }
    }

    public void addFriendById(Integer userId, Integer friendId) {
        User user = findUserById(userId);
        User friend = findUserById(friendId);
        user.getFriends().add(friendId);
        friend.getFriends().add(userId);
    }

    public void deleteFriendById(Integer userId, Integer friendId) {
        User user = findUserById(userId);
        User friend = findUserById(friendId);
        if (!user.getFriends().contains(friendId)) {
            log.debug(user.getName()+" You're haven't that's friend."+friend.getName());
        } else {
            user.getFriends().remove(friendId);
        }
    }

    public List<User> getFriendsListById(Integer userId) {
        User user = findUserById(userId);
        List<User> friendList = new ArrayList<>();
        user.getFriends().stream().forEach(i -> friendList.add(findUserById(i)));
        return friendList;
    }

    public List<User> getCommonFriends(Integer userId, Integer otherId) {
        List<User> commonFriends = new ArrayList<>();
        User user = findUserById(userId);
        User otherUser = findUserById(otherId);
        user.getFriends().stream().filter(x -> otherUser.getFriends().contains(x))
                .forEach(i -> commonFriends.add(findUserById(i)));
        return commonFriends;
    }
}

