package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.AgeException;
import core.basesyntax.exception.LoginException;
import core.basesyntax.exception.PasswordException;
import core.basesyntax.exception.UserAddingException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MINIMUM_LENGTH = 6;
    private static final int MINIMUM_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {

        if (user == null) {
            throw new UserAddingException("User can't be null");
        }
        if (storageDao.get(user.getLogin()) != null) {
            throw new UserAddingException("User with such login already exist");
        }
        if (user.getLogin() == null) {
            throw new LoginException("User login can't be null");
        }
        if (user.getLogin().length() < MINIMUM_LENGTH) {
            throw new LoginException("User login length should be at least 6 characters");
        }
        if (user.getPassword() == null) {
            throw new PasswordException("User password can't be null");
        }
        if (user.getPassword().length() < MINIMUM_LENGTH) {
            throw new PasswordException("User password length should be at least 6 characters");
        }
        if (user.getAge() == null) {
            throw new AgeException("User age can't be null");
        }
        if (user.getAge() < MINIMUM_AGE) {
            throw new AgeException("User age should be at least 18 years");
        }

        storageDao.add(user);
        return storageDao.get(user.getLogin());
    }
}
