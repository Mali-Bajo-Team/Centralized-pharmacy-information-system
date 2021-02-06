package com.pharmacy.cpis.userservice.model.users;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pharmacy.cpis.util.exceptions.PSBadRequestException;

@Entity
public class UserAccount implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "email", nullable = false)
	String email;

	@Column(name = "password", nullable = false)
	String password;

	@Column(name = "is_active", nullable = false)
	boolean isActive;

	@Column(nullable = false)
	boolean needsPasswordChange;

	// only set for pharmacy administrators
	@Column
	private Long pharmacyId;

	@Column(name = "last_password_reset_date")
	private Timestamp lastPasswordResetDate;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "person_id", referencedColumnName = "id")
	private Person person;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
	private List<Authority> authorities;

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getUsername() {
		return email; // Spring security model username is in our case email !! (unique identifier)
	}

	@Override
	public boolean isEnabled() {
		return isActive;
	}

	public void setPassword(String password) {
		if (password == null || password.isEmpty())
			throw new PSBadRequestException("Password is required.");
		Timestamp now = new Timestamp(new Date().getTime());
		this.setLastPasswordResetDate(now);
		this.password = password;
	}

	public Timestamp getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}

	public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
		this.lastPasswordResetDate = lastPasswordResetDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		if (id == null || id < 0)
			throw new PSBadRequestException("Id is required.");
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	public boolean isNeedsPasswordChange() {
		return needsPasswordChange;
	}

	public void setNeedsPasswordChange(boolean needsPasswordChange) {
		this.needsPasswordChange = needsPasswordChange;
	}

	public Long getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(Long pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		UserAccount u = (UserAccount) o;
		if (u.id == null || id == null) {
			return false;
		}
		return Objects.equals(id, u.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return "User{" + "id=" + id + ", email='" + email + '\'' + ", password='" + password + '\'' + ", isActive="
				+ isActive + '}';
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	public String getRole() {
		if (authorities.isEmpty())
			return null;
		Authority authority = authorities.get(0);
		String role = authority.getAuthority(); // we have now for example "ROLE_ADMIN"
		return role.substring(5); // to take "ADMIN" only, we substring "ROLE_"
	}
}
