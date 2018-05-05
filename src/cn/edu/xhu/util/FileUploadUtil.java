package cn.edu.xhu.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.edu.xhu.consts.FileUploadConsts;
import cn.edu.xhu.dao.HouseDao;
import cn.edu.xhu.domain.Image;
import cn.edu.xhu.exception.FileUploadOperatException;
import cn.edu.xhu.service.UserService;

public class FileUploadUtil {
	public static void upload(int userid, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String houseId = null;
		String imageType = null;
		List<Image> images = new ArrayList<Image>();
		UserService service = new UserService();
		if (!ServletFileUpload.isMultipartContent(request)) { // 检查上传表达是否正确
			throw new FileUploadOperatException("表单属性未设置[multipart/form-data或input未设置name]");
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(FileUploadConsts.FACTORY_THRESHOLD);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(FileUploadConsts.MAX_SIZE);
		upload.setFileSizeMax(FileUploadConsts.FILE_MAX_SIZE);
		List<FileItem> itemList = upload.parseRequest(request);
		if (itemList.size() > FileUploadConsts.MAX_AMOUNT) {
			throw new FileUploadOperatException("上传图片数量上限");
		}
		Calendar calendarStart = Calendar.getInstance();
		calendarStart.set(2018, 2, 1);
		Calendar calendarNow = Calendar.getInstance();
		int year = calendarNow.get(Calendar.YEAR) - calendarStart.get(Calendar.YEAR);
		int month = year * 12 + (calendarNow.get(Calendar.MONTH) - calendarStart.get(Calendar.MONTH));
		String splitDir = "/pic" + month + "/";
		String saveDir = request.getServletContext().getRealPath(splitDir);
		File dir = new File(saveDir);
		if (!dir.exists()) {
			dir.mkdir();
		}
		for (FileItem item : itemList) {
			if (item.isFormField()) {
				switch (item.getFieldName()) {
				case "house_id":
					houseId = item.getString("utf-8");
					if ("".equals(houseId) || houseId == null) {
						throw new FileUploadOperatException("提交参数错误");
					}
					HouseDao dao = new HouseDao();
					if (!dao.checkHouseByUserIdAndHouseId(Integer.parseInt(houseId), userid)) {
						throw new FileUploadOperatException("提交参数错误");
					}
					break;
				case "image_type":
					imageType = item.getString("utf-8");
					if ("".equals(imageType) || imageType == null) {
						throw new FileUploadOperatException("提交参数错误");
					}
					break;
				default:
				}
			}
		}
		for (FileItem item : itemList) {
			if (!item.isFormField()) {
				String uploadPath = item.getName();
				if (("".equals(item.getName()) || item.equals(item.getName()))) {
					throw new FileUploadOperatException("未选择图片");
				}
				String dotExtendName = uploadPath.substring(uploadPath.lastIndexOf("."));
				for (String type : FileUploadConsts.ALLOW_TYPE) {
					if (type.equals(dotExtendName)) {
						String saveName = System.currentTimeMillis() + dotExtendName;
						String savePath = saveDir + "/" + saveName;
						File file = new File(savePath);
						FileOutputStream out = new FileOutputStream(file);
						InputStream in = item.getInputStream();
						int len = -1;
						while ((len = in.read()) != -1) {
							out.write(len);
						}
						out.flush();
						out.close();
						in.close();
						Image image = new Image();
						image.setHouseid(Integer.parseInt(houseId));
						image.setType(Integer.parseInt(imageType));
						String path = "http://www.xhban.com:8080/EM" + splitDir + saveName;
						// String path =
						// "http://localhost:8080/cn.edu.xhu.EstateManager" +
						// splitDir + saveName;
						System.out.println(path);
						image.setPath(path);
						images.add(image);
						break;
					} else {
						throw new FileUploadOperatException("图片类型不支持");
					}
				}
			}
		}
		String json = service.uploadImage(Integer.parseInt(houseId), images);
		response.getWriter().print(json);
	}
}
