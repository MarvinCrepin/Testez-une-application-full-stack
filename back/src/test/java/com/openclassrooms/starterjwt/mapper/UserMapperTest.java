package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(MockitoExtension.class)
public class UserMapperTest {
    @InjectMocks
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    public void testToEntity() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("john.doe@test.com");
        userDto.setLastName("Doe");
        userDto.setFirstName("John");
        userDto.setAdmin(false);
        userDto.setPassword("password");

        User mappedUser = userMapper.toEntity(userDto);

        assertEquals(userDto.getLastName(), mappedUser.getLastName());
        assertEquals(userDto.getFirstName(), mappedUser.getFirstName());
    }

    @Test
    public void testToDto() {
        User user = new User();
        user.setId(1L);
        user.setEmail("john.doe@test.com");
        user.setLastName("Doe");
        user.setFirstName("John");
        user.setAdmin(false);

        UserDto userDto = userMapper.toDto(user);

        assertEquals(user.getLastName(), userDto.getLastName());
        assertEquals(user.getFirstName(), userDto.getFirstName());
    }

    @Test
    public void testToEntityList() {
        LocalDateTime date = LocalDateTime.now();
        List<UserDto> userDtoList = Arrays.asList(
                new UserDto(1l,"john.doe@test.com","John","Doe",false,"password",date,date),
                new UserDto(2L,"jane.smith@test.com","Smith", "Jane",false,"password",date,date)
        );

        List<User> mappedUserList = userMapper.toEntity(userDtoList);

        assertEquals(2, mappedUserList.size());
        assertEquals(userDtoList.get(0).getLastName(), mappedUserList.get(0).getLastName());
        assertEquals(userDtoList.get(0).getFirstName(), mappedUserList.get(0).getFirstName());
        assertEquals(userDtoList.get(1).getLastName(), mappedUserList.get(1).getLastName());
        assertEquals(userDtoList.get(1).getFirstName(), mappedUserList.get(1).getFirstName());
    }

    @Test
    public void testToDtoList() {
        LocalDateTime date = LocalDateTime.now();
        List<User> userList = Arrays.asList(
                new User(1l,"john.doe@test.com","John","Doe","password",false,date,date),
                new User(2L,"jane.smith@test.com","Smith", "Jane","password",false,date,date)
        );

        List<UserDto> mappedUserDtoList = userMapper.toDto(userList);

        assertEquals(2, mappedUserDtoList.size());
        assertEquals(userList.get(0).getLastName(), mappedUserDtoList.get(0).getLastName());
        assertEquals(userList.get(0).getFirstName(), mappedUserDtoList.get(0).getFirstName());
        assertEquals(userList.get(1).getLastName(), mappedUserDtoList.get(1).getLastName());
        assertEquals(userList.get(1).getFirstName(), mappedUserDtoList.get(1).getFirstName());
    }


}