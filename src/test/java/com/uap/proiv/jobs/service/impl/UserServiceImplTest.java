package com.uap.proiv.jobs.service.impl;

import com.uap.proiv.jobs.client.UserApiRepository;
import com.uap.proiv.jobs.dto.User;
import com.uap.proiv.jobs.dto.UserApiResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserApiRepository userApiRepository;

    @InjectMocks
    private UserServiceImpl service;

    @Test
    void searchAssignsSequentialJobIds() {
        User firstUser = new User();
        User secondUser = new User();
        UserApiResponse response = new UserApiResponse();
        response.setData(List.of(firstUser, secondUser));
        when(userApiRepository.getUsers(2)).thenReturn(response);

        UserApiResponse result = service.search(2);

        assertEquals(1, result.getData().get(0).getJobId());
        assertEquals(2, result.getData().get(1).getJobId());
        verify(userApiRepository).getUsers(2);
    }

    @Test
    void searchByIdAssignsDefaultJobId() {
        User user = new User();
        when(userApiRepository.getUserById(4)).thenReturn(user);

        User result = service.searchById(4);

        assertEquals(user, result);
        assertEquals(1, result.getJobId());
        verify(userApiRepository).getUserById(4);
    }

    @Test
    void updateWrapsRepositoryFailure() {
        User user = new User();
        doThrow(new IllegalStateException("request failed"))
                .when(userApiRepository).updateUser(user);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.update(user));

        assertEquals("Error al crear el usuario: request failed", exception.getMessage());
        verify(userApiRepository).updateUser(user);
    }
}
