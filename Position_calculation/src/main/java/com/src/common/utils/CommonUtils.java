package com.src.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.src.PositionFiles.ReadInputPositionFile;
import com.src.PositionFiles.ReadTransactionFile;

public class CommonUtils {

	// Read Transaction File
	public ArrayList<ReadTransactionFile> readTransactionFile(String transactionFileLocation)
			throws JsonParseException, JsonMappingException, FileNotFoundException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		List<ReadTransactionFile> readTRFileList = mapper.readValue(new FileInputStream(transactionFileLocation),
				new TypeReference<List<ReadTransactionFile>>() {
				});
		return (ArrayList<ReadTransactionFile>) readTRFileList;
	}

	// Read Input Position File
	public HashMap<String, ArrayList<ReadInputPositionFile>> readInputPositionFile(String inputPositionFileLocation)
			throws FileNotFoundException {
		HashMap<String, ArrayList<ReadInputPositionFile>> inputPositionFileMap = new HashMap<String, ArrayList<ReadInputPositionFile>>();
		ArrayList<ReadInputPositionFile> inputPositionFileList = new ArrayList<ReadInputPositionFile>();
		try {
			File inputFile = new File(inputPositionFileLocation);
			InputStream inputFS = new FileInputStream(inputFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));

			inputPositionFileList = (ArrayList<ReadInputPositionFile>) br.lines().skip(1).map(mapToList)
					.collect(Collectors.toList());
			Set<String> instrument = new HashSet<String>();
			for(ReadInputPositionFile posFile : inputPositionFileList) {
				instrument.add(posFile.getInstrument());
			}
			
			for(String str : instrument) {
				Iterator<ReadInputPositionFile> itr = inputPositionFileList.iterator();
				ArrayList<ReadInputPositionFile> newArrayIPPos = new ArrayList<ReadInputPositionFile>();
				while(itr.hasNext()) {
					ReadInputPositionFile file = itr.next();
					if(str.equalsIgnoreCase(file.getInstrument())) {
						newArrayIPPos.add(file);
					}
				}
				inputPositionFileMap.put(str,newArrayIPPos);
			}
			br.close();
		} catch (IOException e) {

		}
		return inputPositionFileMap;
	}
	
	// Lambda Function implementation for reading file in list.
	private Function<String, ReadInputPositionFile> mapToList = (line) -> {
		String[] p = line.split(",");// a CSV has comma separated lines
		ReadInputPositionFile readPositionFile = new ReadInputPositionFile();
		readPositionFile.setInstrument(p[0]);
		readPositionFile.setAccount(Integer.parseInt(p[1]));
		readPositionFile.setAccountType(p[2]);
		readPositionFile.setQuantity(Integer.parseInt(p[3]));
		return readPositionFile;
	};

	// Read Property file config.property using below method
	public Properties load_Properties() throws IOException {
		Properties prop = new Properties();
		String propFileName = "config.properties";
		File configFile = new File(propFileName);
		//InputStream ipStream = new FileInputStream(configFile);
		InputStream ipStream = CommonUtils.class.getClassLoader().getResourceAsStream(propFileName);
		if (ipStream != null) {
			prop.load(ipStream);
		}
		return prop;
	}
}
