package cn.edu.xhu.test;

import java.util.List;

import org.junit.Test;

import cn.edu.xhu.dao.BaseDao;
import cn.edu.xhu.domain.House;
import cn.edu.xhu.exception.HouseDaoOperatException;

public class JdbcUtilsTest extends BaseDao {
	@Test
	public void test() throws HouseDaoOperatException {
		List<House> houses = null;
		String sql = "select id,house_userid,house_name,house_kind,house_area,house_village,house_address,house_size,house_type,house_traded,house_qualified,house_contact,house_phone,house_price,house_time,house_image from tb_house where house_traded=0 and house_qualified=1";
		try {
			houses = super.query(sql, null, House.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new HouseDaoOperatException("������Ϣ����ʧ��");
		}
		System.out.println(houses.size());
	}
}
