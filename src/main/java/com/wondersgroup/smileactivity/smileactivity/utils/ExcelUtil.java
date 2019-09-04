package com.wondersgroup.smileactivity.smileactivity.utils;

import jxl.write.*;
import jxl.write.Number;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

public class ExcelUtil {

	private final static String BOLD_FORMAT = "boldFormat"; // 加粗字体

	private final static String BASIC_FORMAT = "basicFormat"; // 普通字体

    private final static String BASIC_AMOUNT_FORMAT = "baseAmountFormat"; // 金额格式化


	private final static Map<String, WritableCellFormat> FORMAT_MAP = new HashMap<String, WritableCellFormat>(); // 标签样式表

	/************************************ XSSF *********************************************/

	/**
	 * 取得指定单元格行和列
	 *
	 * @param keyMap 所有单元格行、列集合
	 * @param key 单元格标识
	 * @return 0：列 1：行（列表型数据不记行，即1无值）
	 */
	public static int[] getPos(HashMap<String, Object> keyMap, String key) {
		int[] ret = new int[0];

		String val = (String) keyMap.get(key);

		if (val == null || val.length() == 0)
			return ret;

		String pos[] = val.split(",");

		if (pos.length == 1 || pos.length == 2) {
			ret = new int[pos.length];
			for (int i0 = 0; i0 < pos.length; i0++) {
				if (pos[i0] != null && pos[i0].trim().length() > 0) {
					ret[i0] = Integer.parseInt(pos[i0].trim());
				} else {
					ret[i0] = 0;
				}
			}
		}
		return ret;
	}


	/**
	 * 取整数
	 *
	 * @param srcString
	 * @return
	 */
	private static String getCutDotStr(String srcString) {
		String newString = "";
		if (srcString != null && srcString.endsWith(".0")) {
			newString = srcString.substring(0, srcString.length() - 2);
		} else {
			newString = srcString;
		}
		return newString;
	}

	/**
	 * 获取格式，不适于循环方法中使用，wb.createCellStyle()次数超过4000将抛异常
	 *
	 * @param keyMap
	 * @param key
	 * @return
	 */
	public static CellStyle getStyle(HashMap<String, Object> keyMap, String key, Workbook wb) {
		CellStyle cellStyle = null;

		cellStyle = (CellStyle) keyMap.get(key + "CellStyle");
		// 当字符超出时换行
		cellStyle.setWrapText(true);
		CellStyle newStyle = wb.createCellStyle();
		newStyle.cloneStyleFrom(cellStyle);
		return newStyle;
	}

	/**
	 * Excel单元格输出
	 *
	 * @param sheet
	 * @param row 行
	 * @param cell 列
	 * @param value 值
	 * @param cellStyle 样式
	 */
	public static void setCellValue(Sheet sheet, int row, int cell, Object value, CellStyle cellStyle) {
		Row rowIn = sheet.getRow(row);
		if (rowIn == null) {
			rowIn = sheet.createRow(row);
		}
		Cell cellIn = rowIn.getCell(cell);
		if (cellIn == null) {
			cellIn = rowIn.createCell(cell);
		}
		if (cellStyle != null) {
			// 修复产生多超过4000 cellStyle 异常
			// CellStyle newStyle = wb.createCellStyle();
			// newStyle.cloneStyleFrom(cellStyle);
			cellIn.setCellStyle(cellStyle);
		}
		// 对时间格式进行单独处理
		if (value == null) {
			cellIn.setCellValue("");
		} else {
			if (isCellDateFormatted(cellStyle)) {
				cellIn.setCellValue((Date) value);
			} else {
				cellIn.setCellValue(new XSSFRichTextString(value.toString()));
			}
		}
	}

	/**
	 * 单独设置样式
	 *
	 * @param sheet
	 * @param row 行
	 * @param cell 列
	 * @param cellStyle 样式
	 */
	public static void setCellStyle(Sheet sheet, int row, int cell, CellStyle cellStyle) {
		Row rowIn = sheet.getRow(row);
		if (rowIn == null) {
			rowIn = sheet.createRow(row);
		}
		Cell cellIn = rowIn.getCell(cell);
		if (cellIn == null) {
			cellIn = rowIn.createCell(cell);
		}
		if (cellStyle != null) {
			// 修复产生多超过4000 cellStyle 异常
			// CellStyle newStyle = wb.createCellStyle();
			// newStyle.cloneStyleFrom(cellStyle);
			cellIn.setCellStyle(cellStyle);
		}
	}

	/**
	 * 根据表格样式判断是否为日期格式
	 *
	 * @param cellStyle
	 * @return
	 */
	public static boolean isCellDateFormatted(CellStyle cellStyle) {
		if (cellStyle == null) {
			return false;
		}
		int i = cellStyle.getDataFormat();
		String f = cellStyle.getDataFormatString();

		return org.apache.poi.ss.usermodel.DateUtil.isADateFormat(i, f);
	}

