package br.com.test.ilegra.model;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import br.com.test.ilegra.constants.Constants;
import br.com.test.ilegra.constants.PositionEnum;

public class Customer {

	private String cnpj;

	private String name;

	private String businessArea;

	@SuppressWarnings("unchecked")
	public Customer(String data) {
		super();
		@SuppressWarnings("rawtypes")
		List alist = new ArrayList();
		StringTokenizer st = new StringTokenizer(data, Constants.CHARACTER_DELIMITER);
		while (st.hasMoreTokens()) {
			alist.add(st.nextToken());
		}
		setCnpj((String)alist.get(PositionEnum.ONE.getPosition()));
		setName((String)alist.get(PositionEnum.TWO.getPosition()));
		setBusinessArea((String)alist.get(PositionEnum.TREE.getPosition()));
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBusinessArea() {
		return businessArea;
	}

	public void setBusinessArea(String businessArea) {
		this.businessArea = businessArea;
	}

}
