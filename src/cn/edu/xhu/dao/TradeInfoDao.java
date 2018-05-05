package cn.edu.xhu.dao;

import java.util.List;

import cn.edu.xhu.domain.TradeInfo;
import cn.edu.xhu.exception.TradeInfoDaoException;
import cn.edu.xhu.util.ThreadLocalDateUtil;

public class TradeInfoDao extends BaseDao {
	/**
	 * ��ӽ��׼�¼
	 * 
	 * @param houseId
	 * @param sellerId
	 * @param buyerId
	 * @param tradeTime
	 * @return
	 * @throws TradeInfoDaoException
	 */
	public boolean addTradeInfo(int houseId, int sellerId, int buyerId, String tradeTime) throws TradeInfoDaoException {
		String sql = "insert into tb_tradeinfo values(null,?,?,?,?,?,?)";
		Object[] params = { houseId, sellerId, buyerId, tradeTime, ThreadLocalDateUtil.formatDate(),
				ThreadLocalDateUtil.formatDate() };
		try {
			int result = super.update(sql, params);
			if (result == 1) {
				return true;
			}
		} catch (Exception e) {
			throw new TradeInfoDaoException("��ӽ��׼�¼ʧ��");
		}
		return false;
	}

	/**
	 * ��ѯĳ���û��Ľ��׼�¼
	 * 
	 * @param userId
	 * @return
	 * @throws TradeInfoDaoException
	 */
	public List<TradeInfo> queryTradeInfosByUserId(int userId) throws TradeInfoDaoException {
		List<TradeInfo> tradeInfoList = null;
		String sql = "select id,house_id,seller_id,buyer_id,trade_time from tb_tradeinfo where seller_id=? or buyer_id=?";
		try {
			tradeInfoList = super.query(sql, new Object[] { userId, userId }, TradeInfo.class);
		} catch (Exception e) {
			throw new TradeInfoDaoException("��ѯ���׼�¼ʧ��");
		}
		return tradeInfoList == null ? null : tradeInfoList;
	}

	/**
	 * ��ѯ�Լ��Ĺ����¼
	 * 
	 * @param userId
	 * @return
	 * @throws TradeInfoDaoException
	 */
	public List<TradeInfo> queryBuyInfosByUserId(int userId) throws TradeInfoDaoException {
		List<TradeInfo> tradeInfoList = null;
		String sql = "select id,house_id,seller_id,buyer_id,trade_time from tb_tradeinfo where  buyer_id=?";
		try {
			tradeInfoList = super.query(sql, new Object[] { userId }, TradeInfo.class);
		} catch (Exception e) {
			throw new TradeInfoDaoException("��ѯ���׼�¼ʧ��");
		}
		return tradeInfoList == null ? null : tradeInfoList;
	}

	/**
	 * ��ѯ�Լ����۳���¼
	 * 
	 * @param userId
	 * @return
	 * @throws TradeInfoDaoException
	 */
	public List<TradeInfo> querySellInfosByUserId(int userId) throws TradeInfoDaoException {
		List<TradeInfo> tradeInfoList = null;
		String sql = "select id,house_id,seller_id,buyer_id,trade_time from tb_tradeinfo where seller_id=? ";
		try {
			tradeInfoList = super.query(sql, new Object[] { userId }, TradeInfo.class);
		} catch (Exception e) {
			throw new TradeInfoDaoException("��ѯ���׼�¼ʧ��");
		}
		return tradeInfoList == null ? null : tradeInfoList;
	}
}
