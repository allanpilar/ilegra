package br.com.test.ilegra.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import br.com.test.ilegra.constants.Constants;
import br.com.test.ilegra.constants.PositionEnum;

public class Salesman {

	private Long cpf;

	private String name;

	private BigDecimal salary;

	private List<Sales> sales;

	private BigDecimal totalAmount;
	
	/**
	 * Constructor to decompose DAT file attributes
	 * @param data
	 */
	@SuppressWarnings("unchecked")
	public Salesman(String data) {
		super();
		@SuppressWarnings("rawtypes")
		List alist = new ArrayList();
		StringTokenizer st = new StringTokenizer(data, Constants.CHARACTER_DELIMITER);
		while (st.hasMoreTokens()) {
			alist.add(st.nextToken());
		}
		setCpf(new Long((String) alist.get(PositionEnum.ONE.getPosition())));
		setName((String) alist.get(PositionEnum.TWO.getPosition()));
		setSalary(new BigDecimal((String) alist.get(PositionEnum.TREE.getPosition())));
	}

	public List<Sales> getSales() {
		return sales;
	}

	public void setSales(List<Sales> sales) {
		this.sales = sales;
	}

	public Long getCpf() {
		return cpf;
	}

	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}
	
	/**
	 * method responsible for return the total amount of sales per salesman
	 * @return
	 */
	public BigDecimal getTotalAmountSales() {
		if (totalAmount == null) {
			totalAmount = BigDecimal.ZERO;
		}
		if (sales != null && sales.size() > 0) {
			sales.stream().forEach(i -> {
				totalAmount = totalAmount.add(i.getTotalSale());
			});
			return totalAmount;
		} else {
			return BigDecimal.ZERO;
		}
	}

}
