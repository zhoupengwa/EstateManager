package cn.edu.xhu.dao;

import java.util.List;

import cn.edu.xhu.domain.House;
import cn.edu.xhu.domain.Image;
import cn.edu.xhu.exception.HouseDaoOperatException;
import cn.edu.xhu.util.ThreadLocalDateUtil;

public class HouseDao extends BaseDao {
	/**
	 * ��ӷ���
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
			throw new HouseDaoOperatException("������Ϣ���ʧ��");
		}
		return false;
	}

	/**
	 * Ϊ�������һ��ͼƬ
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
			throw new HouseDaoOperatException("Ϊ�������ͼƬʧ��");
		}
		return false;
	}

	/**
	 * ��������ͼƬ
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
			throw new HouseDaoOperatException("Ϊ��������ͼƬʧ��");
		}
		return false;

	}

	/**
	 * ��ȡHouse�����id
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
			throw new HouseDaoOperatException("��ȡ���Idʧ��");
		}
		return maxId == null ? null : maxId;
	}

	/**
	 * ͨ��id��userid��鷿���Ƿ����
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
			throw new HouseDaoOperatException("������Ϣ��ѯʧ��");
		}
		return false;

	}

	/**
	 * ����id��userId��ѯ������Ϣ�Ƿ����
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
			throw new HouseDaoOperatException("������Ϣ��ѯʧ��");
		}
		return false;
	}

	/**
	 * ����id��鷿���Ƿ����
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
			throw new HouseDaoOperatException("������Ϣ��ѯʧ��");
		}
		return false;
	}

	/**
	 * ����id��ѯ������Ϣ
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
			throw new HouseDaoOperatException("������Ϣ��ѯʧ��");
		}
		return house == null ? null : house;
	}

	/**
	 * ��ѯ�����Ƿ�淿��
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
			throw new HouseDaoOperatException("������Ϣ��ѯʧ��");
		}
		return false;

	}

	/**
	 * ���·�����Ϣ,/���º���Ҫ�������
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
			throw new HouseDaoOperatException("������Ϣ�޸�ʧ��");
		}
		return false;
	}

	/**
	 * ��ѯδ��˵ķ�����Ϣ
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
			throw new HouseDaoOperatException("������Ϣ����ʧ��");
		}
		return houses == null ? null : houses;
	}

	/**
	 * ��ѯδ���ס������ͨ�������з�����Ϣ
	 * 
	 * @throws HouseDaoOperatException
	 */
	public List<House> queryPrettyHouses() throws HouseDaoOperatException {
		List<House> houses = null;
		String sql = "select id,house_userid,house_name,house_kind,house_area,house_village,house_address,house_size,house_type,house_traded,house_qualified,house_contact,house_phone,house_price,house_time,house_image from tb_house where house_traded=0 and house_qualified=1";
		try {
			houses = super.query(sql, null, House.class);
		} catch (Exception e) {
			throw new HouseDaoOperatException("������Ϣ����ʧ��");
		}
		return houses == null ? null : houses;
	}

	// ���۸����,Ĭ������
	public List<House> queryPrettyHousesByPrice(int startPrice, int endPrice) throws HouseDaoOperatException {
		List<House> houses = null;
		String sql = "select id,house_userid,house_name,house_kind,house_area,house_village,house_address,house_size,house_type,house_traded,house_qualified,house_contact,house_phone,house_price,house_time,house_image from tb_house where house_traded=0 and house_qualified=1 and house_price>=? and house_price<=?";
		try {
			houses = super.query(sql, new Object[] { startPrice, endPrice }, House.class);
		} catch (Exception e) {
			throw new HouseDaoOperatException("������Ϣ����ʧ��");
		}
		return houses == null ? null : houses;
	}

	// ���������,Ĭ������
	public List<House> queryHousesBySize(int startSize, int endSize) throws HouseDaoOperatException {
		List<House> houses = null;
		String sql = "select id,house_userid,house_name,house_kind,house_area,house_village,house_address,house_size,house_type,house_traded,house_qualified,house_contact,house_phone,house_price,house_time,house_image from tb_house where house_traded=0 and house_qualified=1 and house_size>=? and house_size<=?";
		try {
			houses = super.query(sql, new Object[] { startSize, endSize }, House.class);
		} catch (Exception e) {
			throw new HouseDaoOperatException("������Ϣ����ʧ��");
		}
		return houses == null ? null : houses;
	}

