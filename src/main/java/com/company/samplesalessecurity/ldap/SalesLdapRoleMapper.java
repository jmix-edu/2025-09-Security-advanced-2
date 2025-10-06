package com.company.samplesalessecurity.ldap;

import com.company.samplesalessecurity.security.FullAccessRole;
import com.company.samplesalessecurity.security.ReaderRole;
import com.company.samplesalessecurity.security.SellerRole;
import com.google.common.collect.ImmutableMap;
import io.jmix.ldap.userdetails.LdapAuthorityToJmixRoleCodesMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Component
public class SalesLdapRoleMapper implements LdapAuthorityToJmixRoleCodesMapper {

    private final static Map<String, String> roleCodes = ImmutableMap.of(
            "admin", FullAccessRole.CODE,
            "managers", SellerRole.CODE,
            "developers", ReaderRole.CODE
    );


    @Override
    public Collection<String> mapAuthorityToJmixRoleCodes(String authority) {
        return Collections.singleton(roleCodes.getOrDefault(authority, authority));
    }
}
