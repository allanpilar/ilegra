package br.com.test.ilegra.schedule.service;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import br.com.test.ilegra.constants.Constants;
import br.com.test.ilegra.model.Customer;
import br.com.test.ilegra.model.Sales;
import br.com.test.ilegra.model.Salesman;
import br.com.test.ilegra.util.Utils;

public class FilesProcessService {

	private static File files[];
	private static List<File> errorFiles = new ArrayList<File>();
	private static List<File> successFiles = new ArrayList<File>();
	private static List<Customer> customers = new ArrayList<Customer>();
	public static List<Salesman> salesman = new ArrayList<Salesman>();
	private static Charset iso_8859_1 = StandardCharsets.ISO_8859_1;
	public static Sales mostExpensiveSale;
	public static Salesman worstSalesman;

	/**
	 * Start the process of .dat files
	 */
	public static void doProcess() {
		readFiles();

		if (files != null && files.length > 0) {
			processFiles();
			Utils.calculateResultsOfFiles();
			writeFinalFile();
		}

		reset();
	}

	/**
	 * read available files in the directory
	 */
	private static void readFiles() {
		FileFilter filter = new FileFilter() {
			public boolean accept(File file) {
				return file.getName().endsWith(Constants.FILE_EXTENSION);
			}
		};

		File dir = new File(Constants.MAIN_PATH + Constants.PATH_IN);
		files = dir.listFiles(filter);
	}

	/**
	 * process content of files
	 */
	private static void processFiles() {
		for (File file : files) {
			try {
				List<String> lines = Files.readAllLines(new File(file.getAbsolutePath()).toPath(), iso_8859_1);
				processCustomers(Utils.getCustomerList(lines));
				processSalesman(Utils.getSalesman(lines));
				processSales(lines);
				successFiles.add(file);
				file.delete();
			} catch (IOException e) {
				errorFiles.add(file);
				file.renameTo(new File(file.getAbsolutePath() + ".error"));
				System.out.println(e.getMessage() + "Error reading file " + file.getName());
				continue;
			}
		}
	}

	/**
	 * process customer lines
	 */
	private static void processCustomers(List<String> lines) {
		lines.stream().forEach(item -> {

			Customer obj = new Customer(item);
			if (!(customers.stream().filter(s -> s.getCnpj().equals(obj.getCnpj())).count() > 0)) {
				customers.add(obj);
			}
		});
	}

	/**
	 * process salesman lines
	 */
	private static void processSalesman(List<String> lines) {
		lines.stream().forEach(item -> {
			Salesman obj = new Salesman(item);
			if (!(salesman.stream().filter(s -> s.getCpf().equals(obj.getCpf())).count() > 0)) {
				salesman.add(obj);
			}
		});
	}

	/**
	 * process sales lines
	 */
	private static void processSales(List<String> lines) {
		salesman.stream().forEach(item -> {
			List<String> listSales = Utils.getSales(lines, item.getName());
			if (item.getSales() == null) {
				item.setSales(new ArrayList<Sales>());
			}
			listSales.stream().forEach(i -> {
				item.getSales().add(new Sales(i, item));
			});
		});
	}
	
	/**
	 * Write resume file
	 */
	private static void writeFinalFile() {
		List<String> lines = new ArrayList<String>();

		lines.add("Amount of clients: " + customers.size());
		lines.add("Amount of salesman: " + salesman.size());
		lines.add("Most expensive sale: ID " + mostExpensiveSale.getId());
		lines.add("Worst salesman: " + worstSalesman.getName());

		Path file = Paths
				.get(Constants.MAIN_PATH + Constants.PATH_OUT + Utils.getStringDate(Constants.FILE_NAME_DATE_PATTERN)
						+ Constants.FILE_DONE + Constants.FILE_EXTENSION);
		try {
			Files.write(file, lines, Charset.forName(iso_8859_1.name()));
		} catch (IOException e) {
			System.out.println("Error: couldn't generate final file.");
		}
	}
	
	/**
	 * Reset local variables
	 */
	@SuppressWarnings("unused")
	private static void reset() {
		errorFiles = new ArrayList<File>();
		successFiles = new ArrayList<File>();
		customers = new ArrayList<Customer>();
		salesman = new ArrayList<Salesman>();
		Sales mostExpensiveSale = null;
		Salesman worstSalesman = null;
	}

}
