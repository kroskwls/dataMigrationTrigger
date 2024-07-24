package com.migration.jwt.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseBody {
	private int status;
	private String error;
	private Object data;
}
