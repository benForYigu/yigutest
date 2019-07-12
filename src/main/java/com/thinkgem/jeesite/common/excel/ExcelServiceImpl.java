package com.thinkgem.jeesite.common.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.common.utils.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.stereotype.Service;

@Service("ExcelService")
public class ExcelServiceImpl implements ExcelService, Serializable {
	private static final long serialVersionUID = 1L;

//	private static final AtomicInteger aint = new AtomicInteger(0);

	// excel 默认最大3万行
	private static final int DEFAULT_ROW_MAX = 30000;

	@Override
	public String exportExcel(List<List<String>> lists, String fileName) {
		HSSFWorkbook wb = new HSSFWorkbook();
		for (int s = 0; s < lists.size() / DEFAULT_ROW_MAX + 1; s++) {
			HSSFSheet sheet = wb.createSheet();

			int min = DEFAULT_ROW_MAX * s;
			int max = min
					+ ((lists.size() - min) / DEFAULT_ROW_MAX > 0 ? DEFAULT_ROW_MAX : lists.size() % DEFAULT_ROW_MAX);
			for (int i = min, rowi = 0; i < max; i++, rowi++) {
				List<?> list = (List<?>) lists.get(i);

				HSSFRow row = sheet.createRow(rowi);

				for (int j = 0; j < list.size(); j++) {
					String str = "";
					if (!StringUtils.isNullOrEmpty(list.get(j))) {
						str = list.get(j).toString();
					}
					HSSFCell cell = row.createCell(j);
					cell.setCellValue(str);
				}
			}

		}
		FileOutputStream os;
		String sclassPath = Thread.currentThread().getContextClassLoader().getResource("../../").getPath();
		String numi = StringUtils.getRandomCharAndNumr(8);
		File fileDir = new File(sclassPath + "/resource/excel" + "/" + numi);
		fileDir.mkdirs();
		File file = new File(fileDir, fileName + ".xls");
		try {
			os = new FileOutputStream(file);
			wb.write(os);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "http://localhost:8080/schoolappserver/resource/excel" + "/" + numi + "/" + fileName
				+ ".xls";
	}

	/**
	 * 导入excel
	 */
	public List<List<String>> importExcel(String filePath) {

		URL url = null;
		POIFSFileSystem pss = null;
		FileInputStream fis = null;
		InputStream in = null;
		HttpURLConnection conn = null;
		List<List<String>> list = new ArrayList<>();

		try {
			if (filePath.contains("http")) {
				url = new URL(filePath);
				conn = (HttpURLConnection) url.openConnection();
				in = conn.getInputStream();
				pss = new POIFSFileSystem(in);
			} else {
				File file = new File(filePath);
				fis = new FileInputStream(file);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		try {
			if (pss == null) {
				pss = new POIFSFileSystem(fis);
			}
			HSSFWorkbook hwb = new HSSFWorkbook(pss);
			HSSFSheet sheet = hwb.getSheetAt(0);

			int begin = sheet.getFirstRowNum();
			int end = sheet.getLastRowNum();

			do {
				List<String> rowList = new ArrayList<>();
				HSSFRow rhead = sheet.getRow(begin);
				if (rhead == null) {
					return list;
				}

				// int b = rhead.getFirstCellNum();
				int b = 0;// 这里本来用的是
							// rhead.getFirstCellNum();但是出现的问题是第一格为空的时候读取不到。
				int e = rhead.getLastCellNum();

				String empty = "";
				inner: do {
					HSSFCell cell = rhead.getCell(b);
					if (cell == null) {
						rowList.add("");
						continue inner;
					}
					if (HSSFCell.CELL_TYPE_NUMERIC == cell.getCellType() && HSSFDateUtil.isCellDateFormatted(cell)) {
						Date date = cell.getDateCellValue();
						SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						String strDate = s.format(date);
						empty += strDate;
						rowList.add(strDate);
					} else {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String content = cell.getStringCellValue().toString();
						if (content == null) {
							content = "";
						}
						empty += content;
						rowList.add(content);
					}

				} while (++b < e);
				if (empty.isEmpty()) {
					return list;
				}
				list.add(rowList);
			} while (++begin < end + 1);

			return list;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (fis != null) {
					fis.close();
					fis = null;
				}
				if (in != null) {
					in.close();
					in = null;
				}
				if (conn != null) {
					conn.disconnect();
					conn = null;
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}

		}

	}

}