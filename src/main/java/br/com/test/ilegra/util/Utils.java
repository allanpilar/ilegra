package br.com.test.ilegra.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import br.com.test.ilegra.constants.Constants;
import br.com.test.ilegra.constants.PositionEnum;
import br.com.test.ilegra.model.Sales;
import br.com.test.ilegra.schedule.service.FilesProcessService;

public class Utils {

	private static BigDecimal mostExpensiveSale = null;
	private static BigDecimal worstSalesman = null;

	/**
	 * get customer list from .dat file
	 * 
	 * @param lines
	 * @return
	 */
	public static List<String> getCustomerList(List<String> lines) {
		return lines.stream()
				.filter(item -> item.substring(PositionEnum.ZERO.getPosition(), PositionEnum.TREE.getPosition())
						.equals(Constants.CUSTOMER_FORMAT_ID))
				.collect(Collectors.toList());
	}

	/**
	 * get salesman list from .dat file
	 * 
	 * @param lines
	 * @return
	 */
	public static List<String> getSalesman(List<String> lines) {
		return lines.stream()
				.filter(item -> item.substring(PositionEnum.ZERO.getPosition(), PositionEnum.TREE.getPosition())
						.equals(Constants.SALESMAN_FORMAT_ID))
				.collect(Collectors.toList());
	}

	/**
	 * get sales list from .dat file
	 * 
	 * @param lines
	 * @return
	 */
	public static List<String> getSales(List<String> lines, String salesmanName) {
		return lines.stream()
				.filter(item -> item.substring(PositionEnum.ZERO.getPosition(), PositionEnum.TREE.getPosition())
						.equals(Constants.SALES_FORMAT_ID) && item.contains(salesmanName))
				.collect(Collectors.toList());
	}

	/**
	 * get the worst salesman ever and the most expensive sale to put in a resume
	 * file
	 * 
	 * @param lines
	 * @return
	 */
	public static void calculateResultsOfFiles() {
		FilesProcessService.salesman.stream().forEach(saleman -> {
			if (worstSalesman == null) {
				worstSalesman = saleman.getTotalAmountSales();
				FilesProcessService.worstSalesman = saleman;
			} else {
				if (saleman.getTotalAmountSales().compareTo(worstSalesman) < 0) {
					worstSalesman = saleman.getTotalAmountSales();
					FilesProcessService.worstSalesman = saleman;
				}
			}
			saleman.getSales().stream().forEach(s -> {
				validateMostExpensiveSale(s);
			});
		});
		mostExpensiveSale = null;
		worstSalesman = null;
	}
	
	/**
	 * Verify if the sales of a saleman are in more expensives
	 * @param s
	 */
	private static void validateMostExpensiveSale(Sales s) {
		if (mostExpensiveSale == null) {
			mostExpensiveSale = s.getTotalSale();
			FilesProcessService.mostExpensiveSale = s;
		} else {
			if (s.getTotalSale().compareTo(mostExpensiveSale) > 0) {
				mostExpensiveSale = s.getTotalSale();
				FilesProcessService.mostExpensiveSale = s;
			}
		}
	}
	
	/**
	 * convert the current date in a string formated
	 * @param format
	 * @return
	 */
	public static String getStringDate(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return "/" + sdf.format(date);
	}

}