	/**
	 * 适用于导出的数据Excel格式样式重复性较少 不适用于循环方法中使用
	 *
	 * @param wbModule
	 * @param sheet
	 * @param pos 模板文件信息
	 * @param startCell 开始的行
	 * @param value 要填充的数据
	 * @param cellStyle 表格样式
	 */
	public static void createCell(Workbook wbModule, Sheet sheet, HashMap<String, Object> pos, int startCell, Object value, String cellStyle) {
		int[] excelPos = getPos(pos, cellStyle);
		setCellValue(sheet, startCell, excelPos[0], value, getStyle(pos, cellStyle, wbModule));
	}

	/************************************ XSSF *******************************************/

	/**
	 * 将vo转换成Map
	 *
	 * @param listVO listVO
	 * @return Map对象
	 */
	public static List<Map<String, Object>> toMap(List<?> listVO) throws Exception {
		List<Map<String, Object>> listVoMap = new ArrayList<Map<String, Object>>();

		for (Object vo : listVO) {
			Map<String, Object> voMap = new HashMap<String, Object>();
			Method[] methods = vo.getClass().getMethods();

			for (Method method : methods) {
				if (method.getName().startsWith("get")) {
					String field = method.getName();
					field = field.substring(field.indexOf("get") + 3);
					field = field.toLowerCase().charAt(0) + field.substring(1);

					if (method.getParameterTypes().length == 0) {
						Object value = method.invoke(vo);
						voMap.put(field, value);
					}
				}
			}

			listVoMap.add(voMap);
		}

		return listVoMap;
	}

	/**
	 * 创建一个2007版本之前的excel文件，格式为xls
	 *
	 * @return
	 */
	public static Workbook newXlsWorkbook() {
		return new HSSFWorkbook();
	}

	/**
	 * 创建一个2007版本之后的excel文件，格式为xlsx
	 *
	 * @return
	 */
	public static Workbook newXlsxWorkbook() {
		return new XSSFWorkbook();
	}

	/**
	 * 打开一个excel文件
	 *
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Workbook openWorkbook(String filePath) throws FileNotFoundException, IOException {
		if (filePath.endsWith(".xlsx")) {
			return new XSSFWorkbook(new FileInputStream(filePath));
		} else if (filePath.endsWith(".xls")) {
			return new HSSFWorkbook(new FileInputStream(filePath));
		} else {
			return null;
		}
	}

	public static void makeGeneralSheetByParams(WritableWorkbook book, String sheetName, String[] titleArray, int sort, List<List<Object>> dataList) throws Exception {
		init();
		final WritableSheet sheet = book.createSheet(sheetName, sort);
		setTitileLabelsTool(sheet, titleArray); // 设置标题
		setDataLabelsTool(sheet, dataList); // 载入数据
	}

	/**
	 * 数据处理失败时调用<br>
	 * </> 防止所有程序崩溃,并提示用户
	 *
	 * @param rowList
	 */
	public static void makeDataError(List<List<String>> rowList) {
		List<String> colList = new ArrayList<String>();
		colList.add("系统错误! ");
		colList.add("请联系管理员!");
		rowList.add(colList);
	}

