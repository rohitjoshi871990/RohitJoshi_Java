package com.src.positionCalculation;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.src.PositionFiles.EndOfDay_PositionFile;
import com.src.PositionFiles.ReadInputPositionFile;
import com.src.PositionFiles.ReadTransactionFile;
import com.src.common.utils.CommonUtils;

public class PositionCalculator {

	CommonUtils utils = new CommonUtils();

	/*
	 * Method : computeEndOfDayPosition
	 * Description : This method used to compute the final end of day position along with delta calculation
	 */
	private HashMap<String, ArrayList<EndOfDay_PositionFile>> computeEndOfTheDayPosition(
			String inputPositionFileLocation, String transactionFileLocation)
			throws JsonParseException, JsonMappingException, IOException {

		HashMap<String, ArrayList<ReadInputPositionFile>> inputPositionMap = utils
				.readInputPositionFile(inputPositionFileLocation);
		ArrayList<ReadTransactionFile> inputTransactionList = utils.readTransactionFile(transactionFileLocation);

		HashMap<String, ArrayList<EndOfDay_PositionFile>> endOfDayPositionMap = new HashMap<String, ArrayList<EndOfDay_PositionFile>>();

		HashSet<String> processedInstruments = new HashSet<String>();

		Iterator<ReadTransactionFile> transactionItr = inputTransactionList.iterator();

		while (transactionItr.hasNext()) {
			ReadTransactionFile readTRFile = transactionItr.next();
			String instrument = readTRFile.getInstrument();

			if (processedInstruments.contains(instrument)) {
				ArrayList<EndOfDay_PositionFile> readEODPosFile = endOfDayPositionMap.get(instrument);
				Iterator<EndOfDay_PositionFile> itrEODPos = readEODPosFile.iterator();
				ArrayList<EndOfDay_PositionFile> endOfDayPositionList = new ArrayList<EndOfDay_PositionFile>();
				while (itrEODPos.hasNext()) {
					EndOfDay_PositionFile ipf = itrEODPos.next();
					if (readTRFile.getTransactionType().equalsIgnoreCase("B")) {

						EndOfDay_PositionFile eodFile = new EndOfDay_PositionFile();

						eodFile.setInstrument(instrument);
						eodFile.setAccount(ipf.getAccount());
						eodFile.setAccountType(ipf.getAccountType());
						if (ipf.getAccountType().equalsIgnoreCase("E")) {
							eodFile.setQuantity(ipf.getQuantity() + readTRFile.getTransactionQuantity());
							eodFile.setDelta(ipf.getDelta() + (readTRFile.getTransactionQuantity()));
						} else if (ipf.getAccountType().equalsIgnoreCase("I")) {
							eodFile.setQuantity(ipf.getQuantity() - readTRFile.getTransactionQuantity());
							eodFile.setDelta(ipf.getDelta() + (-readTRFile.getTransactionQuantity()));
						}
						endOfDayPositionList.add(eodFile);
					} else if (readTRFile.getTransactionType().equalsIgnoreCase("S")) {
						EndOfDay_PositionFile eodFile = new EndOfDay_PositionFile();

						eodFile.setInstrument(instrument);
						eodFile.setAccount(ipf.getAccount());
						eodFile.setAccountType(ipf.getAccountType());

						if (ipf.getAccountType().equalsIgnoreCase("E")) {
							eodFile.setQuantity(ipf.getQuantity() - readTRFile.getTransactionQuantity());
							eodFile.setDelta(ipf.getDelta() + (-readTRFile.getTransactionQuantity()));
						} else if (ipf.getAccountType().equalsIgnoreCase("I")) {
							eodFile.setQuantity(ipf.getQuantity() + readTRFile.getTransactionQuantity());
							eodFile.setDelta(ipf.getDelta() + (+readTRFile.getTransactionQuantity()));
						}
						endOfDayPositionList.add(eodFile);
					}
				}
				processedInstruments.add(instrument);
				endOfDayPositionMap.replace(instrument, readEODPosFile, endOfDayPositionList);
			} else {
				ArrayList<ReadInputPositionFile> readIPPosFile = inputPositionMap.get(instrument);
				Iterator<ReadInputPositionFile> itrIPPos = readIPPosFile.iterator();
				ArrayList<EndOfDay_PositionFile> endOfDayPositionList = new ArrayList<EndOfDay_PositionFile>();
				while (itrIPPos.hasNext()) {
					ReadInputPositionFile ipf = itrIPPos.next();
					if (readTRFile.getTransactionType().equalsIgnoreCase("B")) {

						EndOfDay_PositionFile eodFile = new EndOfDay_PositionFile();

						eodFile.setInstrument(instrument);
						eodFile.setAccount(ipf.getAccount());
						eodFile.setAccountType(ipf.getAccountType());
						if (ipf.getAccountType().equalsIgnoreCase("E")) {
							eodFile.setQuantity(ipf.getQuantity() + readTRFile.getTransactionQuantity());
							eodFile.setDelta(readTRFile.getTransactionQuantity());
						} else if (ipf.getAccountType().equalsIgnoreCase("I")) {
							eodFile.setQuantity(ipf.getQuantity() - readTRFile.getTransactionQuantity());
							eodFile.setDelta(-readTRFile.getTransactionQuantity());
						}
						endOfDayPositionList.add(eodFile);
					} else if (readTRFile.getTransactionType().equalsIgnoreCase("S")) {
						EndOfDay_PositionFile eodFile = new EndOfDay_PositionFile();

						eodFile.setInstrument(instrument);
						eodFile.setAccount(ipf.getAccount());
						eodFile.setAccountType(ipf.getAccountType());

						if (ipf.getAccountType().equalsIgnoreCase("E")) {
							eodFile.setQuantity(ipf.getQuantity() - readTRFile.getTransactionQuantity());
							eodFile.setDelta(-readTRFile.getTransactionQuantity());
						} else if (ipf.getAccountType().equalsIgnoreCase("I")) {
							eodFile.setQuantity(ipf.getQuantity() + readTRFile.getTransactionQuantity());
							eodFile.setDelta(+readTRFile.getTransactionQuantity());
						}
						endOfDayPositionList.add(eodFile);
					}
				}
				processedInstruments.add(instrument);
				endOfDayPositionMap.put(instrument, endOfDayPositionList);
			}
		}
		return endOfDayPositionMap;
	}

