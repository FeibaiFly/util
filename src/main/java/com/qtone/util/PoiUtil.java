package com.qtone.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * poi 导入导出工具类
 * @author luojunjie
 * @date 2018-10-8 20:34
 */
public class PoiUtil {
    private final static String XLS = "xls";

    private final static String XLSX = "xlsx";

    private final static long FILE_MAX_SIZE = 100 * 1024 * 1024L;

    private static Logger logger = LoggerFactory.getLogger(PoiUtil.class);
    
    public static final String POINT = ".";

    /**
     * 导出Excel
     * @param fileName 文件名（不需要后缀）
     * @param sheetName   sheet名称
     * @param headers 表头（数组）
     * @param list<T>    导出的数据（T为entity，不需要序列化，属性个数和顺序要和headers一一对应）
     * @param
     */
    public static <T> void exportExcel(String fileName, String sheetName, String[] headers, List<?> list, HttpServletRequest request, HttpServletResponse response) throws Exception {
        fileName = fileName(request, fileName + ".xls");

        //告诉浏览器用什么软件可以打开此文件
        response.reset(); //清除response中的缓存信息
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        //告诉浏览器用什么软件可以打开此文件
        response.setContentType("application/vnd.ms-excel;");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods","POST, GET, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept, Token");
        response.setHeader("Strict-Transport-Security", "max-age=3600; includeSubDomains");
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-XSS-Protection", "1; mode=block");
        response.setHeader("Referer-Policy", "origin");
        response.setHeader("Content-Security-Policy", "*");
        response.setHeader("X-Permitted-Cross-Domain-Policies", "master-only");
        response.setHeader("X-Download-Options", "noopen");
        response.addHeader("X-Frame-Options","*");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        HSSFWorkbook workbook = null;
        OutputStream out = response.getOutputStream();
        try {
            workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(sheetName);
            sheet.setDefaultColumnWidth(15);
            HSSFCellStyle style = workbook.createCellStyle();
            HSSFFont font = workbook.createFont();
            style.setFont(font);
            HSSFRow row = sheet.createRow(0);
            setHeaders(style, headers, row);
            //迭代数据
            Iterator<?> iterator = list.iterator();
            int index = 0;
            while (iterator.hasNext()) {
                index++;
                row = sheet.createRow(index);
                T entityBean = (T) iterator.next();
                Field[] fields = entityBean.getClass().getDeclaredFields();
                //反射获得所有列
                for (int i = 0; i < fields.length; i++) {
                    HSSFCell cell = row.createCell(i);
                    Field field = fields[i];
                    String fieldName = field.getName();
                    String getMethodName = "get".concat(fieldName.substring(0, 1).toUpperCase()).concat(fieldName.substring(1));
                    Class<? extends Object> classObject = entityBean.getClass();
                    Method getMethod = classObject.getMethod(getMethodName, new Class[]{});
                    Object value = getMethod.invoke(entityBean, new Object[]{});
                    value = value == null ? "" : value;
                    cell.setCellValue(value.toString());
                }
            }
            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }


    private static void setHeaders(CellStyle commonWrapStyle, String[] headers, HSSFRow row2) {
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row2.createCell(i);
            cell.setCellStyle(commonWrapStyle);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
    }


    public static List<String[]> readExcel(File file, int... readNum) throws IOException {
        //检查文件
        if (null == file) {
            throw new FileNotFoundException("IMPORT_FILE_NOT_NULL");
        }
        if (file.length() > FILE_MAX_SIZE) {
            throw new FileNotFoundException("FILE_SIZE_MORE_THAN_100_MB");
        }
        //获得文件名
        String fileName = file.getName();
        //判断文件是否是excel文件
        if (!fileName.endsWith(XLS) && !fileName.endsWith(XLSX)) {
            throw new IOException("FILE_NOT_EXCEL");
        }
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //获取excel文件的io流
            InputStream is = new FileInputStream(file);
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if (fileName.endsWith(XLS)) {
                //2003
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith(XLSX)) {
                //2007
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        List<String[]> list = new ArrayList<String[]>();
        if (workbook != null) {
//            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
            //获得当前sheet工作表(只读第一个)
            Sheet sheet = workbook.getSheetAt(0);

            if (sheet == null) {
                return null;
            }
            //获得当前sheet的开始行
            int firstRowNum = sheet.getFirstRowNum();
            //获得当前sheet的结束行
            int lastRowNum = sheet.getLastRowNum();
            //循环所有行
            for (int rowNum = firstRowNum; rowNum <= lastRowNum; rowNum++) {
                //获得当前行
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }
                //获得当前行的开始列(第一列为空也可以获得)
                //int firstCellNum = row.getFirstCellNum();
                int firstCellNum = 0;
                //获得当前行不为空的列数
                //int lastCellNum = row.getPhysicalNumberOfCells();
                //获得列数
                int lastCellNum = 0;
                if (null != readNum && readNum.length > 0) {
                    lastCellNum = readNum[0];
                } else {
                    lastCellNum = sheet.getRow(rowNum).getLastCellNum();
                }

                String[] cells = new String[lastCellNum];
                //循环当前行
                int num = 0;
                for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {
                    //无论是否为空，都要返回值
                    Cell cell = row.getCell(cellNum, Row.RETURN_BLANK_AS_NULL);
                    String value = getCellValue(cell);
                    ++num;
                    cells[cellNum] = value.trim();
                }
                if (num > 0) {
                    list.add(cells);
                }
            }
//            }
        }
        return list;
    }

    /**
     * create by: zhangpk
     * description: 读入excel文件,过滤空行，解析后返回
     * create time: 17:08 2020/11/26
     * @Param: null
     * @return
     */
    public static List<String[]> readExcelFilterNull(MultipartFile file, int... readNum) throws IOException {
        //检查文件
        checkFile(file);
        //获得Workbook工作薄对象
        Workbook workbook = getWorkBook(file);
        //创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        List<String[]> list = new ArrayList<String[]>();
        if (workbook != null) {
//            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
            //获得当前sheet工作表(只读第一个)
            Sheet sheet = workbook.getSheetAt(0);

            if (sheet == null) {
                return null;
            }
            //获得当前sheet的开始行
            int firstRowNum = sheet.getFirstRowNum();
            //获得当前sheet的结束行
            int lastRowNum = sheet.getLastRowNum();
            //循环所有行
            for (int rowNum = firstRowNum; rowNum <= lastRowNum; rowNum++) {
                //获得当前行
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }
                //获得当前行的开始列(第一列为空也可以获得)
                //int firstCellNum = row.getFirstCellNum();
                int firstCellNum = 0;
                //获得当前行不为空的列数
                //int lastCellNum = row.getPhysicalNumberOfCells();
                //获得列数
                int lastCellNum = 0;
                if(null != readNum && readNum.length > 0){
                    lastCellNum = readNum[0];
                } else {
                    lastCellNum = sheet.getRow(rowNum).getLastCellNum();
                }

                String[] cells = new String[lastCellNum];
                //循环当前行
                int num = 0;
                for (int cellNum = firstCellNum; cellNum < lastCellNum; cellNum++) {

                    Cell cell = row.getCell(cellNum, Row.RETURN_BLANK_AS_NULL);
                    String value = getCellValue(cell);
                    //如果改行全部为空则跳过该行
                    if(!StringUtils.isBlank(value)) {
                        ++num;
                    }
                    cells[cellNum] = value.trim();
                }
                if (num > 0) {
                    list.add(cells);
                }
            }
        }
        return list;
    }


