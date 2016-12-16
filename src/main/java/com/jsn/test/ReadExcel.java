package com.jsn.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	protected static Logger logger;

	protected static String fileName = "/home/abzooba/work/workspace/test/siList.xls";

	private static void readWriteExcel(String inputFileName/*, String outputFileName*/) {
		FileInputStream inputStream;
		try {
			inputStream = new FileInputStream(new File(inputFileName));

			//			FileOutputStream outputStream = new FileOutputStream(new File(outputFileName));

			XSSFWorkbook readWorkbook = new XSSFWorkbook(inputStream);
			//			XSSFWorkbook writeWorkbook = new XSSFWorkbook();
			XSSFSheet readSheet = readWorkbook.getSheetAt(0);
			//			XSSFSheet writeSheet = writeWorkbook.createSheet();
			/* Get first/desired sheet from the workbook */
			/* Iterate through each rows one by one */
			Iterator<Row> rowReadIterator = readSheet.iterator();
			int readRowCount = 0;
			int columnCount = 0;
			//			int writeRowCount = 0;
			while (rowReadIterator.hasNext()) {
				Row readRow = rowReadIterator.next();
				if (readRowCount++ == 0) {
					columnCount = readRow.getLastCellNum();
					//					continue;
				}
				System.out.println("columnCount : " + columnCount);

				for (int i = 0; i < columnCount; ++i) {
					String email = readRow.getCell(i).getStringCellValue();
					System.out.println("column : " + i + "value : " + email);
				}
				/* For each row, iterate through all the columns */
				//				Iterator<Cell> cellIterator = readRow.cellIterator();

				//ID	Importance	Icon	Priority	Subject	From	Message To Me	Message CC to Me	Sender Name	CC	To	Received	Message Size	Contents	Created	Modified	Subject Prefix	Has Attachments	Normalized Subject	Object Type	Content Unread

				//				String srcText = null;
				//				String topic = null;
				//				String id = readRow.getCell(0).getStringCellValue();
				//				String subject = readRow.getCell(4).getStringCellValue();
				//				String from = readRow.getCell(5).getStringCellValue();
				//				String senderName = readRow.getCell(8).getStringCellValue();
				//				String cc = readRow.getCell(9).getStringCellValue();
				//				String to = readRow.getCell(10).getStringCellValue();
				//				String receivedDate = readRow.getCell(11).getStringCellValue();
				//				System.out.println("Received Date : " + receivedDate);
				//				if (HSSFDateUtil.isCellDateFormatted(readRow.getCell(11))) {
				//					receivedDate = readRow.getCell(11).getDateCellValue();
				//				}

				//				String contents = readRow.getCell(13).getStringCellValue();
				//				System.out.println("Subject: " + subject);
				//				System.out.println("Contents: " + contents);

				//				WalmartEmail[] emailsArr = extractEmails(contents);

				//				Row writeRow = writeSheet.createRow(++rowCount);
				//				for (int j = 0; j < emailsArr.length; j++) {
				//					Row writeRow = writeSheet.createRow(writeRowCount++);
				//					for (int i = 0; i <= columnCount - 1; i++) {
				//						Cell writeCell = writeRow.createCell(i);
				//						Cell readCell = readRow.getCell(i);
				//						if (readCell != null) {
				//							if ((i == 11 || i == 14 || i == 16) && readRowCount != 1) {
				//								readCell.setCellType(Cell.CELL_TYPE_NUMERIC);
				//								if (readCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				//									if (HSSFDateUtil.isCellDateFormatted(readCell)) {
				//										//										System.out.println("column : " + i + " SentDate : " + readCell.getDateCellValue());
				//										writeCell.setCellValue(readCell.getDateCellValue());
				//									}
				//								}
				//							} else if ((i == 0 || i == 1 || i == 4 || i == 12 || i == 18 || i == 20) && readRowCount != 1) {
				//								readCell.setCellType(Cell.CELL_TYPE_NUMERIC);
				//								writeCell.setCellValue(readCell.getNumericCellValue());
				//							} else if ((i == 3 || i == 8 || i == 9 || i == 10) && readRowCount != 1) {
				//								readCell.setCellType(Cell.CELL_TYPE_STRING);
				//								writeCell.setCellValue(maskSupplier(maskMDL(readCell.getStringCellValue())));
				//							} else if ((i == 13) && readRowCount != 1) {
				//								readCell.setCellType(Cell.CELL_TYPE_STRING);
				//								writeCell.setCellValue(maskVendorId(readCell.getStringCellValue()));
				//							} else {
				//								readCell.setCellType(Cell.CELL_TYPE_STRING);
				//								writeCell.setCellValue(readCell.getStringCellValue());
				//							}
				//						}
				//					}
				//					if (readRowCount != 1) {
				//						/*This condition is valid for only the emails in body. This will override existing values*/
				//						if (emailsArr[j].getSender() != null) {
				//							writeRow.getCell(5).setCellValue(emailsArr[j].getSubject());
				//							writeRow.getCell(3).setCellValue(maskSupplier(maskMDL(emailsArr[j].getSender())));
				//							writeRow.getCell(9).setCellValue(maskSupplier(maskMDL(emailsArr[j].getCc())));
				//							writeRow.getCell(10).setCellValue(maskSupplier(maskMDL(emailsArr[j].getTo())));
				//							//							System.out.println("sendate : " + emailsArr[j].getSentDate());
				//							writeRow.getCell(11).setCellValue(emailsArr[j].getSentDate());
				//							writeRow.getCell(13).setCellValue(maskVendorId(emailsArr[j].getMailBody()));
				//						} else {
				//							/*For the first row, let it copy the heading*/
				//							writeRow.getCell(13).setCellValue(maskVendorId(emailsArr[j].getMailBody()));
				//							//							System.out.println("First Email Body: " + emailsArr[j].getMailBody());
				//						}
				//					}
				//				}
			}
			//			writeWorkbook.write(outputStream);
			//			outputStream.close();

			//			writeWorkbook.close();
			inputStream.close();
			readWorkbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Map<String, String> getEmailKeyMerchantRoleFromXls(File file) throws Exception {
		System.out.println("step2");
		Map<String, String> dataMap = new HashMap<String, String>();
		try {
			System.out.println("XlsOrXlsxReader.getEmailKeyFromXls() starts");
			FileInputStream fis = new FileInputStream(file);

			//Get the workbook instance for XLS file 
			XSSFWorkbook workbook = new XSSFWorkbook(fis);

			//Get first sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			//Iterate through each rows from first sheet - get common data
			Iterator<Row> rowIterator = sheet.iterator();
			// skip first row since its having column names
			if (rowIterator.hasNext()) {
				System.out.println("step3");
				rowIterator.next();
			}

			while (rowIterator.hasNext()) {
				System.out.println("step4");
				Row row = rowIterator.next();
				String key = row.getCell(0).getStringCellValue();
				String value = row.getCell(1).getStringCellValue().trim();
				// replace space in role with '-' and trim space in email id
				if (key != null && key.length() > 0) {
					value = value.replaceAll(" ", "-");
					key = key.replaceAll(" ", "");
					dataMap.put(key, value);
				}
			}
		} catch (Exception e) {
			System.out.println("-->>Error in getting Merchant roles from XLS file. Error message: " + e.getMessage());
			String message = "XlsOrXlsxReader.getEmailKeyMerchantRoleFromXls()";
			message = message + "  " + e.getStackTrace();
			//			EmailSender.sendEmailWithErrorInfoToAbzDevTeam(message);
			throw new Exception(e);
		}

		System.out.println("XlsOrXlsxReader.getEmailKeyFromXls() ends");
		return dataMap;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//		int[] indexArr = { 0, 1 };
		Map<String, String> siList = new HashMap<String, String>();
		System.out.println("step0");
		File file = new File(fileName);
		System.out.println("stepX");

		try {
			System.out.println("step1");
			//			siList = getEmailKeyMerchantRoleFromXls(file);
			readWriteExcel(fileName);
			System.out.println("SI List : " + siList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
