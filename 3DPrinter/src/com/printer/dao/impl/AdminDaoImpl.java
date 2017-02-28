package com.printer.dao.impl;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.printer.dao.AdminDao;
import com.printer.entities.Admin;
import com.printer.entities.Authority;

@Repository
public class AdminDaoImpl implements AdminDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	//添加管理员
	@Override
	public int addAdmin(Admin admin) {
		
		String sql = "insert into AdminInfo(AdName,AdPassword,AdSex,AdPhone,AdEmail,RoleID,Portrait,AdQQ)"
				+ " values(?,?,?,?,?,?,?,?)";
		return jdbcTemplate.update(sql, new Object[]{
				admin.getAdName(),
				admin.getAdPassword(),
				admin.getAdSex(),
				admin.getAdPhone(),
				admin.getAdEmail(),
				admin.getRoleID(),
				admin.getPortrait(),
				admin.getAdQQ()
		});
	}

	//修改管理员信息
	@Override
	public int updateAdmin(Admin admin) {
		String sql = "update AdminInfo set AdPassword=?,AdSex=?,AdPhone=?,"
					+"AdEmail=?,RoleID=?,Portrait=?,AdQQ=? where AdID=?";
		return jdbcTemplate.update(sql, new Object[]{
				admin.getAdPassword(),
				admin.getAdSex(),
				admin.getAdPhone(),
				admin.getAdEmail(),
				admin.getRoleID(),
				admin.getPortrait(),
				admin.getAdQQ(),
				admin.getAdID()
		});
	}

	//删除管理员信息
	@Override
	public int deleteAdmin(int adID) {
		String sql = "delete from AdminInfo where AdID=?";
		return jdbcTemplate.update(sql, new Object[]{ adID });
	}

	//查询管理员
	@Override
	public Admin queryAdmin(int adID) {
		String sql = "select * from AdminInfo,RoleInfo where AdminInfo.RoleID=RoleInfo.RoleID and AdminInfo.AdID=?";
		final Admin admin = new Admin();
		jdbcTemplate.query(sql, new Object[] { adID },
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						admin.setAdID(rs.getInt("AdID"));
						admin.setAdName(rs.getString("AdName"));
						admin.setAdPassword(rs.getString("AdPassword"));
						admin.setAdSex(rs.getString("AdSex"));
						admin.setAdPhone(rs.getString("AdPhone"));
						admin.setAdEmail(rs.getString("AdEmail"));
						admin.setRoleID(rs.getInt("RoleID"));
						admin.setPortrait(rs.getString("Portrait"));
						admin.setAdQQ(rs.getString("AdQQ"));
						admin.setRoleName(rs.getString("RoleName"));
					}
				});
		return admin;
	}

	//查询管理员列表
	@Override
	public List<Admin> queryAdminList() {
		String sql = "select AdID,AdName,AdPassword,AdEmail,AdPhone,AdSex,RoleInfo.RoleID,RoleName,AdQQ "+
					"from AdminInfo,RoleInfo where AdminInfo.RoleID=RoleInfo.RoleID";
		
		final List<Admin> list = new ArrayList<Admin>();
		jdbcTemplate.query(sql, new Object[] {},
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						do{
							Admin admin = new Admin();
							admin.setAdID(rs.getInt("AdID"));
							admin.setAdName(rs.getString("AdName"));
							admin.setAdPassword(rs.getString("AdPassword"));
							admin.setAdEmail(rs.getString("AdEmail"));
							admin.setAdPhone(rs.getString("AdPhone"));
							admin.setAdSex(rs.getString("AdSex"));
							admin.setRoleID(rs.getInt("RoleID"));
							admin.setRoleName(rs.getString("RoleName"));
							admin.setAdQQ(rs.getString("AdQQ"));
							list.add(admin);
						}while(rs.next());
					}
		});
		return list;
	}


	//获取管理员权限列表
	@Override
	public List<Authority> queryAuthorityList(int roleID) {
		String sql1 = "select * from Authority,RoleAuthority where "
				+"Authority.AuthorityID=RoleAuthority.AuthorityID "
				+"and RoleAuthority.RoleID="+roleID;
		//获取所有权限 
		String sql2 = "select * from Authority";
		String sql="";
		if(roleID==0) 
			sql=sql2;
		else 
			sql=sql1;
		final List<Authority> authoritylist = new ArrayList<Authority>();
		
		jdbcTemplate.query(sql, new Object[] { },
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						do{
							Authority authority = new Authority();
							authority.setAuthorityID(rs.getInt("AuthorityID"));
							authority.setAuthorityName(rs.getString("AuthorityName"));
							authority.setAuthorityLink(rs.getString("AuthorityLink"));
							authoritylist.add(authority);
						}
						while(rs.next());
					}
		});
		return authoritylist;
	}

	//查询是否存在管理员
	@Override
	public Admin isExistAdmin(String adName) {
		String sql = "select * from AdminInfo,RoleInfo where AdminInfo.RoleID=RoleInfo.RoleID and AdName=?";
		final Admin admin = new Admin();
		jdbcTemplate.query(sql, new Object[] { adName },
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						admin.setAdID(rs.getInt("AdID"));
						admin.setAdName(rs.getString("AdName"));
						admin.setAdPassword(rs.getString("AdPassword"));
						admin.setAdSex(rs.getString("AdSex"));
						admin.setAdPhone(rs.getString("AdPhone"));
						admin.setAdEmail(rs.getString("AdEmail"));
						admin.setRoleID(rs.getInt("RoleID"));
						admin.setPortrait(rs.getString("Portrait"));
						admin.setAdQQ(rs.getString("AdQQ"));
						admin.setRoleName(rs.getString("RoleName"));
					}
				});
		return admin;
	}

	//获取设计师列表
	@Override
	public List<Admin> queryDesignerList() {
		String sql = "select * from AdminInfo,RoleInfo where AdminInfo.RoleID=RoleInfo.RoleID "
					+" and RoleName='设计师'";
		final List<Admin> list = new ArrayList<Admin>();
		jdbcTemplate.query(sql, new Object[] {},
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						do{
							Admin admin = new Admin();
							admin.setAdID(rs.getInt("AdID"));
							admin.setAdName(rs.getString("AdName"));
							admin.setPortrait(rs.getString("Portrait"));
							admin.setAdQQ(rs.getString("AdQQ"));
							list.add(admin);
						}while(rs.next());
					}
		});
		return list;
	}

	//查询统计数据
	@Override
	public Map<String, Object> TongJi() {
		String w="where Datediff(dd,DOrderDate,getDate())=1";
		w="";
		String sql1="select OrderState, count(OrderState) as count1,sum(OrderPrice) as sum1," +
				"ROW_NUMBER() over (order by OrderState) as ID " +
				"from OrderInfo "+w+" group by OrderState";
		String sql2="select State, count(State) as count1,sum(APrice) as sum1," +
				"ROW_NUMBER() over (order by State) as ID " +
				"from DOrderInfo "+w+" group by State";
		String sql3="select sum(OrderPrice) as ordersum from OrderInfo "+w;
		String sql4="select sum(APrice) as dordersum from DOrderInfo "+w;
		
		Map<String, Object> modelMap=new HashMap<String, Object>();
		
		final Map<String, Object> map1=new HashMap<String, Object>();
		final Map<String, Object> map2=new HashMap<String, Object>();
		final String data="data";
		final String state="state";
		final String count="count";
		jdbcTemplate.query(sql1, new Object[] {},
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						do{
							Map<String, Object> map=new HashMap<String, Object>();
							map.put(state, rs.getString("OrderState"));
							map.put(count, rs.getString("count1"));
							map1.put(data+rs.getInt("ID"), map);
						}while(rs.next());
					}
		});
		
		modelMap.put("tongji1", map1);
		
		jdbcTemplate.query(sql2, new Object[] {},
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						do{
							Map<String, Object> map=new HashMap<String, Object>();
							map.put(state, rs.getString("State"));
							map.put(count, rs.getString("count1"));
							map2.put(data+rs.getInt("ID"), map);
						}while(rs.next());
					}
		});
		modelMap.put("tongji2", map2);
		
		modelMap.put("sum1", jdbcTemplate.queryForInt(sql3));
		modelMap.put("sum2", jdbcTemplate.queryForInt(sql4));
		
		return modelMap;
	}
	


}
