package cn.edu.xhu.dao;

import java.util.List;

import cn.edu.xhu.domain.Image;
import cn.edu.xhu.exception.ImageDaoException;
import cn.edu.xhu.util.ThreadLocalDateUtil;

public class ImageDao extends BaseDao {
	/**
	 * ÃÌº”Õº∆¨
	 * 
	 * @param image
	 * @return
	 * @throws ImageDaoException
	 */
	public boolean addImage(Image image) throws ImageDaoException {
		String sql = "insert into tb_image values (null,?,?,?,?,?)";
		try {
			Object[] params = { image.getHouseid(), image.getType(), image.getPath(), ThreadLocalDateUtil.formatDate(),
					ThreadLocalDateUtil.formatDate() };
			int result = super.update(sql, params);
			if (result == 1) {
				return true;
			}
		} catch (Exception e) {
			throw new ImageDaoException("Õº∆¨ÃÌº” ß∞‹");
		}
		return false;
	}

	/**
	 * ∏˘æ›id≤Èø¥Õº∆¨ «∑Ò¥Ê‘⁄
	 * 
	 * @param id
	 * @return
	 * @throws ImageDaoException
	 */
	public boolean checkImageByIdAndHouseId(int id, int houseId) throws ImageDaoException {
		String sql = "select count(*) as count from tb_image where id=? and house_id=?";
		try {
			int count = super.count(sql, new Object[] { id, houseId });
			if (count == 1) {
				return true;
			}
		} catch (Exception e) {
			throw new ImageDaoException("Õº∆¨≤È—Ø ß∞‹");
		}
		return false;
	}

	/**
	 * ∏˘æ›id…æ≥˝Õº∆¨
	 * 
	 * @param id
	 * @return
	 * @throws ImageDaoException
	 */
	public boolean deleteImageById(int id) throws ImageDaoException {
		String sql = "delete from tb_image where id=?";
		try {
			int count = super.update(sql, new Object[] { id });
			if (count == 1) {
				return true;
			}
		} catch (Exception e) {
			throw new ImageDaoException("Õº∆¨…æ≥˝ ß∞‹");
		}
		return false;

	}

	/**
	 * ∏˘æ›∑øŒ›id…æ≥˝Õº∆¨
	 * 
	 * @param houseId
	 * @return
	 * @throws ImageDaoException
	 */
	public boolean deleteImageByHouseId(int houseId) throws ImageDaoException {
		String sql = "delete from tb_image where house_id=?";
		try {
			int count = super.update(sql, new Object[] { houseId });
			if (count > 0) {
				return true;
			}
		} catch (Exception e) {
			throw new ImageDaoException("Õº∆¨…æ≥˝ ß∞‹");
		}
		return false;
	}

	/**
	 * ºÏ≤È∑øŒ›±‡∫≈Œ™houseId «∑Ò¥Ê‘⁄Õº∆¨
	 * 
	 * @param houseId
	 * @return
	 * @throws ImageDaoException
	 */
	public boolean checkImagesByHouseId(int houseId) throws ImageDaoException {
		String sql = "select count(*) as count from tb_image where house_id=?";
		try {
			int count = super.count(sql, new Object[] { houseId });
			if (count > 0) {
				return true;
			}
		} catch (Exception e) {
			throw new ImageDaoException("Õº∆¨≤È—Ø ß∞‹");
		}
		return false;

	}

	/**
	 * ∏˘æ›∑øŒ›±‡∫≈≤È’“Õº∆¨
	 * 
	 * @param houseId
	 * @return
	 * @throws ImageDaoException
	 */
	public List<Image> queryImagesByHouseId(int houseId) throws ImageDaoException {
		List<Image> images = null;
		String sql = "select id,house_id,image_type,image_path from tb_image where house_id=?";
		try {
			images = super.query(sql, new Object[] { houseId }, Image.class);
		} catch (Exception e) {
			throw new ImageDaoException("Õº∆¨≤È’“ ß∞‹");
		}
		return images == null ? null : images;
	}
}
