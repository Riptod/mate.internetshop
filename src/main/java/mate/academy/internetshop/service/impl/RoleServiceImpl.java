package mate.academy.internetshop.service.impl;

import java.util.NoSuchElementException;
import mate.academy.internetshop.dao.RoleDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.models.Role;
import mate.academy.internetshop.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    @Inject
    private static RoleDao roleDao;

    @Override
    public Role create(Role role) throws DataProcessingException {
        return roleDao.create(role);
    }

    @Override
    public Role get(Long id) throws DataProcessingException {
        return roleDao.get(id).orElseThrow(() ->
                new NoSuchElementException("Found no role with id " + id));
    }

    @Override
    public Role update(Role role) throws DataProcessingException {
        return roleDao.update(role);
    }

    @Override
    public boolean delete(Long id) throws DataProcessingException {
        return roleDao.delete(id);
    }
}
