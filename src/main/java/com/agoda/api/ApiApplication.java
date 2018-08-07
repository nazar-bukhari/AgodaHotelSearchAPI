package com.agoda.api;

import com.agoda.api.dao.HotelRepository;
import com.agoda.api.helper.CommonHelper;
import com.agoda.api.model.Hotel;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;
import java.util.Properties;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {

		loadDataFile();
		CommonHelper.loadPropertyFile();
		SpringApplication.run(ApiApplication.class, args);

		new ApiApplication().fileWatcher();
	}

	private static void loadDataFile(){

		try {
			List<Hotel> hotelList = new CsvToBeanBuilder(new FileReader("hoteldb.csv")).withType(Hotel.class).build().parse();
			HotelRepository.getInstance().process(hotelList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void fileWatcher(){

		boolean isFileWatcherActive = true;
		String dataFileName = "hoteldb.csv";
		int startupMinutes = LocalTime.now().getMinute();
		Properties properties = CommonHelper.getProperties();
		String datafileCheckSumAtStartup = CommonHelper.generateFileCheckSum(dataFileName);

		while(isFileWatcherActive){

			int currentTimeMinutes = LocalTime.now().getMinute();
			if(currentTimeMinutes - startupMinutes > Integer.parseInt(properties.getProperty("checksumInterval"))){

				try {
					String dataFileCheckSumAfterTimeInterval = CommonHelper.generateFileCheckSum(dataFileName);
					if (!datafileCheckSumAtStartup.equals(dataFileCheckSumAfterTimeInterval)) {
						datafileCheckSumAtStartup = dataFileCheckSumAfterTimeInterval;
						loadDataFile();
					}
				}
				catch (Exception ex){
					isFileWatcherActive = false;
					ex.printStackTrace();
				}
				startupMinutes = LocalTime.now().getMinute();
			}
		}
	}
}
