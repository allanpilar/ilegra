package br.com.test.ilegra.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import br.com.test.ilegra.constants.Constants;
import br.com.test.ilegra.constants.PositionEnum;

public class Sales {
	
	private Long id;
	
	private Salesman salesman;
	
	private List<ItemSale> itens;
	
	@SuppressWarnings("unchecked")
	public Sales(String data, Salesman salesman) {
		super();
		@SuppressWarnings("rawtypes")
		List alist = new ArrayList();
		StringTokenizer st = new StringTokenizer(data, Constants.CHARACTER_DELIMITER);
		while (st.hasMoreTokens()) {
			alist.add(st.nextToken());
		}
		setId(new Long((String)alist.get(PositionEnum.ONE.getPosition())));
		setSalesman(salesman);
		setItens((String)alist.get(PositionEnum.TWO.getPosition()));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Salesman getSalesman() {
		return salesman;
	}

	public void setSalesman(Salesman salesman) {
		this.salesman = salesman;
	}

	public List<ItemSale> getItens() {
		return itens;
	}

	public void setItens(List<ItemSale> itens) {
		this.itens = itens;
	}
	
	public void setItens(String itens) {
		if(this.itens == null) {
			this.itens = new ArrayList<ItemSale>();
		}
		String temp = itens.substring(PositionEnum.ONE.getPosition(), itens.length() - PositionEnum.ONE.getPosition());
		List<String> listItens = Arrays.asList(temp.split(Constants.CHARACTER_DELIMITER_ITENS));
		listItens.stream().forEach(i -> {
			this.itens.add(new ItemSale(i));
		});
	}
	
	public BigDecimal getTotalSale() {
		if(itens != null && itens.size() > 0) {
			BigDecimal totalAmount = BigDecimal.ZERO;
			itens.stream().forEach(i ->{
				totalAmount.add(i.getPrice().multiply(new BigDecimal(i.getQuantity())));
			});
			return totalAmount;
		}else {
			return BigDecimal.ZERO;
		}
	}
	
}
