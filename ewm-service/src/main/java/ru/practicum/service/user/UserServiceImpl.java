package ru.practicum.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.api.page.Page;
import ru.practicum.api.user.NewUserRequest;
import ru.practicum.api.user.UserDto;
import ru.practicum.exception.NotFoundException;
import ru.practicum.jpa.entity.User;
import ru.practicum.jpa.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;

    @Override
    public UserDto create(NewUserRequest userRequest) {
        User user = User.builder()
                .email(userRequest.getEmail())
                .name(userRequest.getName())
                .build();

        userRepository.save(user);
        return userDTOMapper.toDTO(user);
    }

    @Override
    public List<UserDto> getUsers(List<Long> idsList, Integer from, Integer size) {
        List<User> users = Optional.ofNullable(idsList)
                .map(ids -> userRepository.findAllByIdIn(ids, new Page(from, size, Sort.unsorted())))
                .orElse(userRepository.findAll(new Page(from, size, Sort.unsorted())).getContent());
        return List.copyOf(userDTOMapper.toDTO(users));
    }

    @Override
    public void deleteUser(Long userId) {
        Optional.ofNullable(userId)
                .map(id -> userRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Не найден пользователь id = " + id)))
                .ifPresent(userRepository::delete);
    }
}
