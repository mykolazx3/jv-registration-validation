package core.basesyntax.servic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.AgeException;
import core.basesyntax.exception.LoginException;
import core.basesyntax.exception.PasswordException;
import core.basesyntax.exception.UserAddingException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "valid login";
    private static final String VALID_PASSWORD = "valid pass";
    private static final Integer VALID_AGE = 20;
    private static final Integer INVALID_AGE = 15;
    private static final String STRING_LENGTH_5 = "ABCDE";
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);

        Storage.people.clear();
    }

    @Test
    void register_nonExistingUser_Ok() {
        User actualUser = registrationService.register(user);
        User expectedUser = storageDao.get(user.getLogin());
        assertEquals(expectedUser, actualUser);
    }

    @Test
    void register_userNull_notOk() {
        assertThrows(UserAddingException.class, () ->
                registrationService.register(null));
    }

    @Test
    void register_userAlreadyExist_notOk() {
        registrationService.register(user);
        assertThrows(UserAddingException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userLoginNull_notOk() {
        user.setLogin(null);
        assertThrows(LoginException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_userLoginLength_notOk() {
        user.setLogin(STRING_LENGTH_5);
        assertThrows(LoginException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_passwordNull_notOk() {
        user.setPassword(null);
        assertThrows(PasswordException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_passwordLength_notOk() {
        user.setPassword(STRING_LENGTH_5);
        assertThrows(PasswordException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_AgeNull_notOk() {
        user.setAge(null);
        assertThrows(AgeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_AgeInvalid_notOk() {
        user.setAge(INVALID_AGE);
        assertThrows(AgeException.class, () ->
                registrationService.register(user));
    }

}
