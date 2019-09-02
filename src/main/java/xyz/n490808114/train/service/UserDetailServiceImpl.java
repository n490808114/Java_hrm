package xyz.n490808114.train.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import xyz.n490808114.train.dao.UserDao;
import xyz.n490808114.train.domain.User;

public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Can`t found with username:"+username);
        }else{
            return user;
        }
    }
}
