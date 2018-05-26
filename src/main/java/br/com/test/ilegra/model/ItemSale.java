package br.com.test.ilegra.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import br.com.test.ilegra.constants.Constants;
import br.com.test.ilegra.constants.PositionEnum;

public class ItemSale {

	private Long id;

	private Integer quantity;

	private BigDecimal price;
	
	/**
	 * Constructor to decompose DAT file attributes
	 * @param data
	 */
	@SuppressWarnings("unchecked")
	public ItemSale(String data) {
		super();
		@SuppressWarnings("rawtypes")
		List alist = new ArrayList();
		StringTokenizer st = new StringTokenizer(data, Constants.CHARACTER_DELIMITER_ITENS_DATA);
		while (st.hasMoreTokens()) {
			alist.add(st.nextToken());
		}
		setId(new Long((String) alist.get(PositionEnum.ZERO.getPosition())));
		setQuantity(new Integer((String) alist.get(PositionEnum.ONE.getPosition())));
		setPrice(new BigDecimal((String) alist.get(PositionEnum.TWO.getPosition())));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
