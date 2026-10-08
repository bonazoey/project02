package com.mycompany.backendapi.responseexclude.dto;

import lombok.Data;

@Data
public class ProductResponse {
	private int pid;
	private String pname;
	private int pprice;
	private String pcompany;
	private String pimageoname;
	private String pimagesname;
	private String pimagetype;
}
