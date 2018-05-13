package cn.edu.xhu.consts;

public class FileUploadConsts {
	public static final int FILE_MAX_SIZE = 500 * 1024;// 每张最多500k
	public static final int MAX_SIZE = 5 * 500 * 1024;// 总大小限制
	public static final int FACTORY_THRESHOLD = 4 * 1024;// 临时内存大小，多余的存硬盘
	public static final int MAX_AMOUNT = 22;// 张数限制20
	public static final String[] ALLOW_TYPE = { ".jpg", ".png", ".bmp", ".gif" };
}
