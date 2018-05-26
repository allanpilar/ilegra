package br.com.test.ilegra.main;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import br.com.test.ilegra.constants.Constants;
import br.com.test.ilegra.schedule.service.FilesProcessService;

public class Main {
	
	/**
	 * Main method to start listening for directory events
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	public static void main(String[] args) throws IOException, InterruptedException {

		Path folder = Paths.get(Constants.MAIN_PATH + Constants.PATH_IN);
		WatchService watchService = FileSystems.getDefault().newWatchService();
		folder.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

		boolean valid = true;
		do {
			WatchKey watchKey = watchService.take();

			for (WatchEvent event : watchKey.pollEvents()) {
				WatchEvent.Kind kind = event.kind();
				if (StandardWatchEventKinds.ENTRY_CREATE.equals(event.kind())) {
					String fileName = event.context().toString();
					System.out.println("File Created:" + fileName);
				}
			}

			System.out.println("Task started");
			FilesProcessService.doProcess();
			System.out.println("Task executed successfully.");

			valid = watchKey.reset();

		} while (valid);

	}

}
