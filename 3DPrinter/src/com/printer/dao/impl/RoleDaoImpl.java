package com.printer.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.printer.dao.RoleDao;
import com.printer.entities.Role;

@Repository
public class RoleDaoImpl implements RoleDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	//添加角色
	@Override
	public int addRole(Role role) {
		String sql = "insert into RoleInfo(RoleName,RoleIntro) values(?,?)";
		return jdbcTemplate.update(sql, new Object[]{
				role.getRoleName(),
				role.getRoleIntro()
		});
	}

	//删除角色
	@Override
	public int deleteRole(int roleID) {
		String sql = "delete from RoleInfo where RoleID=?";
		return jdbcTemplate.update(sql, new Object[]{ roleID });
	}

	//修改角色
	@Override
	public int updateRole(Role role) {
		String sql ="update RoleInfo set RoleName=?,RoleIntro=? where RoleID=?";
		return jdbcTemplate.update(sql,new Object[]{
				role.getRoleName(),
				role.getRoleIntro(),
				role.getRoleID()
		});
	}

	//查询角色列表
	@Override
	public List<Role> queryRoleList() {
		String sql = "select * from RoleInfo";
		final List<Role> list = new ArrayList<Role>();
		jdbcTemplate.query(sql, new Object[]{},
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						do{
							Role role = new Role();
							role.setRoleID(rs.getInt("RoleID"));
							role.setRoleName(rs.getString("RoleName"));
							role.setRoleIntro(rs.getString("RoleIntro"));
							list.add(role);
						}while(rs.next());
					}
		});
		return list;
	}

	//查询角色
	@Override
	public Role queryRole(int roleID) {
		String sql = "select * from RoleInfo where RoleID=?";
		final Role role = new Role();
		jdbcTemplate.query(sql, new Object[]{roleID},
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						role.setRoleID(rs.getInt("RoleID"));
						role.setRoleName(rs.getString("RoleName"));
						role.setRoleIntro(rs.getString("RoleIntro"));
					}
		});
		return role;
	}

	//给角色分配权限
	@Override
	public int distribution(int roleID, int authorityID) {
		String sql = "insert into RoleAuthority(RoleID,AuthorityID) values(?,?)";
		return jdbcTemplate.update(sql, new Object[]{
				roleID,
				authorityID
		});
	}

	//查询是否存在角色
	@Override
	public Role isExistRole(String roleName) {
		String sql = "select * from RoleInfo where RoleName=?";
		final Role role = new Role();
		jdbcTemplate.query(sql, new Object[]{ roleName },
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						role.setRoleID(rs.getInt("RoleID"));
						role.setRoleName(rs.getString("RoleName"));
						role.setRoleIntro(rs.getString("RoleIntro"));
					}
		});
		return role;
	}

	//删除角色对应的权限
	@Override
	public int deleteAuthorities(int roleID) {
		String sql = "delete from RoleAuthority where RoleID=?";
		return jdbcTemplate.update(sql, new Object[]{ roleID });
	}

}