	// �����Ͳ���,Ĭ������
	public List<House> queryHouseByType(String type) throws HouseDaoOperatException {
		List<House> houses = null;
		String sql = "select id,house_userid,house_name,house_kind,house_area,house_village,house_address,house_size,house_type,house_traded,house_qualified,house_contact,house_phone,house_price,house_time,house_image from tb_house where house_traded=0 and house_qualified=1 and house_type like ?";
		try {
			houses = super.query(sql, new Object[] { type }, House.class);
		} catch (Exception e) {
			throw new HouseDaoOperatException("������Ϣ����ʧ��");
		}
		return houses == null ? null : houses;
	}

	// �����Ͳ��ң�Ĭ������
	public List<House> queryHouseByKind(String kind) throws HouseDaoOperatException {
		List<House> houses = null;
		String sql = "select id,house_userid,house_name,house_kind,house_area,house_village,house_address,house_size,house_type,house_traded,house_qualified,house_contact,house_phone,house_price,house_time,house_image from tb_house where house_traded=0 and house_qualified=1 and house_kind like ?";
		try {
			houses = super.query(sql, new Object[] { kind }, House.class);
		} catch (Exception e) {
			throw new HouseDaoOperatException("������Ϣ����ʧ��");
		}
		return houses == null ? null : houses;
	}

	// ���ύ����������Ĭ������
	public List<House> queryHouseByParameterOrderByDefault(String type, String kind, String startPrice, String endPrice,
			String startSize, String endSize) throws Exception {
		List<House> houses = null;
		StringBuffer sql = new StringBuffer(
				"select id,house_userid,house_name,house_kind,house_area,house_village,house_address,house_size,house_type,house_traded,house_qualified,house_contact,house_phone,house_price,house_time,house_image from tb_house where house_traded=0 and house_qualified=1 ");
		if (type == null && kind == null && startPrice == null && startSize == null) {// ȫ��Ϊ�գ�Ĭ�ϰ�ʱ���ѯ
			sql.append("order by house_time desc");
			houses = super.query(sql.toString(), null, House.class);
		} else if (type != null && kind == null && startPrice == null && startSize == null) {// ����
			sql.append("and house_type =? order by house_time desc");
			houses = super.query(sql.toString(), new Object[] { type }, House.class);
		} else if (type == null && kind != null && startPrice == null && startSize == null) {// ����
			sql.append("and house_kind =? order by house_time desc");
			houses = super.query(sql.toString(), new Object[] { kind }, House.class);
		} else if (type == null && kind == null && startPrice != null && startSize == null) {// �۸�
			sql.append("and house_price >=? and house_price <=? order by house_time desc");
			houses = super.query(sql.toString(), new Object[] { startPrice, endPrice }, House.class);
		} else if (type == null && kind == null && startPrice == null && startSize != null) {// ���
			sql.append("and house_size >=? and house_size <=? order by house_time desc");
			houses = super.query(sql.toString(), new Object[] { startSize, endSize }, House.class);// --------
		} else if (type != null && kind != null && startPrice == null && startSize == null) {// ����+����
			sql.append("and house_type =? and house_kind =? order by house_time desc");
			houses = super.query(sql.toString(), new Object[] { type, kind }, House.class);
		} else if (type != null && kind == null && startPrice != null && startSize == null) {// ����+�۸�
			sql.append("and house_type =? and house_price >=? and house_price <=? order by house_time desc");
			houses = super.query(sql.toString(), new Object[] { type, startPrice, endPrice }, House.class);// -------------
		} else if (type != null && kind == null && startPrice == null && startSize != null) {// ����+���
			sql.append("and house_type =? and house_size >=? and house_size <=? order by house_time desc");
			houses = super.query(sql.toString(), new Object[] { type, startSize, endSize }, House.class);
		} else if (type != null && kind != null && startPrice != null && startSize == null) {// ����+����+�۸�
			sql.append(
					"and house_type =? and house_kind=? and house_price >=? and house_price <=? order by house_time desc");
			houses = super.query(sql.toString(), new Object[] { type, kind, startPrice, endPrice }, House.class);
		} else if (type != null && kind != null && startPrice == null && startSize != null) {// ����+����+���
			sql.append(
					"and house_type =? and house_kind=? and house_size >=? and house_size <=? order by house_time desc");
			houses = super.query(sql.toString(), new Object[] { type, kind, startSize, endSize }, House.class);// -------------------
		} else if (type != null && kind == null && startPrice != null && startSize != null) {// ����+�۸�+���
			sql.append(
					"and house_type =? and house_price >=? and house_price <=? and house_size >=? and house_size <=? order by house_time desc");
			houses = super.query(sql.toString(), new Object[] { type, startPrice, endPrice, startSize, endSize },
					House.class);// ------------
		} else if (type != null && kind != null && startPrice != null && startSize != null) {// ����+����+�۸�+���
			sql.append(
					"and house_type =? and house_kind=? and house_price >=? and house_price <=? and house_size >=? and house_size <=? order by house_time desc");
			houses = super.query(sql.toString(), new Object[] { type, kind, startPrice, endPrice, startSize, endSize },
					House.class);// -------
		} else if (type == null && kind != null && startPrice != null && startSize == null) {// ����+�۸�
			sql.append("and house_kind=? and house_price >=? and house_price <=? order by house_time desc");
			houses = super.query(sql.toString(), new Object[] { kind, startPrice, endPrice }, House.class);// -------
		} else if (type == null && kind != null && startPrice == null && startSize != null) {// ����+���
			sql.append("and house_kind=? and house_size >=? and house_size <=? order by house_time desc");
			houses = super.query(sql.toString(), new Object[] { kind, startSize, endSize }, House.class);// -------
		} else if (type == null && kind != null && startPrice != null && startSize != null) {// ����+�۸�+���
			sql.append(
					"and house_kind=? and house_price >=? and house_price <=? and house_size >=? and house_size <=? order by house_time desc");
			houses = super.query(sql.toString(), new Object[] { kind, startPrice, endPrice, startSize, endSize },
					House.class);
		} else if (type == null && kind == null && startPrice != null && startSize != null) {// �۸�+���
			sql.append(
					"and house_price >=? and house_price <=? and house_size >=? and house_size <=? order by house_time desc");
			houses = super.query(sql.toString(), new Object[] { startPrice, endPrice, startSize, endSize },
					House.class);
		}
		return houses;
	}

