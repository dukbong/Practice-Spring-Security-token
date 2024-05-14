package com.example.securitytesttoken.customDto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.securitytesttoken.dto.UserDetailInfo;
import com.example.securitytesttoken.enums.Role;

public class CustomUserDetails implements UserDetails {
	
	private final UserDetailInfo userDetailInfo;
	
	public CustomUserDetails(UserDetailInfo userDetailInfo) {
		this.userDetailInfo = userDetailInfo;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		return userDetailInfo.getPassword();
	}

	@Override
	public String getUsername() {
		return userDetailInfo.getUsername();
	}
	
	public String getAccessUrl() {
		return userDetailInfo.getAccessUrl();
	}
	
	public Role getRole() {
		return userDetailInfo.getRole();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