	/**
	 * 初始化参数
	 *
	 * @throws WriteException
	 */
	public static void init() throws WriteException {
		// 1.初始化标签样式
		WritableFont FONT_BASIC = new WritableFont(WritableFont.TAHOMA, 11); // 11号 Tahoma 字体
		WritableFont FONT_BOLD = new WritableFont(WritableFont.TAHOMA, 11, WritableFont.BOLD); // 11号 Tahoma 字体 加粗
		WritableCellFormat boldFormat = new WritableCellFormat(FONT_BOLD);
		boldFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 把垂直对齐方式指定为居中
		boldFormat.setAlignment(jxl.format.Alignment.CENTRE);
		FORMAT_MAP.put("boldFormat", boldFormat);

		WritableCellFormat basicFormat = new WritableCellFormat(FONT_BASIC);
		basicFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 把垂直对齐方式指定为居中
		basicFormat.setAlignment(jxl.format.Alignment.CENTRE);
		basicFormat.setWrap(true);
		FORMAT_MAP.put("basicFormat", basicFormat);


        NumberFormat numberFormat = new NumberFormat("#,##0.00");
        WritableCellFormat baseAmountFormat = new WritableCellFormat(numberFormat);
        baseAmountFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);// 把垂直对齐方式指定为居中
        baseAmountFormat.setAlignment(jxl.format.Alignment.CENTRE);
        FORMAT_MAP.put("baseAmountFormat", baseAmountFormat);
	}

	/**
	 * 传入一个工作表对象和标题的名称列表 自动传入标题
	 *
	 * @param sheet
	 * @param titileLabelsArray
	 * @throws WriteException
	 */
	public static void setTitileLabelsTool(final WritableSheet sheet, final String[] titileLabelsArray) throws WriteException {
		if (titileLabelsArray == null) {
			return;
		}
		// 设置列的宽度
		sheet.getSettings().setDefaultColumnWidth(20);
		for (int sort = 0; sort < titileLabelsArray.length; sort++) {
			Label firstTitle = new Label(sort, 0, titileLabelsArray[sort], FORMAT_MAP.get(BOLD_FORMAT));
			sheet.addCell(firstTitle);
		}
	}

	/**
	 * 自动封装数据
	 *
	 * @param sheet
	 * @param dataList List<List<String>> 2维列表代表行和列
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws WriteException
	 */
	public static void setDataLabelsTool(final WritableSheet sheet, final List<List<Object>> dataList)
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, WriteException {
		if (dataList == null) {
			return;
		}
		sheet.getSettings().setDefaultColumnWidth(20);
		for (int rowSort = 0; rowSort < dataList.size(); rowSort++) {
			List<Object> voList = dataList.get(rowSort);
			for (int colSort = 0; colSort < voList.size(); colSort++) {

                WritableCell writableCell ;
                Object val = voList.get(colSort);

                if(val==null){
                    writableCell = new Label(colSort, rowSort + 1, "", FORMAT_MAP.get(BASIC_FORMAT));
                }else if(val instanceof String){
                    writableCell = new Label(colSort, rowSort + 1, (String)val, FORMAT_MAP.get(BASIC_FORMAT));
                }else if(val instanceof Long){
                    writableCell = new Number(colSort, rowSort + 1, (Long)val, FORMAT_MAP.get(BASIC_FORMAT));
                }else if(val instanceof BigDecimal){
                    writableCell = new Number(colSort, rowSort + 1, ((BigDecimal)val).doubleValue(), FORMAT_MAP.get(BASIC_AMOUNT_FORMAT));
                }else{
                    throw new RuntimeException("传入的数据格式有误");
                }
				sheet.addCell(writableCell);
			}

		}
	}

	/**
	 * 金额转换为千分位
	 *
	 * @param money
	 * @return
	 */
	public static String parseMoney(BigDecimal money) {
	    if(money==null){
	        return "";
        }
		if (BigDecimal.ZERO.equals(money)) {
			return "0";
		} else {
			DecimalFormat df = new DecimalFormat(",###,##0.00");
			return df.format(money);
		}
	}


    /**
     * 包含了 xls和xlsx转换的方法
     * @param file
     * @return
     * @throws Exception
     */
	public static List<List<String>> convertExcel(File file) throws Exception {
        List<List<String>> lists ;
        String fileName=file.getName();
        String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
        if("xls".equals(prefix.toLowerCase())){
            lists = ExcelUtil.xls2String(file);
        } else if("xlsx".equals(prefix.toLowerCase())){
            lists = ExcelUtil.xlsxString(file);
        } else{
            throw new Exception("输入的文件格式有误!请上传xls或者xlsx类型的文件");
        }
        return lists;
    }
	
	 /**
     * 包含了 xls和xlsx转换的方法
     * @param file
     * @return
     * @throws Exception
     */
	public static List<List<String>> convertExcelMutileSheet(File file) throws Exception {
        List<List<String>> lists ;
        String fileName=file.getName();
        String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
        if("xls".equals(prefix.toLowerCase())){
            lists = ExcelUtil.xls2StringSheet(file);
        } else if("xlsx".equals(prefix.toLowerCase())){
            lists = ExcelUtil.xlsxStringMutileSheet(file);
        } else{
            throw new Exception("输入的文件格式有误!请上传xls或者xlsx类型的文件");
        }
        return lists;
    }


    /**
     * 转换xls文件为list
     * @param file
     * @return
     */
	public static List<List<String>> xls2String(File file) throws Exception {


        List<List<String>> result = new ArrayList<>();
		try {
			FileInputStream fis = new FileInputStream(file);
			StringBuilder sb = new StringBuilder();
			jxl.Workbook rwb = jxl.Workbook.getWorkbook(fis);
            jxl.Sheet[] sheet = rwb.getSheets();
			for (int i = 0; i < sheet.length; i++) {
                jxl.Sheet rs = rwb.getSheet(i);
				for (int j = 0; j < rs.getRows(); j++) {
                    jxl.Cell[] cells = rs.getRow(j);
//                    if(!cells[0].getContents().equals("")){
                        List<String> data =new ArrayList<>();
                        for (int k = 0; k < cells.length; k++) {
                            String val = cells[k].getContents();
                            data.add(val);
                        }
                        result.add(data);
//                    }
				}
			}
			fis.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("xls文件转换失败,请检测文件后重试!");
		}
		return result;
	}


    /**
     * 转换xls文件为list
     * @param file
     * @return
     */
	public static List<List<String>> xls2StringSheet(File file) throws Exception {


        List<List<String>> result = new ArrayList<>();
		try {
			FileInputStream fis = new FileInputStream(file);
			StringBuilder sb = new StringBuilder();
			jxl.Workbook rwb = jxl.Workbook.getWorkbook(fis);
            jxl.Sheet[] sheet = rwb.getSheets();
			for (int i = 0; i < sheet.length; i++) {
                jxl.Sheet rs = rwb.getSheet(i);
				for (int j = 1; j < rs.getRows(); j++) {
                    jxl.Cell[] cells = rs.getRow(j);
//                    if(!cells[0].getContents().equals("")){
                        List<String> data =new ArrayList<>();
                        for (int k = 0; k < cells.length; k++) {
                            String val = cells[k].getContents();
                            if(!StringUtils.isBlankOrNull(val))
                                data.add(val);
                        }

                    if (data.size() != 0) {
                        data.add(rs.getName());
                        result.add(data);
                    }

				}
			}
			fis.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("xls文件转换失败,请检测文件后重试!");
		}
		return result;
	}

   /**
     * 转换xlsx文件为list
     * @param file
     * @return
     */
    public static List<List<String>> xlsxString(File file) throws Exception {
        List<List<String>> result = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(file);
            StringBuilder sb = new StringBuilder();
            Workbook wb = WorkbookFactory.create(fis);
            //3.得到Excel工作表对象
            	Sheet sheet = wb.getSheetAt(0);
            	//总行数
            	int trLength = sheet.getLastRowNum();
            	//4.得到Excel工作表的行
            	Row row = sheet.getRow(0);
            	//总列数
            	int tdLength = row.getLastCellNum();
            	for(int i=0;i<trLength+1;i++) {
            		//得到Excel工作表的行
            		Row row1 = sheet.getRow(i);
            		List<String> data =new ArrayList<>();
            		for (int j = 0; j < tdLength; j++) {
            			//得到Excel工作表指定行的单元格
            			Cell cell1 = row1.getCell(j);
            			String val = "";
            			if(cell1!=null){
            				val = cell1.toString();
            			}
            			//将所有列中的内容都设置成String类型格式
//                    if (val.equals("")) {
//                        break;
//                    }else {
            			data.add(val);
//                    }
            		}
            		result.add(data);
            	}
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("xlsx文件转换失败,请检测文件后重试!");
        }
        return result;
    }

    /**
     * 转换xlsx文件为list
     * @param file
     * @return
     */
    public static List<List<String>> xlsxStringMutileSheet(File file) throws Exception {
        List<List<String>> result = new ArrayList<>();
        FileInputStream fis = null;
        String errorMsg = "xlsx文件转换失败,请检测文件后重试!";
        try {
        	fis = new FileInputStream(file);
            StringBuilder sb = new StringBuilder();
            Workbook wb = WorkbookFactory.create(fis);
            //3.得到Excel工作表对象
            //int numOfSheet = wbPartModule.getNumberOfSheets();
            int numOfSheet = wb.getNumberOfSheets();
            for(int m = 0; m < numOfSheet; m++){
            	Sheet sheet = wb.getSheetAt(m);
            	//总行数
            	int trLength = sheet.getLastRowNum();
            	if(trLength==0){
            		errorMsg = file.getName()+"文件的"+sheet.getSheetName()+"工作表不能为空";
            	}
            	//4.得到Excel工作表的行
            	Row row = sheet.getRow(0);
            	//总列数
            	int tdLength = row.getLastCellNum();
            	for(int i=1;i<trLength+1;i++) {//从1开始，去掉表头
            		//得到Excel工作表的行
            		Row row1 = sheet.getRow(i);
            		List<String> data =new ArrayList<>();
            		for (int j = 0; j < tdLength; j++) {
            			//得到Excel工作表指定行的单元格
            			Cell cell1 = row1.getCell(j);
            			String val = "";
            			if(cell1!=null){
            				val = cell1.toString();
            			}
            			//将所有列中的内容都设置成String类型格式
//                    if (val.equals("")) {
//                        break;
//                    }else {
            			data.add(val);
//                    }
            		}
            		data.add(sheet.getSheetName());
            		result.add(data);
            	}
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(errorMsg);
        }finally {
			if(null!=fis)fis.close();
		}
        return result;
    }

}