	// ���ύ���������ҡ��۸�����
	public List<House> queryHouseByParameterOrderByPriceUp(String type, String kind, String startSize, String endSize)
			throws Exception {
		List<House> houses = null;
		StringBuffer sql = new StringBuffer(
				"select id,house_userid,house_name,house_kind,house_area,house_village,house_address,house_size,house_type,house_traded,house_qualified,house_contact,house_phone,house_price,house_time,house_image from tb_house where house_traded=0 and house_qualified=1 ");
		if (type == null && kind == null && startSize == null) {
			sql.append("order by house_price asc");
			houses = super.query(sql.toString(), null, House.class);
		} else if (type != null && kind == null && startSize == null) {// ����
			sql.append("and house_type= ? order by house_price asc");
			houses = super.query(sql.toString(), new Object[] { type }, House.class);
		} else if (type == null && kind != null && startSize == null) {// ����
			sql.append("and house_kind= ? order by house_price asc");
			houses = super.query(sql.toString(), new Object[] { kind }, House.class);
		} else if (type == null && kind == null && startSize != null) {// ���
			sql.append("and house_size >=? and house_size <=? order by house_price asc");
			houses = super.query(sql.toString(), new Object[] { startSize, endSize }, House.class);
		} else if (type != null && kind != null && startSize == null) {// ����+����
			sql.append("and house_type=? and house_kind=? order by house_price asc");
			houses = super.query(sql.toString(), new Object[] { type, kind }, House.class);
		} else if (type != null && kind == null && startSize != null) {// ����+���
			sql.append("and house_type=? and house_size >=? and house_size <=? order by house_price asc");
			houses = super.query(sql.toString(), new Object[] { type, startSize, endSize }, House.class);
		} else if (type != null && kind != null && startSize != null) {// ����+����+���
			sql.append(
					"and house_type=? and house_kind=? and house_size >=? and house_size <=? order by house_price asc");
			houses = super.query(sql.toString(), new Object[] { type, kind, startSize, endSize }, House.class);
		} else if (type == null && kind != null && startSize != null) {// ����+���
			sql.append("and house_kind=? and house_size >=? and house_size <=? order by house_price asc");
			houses = super.query(sql.toString(), new Object[] { kind, startSize, endSize }, House.class);
		}
		return houses;

	}

