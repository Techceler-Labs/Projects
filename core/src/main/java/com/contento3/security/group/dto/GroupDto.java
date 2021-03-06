package com.contento3.security.group.dto;

import java.util.Collection;

import com.contento3.account.dto.AccountDto;
import com.contento3.common.dto.Dto;
import com.contento3.security.role.dto.RoleDto;
import com.contento3.security.user.dto.SaltedHibernateUserDto;

public class GroupDto extends Dto {
	/**
	 * Primary key id for group
	 */
	private Integer id;
	
	/**
	 * Group name
	 */
	private String name;
	
	/**
	 * Group description
	 */
	private String description;
	
	/**
	 * Authorities associated to group
	 */
	private Collection<RoleDto> roles;
	
	/**
	 * Members associated to group
	 */
	private Collection<SaltedHibernateUserDto> members;
	
	/**
	 * Account to which this group belongs to.
	 */
	private AccountDto accountDto;
	
	/**
	 * Return group related roles
	 * @return
	 */
	public Collection<RoleDto> getRoles() {
		return roles;
	}

	/**
	 * Sets group roles
	 * @param roles
	 */
	public void setRoles(final Collection<RoleDto> roles) {
		this.roles = roles;
	}

	/**
	 * Return group related users
	 * @return
	 */
	public Collection<SaltedHibernateUserDto> getMembers() {
		return members;
	}

	/**
	 * Sets group members (users)
	 * @param authorities
	 */
	public void setMembers(final Collection<SaltedHibernateUserDto> members) {
		this.members = members;
	}

	/**
	 * Return group description
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set group description
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Sets the group name
	 * @param Group name
	 * @return
	 */
	public void setGroupName(final String groupName)
	{
		name=groupName;
	}
	
	/**
	 * Returns the group name
	 * @param void
	 * @return
	 */
	public String getGroupName()
	{
		return(name);
	}
	
	/**
	 * Sets the group Id
	 * @param Group Id
	 * @return
	 */
	public void setGroupId(final Integer groupId)
	{
		id=groupId;
	}
	
	/**
	 * Returns the group Id
	 * @param void
	 * @return
	 */
	public Integer getGroupId()
	{
		return(id);
	}

	/**
	 * Sets the AccountDto
	 * @param accountDto
	 */
	public void setAccountDto(final AccountDto accountDto) {
		this.accountDto = accountDto;
	}

	/**
	 * Gets the AccountDto
	 * @return
	 */
	public AccountDto getAccountDto() {
		return accountDto;
	}

}