    /**
     * 获取文件
     *
     * @param file
     * @return
     */
    public static Workbook getWorkBook(MultipartFile file) {
        //获得文件名
        String fileName = file.getOriginalFilename();
        //创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            //获取excel文件的io流
            InputStream is = file.getInputStream();
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if (fileName.endsWith(XLS)) {
                //2003
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith(XLSX)) {
                //2007
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
        }   
        return workbook;
    }

    /**
     * 检查文件
     * @param file
     * @throws IOException
     */
    public static void checkFile(MultipartFile file) throws IOException {
        //判断文件是否存在
        if (null == file) {
            logger.error("文件不存在！");
            throw new FileNotFoundException("IMPORT_FILE_NOT_NULL");
        }
        if (file.getSize() > FILE_MAX_SIZE) {
            logger.error("文件不能大于100MB！");
            throw new FileNotFoundException("FILE_SIZE_MORE_THAN_100_MB");
        }
        //获得文件名
        String fileName = file.getOriginalFilename();
        //判断文件是否是excel文件
        if (!fileName.endsWith(XLS) && !fileName.endsWith(XLSX)) {
            logger.error(fileName + "不是excel文件");
            throw new IOException("FILE_NOT_EXCEL");
        }
    }

    /**
     * 获取值
     *
     * @param cell
     * @return
     */
    public static String getCellValue(Cell cell) throws IOException {
        String cellValue = "";
        if (cell == null) {
            return cellValue;
        }
        // 把数字当成String来读，避免出现1读成1.0的情况
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            boolean flag = false;
            short format = cell.getCellStyle().getDataFormat();
            SimpleDateFormat sdf = null;
            // 日期
            if (format == 14 || format == 31 || format == 57 || format == 58 || (176 <= format && format <= 178)
                    || (182 <= format && format <= 196) || (210 <= format && format <= 213) || (208 == format)) {
                sdf = new SimpleDateFormat("yyyy-MM-dd");
                flag = true;
            } else if (format == 20 || format == 32 || format == 183 || (200 <= format && format <= 209)) {
                // 时间
                flag = true;
                sdf = new SimpleDateFormat("HH:mm");
            } else {
                // 不是日期格式
                cell.setCellType(Cell.CELL_TYPE_STRING);
            }
            if (flag) {
                double value = cell.getNumericCellValue();
                Date date = DateUtil.getJavaDate(value);
                return sdf.format(date);
            }

        }
        // 判断数据的类型
        switch (cell.getCellType()) {
            // 数字
            case Cell.CELL_TYPE_NUMERIC:
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            // 字符串
            case Cell.CELL_TYPE_STRING:
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            // Boolean
            case Cell.CELL_TYPE_BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            // 公式
            case Cell.CELL_TYPE_FORMULA:
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            // 空值
            case Cell.CELL_TYPE_BLANK:
                cellValue = "";
                break;
            // 故障
            case Cell.CELL_TYPE_ERROR:
                throw new IOException("EXCEL_WRONG_FORMAT");
            default:
                throw new IOException("EXCEL_WRONG_FORMAT");
        }
        return cellValue;
    }

    /**
     * 解决文件名在不同的浏览器下乱码问题
     *
     * @param request
     * @param fileName 文件名名称
     * @return
     * @throws Exception
     */
    public static String fileName(HttpServletRequest request, String fileName) throws Exception {
        String userAgent = request.getHeader("User-Agent").toLowerCase();
        //解决文件名在不同的浏览器下乱码问题
      /*  if (userAgent.contains("chrome") || userAgent.contains("firefox")) {
            fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
        } else {*/
            fileName = URLEncoder.encode(fileName, "UTF-8");
     //   }
        return fileName;
    }

    /**
	 * 验证文件是否为空
	 * @author renfei
	 * @date 2017年12月25日 上午9:42:05
	 * @param file
	 * @return
	 */
	public static boolean fileIsEmpty(MultipartFile file) {
		if (null == file || StringUtils.isBlank(file.getOriginalFilename()) || file.isEmpty()) {
			return true;
		}
		// 文件大小
		int fileSize = (int) file.getSize();
		if (fileSize == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 验证文件是否为excel文件
	 * @author renfei
	 * @date 2017年12月21日 下午4:06:56
	 * @param fileName 文件名
	 * @return
	 */
	public static boolean isExcel(String fileName) {
		if (StringUtils.isBlank(fileName)) {
			return false;
		}
		// 获取文件后缀名
		String fileSuffix = getFileSuffix(fileName);
		if (StringUtils.isNotBlank(fileSuffix)
				&& (XLS.equals(fileSuffix) || XLSX.equals(fileSuffix))) {
			return true;
		}
		return false;
	}
	
	/**
	 * 获取文件后缀名
	 * @author renfei
	 * @date 2017年12月21日 下午4:11:39
	 * @param fileName 文件名
	 * @return
	 */
	private static String getFileSuffix(String fileName) {
		if (StringUtils.isBlank(fileName)) {
			return null;
		}
		if (fileName.contains(POINT)) {
			return fileName.substring(fileName.lastIndexOf(POINT) + 1, fileName.length());
		}
		return null;
	}
}