	// ���ύ���������ҡ��۸���
	public List<House> queryHouseByParameterOrderByPriceDown(String type, String kind, String startSize, String endSize)
			throws Exception {
		List<House> houses = null;
		StringBuffer sql = new StringBuffer(
				"select id,house_userid,house_name,house_kind,house_area,house_village,house_address,house_size,house_type,house_traded,house_qualified,house_contact,house_phone,house_price,house_time,house_image from tb_house where house_traded=0 and house_qualified=1 ");
		if (type == null && kind == null && startSize == null) {
			sql.append("order by house_price desc");
			houses = super.query(sql.toString(), null, House.class);
		} else if (type != null && kind == null && startSize == null) {// ����
			sql.append("and house_type= ? order by house_price desc");
			houses = super.query(sql.toString(), new Object[] { type }, House.class);
		} else if (type == null && kind != null && startSize == null) {// ����
			sql.append("and house_kind= ? order by house_price desc");
			houses = super.query(sql.toString(), new Object[] { kind }, House.class);
		} else if (type == null && kind == null && startSize != null) {// ���
			sql.append("and house_size >=? and house_size <=? order by house_price desc");
			houses = super.query(sql.toString(), new Object[] { startSize, endSize }, House.class);
		} else if (type != null && kind != null && startSize == null) {// ����+����
			sql.append("and house_type=? and house_kind=? order by house_price desc");
			houses = super.query(sql.toString(), new Object[] { type, kind }, House.class);
		} else if (type != null && kind == null && startSize != null) {// ����+���
			sql.append("and house_type=? and house_size >=? and house_size <=? order by house_price desc");
			houses = super.query(sql.toString(), new Object[] { type, startSize, endSize }, House.class);
		} else if (type != null && kind != null && startSize != null) {// ����+����+���
			sql.append(
					"and house_type=? and house_kind=? and house_size >=? and house_size <=? order by house_price desc");
			houses = super.query(sql.toString(), new Object[] { type, kind, startSize, endSize }, House.class);
		} else if (type == null && kind != null && startSize != null) {// ����+���
			sql.append("and house_kind=? and house_size >=? and house_size <=? order by house_price desc");
			houses = super.query(sql.toString(), new Object[] { kind, startSize, endSize }, House.class);
		}
		return houses;

	}

	// ���ύ���������ҡ��������
	public List<House> queryHouseByParameterOrderBySizeUp(String type, String kind, String startPrice, String endPrice)
			throws Exception {
		List<House> houses = null;
		StringBuffer sql = new StringBuffer("select id,house_userid,house_name,house_kind,house_area,house_village,house_address,house_size,house_type,house_traded,house_qualified,house_contact,house_phone,house_price,house_time,house_image from tb_house where house_traded=0 and house_qualified=1 ");
		if (type == null && kind == null && startPrice == null) {
			sql.append("order by house_size asc");
			houses = super.query(sql.toString(), null, House.class);
		} else if (type != null && kind == null && startPrice == null) {// ����
			sql.append("and house_type= ? order by house_size asc");
			houses = super.query(sql.toString(), new Object[] { type }, House.class);
		} else if (type == null && kind != null && startPrice == null) {// ����
			sql.append("and house_kind= ? order by house_size asc");
			houses = super.query(sql.toString(), new Object[] { kind }, House.class);
		} else if (type == null && kind == null && startPrice != null) {// �۸�
			sql.append("and house_price >=? and house_price <=? order by house_size asc");
			houses = super.query(sql.toString(), new Object[] { startPrice, endPrice }, House.class);
		} else if (type != null && kind != null && startPrice == null) {// ����+����
			sql.append("and house_type=? and house_kind=? order by house_size asc");
			houses = super.query(sql.toString(), new Object[] { type, kind }, House.class);
		} else if (type != null && kind == null && startPrice != null) {// ����+�۸�
			sql.append("and house_type=? and house_price >=? and house_price <=? order by house_size asc");
			houses = super.query(sql.toString(), new Object[] { type, startPrice, endPrice }, House.class);
		} else if (type != null && kind != null && startPrice != null) {// ����+����+�۸�
			sql.append(
					"and house_type=? and house_kind=? and house_price >=? and house_price <=? order by house_size asc");
			houses = super.query(sql.toString(), new Object[] { type, kind, startPrice, endPrice }, House.class);
		} else if (type == null && kind != null && startPrice != null) {// ����+�۸�
			sql.append("and house_kind=? and house_price >=? and house_price <=? order by house_size asc");
			houses = super.query(sql.toString(), new Object[] { kind, startPrice, endPrice }, House.class);
		}
		return houses;
	}

