package com.shop.model.enums;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.security.core.GrantedAuthority;


public enum UserRole{
    ADMIN, USER;

}
