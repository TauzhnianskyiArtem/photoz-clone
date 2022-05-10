package com.photoz.clone.service;

import com.photoz.clone.api.mapper.UserCreateEditMapper;
import com.photoz.clone.api.mapper.UserReadMapper;
import com.photoz.clone.store.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private static final Integer COMPANY_ID = 1;

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserCreateEditMapper userCreateEditMapper;
    @Mock
    private UserReadMapper userReadMapper;

    @InjectMocks
    private UserService companyService;

    @Test
    void findById() {
//        doReturn(Optional.of(new Company(COMPANY_ID, null, Collections.emptyMap())))
//            .when(companyRepository).findById(COMPANY_ID);
//
//        var actualResult = companyService.findById(COMPANY_ID);
//
//        assertTrue(actualResult.isPresent());
//
//        var expectedResult = new CompanyReadDto(COMPANY_ID, null);
//        actualResult.ifPresent(actual -> assertEquals(expectedResult, actual));

    }
}