	// ���ύ���������ҡ��������
	public List<House> queryHouseByParameterOrderBySizeDown(String type, String kind, String startPrice,
			String endPrice) throws Exception {
		List<House> houses = null;
		StringBuffer sql = new StringBuffer(
				"select id,house_userid,house_name,house_kind,house_area,house_village,house_address,house_size,house_type,house_traded,house_qualified,house_contact,house_phone,house_price,house_time,house_image from tb_house where house_traded=0 and house_qualified=1 ");
		if (type == null && kind == null && startPrice == null) {
			sql.append("order by house_size desc");
			houses = super.query(sql.toString(), null, House.class);
		} else if (type != null && kind == null && startPrice == null) {// ����
			sql.append("and house_type= ? order by house_size desc");
			houses = super.query(sql.toString(), new Object[] { type }, House.class);
		} else if (type == null && kind != null && startPrice == null) {// ����
			sql.append("and house_kind= ? order by house_size desc");
			houses = super.query(sql.toString(), new Object[] { kind }, House.class);
		} else if (type == null && kind == null && startPrice != null) {// �۸�
			sql.append("and house_price >=? and house_price <=? order by house_size desc");
			houses = super.query(sql.toString(), new Object[] { startPrice, endPrice }, House.class);
		} else if (type != null && kind != null && startPrice == null) {// ����+����
			sql.append("and house_type=? and house_kind=? order by house_size desc");
			houses = super.query(sql.toString(), new Object[] { type, kind }, House.class);
		} else if (type != null && kind == null && startPrice != null) {// ����+�۸�
			sql.append("and house_type=? and house_price >=? and house_price <=? order by house_size desc");
			houses = super.query(sql.toString(), new Object[] { type, startPrice, endPrice }, House.class);
		} else if (type != null && kind != null && startPrice != null) {// ����+����+�۸�
			sql.append(
					"and house_type=? and house_kind=? and house_price >=? and house_price <=? order by house_size desc");
			houses = super.query(sql.toString(), new Object[] { type, kind, startPrice, endPrice }, House.class);
		} else if (type == null && kind != null && startPrice != null) {// ����+�۸�
			sql.append("and house_kind=? and house_price >=? and house_price <=? order by house_size desc");
			houses = super.query(sql.toString(), new Object[] { kind, startPrice, endPrice }, House.class);
		}
		return houses;

	}

	// ��������
	public List<House> searchHouse(String parameter) throws Exception {
		List<House> houses = null;
		String sql = "select id,house_userid,house_name,house_kind,house_area,house_village,house_address,house_size,house_type,house_traded,house_qualified,house_contact,house_phone,house_price,house_time,house_image from (select id,house_userid,house_name,house_kind,house_area,house_village,house_address,house_size,house_type,house_traded,house_qualified,house_contact,house_phone,house_price,house_time,house_image from tb_house where house_traded=0 and house_qualified=1) a where a.house_name like ? or a.house_type like ? or a.house_kind like ? or a.house_price like ? or a.house_size like ?";
		parameter = "%" + parameter + "%";
		houses = super.query(sql.toString(), new Object[] { parameter,parameter, parameter, parameter, parameter }, House.class);
		return houses;
	}

	// ��ѯǰ����ͼƬ
	public List<Image> queryThreeImages() throws Exception {
		List<Image> images = null;
		String sql = "select a.id,a.house_id,a.image_path from tb_image a,(select id from tb_image where image_type=1 limit 0,3) b where a.id=b.id";
		images = super.query(sql.toString(), null, Image.class);
		return images;
	}

	/**
	 * �������
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
			throw new HouseDaoOperatException("������Ϣ����ʧ��");
		}
		return houses == null ? null : houses;
	}

	/**
	 * ��ѯ���˵ķ��ݼ�¼
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
			throw new HouseDaoOperatException("������Ϣ����ʧ��");
		}
		return houses == null ? null : houses;
	}

	/**
	 * ��ѯ���houseId,����δ������,�����ͨ�����ҵķ����Ƿ���ڣ�����ģ�
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
			throw new HouseDaoOperatException("������Ϣ��ѯʧ��");
		}
		return false;
	}

	/**
	 * ���ݱ����׺��޸Ľ���״̬Ϊ1���ѽ��ף�
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
			throw new HouseDaoOperatException("���ݽ���״̬��Ϣ����ʧ��");
		}
		return false;
	}

	/**
	 * ��˷���
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
			throw new HouseDaoOperatException("�������״̬��Ϣ����ʧ��");
		}
		return false;

	}

	/**
	 * ����idɾ��������Ϣ
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
			throw new HouseDaoOperatException("����ɾ��ʧ��");
		}
		return false;
	}

}
