package com.agoda.api;

import com.agoda.api.dao.HotelRepository;
import com.agoda.api.helper.CommonHelper;
import com.agoda.api.model.Hotel;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Properties;

@SpringBootApplication
public class ApiApplication {

	private static Logger logger = LogManager.getLogger("ApiApplication");

	public static void main(String[] args) {

		loadDataFile();
		CommonHelper.loadPropertyFile();
		SpringApplication.run(ApiApplication.class, args);

		fileWatcher();
	}

	private static void loadDataFile(){

		try {
			List<Hotel> hotelList = new CsvToBeanBuilder(new FileReader("hoteldb.csv")).withType(Hotel.class).build().parse();
			HotelRepository.getInstance().process(hotelList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void fileWatcher(){

		boolean isFileWatcherActive = true;
		String dataFileName = "hoteldb.csv";
		LocalTime startupTime = LocalTime.now();
		Properties properties = CommonHelper.getProperties();
		String datafileCheckSumAtStartup = CommonHelper.generateFileCheckSum(dataFileName);

		while(isFileWatcherActive){

			LocalTime currentTime = LocalTime.now();
			long elapsedTimeInMinutes = Duration.between(startupTime,currentTime).toMinutes();

			if(elapsedTimeInMinutes > Integer.parseInt(properties.getProperty("checksumInterval"))){

				try {
					String dataFileCheckSumAfterTimeInterval = CommonHelper.generateFileCheckSum(dataFileName);
					if (!datafileCheckSumAtStartup.equals(dataFileCheckSumAfterTimeInterval)) {
						datafileCheckSumAtStartup = dataFileCheckSumAfterTimeInterval;
						loadDataFile();
						logger.info("Re loading data file at: "+LocalTime.now());
					}
				}
				catch (Exception ex){
					isFileWatcherActive = false;
					ex.printStackTrace();
				}
				startupTime = LocalTime.now();
			}
		}
	}
}
