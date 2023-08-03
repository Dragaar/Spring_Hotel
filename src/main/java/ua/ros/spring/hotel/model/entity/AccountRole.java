package ua.ros.spring.hotel.model.entity;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Account role enum.
 *
 * @author Rostyslav Ivanyshyn.
 */
public enum AccountRole implements GrantedAuthority {
        USER, MANAGER, UNKNOWN;

        @Override
        public String getAuthority() {
                return name();
        }
}
