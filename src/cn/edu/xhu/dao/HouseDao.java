package cn.edu.xhu.dao;

import java.util.List;

import cn.edu.xhu.domain.House;
import cn.edu.xhu.domain.Image;
import cn.edu.xhu.exception.HouseDaoOperatException;
import cn.edu.xhu.util.ThreadLocalDateUtil;

public class HouseDao extends BaseDao {
	/**
	 * 添加房产
	 * 
	 * @param house
	 * @return
	 * @throws HouseDaoOperatException
	 */
	public boolean addHouse(House house) throws HouseDaoOperatException {
		String sql = "insert into tb_house values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			Object[] params = { house.getId(), house.getUserid(), house.getName(), house.getKind(), house.getArea(),
					house.getVillage(), house.getAddress(), house.getSize(), house.getType(), false, false,
					house.getContact(), house.getPhone(), house.getPrice(), house.getTime(),
					"http://www.xhban.com/none.jpg", ThreadLocalDateUtil.formatDate(),
					ThreadLocalDateUtil.formatDate() };
			int result = super.update(sql.toString(), params);
			if (result == 1) {
				return true;
			}
		} catch (Exception e) {
			throw new HouseDaoOperatException("房产信息添加失败");
		}
		return false;
	}

	/**
	 * 为房屋添加一张图片
	 * 
	 * @param houseId
	 * @param imagePath
	 * @return
	 * @throws HouseDaoOperatException
	 */
	public boolean addHouseImage(int houseId, String imagePath) throws HouseDaoOperatException {
		String sql = "update tb_house set house_image=? where id=?";
		try {
			int count = super.update(sql, new Object[] { imagePath, houseId });
			if (count == 1) {
				return true;
			}
		} catch (Exception e) {
			throw new HouseDaoOperatException("为房屋添加图片失败");
		}
		return false;
	}

	/**
	 * 房屋重置图片
	 * 
	 * @param houseId
	 * @return
	 * @throws HouseDaoOperatException
	 */
	public boolean resetHouseImage(int houseId) throws HouseDaoOperatException {
		String sql = "update tb_house set house_image=? where id=?";
		try {
			int count = super.update(sql, new Object[] { "http://www.xhban.com/none.jpg", houseId });
			if (count == 1) {
				return true;
			}
		} catch (Exception e) {
			throw new HouseDaoOperatException("为房屋重置图片失败");
		}
		return false;

	}

	/**
	 * 获取House的最大id
	 * 
	 * @return
	 * @throws HouseDaoOperatException
	 */
	public Integer getMaxId() throws HouseDaoOperatException {
		String sql = "select max(id)as count from tb_house";
		Integer maxId = null;
		try {
			maxId = super.count(sql, null);
		} catch (Exception e) {
			throw new HouseDaoOperatException("获取最大Id失败");
		}
		return maxId == null ? null : maxId;
	}

	/**
	 * 通过id与userid检查房屋是否存在
	 * 
	 * @param id
	 * @param userId
	 * @return
	 * @throws HouseDaoOperatException
	 */
	public boolean checkHouseByUserIdAndHouseId(int id, int userId) throws HouseDaoOperatException {
		String sql = "select count(*) as count from tb_house where id=? and house_userid=?";
		try {
			Object[] params = { id, userId };
			int result = super.count(sql, params);
			if (result == 1) {
				return true;
			}
		} catch (Exception e) {
			throw new HouseDaoOperatException("房屋信息查询失败");
		}
		return false;

	}

	/**
	 * 根据id与userId查询房产信息是否存在
	 * 
	 * @param id
	 * @param userId
	 * @return
	 * @throws HouseDaoOperatException
	 */
	public boolean checkHouseByIdAndUserId(int id, int userId) throws HouseDaoOperatException {
		String sql = "select count(*) as count from tb_house where id=? and house_userid=?";
		try {
			Object[] params = { id, userId };
			int result = super.count(sql, params);
			if (result == 1) {
				return true;
			}
		} catch (Exception e) {
			throw new HouseDaoOperatException("房屋信息查询失败");
		}
		return false;
	}

	/**
	 * 根据id检查房屋是否存在
	 * 
	 * @param id
	 * @return
	 * @throws HouseDaoOperatException
	 */
	public boolean checkHouseById(int id) throws HouseDaoOperatException {
		String sql = "select count(*) as count from tb_house where id=?";
		try {
			Object[] params = { id };
			int result = super.count(sql, params);
			if (result == 1) {
				return true;
			}
		} catch (Exception e) {
			throw new HouseDaoOperatException("房屋信息查询失败");
		}
		return false;
	}

	/**
	 * 根据id查询房产信息
	 * 
	 * @param id
	 * @return
	 * @throws HouseDaoOperatException
	 */
	public House queryHouseById(int id) throws HouseDaoOperatException {
		House house = null;
		String sql = "select id,house_userid,house_name,house_kind,house_area,house_village,house_address,house_size,house_type,house_traded,house_qualified,house_contact,house_phone,house_price,house_time,house_image from tb_house where id=?";
		try {
			house = super.query(sql, new Object[] { id }, House.class).get(0);
		} catch (Exception e) {
			throw new HouseDaoOperatException("房屋信息查询失败");
		}
		return house == null ? null : house;
	}

	/**
	 * 查询个人是否存房屋
	 * 
	 * @param userId
	 * @return
	 * @throws HouseDaoOperatException
	 */
	public boolean checkHouseByUserId(int userId) throws HouseDaoOperatException {
		String sql = "select count(*) as count from tb_house where house_userid=?";
		try {
			int count = super.count(sql, new Object[] { userId });
			if (count > 0) {
				return true;
			}
		} catch (Exception e) {
			throw new HouseDaoOperatException("房屋信息查询失败");
		}
		return false;

	}

	/**
	 * 更新房产信息,/更新后需要重新审核
	 * 
	 * @param house
	 * @return
	 * @throws HouseDaoOperatException
	 */
	public boolean updateHouseById(House house) throws HouseDaoOperatException {
		String sql = "update tb_house set house_name=?,house_area=?,house_village=?,house_address=?,house_size=?,house_type=?,house_qualified=?,house_contact=?,house_phone=?,house_price=?,gmt_motified=? where id=? and house_userid=? and house_traded=?";
		Object[] params = { house.getName(), house.getArea(), house.getVillage(), house.getAddress(), house.getSize(),
				house.getType(), false, house.getContact(), house.getPhone(), house.getPrice(),
				ThreadLocalDateUtil.formatDate(), house.getId(), house.getUserid(), false };
		try {
			int result = super.update(sql, params);
			if (result == 1) {
				return true;
			}
		} catch (Exception e) {
			throw new HouseDaoOperatException("房屋信息修改失败");
		}
		return false;
	}

	/**
	 * 查询未审核的房产信息
	 * 
	 * @return
	 * @throws HouseDaoOperatException
	 */
	public List<House> queryUnQualifiedHouses() throws HouseDaoOperatException {
		List<House> houses = null;
		String sql = "select id,house_userid,house_name,house_kind,house_area,house_village,house_address,house_size,house_type,house_traded,house_qualified,house_contact,house_phone,house_price,house_time,house_image from tb_house where house_traded=0 and house_qualified=0";
		try {
			houses = super.query(sql, null, House.class);
		} catch (Exception e) {
			throw new HouseDaoOperatException("房屋信息查找失败");
		}
		return houses == null ? null : houses;
	}

	/**
	 * 查询未交易、且审核通过的所有房屋信息
	 * 
	 * @throws HouseDaoOperatException
	 */
	public List<House> queryPrettyHouses() throws HouseDaoOperatException {
		List<House> houses = null;
		String sql = "select id,house_userid,house_name,house_kind,house_area,house_village,house_address,house_size,house_type,house_traded,house_qualified,house_contact,house_phone,house_price,house_time,house_image from tb_house where house_traded=0 and house_qualified=1";
		try {
			houses = super.query(sql, null, House.class);
		} catch (Exception e) {
			throw new HouseDaoOperatException("房屋信息查找失败");
		}
		return houses == null ? null : houses;
	}

	// 按价格查找,默认排序
	public List<House> queryPrettyHousesByPrice(int startPrice, int endPrice) throws HouseDaoOperatException {
		List<House> houses = null;
		String sql = "select id,house_userid,house_name,house_kind,house_area,house_village,house_address,house_size,house_type,house_traded,house_qualified,house_contact,house_phone,house_price,house_time,house_image from tb_house where house_traded=0 and house_qualified=1 and house_price>=? and house_price<=?";
		try {
			houses = super.query(sql, new Object[] { startPrice, endPrice }, House.class);
		} catch (Exception e) {
			throw new HouseDaoOperatException("房屋信息查找失败");
		}
		return houses == null ? null : houses;
	}

	// 按面积查找,默认排序
	public List<House> queryHousesBySize(int startSize, int endSize) throws HouseDaoOperatException {
		List<House> houses = null;
		String sql = "select id,house_userid,house_name,house_kind,house_area,house_village,house_address,house_size,house_type,house_traded,house_qualified,house_contact,house_phone,house_price,house_time,house_image from tb_house where house_traded=0 and house_qualified=1 and house_size>=? and house_size<=?";
		try {
			houses = super.query(sql, new Object[] { startSize, endSize }, House.class);
		} catch (Exception e) {
			throw new HouseDaoOperatException("房屋信息查找失败");
		}
		return houses == null ? null : houses;
	}

	// 按户型查找,默认排序
	public List<House> queryHouseByType(String type) throws HouseDaoOperatException {
		List<House> houses = null;
		String sql = "select id,house_userid,house_name,house_kind,house_area,house_village,house_address,house_size,house_type,house_traded,house_qualified,house_contact,house_phone,house_price,house_time,house_image from tb_house where house_traded=0 and house_qualified=1 and house_type like ?";
		try {
			houses = super.query(sql, new Object[] { type }, House.class);
		} catch (Exception e) {
			throw new HouseDaoOperatException("房屋信息查找失败");
		}
		return houses == null ? null : houses;
	}

	// 按类型查找，默认排序
	public List<House> queryHouseByKind(String kind) throws HouseDaoOperatException {
		List<House> houses = null;
		String sql = "select id,house_userid,house_name,house_kind,house_area,house_village,house_address,house_size,house_type,house_traded,house_qualified,house_contact,house_phone,house_price,house_time,house_image from tb_house where house_traded=0 and house_qualified=1 and house_kind like ?";
		try {
			houses = super.query(sql, new Object[] { kind }, House.class);
		} catch (Exception e) {
			throw new HouseDaoOperatException("房屋信息查找失败");
		}
		return houses == null ? null : houses;
	}

	// 按提交参数来查找默认排序
	public List<House> queryHouseByParameterOrderByDefault(String type, String kind, String startPrice, String endPrice,
			String startSize, String endSize) throws Exception {
		List<House> houses = null;
		StringBuffer sql = new StringBuffer(
				"select id,house_userid,house_name,house_kind,house_area,house_village,house_address,house_size,house_type,house_traded,house_qualified,house_contact,house_phone,house_price,house_time,house_image from tb_house where house_traded=0 and house_qualified=1 ");
		if (type == null && kind == null && startPrice == null && startSize == null) {// 全部为空，默认按时间查询
			sql.append("order by house_time desc");
			houses = super.query(sql.toString(), null, House.class);
		} else if (type != null && kind == null && startPrice == null && startSize == null) {// 户型
			sql.append("and house_type =? order by house_time desc");
			houses = super.query(sql.toString(), new Object[] { type }, House.class);
		} else if (type == null && kind != null && startPrice == null && startSize == null) {// 类型
			sql.append("and house_kind =? order by house_time desc");
			houses = super.query(sql.toString(), new Object[] { kind }, House.class);
		} else if (type == null && kind == null && startPrice != null && startSize == null) {// 价格
			sql.append("and house_price >=? and house_price <=? order by house_time desc");
			houses = super.query(sql.toString(), new Object[] { startPrice, endPrice }, House.class);
		} else if (type == null && kind == null && startPrice == null && startSize != null) {// 面积
			sql.append("and house_size >=? and house_size <=? order by house_time desc");
			houses = super.query(sql.toString(), new Object[] { startSize, endSize }, House.class);// --------
		} else if (type != null && kind != null && startPrice == null && startSize == null) {// 户型+类型
			sql.append("and house_type =? and house_kind =? order by house_time desc");
			houses = super.query(sql.toString(), new Object[] { type, kind }, House.class);
		} else if (type != null && kind == null && startPrice != null && startSize == null) {// 户型+价格
			sql.append("and house_type =? and house_price >=? and house_price <=? order by house_time desc");
			houses = super.query(sql.toString(), new Object[] { type, startPrice, endPrice }, House.class);// -------------
		} else if (type != null && kind == null && startPrice == null && startSize != null) {// 户型+面积
			sql.append("and house_type =? and house_size >=? and house_size <=? order by house_time desc");
			houses = super.query(sql.toString(), new Object[] { type, startSize, endSize }, House.class);
		} else if (type != null && kind != null && startPrice != null && startSize == null) {// 户型+类型+价格
			sql.append(
					"and house_type =? and house_kind=? and house_price >=? and house_price <=? order by house_time desc");
			houses = super.query(sql.toString(), new Object[] { type, kind, startPrice, endPrice }, House.class);
		} else if (type != null && kind != null && startPrice == null && startSize != null) {// 户型+类型+面积
			sql.append(
					"and house_type =? and house_kind=? and house_size >=? and house_size <=? order by house_time desc");
			houses = super.query(sql.toString(), new Object[] { type, kind, startSize, endSize }, House.class);// -------------------
		} else if (type != null && kind == null && startPrice != null && startSize != null) {// 户型+价格+面积
			sql.append(
					"and house_type =? and house_price >=? and house_price <=? and house_size >=? and house_size <=? order by house_time desc");
			houses = super.query(sql.toString(), new Object[] { type, startPrice, endPrice, startSize, endSize },
					House.class);// ------------
		} else if (type != null && kind != null && startPrice != null && startSize != null) {// 户型+类型+价格+面积
			sql.append(
					"and house_type =? and house_kind=? and house_price >=? and house_price <=? and house_size >=? and house_size <=? order by house_time desc");
			houses = super.query(sql.toString(), new Object[] { type, kind, startPrice, endPrice, startSize, endSize },
					House.class);// -------
		} else if (type == null && kind != null && startPrice != null && startSize == null) {// 类型+价格
			sql.append("and house_kind=? and house_price >=? and house_price <=? order by house_time desc");
			houses = super.query(sql.toString(), new Object[] { kind, startPrice, endPrice }, House.class);// -------
		} else if (type == null && kind != null && startPrice == null && startSize != null) {// 类型+面积
			sql.append("and house_kind=? and house_size >=? and house_size <=? order by house_time desc");
			houses = super.query(sql.toString(), new Object[] { kind, startSize, endSize }, House.class);// -------
		} else if (type == null && kind != null && startPrice != null && startSize != null) {// 类型+价格+面积
			sql.append(
					"and house_kind=? and house_price >=? and house_price <=? and house_size >=? and house_size <=? order by house_time desc");
			houses = super.query(sql.toString(), new Object[] { kind, startPrice, endPrice, startSize, endSize },
					House.class);
		} else if (type == null && kind == null && startPrice != null && startSize != null) {// 价格+面积
			sql.append(
					"and house_price >=? and house_price <=? and house_size >=? and house_size <=? order by house_time desc");
			houses = super.query(sql.toString(), new Object[] { startPrice, endPrice, startSize, endSize },
					House.class);
		}
		return houses;
	}

	// 按提交参数来查找【价格升序】
	public List<House> queryHouseByParameterOrderByPriceUp(String type, String kind, String startSize, String endSize)
			throws Exception {
		List<House> houses = null;
		StringBuffer sql = new StringBuffer(
				"select id,house_userid,house_name,house_kind,house_area,house_village,house_address,house_size,house_type,house_traded,house_qualified,house_contact,house_phone,house_price,house_time,house_image from tb_house where house_traded=0 and house_qualified=1 ");
		if (type == null && kind == null && startSize == null) {
			sql.append("order by house_price asc");
			houses = super.query(sql.toString(), null, House.class);
		} else if (type != null && kind == null && startSize == null) {// 户型
			sql.append("and house_type= ? order by house_price asc");
			houses = super.query(sql.toString(), new Object[] { type }, House.class);
		} else if (type == null && kind != null && startSize == null) {// 类型
			sql.append("and house_kind= ? order by house_price asc");
			houses = super.query(sql.toString(), new Object[] { kind }, House.class);
		} else if (type == null && kind == null && startSize != null) {// 面积
			sql.append("and house_size >=? and house_size <=? order by house_price asc");
			houses = super.query(sql.toString(), new Object[] { startSize, endSize }, House.class);
		} else if (type != null && kind != null && startSize == null) {// 户型+类型
			sql.append("and house_type=? and house_kind=? order by house_price asc");
			houses = super.query(sql.toString(), new Object[] { type, kind }, House.class);
		} else if (type != null && kind == null && startSize != null) {// 户型+面积
			sql.append("and house_type=? and house_size >=? and house_size <=? order by house_price asc");
			houses = super.query(sql.toString(), new Object[] { type, startSize, endSize }, House.class);
		} else if (type != null && kind != null && startSize != null) {// 户型+类型+面积
			sql.append(
					"and house_type=? and house_kind=? and house_size >=? and house_size <=? order by house_price asc");
			houses = super.query(sql.toString(), new Object[] { type, kind, startSize, endSize }, House.class);
		} else if (type == null && kind != null && startSize != null) {// 类型+面积
			sql.append("and house_kind=? and house_size >=? and house_size <=? order by house_price asc");
			houses = super.query(sql.toString(), new Object[] { kind, startSize, endSize }, House.class);
		}
		return houses;

	}

	// 按提交参数来查找【价格降序】
	public List<House> queryHouseByParameterOrderByPriceDown(String type, String kind, String startSize, String endSize)
			throws Exception {
		List<House> houses = null;
		StringBuffer sql = new StringBuffer(
				"select id,house_userid,house_name,house_kind,house_area,house_village,house_address,house_size,house_type,house_traded,house_qualified,house_contact,house_phone,house_price,house_time,house_image from tb_house where house_traded=0 and house_qualified=1 ");
		if (type == null && kind == null && startSize == null) {
			sql.append("order by house_price desc");
			houses = super.query(sql.toString(), null, House.class);
		} else if (type != null && kind == null && startSize == null) {// 户型
			sql.append("and house_type= ? order by house_price desc");
			houses = super.query(sql.toString(), new Object[] { type }, House.class);
		} else if (type == null && kind != null && startSize == null) {// 类型
			sql.append("and house_kind= ? order by house_price desc");
			houses = super.query(sql.toString(), new Object[] { kind }, House.class);
		} else if (type == null && kind == null && startSize != null) {// 面积
			sql.append("and house_size >=? and house_size <=? order by house_price desc");
			houses = super.query(sql.toString(), new Object[] { startSize, endSize }, House.class);
		} else if (type != null && kind != null && startSize == null) {// 户型+类型
			sql.append("and house_type=? and house_kind=? order by house_price desc");
			houses = super.query(sql.toString(), new Object[] { type, kind }, House.class);
		} else if (type != null && kind == null && startSize != null) {// 户型+面积
			sql.append("and house_type=? and house_size >=? and house_size <=? order by house_price desc");
			houses = super.query(sql.toString(), new Object[] { type, startSize, endSize }, House.class);
		} else if (type != null && kind != null && startSize != null) {// 户型+类型+面积
			sql.append(
					"and house_type=? and house_kind=? and house_size >=? and house_size <=? order by house_price desc");
			houses = super.query(sql.toString(), new Object[] { type, kind, startSize, endSize }, House.class);
		} else if (type == null && kind != null && startSize != null) {// 类型+面积
			sql.append("and house_kind=? and house_size >=? and house_size <=? order by house_price desc");
			houses = super.query(sql.toString(), new Object[] { kind, startSize, endSize }, House.class);
		}
		return houses;

	}

	// 按提交参数来查找【面积升序】
	public List<House> queryHouseByParameterOrderBySizeUp(String type, String kind, String startPrice, String endPrice)
			throws Exception {
		List<House> houses = null;
		StringBuffer sql = new StringBuffer("select id,house_userid,house_name,house_kind,house_area,house_village,house_address,house_size,house_type,house_traded,house_qualified,house_contact,house_phone,house_price,house_time,house_image from tb_house where house_traded=0 and house_qualified=1 ");
		if (type == null && kind == null && startPrice == null) {
			sql.append("order by house_size asc");
			houses = super.query(sql.toString(), null, House.class);
		} else if (type != null && kind == null && startPrice == null) {// 户型
			sql.append("and house_type= ? order by house_size asc");
			houses = super.query(sql.toString(), new Object[] { type }, House.class);
		} else if (type == null && kind != null && startPrice == null) {// 类型
			sql.append("and house_kind= ? order by house_size asc");
			houses = super.query(sql.toString(), new Object[] { kind }, House.class);
		} else if (type == null && kind == null && startPrice != null) {// 价格
			sql.append("and house_price >=? and house_price <=? order by house_size asc");
			houses = super.query(sql.toString(), new Object[] { startPrice, endPrice }, House.class);
		} else if (type != null && kind != null && startPrice == null) {// 户型+类型
			sql.append("and house_type=? and house_kind=? order by house_size asc");
			houses = super.query(sql.toString(), new Object[] { type, kind }, House.class);
		} else if (type != null && kind == null && startPrice != null) {// 户型+价格
			sql.append("and house_type=? and house_price >=? and house_price <=? order by house_size asc");
			houses = super.query(sql.toString(), new Object[] { type, startPrice, endPrice }, House.class);
		} else if (type != null && kind != null && startPrice != null) {// 户型+类型+价格
			sql.append(
					"and house_type=? and house_kind=? and house_price >=? and house_price <=? order by house_size asc");
			houses = super.query(sql.toString(), new Object[] { type, kind, startPrice, endPrice }, House.class);
		} else if (type == null && kind != null && startPrice != null) {// 类型+价格
			sql.append("and house_kind=? and house_price >=? and house_price <=? order by house_size asc");
			houses = super.query(sql.toString(), new Object[] { kind, startPrice, endPrice }, House.class);
		}
		return houses;
	}

	// 按提交参数来查找【面积降序】
	public List<House> queryHouseByParameterOrderBySizeDown(String type, String kind, String startPrice,
			String endPrice) throws Exception {
		List<House> houses = null;
		StringBuffer sql = new StringBuffer(
				"select id,house_userid,house_name,house_kind,house_area,house_village,house_address,house_size,house_type,house_traded,house_qualified,house_contact,house_phone,house_price,house_time,house_image from tb_house where house_traded=0 and house_qualified=1 ");
		if (type == null && kind == null && startPrice == null) {
			sql.append("order by house_size desc");
			houses = super.query(sql.toString(), null, House.class);
		} else if (type != null && kind == null && startPrice == null) {// 户型
			sql.append("and house_type= ? order by house_size desc");
			houses = super.query(sql.toString(), new Object[] { type }, House.class);
		} else if (type == null && kind != null && startPrice == null) {// 类型
			sql.append("and house_kind= ? order by house_size desc");
			houses = super.query(sql.toString(), new Object[] { kind }, House.class);
		} else if (type == null && kind == null && startPrice != null) {// 价格
			sql.append("and house_price >=? and house_price <=? order by house_size desc");
			houses = super.query(sql.toString(), new Object[] { startPrice, endPrice }, House.class);
		} else if (type != null && kind != null && startPrice == null) {// 户型+类型
			sql.append("and house_type=? and house_kind=? order by house_size desc");
			houses = super.query(sql.toString(), new Object[] { type, kind }, House.class);
		} else if (type != null && kind == null && startPrice != null) {// 户型+价格
			sql.append("and house_type=? and house_price >=? and house_price <=? order by house_size desc");
			houses = super.query(sql.toString(), new Object[] { type, startPrice, endPrice }, House.class);
		} else if (type != null && kind != null && startPrice != null) {// 户型+类型+价格
			sql.append(
					"and house_type=? and house_kind=? and house_price >=? and house_price <=? order by house_size desc");
			houses = super.query(sql.toString(), new Object[] { type, kind, startPrice, endPrice }, House.class);
		} else if (type == null && kind != null && startPrice != null) {// 类型+价格
			sql.append("and house_kind=? and house_price >=? and house_price <=? order by house_size desc");
			houses = super.query(sql.toString(), new Object[] { kind, startPrice, endPrice }, House.class);
		}
		return houses;

	}

	// 搜索房屋
	public List<House> searchHouse(String parameter) throws Exception {
		List<House> houses = null;
		String sql = "select id,house_userid,house_name,house_kind,house_area,house_village,house_address,house_size,house_type,house_traded,house_qualified,house_contact,house_phone,house_price,house_time,house_image from (select id,house_userid,house_name,house_kind,house_area,house_village,house_address,house_size,house_type,house_traded,house_qualified,house_contact,house_phone,house_price,house_time,house_image from tb_house where house_traded=0 and house_qualified=1) a where a.house_name like ? or a.house_type like ? or a.house_kind like ? or a.house_price like ? or a.house_size like ?";
		parameter = "%" + parameter + "%";
		houses = super.query(sql.toString(), new Object[] { parameter,parameter, parameter, parameter, parameter }, House.class);
		return houses;
	}

	// 查询前三张图片
	public List<Image> queryThreeImages() throws Exception {
		List<Image> images = null;
		String sql = "select a.id,a.house_id,a.image_path from tb_image a,(select id from tb_image where image_type=1 limit 0,3) b where a.id=b.id";
		images = super.query(sql.toString(), null, Image.class);
		return images;
	}

	/**
	 * 排序查找
	 * 
	 * @param parameter
	 * @param desc
	 * @return
	 * @throws HouseDaoOperatException
	 */
	public List<House> queryPrettyHousesByOrderParameter(String parameter, boolean desc)
			throws HouseDaoOperatException {
		List<House> houses = null;
		String sql = null;
		if (desc) {
			sql = "select id,house_userid,house_name,house_kind,house_area,house_village,house_address,house_size,house_type,house_traded,house_qualified,house_contact,house_phone,house_price,house_time from tb_house where house_traded=0 and house_qualified=1 order by ? desc";
		} else {
			sql = "select id,house_userid,house_name,house_kind,house_area,house_village,house_address,house_size,house_type,house_traded,house_qualified,house_contact,house_phone,house_price,house_time from tb_house where house_traded=0 and house_qualified=1 order by ?";
		}
		try {
			houses = super.query(sql, new Object[] { parameter }, House.class);
		} catch (Exception e) {
			throw new HouseDaoOperatException("房屋信息查找失败");
		}
		return houses == null ? null : houses;
	}

	/**
	 * 查询个人的房屋记录
	 * 
	 * @param userId
	 * @return
	 * @throws HouseDaoOperatException
	 */
	public List<House> queryHousesByUserId(int userId) throws HouseDaoOperatException {
		List<House> houses = null;
		String sql = "select id,house_userid,house_name,house_kind,house_area,house_village,house_address,house_size,house_type,house_traded,house_qualified,house_contact,house_phone,house_price,house_time,house_image from tb_house where house_userid=?";
		try {
			houses = super.query(sql, new Object[] { userId }, House.class);
		} catch (Exception e) {
			throw new HouseDaoOperatException("房屋信息查找失败");
		}
		return houses == null ? null : houses;
	}

	/**
	 * 查询编号houseId,且尚未被交易,且审核通过，且的房屋是否存在（可买的）
	 * 
	 * @param houseId
	 * @return
	 * @throws HouseDaoOperatException
	 */
	public boolean checkPrettyHouseById(int houseId, int sellerId) throws HouseDaoOperatException {
		String sql = "select count(*) as count from tb_house where id=? and house_userid=? and house_traded=0 and house_qualified=1";
		try {
			int count = super.count(sql, new Object[] { houseId, sellerId });
			if (count == 1) {
				return true;
			}
		} catch (Exception e) {
			throw new HouseDaoOperatException("房屋信息查询失败");
		}
		return false;
	}

	/**
	 * 房屋被交易后，修改交易状态为1（已交易）
	 * 
	 * @param houseId
	 * @return
	 * @throws HouseDaoOperatException
	 */
	public boolean changeHouseTradeState(int houseId) throws HouseDaoOperatException {
		String sql = "update tb_house set house_traded=1, gmt_motified=? where id=?";
		try {
			int count = super.update(sql, new Object[] { ThreadLocalDateUtil.formatDate(), houseId });
			if (count == 1) {
				return true;
			}
		} catch (Exception e) {
			throw new HouseDaoOperatException("房屋交易状态信息设置失败");
		}
		return false;
	}

	/**
	 * 审核房屋
	 * 
	 * @param id
	 * @return
	 * @throws HouseDaoOperatException
	 */
	public boolean permitHouse(int id) throws HouseDaoOperatException {
		String sql = "update tb_house set house_qualified=1, gmt_motified=? where id=?";
		try {
			int count = super.update(sql, new Object[] { ThreadLocalDateUtil.formatDate(), id });
			if (count == 1) {
				return true;
			}
		} catch (Exception e) {
			throw new HouseDaoOperatException("房屋审核状态信息设置失败");
		}
		return false;

	}

	/**
	 * 根据id删除房屋信息
	 * 
	 * @param id
	 * @return
	 * @throws HouseDaoOperatException
	 */
	public boolean deleteHouseById(int id) throws HouseDaoOperatException {
		String sql = "delete from tb_house where id=?";
		try {
			int count = super.update(sql, new Object[] { id });
			if (count == 1) {
				return true;
			}
		} catch (Exception e) {
			throw new HouseDaoOperatException("房屋删除失败");
		}
		return false;
	}

}
