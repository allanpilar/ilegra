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

	public static List<String> getCustomerList(List<String> lines) {
		return lines.stream()
				.filter(item -> item.substring(PositionEnum.ZERO.getPosition(), PositionEnum.TREE.getPosition())
						.equals(Constants.CUSTOMER_FORMAT_ID))
				.collect(Collectors.toList());
	}

	public static List<String> getSalesman(List<String> lines) {
		return lines.stream()
				.filter(item -> item.substring(PositionEnum.ZERO.getPosition(), PositionEnum.TREE.getPosition())
						.equals(Constants.SALESMAN_FORMAT_ID))
				.collect(Collectors.toList());
	}

	public static List<String> getSales(List<String> lines, String salesmanName) {
		return lines.stream()
				.filter(item -> item.substring(PositionEnum.ZERO.getPosition(), PositionEnum.TREE.getPosition())
						.equals(Constants.SALES_FORMAT_ID) && item.contains(salesmanName))
				.collect(Collectors.toList());
	}

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

	public static String getStringDate(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return "/" + sdf.format(date);
	}

}
