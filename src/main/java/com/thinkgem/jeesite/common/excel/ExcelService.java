package com.thinkgem.jeesite.common.excel;

import java.util.List;

public interface ExcelService {

	/**
	 * 根据excel文件得到二维数组。
	 * @param list二维数据
	 * @param fileName生成excel文件名称
	 * @return 生成excel的文件路径
	 */
	public String exportExcel(List<List<String>> lists, String fileName);
	
	/**.
	 * 导入excel
	 * @param filePath excel的文件路径
	 * @return
	 */
	public List<List<String>> importExcel(String filePath);

}