	/*
	 * Method : writeFinalResult
	 * Description: This method is used to write the final output to End of day position file.
	 */
	public void writeFinalResult() throws JsonParseException, JsonMappingException, IOException {
		Properties prop = utils.load_Properties();
		System.out.println(prop.getProperty("inputPositionFileLocation"));
		String inputPositionFileLocation = prop.getProperty("inputPositionFileLocation");
		String transactionFileLocation = prop.getProperty("transactionFileLocation");
		HashMap<String, ArrayList<EndOfDay_PositionFile>> endOfDayPositionFile = new HashMap<String, ArrayList<EndOfDay_PositionFile>>();
		endOfDayPositionFile = computeEndOfTheDayPosition(inputPositionFileLocation, transactionFileLocation);
		FileWriter writer = new FileWriter(prop.getProperty("expectedEndOfDayFileLocation"));
		for (Map.Entry<String, ArrayList<EndOfDay_PositionFile>> entry : endOfDayPositionFile.entrySet()) {
			String instrument = entry.getKey();
			ArrayList<EndOfDay_PositionFile> endOfDayList = entry.getValue();
			for (EndOfDay_PositionFile eodFile : endOfDayList) {
				String str = instrument + "," + eodFile.getAccount() + "," + eodFile.getAccountType() + ","
						+ eodFile.getQuantity() + "," + eodFile.getDelta() + "\n";
				writer.write(str);
			}
		}
		writer.close();
	}
	public static void main(String args[]) throws JsonParseException, JsonMappingException, IOException {
		PositionCalculator posCalc = new PositionCalculator();
		posCalc.writeFinalResult();
	}
}
